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
import bean.userData;
import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class loadAction extends ActionSupport implements ModelDriven<articleData> {

	private String Articleid;
	private JSONArray json;
	private String url;
	private articleData data = new articleData();
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session = null;
	private List<articleData> articleSet = null;
	private int countTag;
	private Integer commentSum;
	private userData ud;
	private String userId;

	public void CloseWirter(PrintWriter out) {
		if (null != out) {
			out.flush();
			out.close();
		}

	}

	public PrintWriter getPrintWriter() throws IOException {
		response = ServletActionContext.getResponse();
		response.setContentType("text/text");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		return out;
	}

	public String getArticleCommentSetForOneLevel() throws IOException {
		request = ServletActionContext.getRequest();
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String articleId = (String) request.getSession().getAttribute("articleId");
		String sql = "select cd from commentData cd where articleId='" + articleId + "'"
				+ "and commentParentId=null order by createTime desc,commentChildCount desc";
		commentSum = Integer.parseInt(request.getParameter("sum"));
		Query query = session.createQuery(sql);
		query.setFirstResult(commentSum * 5);
		query.setMaxResults(10);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}

	public String getMyWorkPage() throws IOException {
		session = ServletActionContext.getRequest().getSession();
		ud = (userData) session.getAttribute("user");
		userId = ud.getId();
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session sessions = sessionFactory.openSession();
		Transaction transaction = sessions.beginTransaction();

		String sql = "select " + "article.articleUpdateTime , " + "article.isDelete , " + "article.isPublished ,"
				+ " article.id ," + " article.articleAbstract ," + " article.articleName ," + " article.authorName ,"
				+ " article.authorId ," + " article.authorHeadPortraitUrl  , " + "article.articlePictureUrl ,"
				+ " article.likeCount , " + "article.readCount , " + "article.commentCount ,"
				+ "collection.collectionName , article.collectionTagId   from articleData article,collectionTag collection where article.collectionTagId=collection.id and article.collectionTagId=4 and article.authorId='"+userId+"'"+" order by article.commentCount desc , article.articleCreateTime desc";

		Query query = sessions.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		sessions.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;

	}

	public String getArticlePage() throws IOException {
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String sql = "select " + "article.articleUpdateTime , " + "article.isDelete , " + "article.isPublished ,"
				+ " article.id ," + " article.articleAbstract ," + " article.articleName ," + " article.authorName ,"
				+ " article.authorId ," + " article.authorHeadPortraitUrl  , " + "article.articlePictureUrl ,"
				+ " article.likeCount , " + "article.readCount , " + "article.commentCount ,"
				+ "collection.collectionName , article.collectionTagId   from articleData article,collectionTag collection where article.collectionTagId=collection.id and article.collectionTagId!=4 order by article.commentCount desc , article.articleCreateTime desc";

		Query query = session.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}

	public String getSetPage() throws IOException {
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String sql = "select " + "article.articleUpdateTime , " + "article.isDelete , " + "article.isPublished ,"
				+ " article.id ," + " article.articleAbstract ," + " article.articleName ," + " article.authorName ,"
				+ " article.authorId ," + " article.authorHeadPortraitUrl  , " + "article.articlePictureUrl ,"
				+ " article.likeCount , " + "article.readCount , " + "article.commentCount ,"
				+ "collection.collectionName , article.collectionTagId   from articleData article,collectionTag collection where article.collectionTagId=collection.id order by article.commentCount desc , article.articleCreateTime desc";

		Query query = session.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);

		return null;

	}

	String getArticleId(String url) {

		String temp, result;
		temp = url.split("/")[3];
		result = temp.split("\\.")[0];
		return result;

	}

	public String getArticle() throws IOException {
		request = ServletActionContext.getRequest();
		Articleid = (String) request.getSession().getAttribute("articleId");
		System.out.println("articleid:" + Articleid);
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select article.articleContent," + " article.articleUpdateTime ," + " article.isDelete ,"
				+ " article.isPublished , " + "article.id , " + " article.articleName , " + "article.authorName ,"
				+ " article.authorId ," + " article.authorHeadPortraitUrl  , " + "article.articlePictureUrl ,"
				+ " article.likeCount , " + "article.readCount , " + "article.commentCount ,"
				+ "collection.collectionName   " + " from articleData article ,collectionTag collection where "
				+ "article.collectionTagId=collection.id and article.id='" + Articleid + "'";
		/* System.out.println(sql); */
		Query query = session.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		/* System.out.println(json); */
		CloseWirter(out);

		return null;
	}

	public String Page() throws IOException {
		/* System.out.println("bengin Page paser"); */
		articleData articleData = null;
		request = ServletActionContext.getRequest();
		url = request.getRequestURI();
		Articleid = getArticleId(url);
		/**/
		request = ServletActionContext.getRequest();
		session = request.getSession();
		session.setAttribute("articleId", Articleid);
		System.out.println("page:" + Articleid);
		return "success";
	}

	@Override
	public articleData getModel() {
		// TODO Auto-generated method stub
		return data;
	}
}
