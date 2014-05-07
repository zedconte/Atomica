package com.industrika.inventory.commands;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.industrika.administration.dto.Tax;
import com.industrika.commons.utils.TextFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.dao.BranchDao;
import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.Warehouse;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dao.BuyOrderDao;
import com.industrika.inventory.dao.ItemDao;
import com.industrika.inventory.dao.ProviderDao;
import com.industrika.inventory.dto.BuyOrder;
import com.industrika.inventory.dto.DocumentRow;
import com.industrika.inventory.dto.Item;
import com.industrika.inventory.dto.Provider;
import com.industrika.inventory.dto.SimpleDocumentRow;
import com.industrika.inventory.i18n.InventoryMessages;

@Component("buyorderCommand")
public class BuyOrderCommand implements IndustrikaCommand {

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private BranchDao branchDao;

	@Autowired
	private ProviderDao providerDao;

	GenericDao<Integer, Warehouse> genericDaoWarehouse;

	@Autowired
	@Qualifier("warehouseDaoHibernate")
	public void setDao(GenericDao<Integer, Warehouse> dao) {
		this.genericDaoWarehouse = dao;
	}

	@Autowired
	private BuyOrderDao buyDao;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/inventory/buyorder.jsp");
		if (parameters.get("returnType") != null
				&& ((String[]) parameters.get("returnType"))[0]
						.equalsIgnoreCase("jsp")) {
			String action = parameters.get("action") != null ? ((String[]) parameters
					.get("action"))[0] : "";
			BuyOrder dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("add")) {
				try {
					Integer buyOrderID = buyDao.add(dto);
					dto.setIdDocument(buyOrderID);
					BuyOrder byOrd = new BuyOrder();
					byOrd = tranferValues(dto, byOrd);
					dto = byOrd;
					results.put("message",
							CommonsMessages.getMessage("sucess_persistence"));
				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")) {
				try {
					dto.setRowsAsJson(null);
					String[] order = { "idDocument", "date", "folio" };
					dto.setDate(null);
					Vector<BuyOrder> lista = new Vector<BuyOrder>(buyDao.find(
							dto, order));
					if (lista != null && lista.size() == 1) {
						BuyOrder firstOrder = lista.get(0);
						firstOrder = tranferValues(dto, firstOrder);
						List<DocumentRow> rows = firstOrder.getRows();
						List<SimpleDocumentRow> simpleRows = new ArrayList<SimpleDocumentRow>();
						for (DocumentRow actualRow : rows) {
							SimpleDocumentRow simpleRow = new SimpleDocumentRow();
							simpleRow.setItemBranchID(""
									+ actualRow.getBranch().getIdBranch());
							double taxes = 0D;
							if (actualRow.getItem().getTaxes() != null
									&& actualRow.getItem().getTaxes().size() > 0) {
								for (Tax tx : actualRow.getItem().getTaxes()) {
									if (tx.isPercentage() && actualRow.getItem().getCost() !=null) {
										taxes += actualRow.getItem().getCost()
												.doubleValue()
												* (tx.getTaxValue() / 100);
									} else {
										taxes += tx.getTaxValue();
									}
								}
							}
							simpleRow.setItemId(""
									+ actualRow.getItem().getIdItem() + "_"
									+ actualRow.getItem().getCost() + "_"
									+ taxes);
							simpleRow.setItemQuantity(""
									+ actualRow.getQuantity().intValue());
							simpleRow
									.setItemWarehouseID(""
											+ actualRow.getWarehouse()
													.getIdWarehouse());
							simpleRows.add(simpleRow);
						}

						SimpleDocumentRow[] arraySimpleRows = simpleRows
								.toArray(new SimpleDocumentRow[simpleRows
										.size()]);
						Gson gson = new GsonBuilder()
								.excludeFieldsWithoutExposeAnnotation()
								.create();
						String jsonRows = gson.toJson(arraySimpleRows);
						firstOrder.setRowsAsJson(jsonRows);
						dto = firstOrder;
					}

					if (lista != null && lista.size() > 0) {
						results.put("list", lista);
					} else {
						results.put("message", InventoryMessages
								.getMessage("buyorder.search.notfound"));
					}

				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")) {
				try {

					buyDao.delete(dto);
					BuyOrder buyOrder = new BuyOrder();
					buyOrder = tranferValues(dto, buyOrder);
					dto = buyOrder;
					results.put("message", InventoryMessages
							.getMessage("buyorder.remove.persintence"));
				} catch (DataIntegrityViolationException e) {
					results.put("error",
							"No puede ser eliminado pues existe información que depende de ésta");
				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}

			if (action != null && action.equalsIgnoreCase("update")) {
				try {
					buyDao.update(dto);
					results.put("message", InventoryMessages
							.getMessage("buyorder.update.persintence"));
				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}

			results.put("dto", dto);
		}
		return results;
	}

	private BuyOrder makeDtoForWeb(Map<String, String[]> parameters) {
		BuyOrder dto = new BuyOrder();

		if (parameters.get("rowsAsJson") != null) {
			try {
				if (((String[]) parameters.get("rowsAsJson")) != null) {
					String rows = ((String[]) parameters.get("rowsAsJson"))[0];
					dto.setRowsAsJson(rows);
					dto.setRows(getMyRowsUsingJsonString(rows));
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		if (parameters.get("folio") != null) {
			try {
				dto.setFolio(((String[]) parameters.get("folio"))[0]);
			} catch (Exception e) {
				dto.setFolio(null);
			}
		}

		if (parameters.get("idDocument") != null) {
			try {
				dto.setIdDocument(Integer.parseInt(((String[]) parameters
						.get("idDocument"))[0]));
			} catch (Exception e) {
				dto.setIdDocument(null);
			}
		}

		if (parameters.get("date") != null) {
			try {
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter
						.parse(((String[]) parameters.get("date"))[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				dto.setDate(calendar);
			} catch (Exception e) {
				dto.setDate(null);
			}
		}

		if (parameters.get("discount") != null) {
			try {
				dto.setDiscount((TextFormatter.getNumber(((String[]) parameters
						.get("discount"))[0])).doubleValue());
			} catch (Exception e) {
				dto.setDiscount(null);
			}
		}

		if (parameters.get("total") != null) {
			try {
				dto.setTotal((TextFormatter.getNumber(((String[]) parameters
						.get("total"))[0])).doubleValue());
			} catch (Exception e) {
				dto.setTotal(null);
			}
		}

		if (parameters.get("subtotal") != null) {
			try {
				dto.setSubtotal((TextFormatter.getNumber(((String[]) parameters
						.get("subtotal"))[0])).doubleValue());
			} catch (Exception e) {
				dto.setSubtotal(null);
			}
		}

		if (parameters.get("tax") != null) {
			try {
				dto.setTax((TextFormatter.getNumber(((String[]) parameters
						.get("tax"))[0])).doubleValue());
			} catch (Exception e) {
				dto.setTax(null);
			}
		}

		/* Fill the items in the providers */
		if (parameters.get("providersAccount") == null
				|| ((String[]) parameters.get("providersAccount"))[0].trim() == "") {
			try {
				dto.setProvidersAccount(getMyAvailableProviders());
			} catch (IndustrikaPersistenceException e) {
			} catch (IndustrikaObjectNotFoundException e) {
			}
		} else {
			dto.setProvidersAccount(((String[]) parameters
					.get("providersAccount"))[0].trim());
		}

		if (parameters.get("provider") != null) {
			String provider = ((String[]) parameters.get("provider"))[0];
			Provider prov = new Provider();
			prov.setIdPerson(Integer.parseInt(provider));
			List<Provider> providers;
			try {
				providers = (providerDao.find(prov, null));
				prov = providers.get(0);
				dto.setProvider(prov);
			} catch (IndustrikaPersistenceException e) {
				dto.setProvider(null);
			} catch (IndustrikaObjectNotFoundException e) {
				dto.setProvider(null);
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
			} catch (IndustrikaObjectNotFoundException e) {
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
			} catch (IndustrikaObjectNotFoundException e) {
			}
		} else {
			dto.setWarehouseJson(((String[]) parameters.get("warehouseJson"))[0]
					.trim());
		}

		/* Fill the items Max Numbers */
		if (parameters.get("productQuantityJson") == null
				|| ((String[]) parameters.get("productQuantityJson"))[0].trim() == "") {
			StringBuilder itemsMax = new StringBuilder("");
			for (int i = 0; i <= 50; i++) {
				itemsMax.append(i + ":" + i);
				if (i < 50) {
					itemsMax.append(";");
				}
			}
			dto.setProductQuantityJson(itemsMax.toString());
		} else {
			dto.setProductQuantityJson(((String[]) parameters
					.get("productQuantityJson"))[0].trim());
		}

		return dto;
	}

	private BuyOrder tranferValues(BuyOrder dto, BuyOrder tranfer) {
		tranfer.setBranchJson(dto.getBranchJson());
		tranfer.setItemsJson(dto.getItemsJson());
		tranfer.setProductQuantityJson(dto.getProductQuantityJson());
		tranfer.setProvidersAccount(dto.getProvidersAccount());
		tranfer.setWarehouseJson(dto.getWarehouseJson());
		return tranfer;
	}

	public String getMyAvailableWareHouses()
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		String[] order = { "name" };
		Warehouse whouse = new Warehouse();
		List<Warehouse> warehouses = genericDaoWarehouse.find(whouse, order);
		StringBuilder wareHouseSt = new StringBuilder("");
		int i = 1;
		for (Warehouse warehouseVal : warehouses) {
			wareHouseSt.append(warehouseVal.getIdWarehouse() + ":"
					+ warehouseVal.getName());
			i++;
			if (i <= warehouses.size()) {
				wareHouseSt.append(";");
			}
		}
		return wareHouseSt.toString();
	}

	private String getMyAvailableItems() throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		String[] order = { "name" };
		Item it = new Item();
		List<Item> items = itemDao.find(it, order);
		StringBuilder itemsSt = new StringBuilder("");
		int i = 1;
		for (Item itemVal : items) {
			String price = TextFormatter.getCurrencyValue(itemVal.getCost());
			double taxes = 0D;
			if (itemVal.getTaxes() != null && itemVal.getTaxes().size() > 0) {
				for (Tax tx : itemVal.getTaxes()) {
					if (tx.isPercentage() && itemVal.getCost() != null) {
						taxes += itemVal.getCost().doubleValue()
								* (tx.getTaxValue() / 100);
					} else {
						taxes += tx.getTaxValue();
					}
				}
			}
			itemsSt.append(itemVal.getIdItem() + "_" + itemVal.getCost() + "_"
					+ taxes + ":" + itemVal.getName() + " - " + price);
			i++;
			if (i <= items.size()) {
				itemsSt.append(";");
			}
		}

		return itemsSt.toString();
	}

	private String getMyAvailableBranches()
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		String[] order = { "businessName" };
		Branch it = new Branch();
		List<Branch> branches = branchDao.find(it, order);
		StringBuilder branchSt = new StringBuilder("");
		int i = 1;
		for (Branch branchVal : branches) {
			branchSt.append(branchVal.getIdBranch() + ":" + branchVal.getName());
			i++;
			if (i <= branches.size()) {
				branchSt.append(";");
			}
		}

		return branchSt.toString();
	}

	private String getMyAvailableProviders()
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		String json = "";
		String[] order = { "businessName" };
		Provider prov = new Provider();
		List<Provider> providers = (providerDao.find(prov, order));
		if (providers != null && providers.size() >= 1) {
			for (Provider provi : providers) {
				provi.setBusinessName(provi.getBusinessName());
			}
			/* Convert the providers into JSON */
			Provider[] providerArray = providers.toArray(new Provider[providers
					.size()]);
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			json = gson.toJson(providerArray);

		}

		return json;
	}

	private List<DocumentRow> getMyRowsUsingJsonString(String rows)
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		List<DocumentRow> documentsRows = null;
		if (rows != null && !rows.isEmpty()) {
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			List<SimpleDocumentRow> myRows = Arrays.asList(gson.fromJson(rows,
					SimpleDocumentRow[].class));

			documentsRows = new ArrayList<DocumentRow>();
			for (SimpleDocumentRow row : myRows) {
				String iditem = row.getItemId().split("_")[0];
				String quantity = row.getItemQuantity();
				/* Search for the Item and set */
				DocumentRow docRow = new DocumentRow();

				/* Search and set the ITEM */
				Item it = new Item();
				it.setIdItem(Integer.parseInt(iditem));
				List<Item> items = itemDao.find(it, null);
				docRow.setItem(items.get(0));

				/* Amount and quantity */
				docRow.setQuantity(Double.parseDouble(quantity));
				docRow.setAmount(docRow.getQuantity()
						* (docRow.getItem().getCost() != null ? docRow.getItem().getCost() : 0));

				/* Search and set the BRANCH */
				Branch branch = new Branch();
				String idBranch = row.getItemBranchID();
				branch.setIdBranch(Integer.parseInt(idBranch));
				List<Branch> branches = branchDao.find(branch, null);
				docRow.setBranch(branches.get(0));

				/* Search and set the WAREHOUSE */
				Warehouse whouse = new Warehouse();
				String idWarehouse = row.getItemWarehouseID();
				whouse.setIdWarehouse(Integer.parseInt(idWarehouse));
				List<Warehouse> warehouses = genericDaoWarehouse.find(whouse,
						null);
				docRow.setWarehouse(warehouses.get(0));
				// docRow.setTaxes(taxes);
				documentsRows.add(docRow);

			}
		}
		return documentsRows;
	}
}
