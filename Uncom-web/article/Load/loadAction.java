package Load;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.articleData;
import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class loadAction extends ActionSupport implements ModelDriven<articleData> {

	private String Articleid;
	private JSONArray json;
	private String url;
	private articleData data= new  articleData();
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session = null;
	private List<articleData> articleSet=null;
	private int countTag;
	public  void CloseWirter(PrintWriter out){
			if(null!=out){
				out.flush();
				out.close();
			}
			
	  }
	 public  PrintWriter getPrintWriter() throws IOException{
		  response=ServletActionContext.getResponse();
		  response.setContentType("text/text");
	      response.setCharacterEncoding("UTF-8");
	      
		  PrintWriter out = response.getWriter();
		  return out;
	  }
	
	
	public String getArticleSet() throws IOException{
		SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		String sql="select "
				+ "article.articleUpdateTime , "
				+ "article.isDelete , "
				+ "article.isPublished ,"
				+ " article.id ,"
				+ " article.articleAbstract ,"
				+ " article.articleName ,"
				+ " article.authorName ,"
				+ " article.authorId ,"
				+ " article.authorHeadPortraitUrl  , "
				+ "article.articlePictureUrl ,"
				+ " article.likeCount , "
				+ "article.readCount , "
				+ "article.commentCount ,"
				+ "collection.collectionName    from articleData article,collectionTag collection where article.collectionTagId=collection.id";
		
		Query query=session.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
	    CloseWirter(out);
		
		
		
		return null;
		
	}
	
	
	String getArticleId(String url){
		
		 	
		String temp,result;
		temp=url.split("/")[3];
		result=temp.split("\\.")[0];
		return result;
		
	}

	
	public String getArticle()throws IOException{
		request=ServletActionContext.getRequest();
		Articleid=(String)request.getSession().getAttribute("articleId");
		System.out.println("articleid:"+Articleid);
		SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
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
				+ "article.collectionTagId=collection.id and article.id='"+Articleid+"'";
		/*System.out.println(sql);*/
        Query query=session.createQuery(sql);	
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		/*System.out.println(json);*/
	    CloseWirter(out);
		
		return null;
	}
	
	public String Page() throws IOException{
		/*System.out.println("bengin Page paser");*/
		articleData articleData=null;
		request=ServletActionContext.getRequest();
		url=request.getRequestURI();
		Articleid=getArticleId(url);
		/**/
		request=ServletActionContext.getRequest();
		session=request.getSession();
		session.setAttribute("articleId", Articleid);
		System.out.println("page:"+Articleid);
		return "success";
	}

	@Override
	public articleData getModel() {
		// TODO Auto-generated method stub
		return data;
	}
}
