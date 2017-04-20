package hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import bean.userData;
import dao.hibernateStartPrepare;

public class queryForParameter {
	
	
	public static void main(String[] args) {
		String sors="phone";
		String use="15247126123";
		getUser(sors, use);
	}
	
	public static userData getUser(String sors,String use){
		    userData User=null; 
		    SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			String sql = null;
			
		  sql="select user from userData user where user.phone=?";
		  try {
				Query query=session.createQuery(sql).setParameter(0, "15247126123");
				userData article_data= (userData) query.uniqueResult();
				System.out.println("awd");
				System.out.println(article_data);
				 transaction.commit();
			} catch (Exception e) {
				System.out.println(e);
				// TODO: handle exception
			}finally {
				session.close();
			}
		return User;
		}
}
