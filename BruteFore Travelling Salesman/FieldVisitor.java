
import java.util.*;


public class FieldVisitor {
	
	public enum Day {
		Monday,Tuesday;
		Day (){
		}
	}

	private ArrayList<City> unvisited = new ArrayList<City>();
	private ArrayList<City> visited = new ArrayList<City>();
	private ArrayList<FuelingStation> fuelStations = new ArrayList<FuelingStation>();
	private double remainingFuel;
	private double remainingChemicals;
	private float fuelCosts;
	private int flightHours;
	private double flightCost;
	private double fuelGallons;
	private int milesFlown;
	private double chemicalGallons;
	private double profit;
	private City source;
	private City current;
	private City home;
	private Day day;
	private PathStats mainStats;
	private double currentTripCost=6700;
	private double optimalCost; //= 67000;//Double.MAX_VALUE; //initialize to this
	private double currentEarnings;
	
	static final double MAX_FUEL_CAPACITY = 50;
	static final double MAX_CHEMICAL_CAPACITY = 100;
	static final double PLANE_RENTAL = 120;
	static final double PILOT_COST = 100;
	static final double FLIGHT_SPEED = 100; //per hour
	static final double FUEL_PER_HOUR = 10;
	
	public FieldVisitor(Day day) {
		this.day = day;
		fuelCosts = 0;
		milesFlown = 0;
		remainingFuel = 50;
		remainingChemicals = 100;
		currentTripCost = 100; //pilot costs are incurred before trip starts
		currentEarnings = 0;
		mainStats = new PathStats(unvisited, visited, remainingFuel, 
				remainingChemicals, source, current, fuelCosts, flightHours,
				flightCost, fuelGallons, milesFlown, chemicalGallons, profit, 1000000 );
	}
	
	
	public PathStats getMainStats() {
		return this.mainStats;
	}
	
	public double getOptimalCost() {
		return this.optimalCost;
	}
	
	public static boolean canVisitLocation(City from, City other, double remainingFuel) {
		double distanceNeeded = from.distanceToOtherCity(other);
		double remainingHrs = remainingFuel - FUEL_PER_HOUR;
		return (remainingHrs*FLIGHT_SPEED)>distanceNeeded;
	}
	
	public static boolean canApplyChemicals(City from, City other, double remainingChemicals) {
		return remainingChemicals>25;
	}
	
	public void populateCities() {
		if(day==Day.Monday) {
			City c1 = new City(20,25, true); 
			unvisited.add(c1);
			City c2 = new City(84, 90, false);
			unvisited.add(c2);
			City c3  =new City(20, 79, false);
			unvisited.add(c3);
			City c4 = new City(34, 89, false);
			unvisited.add(c4);
			City c5 = new City(50, 67, false);
			unvisited.add(c5);
			City c6  =new City(95, 64, false);
			unvisited.add(c6);
			City c7 = new City(51, 31, false);
			unvisited.add(c7);
			City c8 = new City(1, 71, false);
			unvisited.add(c8);
			City c9 = new City(43,43,false);
			unvisited.add(c9);
			City c10 = new City(78, 88, false);
			unvisited.add(c10);
			FuelingStation fg1 = new FuelingStation(20,25,5,16);
			//unvisited.add(fg1);
			fuelStations.add(fg1);
			FuelingStation fg2 = new FuelingStation(70,80,6,15);
			//unvisited.add(fg2);
			fuelStations.add(fg2);
			
			
		}
		else if (day==Day.Tuesday) {
			City c1 = new City(20,25, true); 
			unvisited.add(c1);
			City c2 = new City(25, 12, false);
			unvisited.add(c2);
			City c3  =new City(72, 48, false);
			unvisited.add(c3);
			City c4 = new City(87, 94, false);
			unvisited.add(c4);
			City c5 = new City(89, 76, false);
			unvisited.add(c5);
			City c6  =new City(6, 27, false);
			unvisited.add(c6);
			City c7 = new City(36, 72, false);
			unvisited.add(c7);
			City c8 = new City(30, 38, false);
			unvisited.add(c8);
			City c9 = new City(72, 59,false);
			unvisited.add(c9);
			City c10 = new City(29, 80, false);
			unvisited.add(c10);
			FuelingStation fg1 = new FuelingStation(20,25,5,16);
			unvisited.add(fg1);
			FuelingStation fg2 = new FuelingStation(20,25,6,15);
			unvisited.add(fg2);
		}
		
	}
	
