

public class FuelingStation extends City {
	
	private float fuelPrice;
	private float chemicalPrice;
	
	public FuelingStation(int x, int y, float fuel, float chemical) {
		super(x,y, false);
		this.fuelPrice = fuel;
		this.chemicalPrice = chemical;
	}

	public float getFuelPrice() {
		return fuelPrice;
	}

	public float getChemicalPrice() {
		return chemicalPrice;
	}




	
	

}
