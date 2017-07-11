package hql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dao.hibernateStartPrepare;

public class connectiontest {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		  Connection con = null;
		 
			    //加载MySql的驱动类   
			    Class.forName("com.mysql.jdbc.Driver") ;   
		
			    String url = "jdbc:mysql://47.95.0.73:3306/currency_db?autoReconnect=true&failOverReadOnly=false&useUnicode=true&characterEncoding=utf8&useSSL=false" ;   
			     String username = "root" ;   
			     String password = "18247352203" ;   
			    con = (Connection) DriverManager.getConnection(url , username , password ) ;   
			    
		   String sql = "select * from user";
		   PreparedStatement pstmt;

		        pstmt = (PreparedStatement)con.prepareStatement(sql);
		        ResultSet rs = pstmt.executeQuery();
		        rs.next();
		        System.out.println(rs.getString("username"));
		   
		   
	}

}
