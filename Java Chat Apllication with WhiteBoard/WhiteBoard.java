import java.awt.AWTEventMulticaster;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class WhiteBoard extends JPanel implements MouseMotionListener, MouseListener{
	int x;
	int y;
	int newx;
	int newy;
	transient ActionListener actionListener;
	boolean newEventsOnly = false;
	ExtendedChatMessage ecm = new ExtendedChatMessage();

	public WhiteBoard(){
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g){
		for(Points p: Client.points){
			//super.paintComponent(g);
			g.drawLine(p.getNewx(), p.getNewy(), p.getX(), p.getY());
		}   
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		newx = x;
		newy = y;
		x = e.getX();
		y = e.getY();
		ecm.setX(x);
		ecm.setY(y);
		ecm.setMessage("");
		ecm.setName("");
		ecm.setNewx(newx);
		ecm.setNewy(newy);
		ecm.setIndicate("WB");

		try{
			Client.myOutputStream.reset();
			//System.out.println("X : "+ecm.getX());
			Client.myOutputStream.writeObject(ecm);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	public void paintBroadcast(Point p, Point q){

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public synchronized void addActionListener(ActionListener l) {
		if (l == null) {
			return;
		}
		actionListener = AWTEventMulticaster.add(actionListener, l);
		newEventsOnly = true;
	}
	public static void main(String a[]){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run() {

				JFrame frame = new JFrame();
				WhiteBoard linedraw= new WhiteBoard();
				frame.add(linedraw);
				frame.setSize(500,500);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setVisible(true);   

			}
		});
	}  
}

