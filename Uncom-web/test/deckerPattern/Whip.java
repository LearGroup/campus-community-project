package deckerPattern;

public class Whip implements CondimentDecorator{
	
	Beverage beverage;
	
	public Whip(Beverage beverage) {
		this.beverage=beverage;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double cost() {
		// TODO Auto-generated method stub
		return 0.5+beverage.cost();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return beverage.getDescription()+","+"Whip";
	}

}
