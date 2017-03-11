

public class City {
	
	private int x;
	private int y;
	boolean isHome;
	
	public City (int x, int y, boolean isHome) {
		this.x = x;
		this.y = y;
		this.isHome = isHome;
	}
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double distanceToOtherCity(City otherCity) {
		//uses distance formula to get distance between 2 endpoints
		//distance formula : square root of (x*x)+(y*y)
		
		int xDistance = Math.abs(this.getX()-otherCity.getX());
		int yDistance = Math.abs(this.getY()-otherCity.getY());
		
		return Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
	}
	
	
}
	
