

public class TravelingSalesman {
	
	public static void main(String [] args) {
		FieldVisitor fv = new FieldVisitor(FieldVisitor.Day.Monday);
		fv.populateCities();
		fv.travel();
		System.out.println(fv.getMainStats().toString());
	}

}
