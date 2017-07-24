package hql;

public class abstractPaserTest {
	public static void main(String[] args) {
		String str="<p>测试HTML标签是否存在</p>";
	    str=str.replace("<p>", "").replace("</p>", "");
		
		System.out.println(str);
	}
	
	
	/*String str="<p>测试HTML标签是否存在</p>";
    String str2=str.replace("<P>", " ");
	System.out.println(str2);*/

}
