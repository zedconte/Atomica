package com.industrika.sales.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.industrika.administration.dto.Tax;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.dao.BranchDao;
import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.Warehouse;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.utils.TextFormatter;
import com.industrika.inventory.dao.ItemDao;
import com.industrika.inventory.dto.DocumentRow;
import com.industrika.inventory.dto.Item;
import com.industrika.inventory.dto.SimpleDocumentRow;
import com.industrika.sales.dao.CustomerDao;
import com.industrika.sales.dao.QuotationDao;
import com.industrika.sales.dto.Customer;
import com.industrika.sales.dto.Quotation;
import com.industrika.sales.i18n.SalesMessages;

@Component("quotationCommand")
public class QuotationCommand implements IndustrikaCommand {
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private BranchDao branchDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	GenericDao<Integer, Warehouse> genericDaoWarehouse;
	

	@Autowired
	@Qualifier("warehouseDaoHibernate")
	public void setDao(GenericDao<Integer, Warehouse> dao) {
		this.genericDaoWarehouse = dao;
	}

	@Autowired
	private QuotationDao dao;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/sales/quotation.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Quotation dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					Integer buyOrderID = dao.add(dto);
					dto.setIdDocument(buyOrderID);
					Quotation byOrd= new Quotation();
					byOrd=tranferValues(dto,byOrd);
					dto=byOrd;
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					dto.setRowsAsJson(null);
					String[] order={"idDocument","date","folio"};
					Vector<Quotation> lista = new Vector<Quotation>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						Quotation firstOrder = lista.get(0);
						firstOrder=tranferValues(dto,firstOrder);
						List<DocumentRow> rows=firstOrder.getRows();
						List<SimpleDocumentRow> simpleRows = new ArrayList<SimpleDocumentRow>();
						for(DocumentRow actualRow: rows){
							SimpleDocumentRow simpleRow= new SimpleDocumentRow();
							simpleRow.setItemBranchID(""+actualRow.getBranch().getIdBranch());
							double taxes=0D;
							if (actualRow.getItem().getTaxes() != null && actualRow.getItem().getTaxes().size()>0){
								for (Tax tx : actualRow.getItem().getTaxes()){
									if (tx.isPercentage() && actualRow.getItem().getPrice() != null && tx.getTaxValue() !=null){
										taxes += actualRow.getItem().getPrice().doubleValue()*(tx.getTaxValue()/100);
									} else {
										taxes += tx.getTaxValue();
									}
								}
							}
							simpleRow.setItemId(""+actualRow.getItem().getIdItem()+"_"+actualRow.getItem().getPrice()+"_"+taxes);
							simpleRow.setItemQuantity(""+actualRow.getQuantity().intValue());
							simpleRow.setItemWarehouseID(""+actualRow.getWarehouse().getIdWarehouse());
							simpleRows.add(simpleRow);
						}
						
