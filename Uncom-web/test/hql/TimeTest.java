package hql;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Array;

public class TimeTest {
	public static void main(String[] args) {
		
		
		ArrayList<String>list=new ArrayList<>();
		list.add("test1");
		list.add("test2");
		list.add("test3");
		for (int i = 0; i < list.size(); i++) {
		
			System.out.println(list.get(i));
		}
		list.set(0, "cjen");
		for (int i = 0; i < list.size(); i++) {
			
			System.out.println(list.get(i));
		}
		
		
		
		
		
		
		
	/*	 Calendar calendar=Calendar.getInstance();  
	     calendar.setTime(new Date());  
	     System.out.println("现在时间是："+new Date());  
	     String year=String.valueOf(calendar.get(Calendar.YEAR));  
	     String month=String.valueOf(calendar.get(Calendar.MONTH)+1);  
	     String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));  
	     String week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)-1);  
	     System.out.println("现在时间是："+year+"年"+month+"月"+day+"日，星期"+week);  
	     long year2009=calendar.getTimeInMillis();  
	     calendar.set(1989,9,26);//这里与真实的月份之间相差1  
	     long year1989=calendar.getTimeInMillis();  
	     long days=(year2009-year1989)/(1000*60*60*24);  
	     System.out.println("今天和1989年10月26日相隔"+days+"天，"+"也就是说我在这个美丽的星球上已经幸福的生活了"+days+"天。");  

	   
	        
	        
	        
	       */
	       
	       
	       
	       
	       
	       
/*	        
		  Date date = new Date();
	        DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
	        System.out.println(df1.format(date));
	        DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分秒
	        System.out.println(df2.format(date));
	        DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
	        System.out.println(df3.format(date));
	        DateFormat df4 = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL); //显示日期，周，上下午，时间（精确到秒）
	        System.out.println(df4.format(date)); 
	        DateFormat df5 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG); //显示日期,上下午，时间（精确到秒）
	        System.out.println(df5.format(date));
	        DateFormat df6 = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT); //显示日期，上下午,时间（精确到分）
	        System.out.println(df6.format(date));
	        DateFormat df7 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
	        System.out.println(df7.format(date));*/
	}

}
