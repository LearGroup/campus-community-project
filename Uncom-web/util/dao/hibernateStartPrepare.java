package dao;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class hibernateStartPrepare {
	private static final SessionFactory SESSION_FACTOR;
	private static final hibernateStartPrepare HIBERNATE_START_PREPARE=new hibernateStartPrepare();
	
	static{
		StandardServiceRegistryBuilder serviceRegistryBuilder=new StandardServiceRegistryBuilder().configure();
		StandardServiceRegistry standardServiceRegistry=serviceRegistryBuilder.build();
	    SESSION_FACTOR=new MetadataSources().buildMetadata(standardServiceRegistry).buildSessionFactory();
	}
	
	public static  hibernateStartPrepare getInstance(){
		return HIBERNATE_START_PREPARE;
	}
	
	
	public static SessionFactory getSessionFactory(){
		return SESSION_FACTOR;
	}
}