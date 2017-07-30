package hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bean.articleData;
import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class accontLoadTest {
	public static void main(String[] args) {
		 SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		 Session session=sessionFactory.openSession();
		 Transaction transaction=session.beginTransaction();
		 
		 String email="123456";
		 String hql="select user from userData user where user.email='"+email+"'";
		 Query query=session.createQuery(hql);
			
			JSONArray json = JSONArray.fromObject(query.list());
			System.out.println(json.toString());
		     if(json.toString().equals("[]")){
				System.out.println(json);
			}
		
			session.close();
	}

}
