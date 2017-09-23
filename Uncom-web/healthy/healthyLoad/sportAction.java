package healthyLoad;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.opensymphony.xwork2.ActionSupport;

import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class sportAction  extends ActionSupport{

	private HttpServletRequest request;
	private HttpServletResponse response;


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
	
	public String getSportList()throws Exception{
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String sql="select st from sportData st";
		Query query = session.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}
	
	
	public String getFoodList()throws Exception{
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String sql="select fd from foodData fd";
		Query query = session.createQuery(sql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}
}
