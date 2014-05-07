package com.industrika.shipping.commands;

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
import com.industrika.shipping.dao.ShippingRouteDao;
import com.industrika.shipping.dao.ShippingScheduleDao;
import com.industrika.shipping.dto.ShippingRoute;
import com.industrika.shipping.dto.ShippingSchedule;
import com.industrika.shipping.i18n.ShippingMessages;

@Component("shippingsheduleCommand")
public class ShippingScheduleCommand implements IndustrikaCommand {
	@Autowired
	@Qualifier("shippingRouteDao")
	private ShippingRouteDao shippingDao;
	
	
	@Autowired
	@Qualifier("shippingScheduleDao")
	private ShippingScheduleDao shippingScheduleDao;
	

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private BranchDao branchDao;

	@Autowired
	@Qualifier("shippingRouteDao")
	private ShippingRouteDao routeDao;

	GenericDao<Integer, Warehouse> genericDaoWarehouse;

	@Autowired
	@Qualifier("warehouseDaoHibernate")
	public void setDao(GenericDao<Integer, Warehouse> dao) {
		this.genericDaoWarehouse = dao;
	}

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/shipping/shippingschedule.jsp");
		if (parameters.get("returnType") != null && ((String[]) parameters.get("returnType"))[0].equalsIgnoreCase("jsp")) {
			String action = parameters.get("action") != null ? ((String[]) parameters.get("action"))[0] : "resultados";
					
			ShippingSchedule dto = makeDtoForWeb(parameters);

			if (action != null && action.equalsIgnoreCase("add")) {
				try {
					ShippingSchedule sSchedule = shippingScheduleDao.save(dto);
					dto.setIdShippingSchedule(sSchedule.getIdShippingSchedule());
					ShippingSchedule schedule = new ShippingSchedule();
					schedule = tranferValues(dto, schedule);
					dto = schedule;
					results.put("message",CommonsMessages.getMessage("sucess_persistence"));
				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("search")) {
				try {
					System.out.println("searching....");
					dto.setRowsAsJson(null);
					String[] order = { "idShippingSchedule", "date"};
					Vector<ShippingSchedule> lista = new Vector<ShippingSchedule>(shippingScheduleDao.find(dto, order));
					if (lista != null && lista.size() == 1) {
						ShippingSchedule firstSchedule = lista.get(0);
						
						firstSchedule = tranferValues(dto, firstSchedule);
						
						List<DocumentRow> rows = firstSchedule.getItemsRows();
						
						List<SimpleDocumentRow> simpleRows = new ArrayList<SimpleDocumentRow>();
						
						for (DocumentRow actualRow : rows) {
							SimpleDocumentRow simpleRow = new SimpleDocumentRow();
							simpleRow.setItemBranchID(""+ actualRow.getBranch().getIdBranch());
							double taxes = 0D;
							if (actualRow.getItem().getTaxes() != null && actualRow.getItem().getTaxes().size() > 0) {
								for (Tax tx : actualRow.getItem().getTaxes()) {
									if (tx.isPercentage() && actualRow.getItem().getPrice()!=null) {
										taxes += actualRow.getItem().getPrice().doubleValue()* (tx.getTaxValue() / 100);
									} else {
										taxes += tx.getTaxValue();
									}
								}
							}
							simpleRow.setItemId(""+ actualRow.getItem().getIdItem() + "_"+ actualRow.getItem().getPrice() + "_"+ taxes);
							simpleRow.setItemQuantity(""+ actualRow.getQuantity().intValue());
							simpleRow.setItemWarehouseID(""+ actualRow.getWarehouse().getIdWarehouse());
							simpleRows.add(simpleRow);
						}

						SimpleDocumentRow[] arraySimpleRows = simpleRows.toArray(new SimpleDocumentRow[simpleRows.size()]);
						Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
						String jsonRows = gson.toJson(arraySimpleRows);
						firstSchedule.setRowsAsJson(jsonRows);
						dto = firstSchedule;
					}

					if (lista != null && lista.size() > 0) {
						results.put("list", lista);
					} else {
						results.put("message",ShippingMessages.getMessage("schedule.search.notfound"));
					}

				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")) {
				try {

					shippingScheduleDao.remove(dto);
					ShippingSchedule schedule = new ShippingSchedule();
					schedule = tranferValues(dto, schedule);
					dto = schedule;
					results.put("message", ShippingMessages.getMessage("schedule.remove.persintence"));
				} catch (DataIntegrityViolationException e) {
					results.put("error",
							"No puede ser eliminado pues existe información que depende de ésta");
				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}

			if (action != null && action.equalsIgnoreCase("update")) {
				try {
					shippingScheduleDao.update(dto);
					results.put("message", ShippingMessages.getMessage("schedule.update.persintence"));
				} catch (Exception ex) {
					results.put("error", ex.getMessage());
				}
			}
			
			
			results.put("dto", dto);

		}

		return results;
	}

	private ShippingSchedule makeDtoForWeb(Map<String, String[]> parameters) {
		ShippingSchedule dto = new ShippingSchedule();
		if (parameters.get("date") != null) {
			try {
				DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
				Date date = formatter
						.parse(((String[]) parameters.get("date"))[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				dto.setDate(calendar);
			} catch (Exception e) {
				dto.setDate(null);
			}
		}

		if (parameters.get("route") != null) {
			String route = ((String[]) parameters.get("route"))[0];
			ShippingRoute routeObj = new ShippingRoute();
			try {
				routeObj.setIdRoute(Integer.parseInt(route));
				List<ShippingRoute> routes;
				routes = (shippingDao.find(routeObj, null));
				routeObj = routes.get(0);
				dto.setRoute(routeObj);
			} catch (IndustrikaPersistenceException e) {
				dto.setRoute(null);
			} catch (Exception e) {
				dto.setRoute(null);
			}

		}

		if (parameters.get("rowsAsJson") != null) {
			try {
				if (((String[]) parameters.get("rowsAsJson")) != null) {
					String rows = ((String[]) parameters.get("rowsAsJson"))[0];
					dto.setRowsAsJson(rows);
					dto.setItemsRows(getMyRowsUsingJsonString(rows));
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		if (parameters.get("idShippingSchedule") != null) {
			try {
				dto.setIdShippingSchedule(Integer
						.parseInt(((String[]) parameters
								.get("idShippingSchedule"))[0]));
			} catch (Exception e) {
				dto.setIdShippingSchedule(null);
			}
		}

		/* Fill the routes */
		if (parameters.get("routesAccount") == null
				|| ((String[]) parameters.get("routesAccount"))[0].trim() == "") {
			try {
				dto.setRoutesAccount(getAvailableRoutes());

			} catch (IndustrikaPersistenceException e) {
			} catch (IndustrikaObjectNotFoundException e) {
			}
		} else {
			dto.setRoutesAccount(((String[]) parameters.get("routesAccount"))[0]
					.trim());
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

	private ShippingSchedule tranferValues(ShippingSchedule dto, ShippingSchedule tranfer) {
		tranfer.setBranchJson(dto.getBranchJson());
		tranfer.setItemsJson(dto.getItemsJson());
		tranfer.setProductQuantityJson(dto.getProductQuantityJson());
		tranfer.setRoutesAccount((dto.getRoutesAccount()));
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
			String price = TextFormatter.getCurrencyValue(itemVal.getPrice());
			double taxes = 0D;
			if (itemVal.getTaxes() != null && itemVal.getTaxes().size() > 0) {
				for (Tax tx : itemVal.getTaxes()) {
					if (tx.isPercentage() && itemVal.getPrice()!=null) {
						taxes += itemVal.getPrice().doubleValue()
								* (tx.getTaxValue() / 100);
					} else {
						taxes += tx.getTaxValue();
					}
				}
			}
			itemsSt.append(itemVal.getIdItem() + "_" + itemVal.getPrice() + "_"
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
						* docRow.getItem().getPrice());

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

	private String getAvailableRoutes()
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		String json = "";
		ShippingRoute route = new ShippingRoute();
		String[] order = { "idRoute", "name" };
		List<ShippingRoute> routes = (routeDao.find(route, order));
		if (routes != null && routes.size() >= 1) {
			ShippingRoute[] routesArray = routes
					.toArray(new ShippingRoute[routes.size()]);
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
			json = gson.toJson(routesArray);
		}
		return json;
	}
}
