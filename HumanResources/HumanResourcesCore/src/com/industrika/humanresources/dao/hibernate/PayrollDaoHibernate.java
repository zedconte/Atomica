package com.industrika.humanresources.dao.hibernate;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.administration.dao.DeductionDao;
import com.industrika.administration.dto.Deduction;
import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dao.AbsenceDao;
import com.industrika.humanresources.dao.EmployeeDao;
import com.industrika.humanresources.dao.PayrollDao;
import com.industrika.humanresources.dto.Absence;
import com.industrika.humanresources.dto.Payroll;
import com.industrika.humanresources.dto.Employee;
import com.industrika.humanresources.dto.PayrollDetail;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.PayrollValidator;

@Repository("payrollDao")
public class PayrollDaoHibernate extends RootDao implements PayrollDao {
	
	@Autowired
	private PayrollValidator validator;
	@Autowired
	private EmployeeDao daoEmployee;
	@Autowired
	private DeductionDao daoDeduction;
	@Autowired
	private AbsenceDao daoAbsence;
	
	private static final String TITLE = HRMessages.getMessage("payroll.Title");

	private List<PayrollDetail> calculate(Payroll payroll) {
		List<PayrollDetail> detail = null;
		String[] employeeOrder = {"lastName","middleName","firstName"};
		try{
			List<Employee> employees = daoEmployee.find(new Employee(), employeeOrder);
			if (employees != null && employees.size() > 0){
				detail = new Vector<PayrollDetail>();
				for (Employee employee : employees){
					int days = payroll.getDays() != null ? payroll.getDays().intValue() : 0;
					double salary = (employee.getSalary() != null ? employee.getSalary() : 0);
					double subtotal= salary * days;
					double discount=0;
					double deduction=0;
					// Now we gonna search for absences
					try{
						List<Absence> absences = daoAbsence.getByPayrol(payroll, employee);
						if (absences != null && absences.size() > 0){
							for (Absence absence : absences){
								if (absence.getApplyDiscount() != null && absence.getApplyDiscount().intValue()==1){
									discount += salary;
								}
							}
						}
					}catch(Exception ex){
						ex.getMessage();
					}
					// Now we gonna apply the deductions
					try{
						List<Deduction> deductions = daoDeduction.find(new Deduction(), null);
						if (deductions != null && deductions.size() > 0){
							for (Deduction deduc : deductions){
								deduction += salary * (deduc.getValue() != null ? (deduc.getValue()/100) : 0) * days; 
							}
						}
					}catch(Exception ex){
						ex.getMessage();
					}
					PayrollDetail det = new PayrollDetail();
					det.setDeductions(deduction);
					det.setDiscount(discount);
					det.setEmployee(employee);
					det.setSalary(salary);
					det.setTotal(subtotal-discount-deduction);
					detail.add(det);
				}
			}
		}catch(Exception ex){
			ex.getMessage();
		}
		return detail;
	}
	
