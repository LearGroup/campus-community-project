package hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bean.articleData;
import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class appFrontGetTripAttractionTest {

	public static void main(String[] args) {
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		/*Integer  money = Integer.parseInt(request.getParameter("money"));*/
		Integer money=50;
		String hql = "select trip, td.url from tripAttractionData trip ,tripImgData td where trip.id=td.tripAttractionId and trip.minimumConsumption<="+money+"  and td.id=(select max(td.id) from td where trip.id=td.tripAttractionId )";
		Query query = session.createQuery(hql);
		JSONArray json = JSONArray.fromObject(query.list().size());
		session.close();
		System.out.println(json);

	}
}
