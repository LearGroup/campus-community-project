package hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class getArticleCommentSetForOneLevel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String articleId="ea435dfa-5676-4070-8e45-06e4253b324b";
		String sql="select cd from commentData cd where articleId='"+articleId+"'"+"and commentParentId=null order by createTime desc,commentChildCount desc";
		Query query=session.createQuery(sql);
		
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		System.out.println(json);
	}

}