	public void travel() {
		source = unvisited.get(0);
		if(source.isHome) {
			home = source;
		}
		for(City c: unvisited) {
			if(!(c instanceof FuelingStation)) {
				if(!c.isHome) {
					try{
					PathStats temp1 = new PathStats(unvisited, visited, remainingFuel, remainingChemicals, source, c, fuelCosts, flightHours,flightCost, fuelGallons, milesFlown, chemicalGallons, profit, currentTripCost );
					PathStats ps1 = (PathStats)temp1.clone();
					PathStats ps2 = (PathStats)temp1.clone();
					
					PathStats ps3 = (PathStats)temp1.clone();
          System.out.println("Main1 = "+this.mainStats.currentTripCost);
					goToCity(ps1);
          System.out.println("Main2 = "+mainStats.currentTripCost);                             
					goToFuelStationAndCity(ps2, fuelStations.get(0));
					System.out.println("Main3 = "+mainStats.currentTripCost);
					goToFuelStationAndCity(ps3, fuelStations.get(1));
                                        System.out.println("Flag4");
                                        System.out.println("Main4 = "+mainStats.currentTripCost);
                                       }
                                       catch(Exception e){System.out.println("Caught the exception"+e);}
					
				}
			}
		}
	}
	
	public void goToCity(PathStats ps) {
		City src = ps.source;
		City curr = ps.current;
		ArrayList<City> temp =  ps.unvisited;
		double remainingFuel = ps.remainingFuel;
		//calcualte distance between both cities, see if you have enough fuel to get there
		double distance = src.distanceToOtherCity(curr);
		if(FieldVisitor.canVisitLocation(src,  curr, remainingFuel) && FieldVisitor.canApplyChemicals(src, curr, remainingChemicals)&&(ps.currentTripCost<this.mainStats.currentTripCost)) {//ps.currentTripCost<optimalCost
				
			//all conditions valid
			curr = ps.unvisited.remove(0);
                        
			ps.visited.add(curr);
			ps.source = curr;
			//have now visited city, update stats
			FieldVisitor.markSiteVisited(ps, distance);
      if(ps.unvisited.isEmpty()) {
        FieldVisitor.update(ps, this);
                                
			}
			else {  ps.current = ps.unvisited.get(0); //get new first element in unvisited queue
				for (City c: ps.unvisited) {
					try {
					PathStats ps1 = (PathStats)ps.clone();
					PathStats ps2 = (PathStats)ps.clone();
					PathStats ps3 = (PathStats)ps.clone();
					
					goToFuelStationAndCity(ps2, fuelStations.get(0));
					
					
					goToFuelStationAndCity(ps3, fuelStations.get(1));
				}
					catch (CloneNotSupportedException e) {}
				}
			}
		}
		
	}
	
	public void goToFuelStationAndCity(PathStats ps, FuelingStation station) {
		if (FieldVisitor.canVisitLocation(ps.source, (City)station, ps.remainingFuel)&&(ps.currentTripCost<this.mainStats.currentTripCost)
				) {
			
			//determined that you can visit gas station
			//see if after filling tank, you can visit next city from gas station
			if(FieldVisitor.canVisitLocation((City) station, ps.current, MAX_FUEL_CAPACITY)) {

				double distanceToStation = ps.source.distanceToOtherCity((City)station);
				double distanceFromStationToCity = station.distanceToOtherCity(ps.current);

				City curr = ps.unvisited.remove(0);
				ps.source = curr;
        ps.visited.add(curr);
			
				
				
				FieldVisitor.markStationAndSiteVisited(ps, distanceToStation, distanceFromStationToCity);
				
				if(ps.unvisited.isEmpty()) {
					FieldVisitor.update(ps,this);
				}
				else {
					ps.current = ps.unvisited.get(0); //get new first element in unvisited queue
						
						try {
							PathStats ps1 = (PathStats)ps.clone();
							PathStats ps2 = (PathStats)ps.clone();
							PathStats ps3 = (PathStats)ps.clone();
              goToCity(ps1); //call recursively
              goToFuelStationAndCity(ps2, fuelStations.get(0));
              goToFuelStationAndCity(ps3, fuelStations.get(1));
						}
						catch (CloneNotSupportedException e) {System.out.println("This is the fing clone exception");}
						
					
				}
				
			}
		}
	}
	
