package com.industrika.business.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.industrika.business.dto.Amount;
import com.industrika.business.dto.PurchaseReport;
import com.industrika.business.dto.Report;
import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.inventory.dao.PurchaseDao;
import com.industrika.inventory.dto.Purchase;

@Repository("purchasereportDao")
public class PurchaseReportDao extends RootDao implements ReportDao<PurchaseReport> {
	
	@Autowired
	@Qualifier("purchaseDao")
	private PurchaseDao purchaseDao;
	
	@Override
	public List<PurchaseReport> getGenericReport(PurchaseReport dto,  String[] sortFields) {
		List<PurchaseReport> reportPurchasesList= new ArrayList<PurchaseReport>();
		try {
			Purchase sale = new Purchase();
			List<Purchase> resultPurchases= purchaseDao.find(sale, sortFields);
			for(Purchase purchase: resultPurchases){
				PurchaseReport purchaseReportRow = new PurchaseReport();
				purchaseReportRow.setMyPurchase(purchase);
				reportPurchasesList.add(purchaseReportRow);
			}
			
		} catch (IndustrikaPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndustrikaObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reportPurchasesList;
	}

	@Override
	public List<PurchaseReport> getByFilterReport(PurchaseReport dto,  String[] sortFields) {
		List<PurchaseReport> reportPurchasesList= new ArrayList<PurchaseReport>();
		try {
			Purchase sale = new Purchase();
			List<Purchase> resultPurchases= purchaseDao.find(sale, sortFields);
			
			for(Purchase purchase: resultPurchases){
				PurchaseReport reportRow = new PurchaseReport();
				reportRow.setMyPurchase(purchase);
				Amount amt	= Report.getAmounts(purchase.getRows(), dto);
				if(amt.isHasBranchVals() || amt.isHasItemVals() || amt.isHasWarehouseVals()){
					reportRow.setBranchTotal(amt.getTotalBranch());
					reportRow.setHasBranchVals(amt.isHasBranchVals());
					
					reportRow.setItemTotal(amt.getTotalItem());
					reportRow.setHasItemVals(amt.isHasItemVals());
					
					reportRow.setWarehouseTotal(amt.getTotalWarehouse());
					reportRow.setHasWarehouseVals(amt.isHasWarehouseVals());
					reportPurchasesList.add(reportRow);
				}
				
				amt=null;
			}
			
		} catch (IndustrikaPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndustrikaObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reportPurchasesList;
	}
	
	
}
