package com.industrika.business.dto;
import java.util.List;
import javax.persistence.Transient;
import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.Warehouse;
import com.industrika.inventory.dto.DocumentRow;
import com.industrika.inventory.dto.Item;

public class Report {
	
	public enum ReportType { GENERIC, BYFILTER};
	
	private Double total;
	
	private Double branchTotal;
	
	private Branch branch;
	
	private Double warehouseTotal;
	
	private Warehouse warehouse;
	
	private Double itemTotal;
	
	private Item item;
	
	private ReportType type;
	
	@Transient
	// Sucursal
	private String branchJson;

	@Transient
	// Almacen
	private String warehouseJson;

	// Items
	@Transient
	private String itemsJson;
	
	private boolean hasBranchVals=false;
	private boolean hasItemVals=false;
	private boolean hasWarehouseVals=false;

	public ReportType getType() {
		return type;
	}

	public void setType(ReportType type) {
		this.type = type;
	}

	public String getBranchJson() {
		return branchJson;
	}

	public void setBranchJson(String branchJson) {
		this.branchJson = branchJson;
	}

	public String getWarehouseJson() {
		return warehouseJson;
	}

	public void setWarehouseJson(String warehouseJson) {
		this.warehouseJson = warehouseJson;
	}

	public String getItemsJson() {
		return itemsJson;
	}

	public void setItemsJson(String itemsJson) {
		this.itemsJson = itemsJson;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public void SetDefault(){
		this.setBranch(getBranchDefault());
		this.setItem(getItemDefault());
		this.setWarehouse(getWarehouseDefault());
	}
	
	public static Warehouse getWarehouseDefault(){
		Warehouse warehouse = new Warehouse();
		warehouse.setIdWarehouse(-1);
		warehouse.setName("No aplicar filtro [Almacén]");
		return warehouse;
	}
	
	public static Item getItemDefault(){
		Item item = new Item();
		item.setIdItem(-1);
		item.setName("No aplicar filtro [Producto]");
		return item;
	}
	
	public static Branch getBranchDefault(){
		Branch branch	= new Branch();
		branch.setIdBranch(-1);
		branch.setName("No aplicar filtro [Sucursal]");
		return branch;
	}

	public Double getBranchTotal() {
		return branchTotal;
	}

	public void setBranchTotal(Double branchTotal) {
		this.branchTotal = branchTotal;
	}

	public Double getWarehouseTotal() {
		return warehouseTotal;
	}

	public void setWarehouseTotal(Double warehouseTotal) {
		this.warehouseTotal = warehouseTotal;
	}

	public Double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	public boolean isHasBranchVals() {
		return hasBranchVals;
	}
	public void setHasBranchVals(boolean hasBranchVals) {
		this.hasBranchVals = hasBranchVals;
	}
	public boolean isHasItemVals() {
		return hasItemVals;
	}
	public void setHasItemVals(boolean hasItemVals) {
		this.hasItemVals = hasItemVals;
	}
	public boolean isHasWarehouseVals() {
		return hasWarehouseVals;
	}
	public void setHasWarehouseVals(boolean hasWarehouseVals) {
		this.hasWarehouseVals = hasWarehouseVals;
	}
	
	public static Amount getAmounts(List<DocumentRow> rows, Report dto){
		Amount amt = new Amount();
		for(DocumentRow row: rows){
			try
			{
				Double quantity= row.getQuantity();
				Double price = row.getItem().getCost();
				
				if(dto.getBranch()!=null){
					if(row.getBranch().getIdBranch().equals(dto.getBranch().getIdBranch())){
						Double total=amt.getTotalBranch()+(quantity*price);
						amt.setTotalBranch(total);
						amt.setHasBranchVals(true);
					}
				}
				
				if(dto.getItem()!=null){
					if(row.getItem().getIdItem().equals(dto.getItem().getIdItem())){
						Double total=amt.getTotalItem()+(quantity*price);
						amt.setTotalItem(total);
						amt.setHasItemVals(true);
					}
				}
				
				if(dto.getWarehouse()!=null){
					if(row.getWarehouse().getIdWarehouse().equals(dto.getWarehouse().getIdWarehouse())){
						Double total=amt.getTotalWarehouse()+(quantity*price);
						amt.setTotalWarehouse(total);
						amt.setHasWarehouseVals(true);
					}
				}
			}catch(Exception e){
				//Keep iterating
			}
		}
		return amt;
	}
	
}