	public static void update(PathStats stats,FieldVisitor fv) {
    if (stats.currentTripCost<fv.mainStats.currentTripCost){             //fv.getOptimalCost()) {
			//you have found path, update field values
      fv.mainStats.updateDetails(stats);
		}
		
	}
	
	public static void markStationAndSiteVisited(PathStats currentStats, double stationDistance, 
			double cityDistance) {
		//will be called when you are visiting a city en route from a fueling station
		currentStats.milesFlown+=stationDistance;
		currentStats.flightHours+=(stationDistance/FLIGHT_SPEED);
		currentStats.flightHours+=45; //re-fueling and loading chemicals at same time
		currentStats.remainingFuel=50;
		currentStats.remainingChemicals  =100;
		double rentalCost = (stationDistance/FLIGHT_SPEED)*PLANE_RENTAL;
		currentStats.currentTripCost+=rentalCost;
		//incurred pilot costs
		currentStats.currentTripCost+=((stationDistance/FLIGHT_SPEED)*PILOT_COST);
		FieldVisitor.markSiteVisited(currentStats, cityDistance);
	}
	
	public static void markSiteVisited(PathStats currentStats, double distance) {
		//update stats
		currentStats.milesFlown+=distance;
		currentStats.flightHours+=(distance/FLIGHT_SPEED);
		currentStats.flightHours+=30; //half hour to dust each field
		double fuelReduction = (distance/FLIGHT_SPEED)*FUEL_PER_HOUR;
		currentStats.fuelGallons+=fuelReduction;
		currentStats.remainingFuel-=fuelReduction;
		currentStats.remainingChemicals-=25; 
		currentStats.chemicalGallons+=25;
		//flight elapsed cost
		double rentalCost = (distance/FLIGHT_SPEED)*PLANE_RENTAL;
		currentStats.currentTripCost+=rentalCost;
		//incurred pilot costs
		currentStats.currentTripCost+=((distance/FLIGHT_SPEED)*PILOT_COST);
		
	}
	

	
	
	class PathStats implements Cloneable{
		public ArrayList<City> unvisited = new ArrayList<City>();
		public ArrayList<City> visited = new ArrayList<City>();
		public double remainingFuel;
		public double remainingChemicals;
		public float fuelCosts;
		public int flightHours;
		public double flightCost;
		public double fuelGallons;
		public int milesFlown;
		public double chemicalGallons;
		public float chemicalCost;
		public double profit;
		public City source;
		public City current;
		public Day day;
		public double currentTripCost;
		public double currentEarnings;
		
		public PathStats(ArrayList<City> unvisited, ArrayList<City> visited, double remainingFuel, 
				double remainingChemicals, City source, City current, float fuelCosts, int flightHours,
				double flightCost, double fuelGallons, int milesFlown, double chemicalGallons, double profit,
				double currentTripCost) {
		
			this.unvisited = unvisited;
			this.visited = visited;
			this.remainingFuel = remainingFuel;
			this.remainingChemicals = remainingChemicals;
			this.source = source;
			this.current = current;
			this.fuelCosts = fuelCosts;
			this.flightHours = flightHours;
			this.flightCost = flightCost;
			this.fuelGallons = fuelGallons;
			this.milesFlown = milesFlown;
			this.chemicalGallons = chemicalGallons;
			this.profit = profit;
			this.currentTripCost = currentTripCost;
		}
		
