package com.industrika.humanresources.dao.hibernate;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dao.AbsenceDao;
import com.industrika.humanresources.dto.Absence;
import com.industrika.humanresources.dto.Employee;
import com.industrika.humanresources.dto.Payroll;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.AbsenceValidator;

@Repository("absenceDao")
public class AbsenceDaoHibernate extends RootDao implements AbsenceDao {
	
	@Autowired
	private AbsenceValidator validator;
	
	private static final String TITLE = HRMessages.getMessage("absence.Title");
	
	@Override
	@Transactional
	public Integer add(Absence dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(dto);
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
	public void update(Absence dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdAbsence() == null || dto.getIdAbsence().intValue() <= 0){
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
	public void remove(Absence dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdAbsence() != null && dto.getIdAbsence().intValue() > 0){
			remove(dto.getIdAbsence());
		} else {
			Absence temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idAbsence) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idAbsence != null && idAbsence.intValue() > 0){
			Absence temp = (Absence) sessionFactory.getCurrentSession().get(Absence.class, idAbsence);
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
	public Absence get(Absence dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdAbsence() != null && dto.getIdAbsence().intValue() > 0){
			return get(dto.getIdAbsence());
		} else {
			List<Absence> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Absence get(Integer idAbsence) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Absence dto = null;
		try{
			dto = (Absence) sessionFactory.getCurrentSession().get(Absence.class, idAbsence);
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
	public List<Absence> find(Absence dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Absence.class);
		try{
			if (dto != null){
				if (dto.getIdAbsence() != null && dto.getIdAbsence().intValue() > 0){
					criteria.add(Restrictions.eq("idAbsence", dto.getIdAbsence().intValue()));
				} else {
					if (dto.getDate() != null){
						Calendar begin = Calendar.getInstance();
						begin.set(dto.getDate().get(Calendar.YEAR), dto.getDate().get(Calendar.MONTH), dto.getDate().get(Calendar.DAY_OF_MONTH));
						begin.set(Calendar.HOUR, 0);
						begin.set(Calendar.MINUTE, 0);
						begin.set(Calendar.SECOND, 0);
						begin.set(Calendar.MILLISECOND, 0);
						Calendar end = Calendar.getInstance();
						end.set(dto.getDate().get(Calendar.YEAR), dto.getDate().get(Calendar.MONTH), dto.getDate().get(Calendar.DAY_OF_MONTH));
						end.set(Calendar.HOUR, 23);
						end.set(Calendar.MINUTE, 59);
						end.set(Calendar.SECOND, 59);
						end.set(Calendar.MILLISECOND, 999);
						criteria.add(Restrictions.between("date", begin, end));
					}
					if (dto.getEmployee() != null && dto.getEmployee().getIdPerson() != null && dto.getEmployee().getIdPerson().intValue() > 0){
						criteria.add(Restrictions.eq("employee.idPerson", dto.getEmployee().getIdPerson()));
					}
					if (dto.getApplyDiscount() != null && dto.getApplyDiscount().intValue() > 0){
						criteria.add(Restrictions.eq("applyDiscount", dto.getApplyDiscount()));
					}
					if (dto.getJustified() != null && dto.getJustified().intValue() > 0){
						criteria.add(Restrictions.eq("justified", dto.getJustified()));
					}
					if (dto.getReason() != null && !dto.getReason().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("reason", "%" + dto.getReason()+"%"));
					}
				}
			}
			if (orderFields != null && orderFields.length > 0){
				boolean addAlias=true;
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						if (orderFields[a].indexOf("employee") > -1 && addAlias){
							criteria.createAlias("employee", "employee");
							addAlias = false;
						}
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Absence> results = Collections.checkedList(criteria.list(),Absence.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Absence> getByPayrol(Payroll payroll, Employee employee)
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Absence.class);
		try{
			if (payroll != null && payroll.getBegin() != null && payroll.getEnd() != null){
				Calendar payrollbegin = Calendar.getInstance();
				payrollbegin.set(payroll.getBegin().get(Calendar.YEAR), payroll.getBegin().get(Calendar.MONTH), payroll.getBegin().get(Calendar.DAY_OF_MONTH));
				payrollbegin.set(Calendar.HOUR, 0);
				payrollbegin.set(Calendar.MINUTE, 0);
				payrollbegin.set(Calendar.SECOND, 0);
				payrollbegin.set(Calendar.MILLISECOND, 0);
				Calendar payrollend = Calendar.getInstance();
				payrollend.set(payroll.getEnd().get(Calendar.YEAR), payroll.getEnd().get(Calendar.MONTH), payroll.getEnd().get(Calendar.DAY_OF_MONTH));
				payrollend.set(Calendar.HOUR, 23);
				payrollend.set(Calendar.MINUTE, 59);
				payrollend.set(Calendar.SECOND, 59);
				payrollend.set(Calendar.MILLISECOND, 999);
				criteria.add(Restrictions.between("date", payrollbegin, payrollend));
				if (employee != null && employee.getIdPerson() != null && employee.getIdPerson().intValue() > 0){
					criteria.add(Restrictions.eq("employee.idPerson", employee.getIdPerson()));
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Absence> results = Collections.checkedList(criteria.list(),Absence.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
