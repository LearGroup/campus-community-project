package hql;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bean.articleData;
import dao.hibernateStartPrepare;

public class aticleSaveTest {
	
	public static void main(String[] args) {
		SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		Session session =sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		articleData ad=new articleData();
		String id=UUID.randomUUID().toString();
		ad.setId(id);
		String article_content="“release ”通常负责“短期的发布前准备工作”、“小bug的修复工作”、“版本号等元信息的准备工作”。与此同时，“develop”分支又可以承接下一个新功能的开发工作了。 团队成员从主分支(master)获得的都是处于可发布状态的代码，而从开发分支(develop)应该总能够获得最新开发进展的代码。";
		String author="劳谦";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=sdf.format(date);
		int isDelete=0;
		int isPublished=1;
		String article_abstract;
		if(article_content.length()<100){
			article_abstract=article_content;
		}else{
			article_abstract=article_content.substring(0,100)+"...";
		}
		String article_name="Git分支管理策略 ";
		String author_id="46e0a594-a062-497f-8dd8-5d2bac3faf8a";
		String article_url="img/back-img2.jpg";
		int like_count=0;
		int read_count=0;
		int commment_count=0;
		int no_like_count=0;
		ad.setArticleAbstract(article_abstract);
		ad.setArticleContent(article_content);
		ad.setArticleCreateTime(date);
		ad.setArticleName(article_name);
		ad.setArticlePictureUrl(article_url);
		ad.setArticleUpdateTime(date);
		ad.setAuthorId(author_id);
		ad.setAuthorName(author);
		ad.setId(id);
		ad.setIsDelete(isDelete);
		ad.setIsPublished(isPublished);
		ad.setLikeCount(like_count);
		ad.setNoLikeCount(no_like_count);
		ad.setReadCount(read_count);
		ad.setCommentCount(commment_count);
		ad.setCollectionTagId(1);
		System.out.println(article_abstract);
		System.out.println(createTime);
		
	    try {
		    session.save(ad);
		    transaction.commit();
	    } catch (Exception e) {
		   // TODO: handle exception
		   System.out.println(e);
		   transaction.rollback();
	    }finally {
	     	session.close();
        }
		 
	}
}
