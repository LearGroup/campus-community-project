package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mysql.fabric.Response;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.hibernateStartPrepare;
import net.sf.json.JSONObject;
import transmission.ReseponseUtil;
import bean.userData;

public class loginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session = null;
	private String use;
	private String password;
	private String username;
	private JSONObject userContent;
	
	
	
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

	public userData getUser(String sors, String use) {
		userData User = null;
		SessionFactory sessionFactory = hibernateStartPrepare.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String sql = null;
		/*System.out.println("sors:" + sors + "user:" + use);*/
		if (sors.equals("phone")) {
			sql = "select user from userData user where user.phone=?";

		} else if (sors.equals("email")) {
			sql = "select user from userData user where user.email=?";
		} else if (sors.equals("username")) {
			sql = "select user from userData user where user.username=?";
		}
		/*System.out.println(use);*/
		Query query = session.createQuery(sql).setParameter(0, use);
		User = (userData) query.uniqueResult();
		/*System.out.println(sql);*/
		try {
			transaction.commit();
		} catch (Exception e) {
			System.out.println(e);
			transaction.rollback();
			// TODO: handle exception
		} finally {
			session.close();
		}
		if (User == null) {
			System.out.println("return null");
			return null;
		}
		return User;

	}

	public String checkStatus() throws Exception {

		response = ServletActionContext.getResponse();
		session = ServletActionContext.getRequest().getSession();
		userData ud = (userData) session.getAttribute("user");
		PrintWriter out = getPrintWriter();
		if (null != ud) {
			out.write(ud.getUsername());

		} else {
			/* System.out.println("out put"); */
			out.write("000");

		}

		CloseWirter(out);

		return null;

	}

	public String Page() throws Exception {
		/* System.out.println("Get into Page"); */
		request = ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		String para = request.getParameter("request");
		PrintWriter out = getPrintWriter();
		if (para.equals("login_page")) {
			out.print("success");
			CloseWirter(out);

			return null;
		} else {
			out.print("error");
			CloseWirter(out);
		}
		return null;

	}

	public String Login() throws Exception {
		userContent=new JSONObject();
		System.out.println("Get into Login");
		request = ServletActionContext.getRequest();
		PrintWriter out = getPrintWriter();
		session = ServletActionContext.getRequest().getSession();
        userContent=JSONObject.fromObject(request.getReader().readLine());
		System.out.println(userContent);
		use=(String) userContent.get("use");
		username=(String)userContent.get("user");
		password=(String)userContent.get("password");
		userData user = getUser(use, username);
		if (user == null) {
			out.print(JSONObject.fromObject("{status:0}"));
		} else if (password.equals(user.getPassword())) {
			if (use.equals("phone")) {
				if (user.getPhone().equals(username)) {
					
					out.print(JSONObject.fromObject("{status:1,results:"+JSONObject.fromObject(user)+"}"));
					System.out.println("set session: " + user.getUsername());
					session.setAttribute("user", user);
					session.setAttribute("username", user.getUsername());
				} else {
					out.print(JSONObject.fromObject("{status:0}"));
				}
			} else if (use.equals("email")) {
				if (user.getEmail().equals(username)) {
					out.print(JSONObject.fromObject("{status:1,results:"+JSONObject.fromObject(user)+"}"));
					session.setAttribute("user", user);
					session.setAttribute("username", user.getUsername());
				} else {
					out.print(JSONObject.fromObject("{status:0}"));
				}
			}
		} else {
			out.print(JSONObject.fromObject("{status:0}"));
		}
		CloseWirter(out);

		
		return null;
	}

	public String Logout() throws Exception {
		response = ServletActionContext.getResponse();
		session = ServletActionContext.getRequest().getSession();
		request = ServletActionContext.getRequest();
		session.removeAttribute("username");
		session.invalidate();
		String path = request.getContextPath();
		int port = request.getServerPort();
		String basePath = null;
		if (port == 80) {
			basePath = request.getScheme() + "://" + request.getServerName() + path;
		} else {
			basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		}
		response.sendRedirect(basePath + "/index.html");
		return null;
	}
	
	
	public void prepare() throws Exception
	{
	  // Clear last error messages
	  clearErrorsAndMessages();
	}
}
