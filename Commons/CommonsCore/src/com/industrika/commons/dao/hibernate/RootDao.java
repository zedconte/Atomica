package com.industrika.commons.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class RootDao {
	
    @Autowired
    protected SessionFactory sessionFactory;


}
