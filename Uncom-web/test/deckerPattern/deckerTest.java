package deckerPattern;

public class deckerTest {
	
	
	public static void main(String[] args) {
		Beverage beverage =new Espresso();
		beverage=new Mocha(new Whip(beverage));
		System.out.println(beverage.getDescription()+"$"+beverage.cost());
		int a=10;
		int b=a;
		a=12; 
		System.out.println(b);
		 
	}
	
	
}
