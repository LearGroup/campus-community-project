package hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class appFrontGetTripImgTest {
	public static void main(String[] args) {
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String hql = "select trip from tripImgData trip ";
		Query query = session.createQuery(hql);
		JSONArray json = JSONArray.fromObject(query.list().get(0));
		session.close();
		System.out.println(json);
	}
}
