package register;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.userData;
import dao.hibernateStartPrepare;

public class registerAction extends ActionSupport implements ModelDriven<userData>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3508938291299823054L;
	private userData ud=new userData();
	
	
	public String Page()throws Exception{
		System.out.println("进入注册 Page() 函数");
		return "register_Page";
	}
	
	public String Check()throws Exception{
		
		
		return "1";
	}
	
	
	public String Register(){
	    System.out.println("进入注册 Register() 函数");
		SessionFactory sessionFactory=hibernateStartPrepare.getSessionFactory();
		Session session =sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
	    try {
		    session.save(ud);
		    transaction.commit();
	    } catch (Exception e) {
		   // TODO: handle exception
		   System.out.println(e);
		   transaction.rollback();
	    }finally {
	     	session.close();
        }
		    return "register_Page_Success";
	}
	@Override
	public userData getModel() {
		// TODO Auto-generated method stub
		return ud;
	}

	
}
