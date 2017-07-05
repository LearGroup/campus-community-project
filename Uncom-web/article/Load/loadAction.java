package Load;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.articleData;
import dao.hibernateStartPrepare;

public class loadAction extends ActionSupport implements ModelDriven<articleData> {

	
	
	private String url;
	private articleData data= new  articleData();
	private HttpServletRequest request;
	
	
	private void setAttr(articleData ad,HttpServletRequest request){
		request.setAttribute("authorName",ad.getAuthorName());
		request.setAttribute("articleContent", ad.getArticleContent());
		request.setAttribute("articleName", ad.getArticleName());
		request.setAttribute("articleReadCount", ad.getReadCount());
		request.setAttribute("artucleLikeCount", ad.getLikeCount());
		request.setAttribute("articleComments", ad.getCommentCount());
		
	}

	String getArticleId(String url){
		
		
		String temp,result;
		temp=url.split("/")[3];
		result=temp.split("\\.")[0];
		return result;
		
	}
	
	
	public String Page(){
		String Articleid,sql=null;
		articleData articleData=null;
		request=ServletActionContext.getRequest();
		url=request.getRequestURI();
		Articleid=getArticleId(url);
		sql="select article from articleData article where id='"+Articleid+"'";
		System.out.println(sql);
		SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try {
			data=(bean.articleData) session.createQuery(sql).uniqueResult();
			System.out.println(data);
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}finally {
			session.close();
		}
		
		setAttr(data, request);
		System.out.println(request.getRequestURI());
		return "article_Page";
	}

	@Override
	public articleData getModel() {
		// TODO Auto-generated method stub
		return data;
	}
}
