package com.pps.util;

import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import com.pps.bean.ZzUsers;


/**
 * po���������Ӧӳ���ļ���һ�£���Student.java��Student.hbm.xml
 */
public class HibernateUtil {
	
	private static Configuration hibernateConf;

	@SuppressWarnings("unused")
	private static Configuration getHibernateConf() {
		if (hibernateConf == null) {
			return new Configuration();
		}
		return hibernateConf;
	}

	@SuppressWarnings("unchecked")
	private static PersistentClass getPersistentClass(Class clazz) {
		synchronized (HibernateUtil.class) {
			PersistentClass pc = getHibernateConf().getClassMapping(
					clazz.getName());
			if (pc == null) {
				hibernateConf = getHibernateConf().addClass(clazz);
				pc = getHibernateConf().getClassMapping(clazz.getName());

			}
			return pc;
		}
	}

	/**
	 * ������������ȡʵ���Ӧ�ı���
	 * 
	 * @param clazz
	 *            ʵ����
	 * @return ����
	 */
	@SuppressWarnings("unchecked")
	public static String getTableName(Class clazz) {
		return getPersistentClass(clazz).getTable().getName();
	}

	/**
	 * ������������ȡʵ���Ӧ��������ֶ�����
	 * 
	 * @param clazz
	 *            ʵ����
	 * @return �����ֶ�����
	 */
	@SuppressWarnings("unchecked")
	public static String getPkColumnName(Class clazz) {
		return getPersistentClass(clazz).getTable().getPrimaryKey().getColumn(0).getName();
	}

	/**
	 * ����������ͨ��ʵ��������ԣ���ȡʵ�������Զ�Ӧ�ı��ֶ�����
	 * 
	 * @param clazz
	 *            ʵ����
	 * @param propertyName
	 *            ��������
	 * @return �ֶ�����
	 */
	@SuppressWarnings("unchecked")
	public static String getColumnName(Class clazz, String propertyName) {
		PersistentClass persistentClass = getPersistentClass(clazz);
		Property property = persistentClass.getProperty(propertyName);
		Iterator it = property.getColumnIterator();
		if (it.hasNext()) {
			Column column = (Column) it.next();
			return column.getName();
		}
		return null;
	}

	/**
	 * ���ԣ���ȡ����
	 */
	public static void main(String[] args) {
		System.out.println(getTableName(ZzUsers.class));
	}
}
