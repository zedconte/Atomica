package com.industrika.business.dto;

public class Amount{
	private Double totalBranch=0.0;
	private Double totalItem=0.0;
	private Double totalWarehouse=0.0;
	private boolean hasBranchVals=false;
	private boolean hasItemVals=false;
	private boolean hasWarehouseVals=false;
	
	public Double getTotalBranch() {
		return totalBranch;
	}
	public void setTotalBranch(Double totalBranch) {
		this.totalBranch = totalBranch;
	}
	public Double getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}
	public Double getTotalWarehouse() {
		return totalWarehouse;
	}
	public void setTotalWarehouse(Double totalWarehouse) {
		this.totalWarehouse = totalWarehouse;
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
}