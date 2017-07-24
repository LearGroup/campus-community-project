package hql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bean.articleData;
import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;
public class hqlArticleSetTest {
	
	private static List<articleData> articleSet=null;
	public static void main(String[] args) {
		SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String sql_2="article.articleUpdateTime , article.isDelete , article.isPublished , article.id , article.articleAbstract , article.articleName , article.authorName , article.authorId , article.authorHeadPortraitUrl  , article.articlePictureUrl , article.likeCount , article.readCount , article.commentCount";
		String sql="select article.articleContent,"
				+ " article.articleUpdateTime ,"
				+ " article.isDelete ,"
				+ " article.isPublished , "
				+ "article.id , "
				+ " article.articleName , "
				+ "article.authorName ,"
				+ " article.authorId ,"
				+ " article.authorHeadPortraitUrl  , "
				+ "article.articlePictureUrl ,"
				+ " article.likeCount , "
				+ "article.readCount , "
				+ "article.commentCount ,"
				+ "collection.collectionName   "
				+ " from articleData article ,collectionTag collection where "
				+ "article.collectionTagId=collection.id and article.id='"+"ea435dfa-5676-4070-8e45-06e4253b324b"+"'";
		/*String sql="select collection from collectionTag collection";*/
		
		Query query=session.createQuery(sql);
		
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		System.out.println(json);
	}
}
