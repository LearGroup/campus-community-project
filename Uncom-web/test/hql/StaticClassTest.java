package hql;

public class StaticClassTest {
	
	
	public static void main(String[] args) {
		StaticClassTest test=new StaticClassTest();
		T t=test.func();
		System.out.println(t.str);
	}
	
	public T func(){
	 T t=new T();
	 return t;
	
	} 
	
	
	public  class T{
		 String str;
		public T(){
			str="str";
		}
		
	}

}
