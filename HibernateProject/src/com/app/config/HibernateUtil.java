package com.app.config;

import java.util.HashMap;

import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import com.app.model.Student;

public class HibernateUtil {
	private static SessionFactory sf;
	private static StandardServiceRegistry registry;
	public static SessionFactory getSessionFactory()
	{
		try {
			if(sf==null)
			{
				// connection properties
				Map<String, Object> map=new HashMap<>();
				

				map.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
				map.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3306/sms");
				map.put(Environment.JAKARTA_JDBC_USER, "root");
				map.put(Environment.JAKARTA_JDBC_PASSWORD, "root");
				//hibernate propeties
				map.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
				map.put(Environment.HBM2DDL_AUTO, "update");
				map.put(Environment.SHOW_SQL,"true");
				// enable 2 nd level cache
				map.put(Environment.USE_SECOND_LEVEL_CACHE,"true");
				// provide EHChache class
				map.put(Environment.CACHE_REGION_FACTORY,"org.hibernate.cache.jcache.JCacheRegionFactory");
				map.put("hibernate.javax.cache.provider","org.ehcache.jsr107.EhcacheCachingProvider");
				
				// create object of StandardServiceRegistryBuilder
				registry=new StandardServiceRegistryBuilder().applySettings(map).build();
				// create object of MetaDataSources
				MetadataSources mds=new MetadataSources(registry);
				mds.addAnnotatedClass(Student.class);
				// create object of MetaData
				  Metadata md  =  mds.getMetadataBuilder().build();
				  // create object of SessionFactory
				  sf=md.getSessionFactoryBuilder().build();
			}
		}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		return sf;
	}

}
