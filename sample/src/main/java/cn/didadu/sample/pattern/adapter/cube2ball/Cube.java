package cn.didadu.sample.pattern.adapter.cube2ball;

public class Cube {

	private double width;
	
	public Cube(double width){
		this.width = width;
	}
	
	public double calculateVolume(){
		return width*width*width;
	}
	
	public double calculateFaceArea(){
		return width*width;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
	
}
