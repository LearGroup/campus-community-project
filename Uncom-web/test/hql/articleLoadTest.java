package hql;

import org.hibernate.query.Query;

import bean.articleData;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class articleLoadTest {
	
	
	public static void main(String[] args) {
		Test();
	}
	
	
	public static void Test(){
		 SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		 Session session=sessionFactory.openSession();
		 Transaction transaction=session.beginTransaction();
		 
		 String id="99d64054-3f29-4f41-8f05-3835aa068c25";
		 String hql="select article from articleData article where article.id='99d64054-3f29-4f41-8f05-3835aa068c25'";
		 Query query=session.createQuery(hql);
			
			JSONArray json = JSONArray.fromObject(query.list());
			session.close();
			System.out.println(json);
		 
	}
}
