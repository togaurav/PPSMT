package com.ppstream.mt.test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Spring TestContext Framework，不依赖于测试框架
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;
	private DataSource dataSource;
	
	@Required
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Required
	@Resource
	@Override
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		super.setDataSource(dataSource);  
	}
	
	protected DataSource getDataSource(){
		return dataSource;
	}
	
	protected HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	protected SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}