		public PathStats(PathStats path) {
			this.unvisited  =path.unvisited;
			this.visited = path.visited;
			this.remainingFuel  =path.remainingFuel;
			this.remainingChemicals = path.remainingChemicals;
			this.source = path.source;
			this.current = path.current;
			this.fuelCosts = path.fuelCosts;
			this.flightHours = path.flightHours;
			this.flightCost = path.flightCost;
			this.fuelGallons = path.fuelGallons;
			this.milesFlown = path.milesFlown;
			this.chemicalGallons = path.chemicalGallons;
			this.profit = path.profit;
			this.currentTripCost = path.currentTripCost;
			//populateCities();
		}
		
		public void updateDetails(PathStats other) {
                        
			this.remainingFuel = other.remainingFuel;
			this.remainingChemicals = other.remainingChemicals;
			this.fuelCosts = other.fuelCosts;
			this.flightHours = other.flightHours;
			this.flightCost = other.flightCost;
			this.fuelGallons = other.fuelGallons;
			this.milesFlown = other.milesFlown;
			this.chemicalGallons = other.chemicalGallons;
			this.chemicalCost = other.chemicalCost;
			this.profit = other.profit;
			this.source = other.source;
			this.current = other.current;
			this.day = other.day;
			this.currentTripCost = other.currentTripCost;
			this.currentEarnings = other.currentEarnings;
		}
		
		public String toString() {
			String output;
			output = "Remaining Fuel:" + this.remainingFuel +
			"remaining chemicals:" + this.remainingChemicals +
			"fuel costs: " + this.fuelCosts +
			"flight hours: " + this.flightHours +
			"flight cost " + this.flightCost + 
			"fuel gallons: " + this.fuelGallons +
			" miles flown: " + this.milesFlown +
			" chemical gallons: " + this.chemicalGallons +
			" chemical cost: " + this.chemicalCost + 
			"Day: " + this.day + 
			" current trip cost: " + this.currentTripCost + 
			" current earnings: " + this.currentEarnings;
			return output;
		}
		@Override
		protected Object clone() throws CloneNotSupportedException {
			PathStats clone = (PathStats) super.clone();
			clone.unvisited = (ArrayList<City>)clone.unvisited.clone();
			clone.visited = (ArrayList<City>)clone.visited.clone();
			return clone;
		}
		
		public void populateCities() {
			unvisited.clear();
			if(day==Day.Monday) {
				City c1 = new City(20,25, true); 
				unvisited.add(c1);
				City c2 = new City(84, 90, false);
				unvisited.add(c2);
				City c3  =new City(20, 79, false);
				unvisited.add(c3);
				City c4 = new City(34, 89, false);
				unvisited.add(c4);
				City c5 = new City(50, 67, false);
				unvisited.add(c5);
				City c6  =new City(95, 64, false);
				unvisited.add(c6);
				City c7 = new City(51, 31, false);
				unvisited.add(c7);
				City c8 = new City(1, 71, false);
				unvisited.add(c8);
				City c9 = new City(43,43,false);
				unvisited.add(c9);
				City c10 = new City(78, 88, false);
				unvisited.add(c10);
				FuelingStation fg1 = new FuelingStation(20,25,5,16);
				fuelStations.add(fg1);
				FuelingStation fg2 = new FuelingStation(20,25,6,15);
				fuelStations.add(fg2);
			}
			else if (day==Day.Tuesday) {
				City c1 = new City(20,25, true); 
				unvisited.add(c1);
				City c2 = new City(25, 12, false);
				unvisited.add(c2);
				City c3  =new City(72, 48, false);
				unvisited.add(c3);
				City c4 = new City(87, 94, false);
				unvisited.add(c4);
				City c5 = new City(89, 76, false);
				unvisited.add(c5);
				City c6  =new City(6, 27, false);
				unvisited.add(c6);
				City c7 = new City(36, 72, false);
				unvisited.add(c7);
				City c8 = new City(30, 38, false);
				unvisited.add(c8);
				City c9 = new City(72, 59,false);
				unvisited.add(c9);
				City c10 = new City(29, 80, false);
				unvisited.add(c10);
				FuelingStation fg1 = new FuelingStation(20,25,5,16);
				unvisited.add(fg1);
				FuelingStation fg2 = new FuelingStation(20,25,6,15);
				unvisited.add(fg2);
			}
			
		}
	}
}