	@Override
	@Transactional
	public Integer add(Payroll dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(dto);
		dto.setDetail(calculate(dto));
		double subtotal=0;
		double total=0;
		double deductions=0;
		double discounts=0;
		if (dto.getDetail()!=null && dto.getDetail().size()>0){
			for (PayrollDetail detail : dto.getDetail()){
				deductions += detail.getDeductions() != null ? detail.getDeductions() : 0;
				discounts += detail.getDiscount() != null ? detail.getDiscount() : 0;
				subtotal += (detail.getTotal() != null ? detail.getTotal() : 0);
				total += (detail.getTotal() != null ? detail.getTotal() : 0) + (detail.getDeductions() != null ? detail.getDeductions() : 0);
			}
		}
		dto.setDecutions(deductions);
		dto.setDiscount(discounts);
		dto.setSubtotal(subtotal);
		dto.setTotal(total);
		Integer id = null;
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(dto);
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_add")+TITLE,ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Payroll dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		dto.setDetail(calculate(dto));
		double subtotal=0;
		double total=0;
		double deductions=0;
		double discounts=0;
		if (dto.getDetail()!=null && dto.getDetail().size()>0){
			for (PayrollDetail detail : dto.getDetail()){
				deductions += detail.getDeductions() != null ? detail.getDeductions() : 0;
				discounts += detail.getDiscount() != null ? detail.getDiscount() : 0;
				subtotal += (detail.getTotal() != null ? detail.getTotal() : 0);
				total += (detail.getTotal() != null ? detail.getTotal() : 0) + (detail.getDeductions() != null ? detail.getDeductions() : 0);
			}
		}
		dto.setDecutions(deductions);
		dto.setDiscount(discounts);
		dto.setSubtotal(subtotal);
		dto.setTotal(total);
		try{
			if (dto.getIdPayroll() == null || dto.getIdPayroll().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				sessionFactory.getCurrentSession().saveOrUpdate(dto);
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_update")+TITLE,ex);
		}
	}

	@Override
	@Transactional
	public void remove(Payroll dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdPayroll() != null && dto.getIdPayroll().intValue() > 0){
			remove(dto.getIdPayroll());
		} else {
			Payroll temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idPayroll) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idPayroll != null && idPayroll.intValue() > 0){
			Payroll temp = (Payroll) sessionFactory.getCurrentSession().get(Payroll.class, idPayroll);
			try{				
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		} else {
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
	}

	@Override
	@Transactional
	public Payroll get(Payroll dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdPayroll() != null && dto.getIdPayroll().intValue() > 0){
			return get(dto.getIdPayroll());
		} else {
			List<Payroll> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Payroll get(Integer idPayroll) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Payroll dto = null;
		try{
			dto = (Payroll) sessionFactory.getCurrentSession().get(Payroll.class, idPayroll);
			if (dto == null){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE,ex);
		}		
		return dto;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Payroll> find(Payroll dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payroll.class);
		try{
			if (dto != null){
				if (dto.getIdPayroll() != null && dto.getIdPayroll().intValue() > 0){
					criteria.add(Restrictions.eq("idPayroll", dto.getIdPayroll().intValue()));
				} else {
					if (dto.getBegin() != null){
						Calendar begin = Calendar.getInstance();
						begin.set(dto.getBegin().get(Calendar.YEAR), dto.getBegin().get(Calendar.MONTH), dto.getBegin().get(Calendar.DAY_OF_MONTH));
						begin.set(Calendar.HOUR, 0);
						begin.set(Calendar.MINUTE, 0);
						begin.set(Calendar.SECOND, 0);
						begin.set(Calendar.MILLISECOND, 0);
						Calendar end = Calendar.getInstance();
						end.set(dto.getBegin().get(Calendar.YEAR), dto.getBegin().get(Calendar.MONTH), dto.getBegin().get(Calendar.DAY_OF_MONTH));
						end.set(Calendar.HOUR, 23);
						end.set(Calendar.MINUTE, 59);
						end.set(Calendar.SECOND, 59);
						end.set(Calendar.MILLISECOND, 999);
						criteria.add(Restrictions.between("begin", begin, end));
					}
					if (dto.getEnd() != null){
						Calendar begin = Calendar.getInstance();
						begin.set(dto.getEnd().get(Calendar.YEAR), dto.getEnd().get(Calendar.MONTH), dto.getEnd().get(Calendar.DAY_OF_MONTH));
						begin.set(Calendar.HOUR, 0);
						begin.set(Calendar.MINUTE, 0);
						begin.set(Calendar.SECOND, 0);
						begin.set(Calendar.MILLISECOND, 0);
						Calendar end = Calendar.getInstance();
						end.set(dto.getEnd().get(Calendar.YEAR), dto.getEnd().get(Calendar.MONTH), dto.getEnd().get(Calendar.DAY_OF_MONTH));
						end.set(Calendar.HOUR, 23);
						end.set(Calendar.MINUTE, 59);
						end.set(Calendar.SECOND, 59);
						end.set(Calendar.MILLISECOND, 999);
						criteria.add(Restrictions.between("end", begin, end));
					}
				}
			}
			if (orderFields != null && orderFields.length > 0){
				boolean addOrder = true;
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						if (orderFields[a].indexOf("detail") > -1 && addOrder){
							criteria.createAlias("detail", "detail");
							addOrder = false;
						}
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Payroll> results = Collections.checkedList(criteria.list(),Payroll.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}
}
