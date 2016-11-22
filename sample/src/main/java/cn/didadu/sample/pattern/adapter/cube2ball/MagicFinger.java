package cn.didadu.sample.pattern.adapter.cube2ball;

public class MagicFinger implements BallIF{

	private double radius = 0;
	
	private static final double PI = 3.14D;
	
	private Cube adaptee;
	
	public MagicFinger(Cube adaptee){
		super();
		this.adaptee = adaptee;
		radius = adaptee.getWidth();
	}
 	
	
	@Override
	public double calculateArea() {
		// TODO Auto-generated method stub
		return PI * 4.0D * (radius*radius);
	}

	@Override
	public double calculateVolume() {
		// TODO Auto-generated method stub
		return PI * 4.0D/3.0D * (radius*radius*radius);
	}

	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return this.radius;
	}


	@Override
	public void setRadius(double radius) {
		// TODO Auto-generated method stub
		this.radius = radius;
	}

}
