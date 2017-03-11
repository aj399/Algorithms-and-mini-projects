
public class List {

	private List next;
	private int index;
	private int m;
	
	public List(int index){
		this.index = index;
		this.next = null;
		this.m = 0;
	}
	
	public List getNext(){
		return next;
	}
	public int getM(){
		return m;
	}
	public int getIndex(){
		return index;
	}
	public void setM(int m){
	  this.m = m;
	}
	public void setNext(List next){
	  this.next = next;
	}
	public void setIndex(int index){
	 this.index = index;
	}
}
