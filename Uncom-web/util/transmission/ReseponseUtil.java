package transmission;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class ReseponseUtil {
	
	
	  private static HttpServletRequest request;
	  private static HttpServletResponse response;
	  private static HttpSession session = null;
	
	static{
		 response=ServletActionContext.getResponse();
		  response.setContentType("text/text");
	      response.setCharacterEncoding("UTF-8");
	}
	  
	  
	public static PrintWriter getPrintWriter() throws IOException{
		  response=ServletActionContext.getResponse();
		  response.setContentType("text/text");
	      response.setCharacterEncoding("UTF-8");
		  PrintWriter out = response.getWriter();
		  return out;
	}
	
	
	public static void CloseWirter(PrintWriter out){
		if(null!=out){
			out.flush();
			out.close();
		}
		
	}
}
