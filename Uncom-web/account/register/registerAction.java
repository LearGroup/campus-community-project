package register;

import java.io.IOException;
import java.io.PrintWriter;
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

import bean.userData;
import dao.hibernateStartPrepare;
import net.sf.json.JSONArray;

public class registerAction extends ActionSupport implements ModelDriven<userData> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3508938291299823054L;
	private userData ud = new userData();
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

	public String Page() throws Exception {
		request = ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		String para = request.getParameter("request");
		PrintWriter out = getPrintWriter();
		if (para.equals("register_page")) {
			out.write("success");
		} else {
			out.write("error");
		}
		CloseWirter(out);
		return null;
	}

	public String Check() throws Exception {

		return null;
	}

	public String Register() throws IOException {
		/* System.out.println("Get into Register"); */
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String email = ud.getEmail();
		String hql = "select user from userData user where user.email='" + email + "'";
		Query query = session.createQuery(hql);
		JSONArray json = JSONArray.fromObject(query.list());
		PrintWriter out = getPrintWriter();
		if (json.toString().equals("[]")) {
			String id = UUID.randomUUID().toString();
			ud.setId(id);
			try {
				session.save(ud);
				transaction.commit();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				out.print("0");
				transaction.rollback();
			} finally {
				session.close();
			}
			out.print("1");
			
		}else{
			out.print("2");
		}
		
		CloseWirter(out);

		
		
		return null;
	}

	@Override
	public userData getModel() {
		// TODO Auto-generated method stub
		return ud;
	}

}