						SimpleDocumentRow[] arraySimpleRows=simpleRows.toArray(new SimpleDocumentRow[simpleRows.size()]);
						Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
						String jsonRows = gson.toJson(arraySimpleRows);
						firstOrder.setRowsAsJson(jsonRows);
						dto=firstOrder;
					}
					
					if (lista != null && lista.size() >0){
						results.put("list", lista);
					}
					else{
						results.put("message", SalesMessages.getMessage("quotation.search.notfound"));
					}
						
					
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
						
						dao.delete(dto);
						Quotation buyOrder= new Quotation();
						buyOrder=tranferValues(dto,buyOrder);
						dto=buyOrder;
						results.put("message", SalesMessages.getMessage("quotation.remove.persintence"));
					}catch(DataIntegrityViolationException e){
						results.put("error","No puede ser eliminado pues existe información que depende de ésta");
					}catch(Exception ex){
						results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					results.put("message", SalesMessages.getMessage("quotation.update.persintence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			results.put("dto", dto);
		}
		return results;
	}

	private Quotation makeDtoForWeb(Map<String, String[]> parameters){
		Quotation dto = new Quotation();
		
		if (parameters.get("rowsAsJson") != null){
			try{
				if(((String[])parameters.get("rowsAsJson"))!=null){
					String rows=((String[])parameters.get("rowsAsJson"))[0];
					dto.setRowsAsJson(rows);
					if(rows!=null && !rows.isEmpty()){
						Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
						List<SimpleDocumentRow> myRows = Arrays.asList(gson.fromJson(rows, SimpleDocumentRow[].class));
						
						List<DocumentRow> documentsRows= new ArrayList<DocumentRow>();
						for(SimpleDocumentRow row: myRows){
							String iditem=row.getItemId().split("_")[0];
							String quantity=row.getItemQuantity();
							/*Search for the Item and set*/
							DocumentRow docRow= new DocumentRow();
							
							/*Search and set the ITEM*/
							Item it= new Item();
							it.setIdItem(Integer.parseInt(iditem));
							List<Item> items=itemDao.find(it, null);
							docRow.setItem(items.get(0));
							
							/*Amount and quantity*/
							docRow.setQuantity(Double.parseDouble(quantity));
							docRow.setAmount(docRow.getQuantity()*(docRow.getItem().getPrice() != null ? docRow.getItem().getPrice() : 0));
							
							
							/*Search and set the BRANCH*/
							Branch branch= new Branch();
							String idBranch=row.getItemBranchID();
							branch.setIdBranch(Integer.parseInt(idBranch));
							List<Branch> branches=branchDao.find(branch, null);
							docRow.setBranch(branches.get(0));
							
							/*Search and set the WAREHOUSE*/
							Warehouse whouse= new Warehouse();
							String idWarehouse=row.getItemWarehouseID();
							whouse.setIdWarehouse(Integer.parseInt(idWarehouse));
							List<Warehouse> warehouses=genericDaoWarehouse.find(whouse, null);
							docRow.setWarehouse(warehouses.get(0));
							//docRow.setTaxes(taxes);
							documentsRows.add(docRow);
							
						}
						dto.setRows(documentsRows);
					}
				}
			}catch(Exception ex){
				throw new RuntimeException(ex);
			}
		}
		
		if (parameters.get("folio") != null){
			try{
			dto.setFolio(((String[])parameters.get("folio"))[0]);
			}
			catch(Exception e){
				dto.setFolio(null);
			}
		}
		
		if (parameters.get("idDocument") != null){
			try{
			dto.setIdDocument(Integer.parseInt(((String[])parameters.get("idDocument"))[0]));
			}
			catch(Exception e){
				dto.setIdDocument(null);
			}
		}
		
		if (parameters.get("date") != null){
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter.parse(((String[])parameters.get("date"))[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				dto.setDate(calendar);
			}
			catch(Exception e){
				dto.setDate(null);
			}
		}
		
		if (parameters.get("discount") != null){
			try{
				dto.setDiscount((TextFormatter.getNumber(((String[])parameters.get("discount"))[0])).doubleValue());
			}
			catch(Exception e){
				dto.setDiscount(null);
			}
		}
		
		if (parameters.get("total") != null){
			try{
				dto.setTotal((TextFormatter.getNumber(((String[])parameters.get("total"))[0])).doubleValue());
			}
			catch(Exception e){
				dto.setTotal(null);
			}
		}

		if (parameters.get("subtotal") != null){
			try{
				dto.setSubtotal((TextFormatter.getNumber(((String[])parameters.get("subtotal"))[0])).doubleValue());
			}
			catch(Exception e){
				dto.setSubtotal(null);
			}
		}

		if (parameters.get("tax") != null){
			try{
				dto.setTax((TextFormatter.getNumber(((String[])parameters.get("tax"))[0])).doubleValue());
			}
			catch(Exception e){
				dto.setTax(null);
			}
		}

		/*Fill the items in the customers*/
		if (parameters.get("customersAccount") == null || ((String[])parameters.get("customersAccount"))[0].trim() ==""){
			try {
				String[] order={"businessName"};
				Customer prov= new Customer();
				List<Customer> customers = (customerDao.find(prov, order));
				if (customers != null && customers.size() >= 1){
					/*Convert the customers into JSON*/
					Customer[] customerArray=customers.toArray(new Customer[customers.size()]);
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					String json = gson.toJson(customerArray);
					dto.setCustomersAccount(json);
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		else{
			dto.setCustomersAccount(((String[])parameters.get("customersAccount"))[0].trim());
		}
		
		if (parameters.get("customer") != null){
			String customer=((String[])parameters.get("customer"))[0];
			Customer prov= new Customer();
			prov.setIdPerson(Integer.parseInt(customer));
			List<Customer> customers;
			try {
				customers = (customerDao.find(prov, null));
				prov=customers.get(0);
				dto.setCustomer(prov);
			} catch (IndustrikaPersistenceException e) {
				dto.setCustomer(null);
			} catch (IndustrikaObjectNotFoundException e) {
				dto.setCustomer(null);
			}
			
		}
		
		/*Fill the items in the DB*/
		if (parameters.get("itemsJson") == null || ((String[])parameters.get("itemsJson"))[0].trim() ==""){
			try {				
				String[] order={"name"};
				Item it= new Item();
				List<Item> items=itemDao.find(it, order);
				StringBuilder itemsSt= new StringBuilder("");
				int i=1;
				for(Item itemVal: items){
					String price=TextFormatter.getCurrencyValue(itemVal.getPrice());
					double taxes=0D;
					if (itemVal.getTaxes() != null && itemVal.getTaxes().size()>0){
						for (Tax tx : itemVal.getTaxes()){
							if (tx.isPercentage()){
								taxes += itemVal.getPrice().doubleValue()*(tx.getTaxValue()/100);
							} else {
								taxes += tx.getTaxValue();
							}
						}
					}			
					itemsSt.append(itemVal.getIdItem()+"_"+itemVal.getPrice()+"_"+taxes+":"+itemVal.getName() +" - "+price);
					i++;
					if(i<=items.size()){
						itemsSt.append(";");
					}
				}
				dto.setItemsJson(itemsSt.toString());
			} catch (IndustrikaPersistenceException e) {
			} catch (IndustrikaObjectNotFoundException e) {
			}
		}
		else{
			dto.setItemsJson(((String[])parameters.get("itemsJson"))[0].trim());
		}
		
		/*Fill the items in the Branches*/
		if (parameters.get("branchJson") == null || ((String[])parameters.get("branchJson"))[0].trim() ==""){
			try {			
				String[] order={"businessName"};
				Branch it= new Branch();
				List<Branch> branches=branchDao.find(it, order);
				StringBuilder branchSt= new StringBuilder("");
				int i=1;
				for(Branch branchVal: branches){
					branchSt.append(branchVal.getIdBranch()+":"+branchVal.getName());			
					i++;
					if(i<=branches.size()){
						branchSt.append(";");
					}
				}
				dto.setBranchJson(branchSt.toString());
			} catch (IndustrikaPersistenceException e) {				
			} catch (IndustrikaObjectNotFoundException e) {
			}
		}
		else{
			dto.setBranchJson(((String[])parameters.get("branchJson"))[0].trim());
		}
		
		/*Fill the items in the WareHouses*/
		if (parameters.get("warehouseJson") == null || ((String[])parameters.get("warehouseJson"))[0].trim() ==""){
			try {				
				String[] order={"name"};
				Warehouse whouse= new Warehouse();
				List<Warehouse> warehouses=genericDaoWarehouse.find(whouse, order);
				StringBuilder wareHouseSt= new StringBuilder("");
				int i=1;
				for(Warehouse warehouseVal: warehouses){
					wareHouseSt.append(warehouseVal.getIdWarehouse()+":"+warehouseVal.getName());			
					i++;
					if(i<=warehouses.size()){
						wareHouseSt.append(";");
					}
				}
				dto.setWarehouseJson(wareHouseSt.toString());
			} catch (IndustrikaPersistenceException e) {
			} catch (IndustrikaObjectNotFoundException e) {
			}
		}
		else{
			dto.setWarehouseJson(((String[])parameters.get("warehouseJson"))[0].trim());
		}
		
		/*Fill the items Max Numbers*/
		if (parameters.get("productQuantityJson") == null || ((String[])parameters.get("productQuantityJson"))[0].trim() ==""){	
				StringBuilder itemsMax= new StringBuilder("");
				for(int i=0; i<=50; i++){
					itemsMax.append(i+":"+i);
					if(i<50){
						itemsMax.append(";");
					}
				}				
				dto.setProductQuantityJson(itemsMax.toString());
		}else{
			dto.setProductQuantityJson(((String[])parameters.get("productQuantityJson"))[0].trim());
		}
		
		
		return dto;
	}
	
	private Quotation tranferValues(Quotation dto, Quotation tranfer){
		tranfer.setBranchJson(dto.getBranchJson());
		tranfer.setItemsJson(dto.getItemsJson());
		tranfer.setProductQuantityJson(dto.getProductQuantityJson());
		tranfer.setCustomersAccount(dto.getCustomersAccount());
		tranfer.setWarehouseJson(dto.getWarehouseJson());
		return tranfer;
	}
}
