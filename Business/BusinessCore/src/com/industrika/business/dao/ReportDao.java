package com.industrika.business.dao;

import java.util.List;

public interface ReportDao<E> {

	List<E> getGenericReport(E dto,  String[] sortFields);
	
	List<E> getByFilterReport(E dto,  String[] sortFields);
	
}
