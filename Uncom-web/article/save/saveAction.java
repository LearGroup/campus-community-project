package save;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
import bean.commentData;
import bean.userData;
import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class saveAction extends ActionSupport {

	private articleData ad;
	private userData ud;

	private JSONArray json;
	private String url;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session = null;
	private String articleId;
	private String authorId;
	private Integer commentLevel;
	private String comment;
	private String commentParentId = null;
	private commentData cd;
	private Integer commentCount = 0;

	public String checkStatus() throws Exception {

		response = ServletActionContext.getResponse();
		session = ServletActionContext.getRequest().getSession();
		ud = (userData) session.getAttribute("user");

		if (null != ud) {
			return "1";

		} else {
			/* System.out.println("out put"); */
			return "0";

		}

	}

	public PrintWriter getPrintWriter() throws IOException {
		response = ServletActionContext.getResponse();
		response.setContentType("text/text");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		return out;
	}

	public void CloseWirter(PrintWriter out) {
		if (null != out) {
			out.flush();
			out.close();
		}

	}

	public String pushComment() throws IOException {
		cd = new commentData();
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session sessions = sessionFactory.openSession();
		Transaction transaction = sessions.beginTransaction();

		PrintWriter out = getPrintWriter();
		request = ServletActionContext.getRequest();
		articleId = (String) request.getSession().getAttribute("articleId");
		response = ServletActionContext.getResponse();
		session = ServletActionContext.getRequest().getSession();
		ud = (userData) session.getAttribute("user");
		authorId = ud.getId();
		commentLevel = Integer.parseInt(request.getParameter("commentLevel"));
		comment = request.getParameter("comment");
		commentCount = 0;
		String sql_2 = "select ad from articleData ad where ad.id='" + articleId + "'";
		Query query = sessions.createQuery(sql_2);
		ad = (articleData) query.uniqueResult();
		commentCount = ad.getCommentCount();
		commentCount += 1;
		ad.setCommentCount(commentCount);
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
		cd.setCommentChildCount(0);
		try {
			sessions.save(ad);
			sessions.save(cd);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			out.print("0");
			transaction.rollback();
		} finally {
			sessions.close();
		}
		out.print(articleId);
		return null;
	}

	public String getcollactionTag() throws Exception {
		String status = checkStatus();
		PrintWriter out = getPrintWriter();
		if (status.equals("1")) {

		} else {
			out.print("0");
			return null;
		}

		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select collaction from collectionTag collaction";
		Query query = session.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		out.print(json);
		return null;
	}

	public String saveArticle() throws Exception {
		String status = checkStatus();
		if (status.equals("1")) {
			SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
			Session sessions = sessionFactory.openSession();
			Transaction transaction = sessions.beginTransaction();
			ud = (userData) session.getAttribute("user");
			PrintWriter out = getPrintWriter();
			request = ServletActionContext.getRequest();
			String article_content = request.getParameter("articleContent");
			Integer isPublished = Integer.parseInt(request.getParameter("isPublished"));
			Integer collectionTagId = Integer.parseInt(request.getParameter("collectionTagId"));
			articleData ad = new articleData();
			String id = UUID.randomUUID().toString();
			ad.setId(id);
			String author = ud.getUsername();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createTime = sdf.format(date);
			int isDelete = 0;
			String article_abstract;
			article_content = article_content.replace("<br>", "");

			if (article_content.length() < 100) {
				article_abstract = article_content;
				article_abstract = article_abstract.replace("<p>", "").replace("</p>", "").replace("<br>", "");

			} else {
				article_abstract = article_content.substring(0, 100) + "...";
				article_abstract = article_abstract.replace("<p>", "").replace("</p>", "").replace("<br>", "");

			}
			if (collectionTagId.equals(4)) {
				article_abstract=article_content.replace("<p>", "").replace("</p>", "");
				article_content="";
			}
			
			String article_name = request.getParameter("articleName");
			;
			String author_id = ud.getId();
			String article_picture_url = request.getParameter("articlePictureUrl");
			int like_count = 0;
			int read_count = 0;
			int commment_count = 0;
			int no_like_count = 0;
			ad.setArticleAbstract(article_abstract);
			ad.setArticleContent(article_content);
			ad.setArticleCreateTime(date);
			ad.setArticleName(article_name);
			ad.setArticlePictureUrl(article_picture_url);
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
			ad.setCollectionTagId(collectionTagId);
			System.out.println(ad.toString());
			try {
				sessions.save(ad);
				transaction.commit();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				out.print("0");
				transaction.rollback();
			} finally {
				sessions.close();
			}
			out.print("1");

			CloseWirter(out);
		}

		return null;
	}

}
