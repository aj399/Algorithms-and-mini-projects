import java.io.Serializable;
import java.util.Vector;

public class ExtendedChatMessage extends ChatMessage implements Serializable{
	public int x;
	public int y;
	public int newx;
	public int newy;
	public String indicate;
	public Vector<String> userlist;
	public Vector<String> history;
	public ExtendedChatMessage(){}
	public ExtendedChatMessage(String name,String message,int x,int y, int newx, int newy, Vector<String> userlist){
		setName(name);
		setMessage(message);
		setX(x);
		setY(y);
		setNewx(newx);
		setNewy(newy);
	}
	public Vector<String> getUserlist() {
		return userlist;
	}
	public void setUserlist(Vector<String> userlist) {
		this.userlist = userlist;
	}
	public Vector<String> getHistory() {
		return history;
	}
	public void setHistory(Vector<String> history) {
		this.history = history;
	}
	public String getIndicate() {
		return indicate;
	}
	public void setIndicate(String indicate) {
		this.indicate = indicate;
	}
	public int getX() {
		return x;
	}
	public int getNewx() {
		return newx;
	}
	public void setNewx(int newx) {
		this.newx = newx;
	}
	public int getNewy() {
		return newy;
	}
	public void setNewy(int newy) {
		this.newy = newy;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
