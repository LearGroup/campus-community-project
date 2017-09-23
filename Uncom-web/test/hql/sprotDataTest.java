package hql;

import java.io.PrintWriter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class sprotDataTest {
	public static void main(String[] args) {
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String sql="select fd from foodData fd";
		Query query = session.createQuery(sql);
		
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		System.out.println(json);
	
		
	}
}
