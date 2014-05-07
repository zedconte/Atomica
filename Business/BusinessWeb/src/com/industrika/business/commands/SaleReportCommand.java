package com.industrika.business.commands;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.industrika.business.dao.ReportDao;
import com.industrika.business.dto.SaleReport;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.dao.BranchDao;
import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.Warehouse;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dao.ItemDao;
import com.industrika.inventory.dto.Item;
import static com.industrika.business.dto.Report.ReportType;

@Component("salesreportCommand")
public class SaleReportCommand implements IndustrikaCommand {
	
	@Autowired
	@Qualifier("salesreportDao")
	ReportDao<SaleReport> salesReportDao;
	
	@Autowired
	private ItemDao itemDao;

	@Autowired
	private BranchDao branchDao;

	
	GenericDao<Integer, Warehouse> genericDaoWarehouse;
	@Autowired
	@Qualifier("warehouseDaoHibernate")
	public void setDao(GenericDao<Integer, Warehouse> dao) {
		this.genericDaoWarehouse = dao;
	}


	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/business/salesreport.jsp");
		if (parameters.get("returnType") != null && ((String[]) parameters.get("returnType"))[0].equalsIgnoreCase("jsp")) {
			String action = parameters.get("action") != null ? ((String[]) parameters.get("action"))[0] : "resultados";
					
			SaleReport dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("search")) {
				Vector<SaleReport> lista;
					if(dto.getType().equals(ReportType.GENERIC)){
						try {
							lista	= new Vector<SaleReport>(salesReportDao.getGenericReport(dto, null));
							if (lista != null && lista.size() == 1) {
								
							}
							if (lista != null && lista.size() > 0) {
								results.put("list", lista);
							} else {
								results.put("message",CommonsMessages.getMessage("error_persistence_objectnotfound"));
							}

						} catch (Exception ex) {
							results.put("error", ex.getMessage());
						}
					}
					
					if(dto.getType().equals(ReportType.BYFILTER)){
						try {
							lista	= new Vector<SaleReport>(salesReportDao.getByFilterReport(dto, null));
							if (lista != null && lista.size() > 0) {
								results.put("list", lista);
							} else {
								results.put("message",CommonsMessages.getMessage("error_persistence_objectnotfound"));
							}

						} catch (Exception ex) {
							results.put("error", ex.getMessage());
						}
					}
			}
			else{
				dto.SetDefault();
			}
			results.put("dto", dto);
		}

		return results;
	}
	
	
	private SaleReport makeDtoForWeb(Map<String, String[]> parameters) {
		SaleReport dto = new SaleReport();
		dto.setType(ReportType.GENERIC);
		if (parameters.get("item") != null) {
			try{
				String itemID = ((String[]) parameters.get("item"))[0];
				
				if(Integer.parseInt(itemID)==-1){
					throw new Exception();
				}
				
				Item it = new Item();
				it.setIdItem(Integer.parseInt(itemID));
				List<Item> items = itemDao.find(it, null);
				dto.setItem(items.get(0));
				dto.setType(ReportType.BYFILTER);
			}
			catch(Exception e){
				dto.setItem(null);
			}
		}
		
		if (parameters.get("branch") != null) {
			try{
				String branchID = ((String[]) parameters.get("branch"))[0];
				
				if(Integer.parseInt(branchID)==-1){
					throw new Exception();
				}
				
				Branch branch = new Branch();
				branch.setIdBranch(Integer.parseInt(branchID));
				List<Branch> branches = branchDao.find(branch, null);
				dto.setBranch(branches.get(0));
				dto.setType(ReportType.BYFILTER);
			}
			catch(Exception e){
				dto.setBranch(null);
			}
		}
		
		if (parameters.get("warehouse") != null) {
			try{
				String warehouseID = ((String[]) parameters.get("warehouse"))[0];
				
				if(Integer.parseInt(warehouseID)==-1){
					throw new Exception();
				}
				
				Warehouse whouse = new Warehouse();
				whouse.setIdWarehouse(Integer.parseInt(warehouseID));
				List<Warehouse> warehouses = genericDaoWarehouse.find(whouse,null);
				dto.setWarehouse(warehouses.get(0));
				dto.setType(ReportType.BYFILTER);
			}catch(Exception e){
				dto.setWarehouse(null);
			}
		}

		/* Fill the items in the DB */
		if (parameters.get("itemsJson") == null
				|| ((String[]) parameters.get("itemsJson"))[0].trim() == "") {
			try {
				dto.setItemsJson(getMyAvailableItems());
			} catch (IndustrikaPersistenceException e) {
			} catch (IndustrikaObjectNotFoundException e) {
			}
		} else {
			dto.setItemsJson(((String[]) parameters.get("itemsJson"))[0].trim());
		}

		/* Fill the items in the Branches */
		if (parameters.get("branchJson") == null
				|| ((String[]) parameters.get("branchJson"))[0].trim() == "") {
			try {
				dto.setBranchJson(getMyAvailableBranches());
			} catch (IndustrikaPersistenceException e) {
				// TODO Auto-generated catch block

			} catch (IndustrikaObjectNotFoundException e) {
				// TODO Auto-generated catch block

			}
		} else {
			dto.setBranchJson(((String[]) parameters.get("branchJson"))[0]
					.trim());
		}

		/* Fill the items in the WareHouses */
		if (parameters.get("warehouseJson") == null
				|| ((String[]) parameters.get("warehouseJson"))[0].trim() == "") {
			try {
				dto.setWarehouseJson(getMyAvailableWareHouses());
			} catch (IndustrikaPersistenceException e) {
				// TODO Auto-generated catch block

			} catch (IndustrikaObjectNotFoundException e) {
				// TODO Auto-generated catch block

			}
		} else {
			dto.setWarehouseJson(((String[]) parameters.get("warehouseJson"))[0]
					.trim());
		}

		return dto;
	}

	private SaleReport tranferValues(SaleReport dto, SaleReport tranfer) {
		tranfer.setBranchJson(dto.getBranchJson());
		tranfer.setItemsJson(dto.getItemsJson());
		tranfer.setWarehouseJson(dto.getWarehouseJson());
		return tranfer;
	}

	//idWarehouse
	//warehouseName
	public String getMyAvailableWareHouses()
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		String[] order = { "name" };
		String json="";
		Warehouse whouse = new Warehouse();
		List<Warehouse> warehouses = genericDaoWarehouse.find(whouse, order);
		warehouses.add(SaleReport.getWarehouseDefault());
		if (warehouses != null && warehouses.size() >= 1) {
			Warehouse[] warehouseArray = warehouses.toArray(new Warehouse[warehouses.size()]);
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			json = gson.toJson(warehouseArray);
		}
		
		return json;
	}

	//idItem
	//idItemName
	public String getMyAvailableItems() throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		String[] order = { "name" };
		String json="";
		Item it = new Item();
		List<Item> items = itemDao.find(it, order);
		items.add(SaleReport.getItemDefault());
		if (items != null && items.size() >= 1) {
			Item[] itemArray = items.toArray(new Item[items.size()]);
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			json = gson.toJson(itemArray);
		}
		return json;
	}

	//idIBranch
	//branchName
	public String getMyAvailableBranches()
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		String[] order = { "businessName" };
		Branch it = new Branch();
		String json="";
		List<Branch> branches = branchDao.find(it, order);
		branches.add(SaleReport.getBranchDefault());
		if (branches != null && branches.size() >= 1) {
			Branch[] branchArray = branches.toArray(new Branch[branches.size()]);
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			json = gson.toJson(branchArray);
		}

		return json;
	}
}
