package com.industrika.maintenance.dao.hibernate;

import static org.mockito.Mockito.*;

import java.util.HashMap;
 









import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.SQLFunctionRegistry;
import org.hibernate.engine.SessionFactoryImplementor;
import org.testng.annotations.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.industrika.maintenance.dto.ResourceType;
 
@Test
public abstract class HibernateBaseTest extends TestCase {
 
		SessionFactory sf;
		SessionFactoryImplementor sfi;
		@Mock
		protected Session s;
		@Mock
		HibernateTemplate t;
		@Mock
		protected Criteria criteria;
		 
		@BeforeMethod(alwaysRun=true)
		public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sf = mock(SessionFactory.class, withSettings().extraInterfaces(SessionFactoryImplementor.class));
		sfi = (SessionFactoryImplementor) sf;
		when(sf.openSession()).thenReturn(s);
		when(sf.getCurrentSession()).thenReturn(s);
		when(sfi.getSqlFunctionRegistry()).thenReturn(new SQLFunctionRegistry(new MySQL5Dialect(), new HashMap<String, SQLFunction>()));
		criteria = mock(Criteria.class);
		when(s.createCriteria(ResourceType.class)).thenReturn(criteria);
	}
}