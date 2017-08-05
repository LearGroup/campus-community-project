package tripLoad;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.opensymphony.xwork2.ActionSupport;

import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class loadAction extends ActionSupport {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session = null;

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

	public String getTripList() throws IOException {
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String hql = "select trip, td.url from tripAttractionData trip ,tripImgData td where trip.id=td.tripAttractionId and td.id=(select max(td.id) from td where trip.id=td.tripAttractionId )";
		Query query = session.createQuery(hql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		/*System.out.println(json);*/
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}
	
	public String getCustomResultTripList() throws IOException{
		request = ServletActionContext.getRequest();
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Integer  money = Integer.parseInt(request.getParameter("money"));

		String hql = "select trip, td.url from tripAttractionData trip ,tripImgData td where trip.id=td.tripAttractionId and trip.minimumConsumption<="+money+"  and td.id=(select max(td.id) from td where trip.id=td.tripAttractionId )";
		Query query = session.createQuery(hql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		/*System.out.println(json);*/
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}
	
	
	public String getImgList() throws IOException{
		request = ServletActionContext.getRequest();
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String  id = (String)request.getParameter("id");
		String hql="select td from tripImgData td where td.tripAttractionId='"+id+"'";
		Query query = session.createQuery(hql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		/*System.out.println(json);*/
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}
	
	
	public String getStrategy() throws IOException{
		request = ServletActionContext.getRequest();
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String  id = (String)request.getParameter("id");
		String hql="select sd from strategyData sd where sd.tripAttractionId='"+id+"'";
		Query query = session.createQuery(hql);
		JSONArray json = JSONArray.fromObject(query.list());
		session.close();
		/*System.out.println(json);*/
		PrintWriter out = getPrintWriter();
		out.print(json);
		CloseWirter(out);
		return null;
	}
}
