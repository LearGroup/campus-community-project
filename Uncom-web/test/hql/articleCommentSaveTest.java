package hql;

import java.util.Date;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bean.articleData;
import bean.commentData;
import bean.userData;
import dao.hibernateStartPrepare;

public class articleCommentSaveTest {
	private static String articleId;
	private static String authorId;
	private static Integer commentLevel;
	private static String comment;
	private static String commentParentId=null;
	private static articleData ad;
	private static Integer commentCount;
	public static void main(String[] args) {
		commentData cd=new commentData();
		String sql_2="select ad from articleData ad where ad.id='ea435dfa-5676-4070-8e45-06e4253b324b'";
		articleId = "ea435dfa-5676-4070-8e45-06e4253b324b";
		authorId="46e0a594-a062-497f-8dd8-5d2bac3faf8a";
		commentLevel=1;
		comment="想法不错";
		
	
		Date date = new Date();
		String id = UUID.randomUUID().toString();
		cd.setId(id);
		cd.setArticleId(articleId);
		cd.setAuthorId(authorId);
		cd.setCommentLevel(commentLevel);
		cd.setContent(comment);
		cd.setCreateTime(date);
		cd.setIsDelete(0);
		cd.setLikes(0);
		cd.setNoLike(0);
		cd.setCommentParentId(commentParentId);
		cd.setUpdateTime(date);
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session sessions = sessionFactory.openSession();
		Transaction transaction = sessions.beginTransaction();
		Query query=sessions.createQuery(sql_2);
		ad = (articleData) query.uniqueResult();
		commentCount=ad.getCommentCount();
		commentCount+=1;
		ad.setCommentCount(commentCount);
		try {
			sessions.save(ad);
			sessions.save(cd);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			
			transaction.rollback();
		} finally {
			sessions.close();
		}
	
	}
}
