package com.industrika.business.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.industrika.business.dto.Amount;
import com.industrika.business.dto.Report;
import com.industrika.business.dto.SaleReport;
import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.sales.dao.SaleDao;
import com.industrika.sales.dto.Sale;

@Repository("salesreportDao")
public class SalesReportDao extends RootDao implements ReportDao<SaleReport> {
	
	@Autowired
	@Qualifier("saleDao")
	private SaleDao salesDao;
	
	@Override
	public List<SaleReport> getGenericReport(SaleReport dto,  String[] sortFields) {
		Sale sale = new Sale();
		List<SaleReport> reportSales= new ArrayList<SaleReport>();
		try {
			List<Sale> resultSales= salesDao.find(sale, sortFields);
			for(Sale mySale: resultSales){
				SaleReport reportRow = new SaleReport();
				reportRow.setMySale(mySale);
				reportSales.add(reportRow);
			}
			
		} catch (IndustrikaPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndustrikaObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reportSales;
	}

	@Override
	public List<SaleReport> getByFilterReport(SaleReport dto,  String[] sortFields) {
		Sale sale = new Sale();
		List<SaleReport> reportSales= new ArrayList<SaleReport>();
		try {
			List<Sale> resultSales	= salesDao.find(sale, sortFields);
			for(Sale mySale: resultSales){
				SaleReport reportRow = new SaleReport();
				reportRow.setMySale(mySale);
				Amount amt	= Report.getAmounts(mySale.getRows(), dto);
				if(amt.isHasBranchVals() || amt.isHasItemVals() || amt.isHasWarehouseVals()){
					reportRow.setBranchTotal(amt.getTotalBranch());
					reportRow.setHasBranchVals(amt.isHasBranchVals());
					
					reportRow.setItemTotal(amt.getTotalItem());
					reportRow.setHasItemVals(amt.isHasItemVals());
					
					reportRow.setWarehouseTotal(amt.getTotalWarehouse());
					reportRow.setHasWarehouseVals(amt.isHasWarehouseVals());
					reportSales.add(reportRow);
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
		
		return reportSales;
	}
	
}
