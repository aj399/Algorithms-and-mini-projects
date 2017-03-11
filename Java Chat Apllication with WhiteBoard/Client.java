import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class Client extends Thread implements ActionListener{

	ExtendedChatMessage myObject;
	boolean sendingdone = false, receivingdone = false;
	Scanner scan;
	Socket socketToServer;
	static ObjectOutputStream myOutputStream;
	ObjectInputStream myInputStream;
	Frame f;
	TextField tf;
	TextArea ta;
	WhiteBoard wb;
	JFrame jf;
	static ArrayList<Points> points = new ArrayList<Points>();
	JPanel north;
	JButton connect;
	JButton discon;
	String name;
	JPanel listarea;
	JList<String> userlist;
	Vector<String> namelist;
	public Client(){	
		
		listarea = new JPanel();
		connect = new JButton("Connect");
		discon = new JButton("Disconnect");
		connect.addActionListener(this);
		connect.setActionCommand("connect");
		discon.addActionListener(this);
		discon.setActionCommand("discon");
		discon.setEnabled(false);
		userlist = new JList<String>();
		namelist = new Vector<String>();
		
		listarea.add(userlist, BorderLayout.CENTER);
		//listarea.setSize(150, 400);
		//listarea.add(new JButton("UserList"), BorderLayout.NORTH);
		//tf.setEditable(true);
		f = new Frame();
		f.setSize(720,480);
		f.setTitle("Chat Client");
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		tf = new TextField();
		tf.addActionListener(this);
		f.add(tf, BorderLayout.SOUTH);
		ta = new TextArea();
		f.add(ta, BorderLayout.CENTER);
		wb = new WhiteBoard();
		wb.setPreferredSize(new Dimension(300,150));
		wb.addActionListener(this);
		f.add(wb, BorderLayout.EAST);
		north = new JPanel();
		north.setLayout(new GridLayout(1,2));
		north.add(connect);
		north.add(discon);
		f.add(north, BorderLayout.NORTH);
		ta.append("Enter Username in Text field");
		f.add(listarea, BorderLayout.WEST);
		ta.setEnabled(false);
		try{						

			//scan = new Scanner(System.in);

			//myObject = new ChatMessage();

			socketToServer = new Socket("afsconnect1.njit.edu", 4002);

			myOutputStream =
					new ObjectOutputStream(socketToServer.getOutputStream());

			myInputStream =
					new ObjectInputStream(socketToServer.getInputStream());
			start();


		}
		catch(Exception e){
			System.out.println(e.getMessage());	
		}
		f.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae){
		myObject = new ExtendedChatMessage();
		boolean user = true;
		if(ae.getActionCommand().equals("connect")){
			
			//tf.setEditable(true);
			user = false;
			connect.setEnabled(false);
			name = tf.getText();
			ta.setEnabled(true);
			ta.setText("");
			discon.setEnabled(true);
			userlist.setListData(namelist);
			userlist.invalidate();
			userlist.validate();
			
		}
		if(ae.getActionCommand().equals("discon")){
			connect.setEnabled(true);
			user = true;
			ta.setText("");
			tf.setText("");
			points.clear();
			wb.repaint();
			discon.setEnabled(false);
			ta.setEnabled(false);
			namelist.removeAllElements();
			userlist.setListData(namelist);
			userlist.invalidate();
			userlist.validate();
			
		}
		else{
			if(!user){
				//myObject.setName(tf.getText());
				myObject.setIndicate("NAME");
				user = true;
			}
		myObject.setName(name);
		myObject.setMessage(tf.getText());
		tf.setText("");
			
		try{
			myOutputStream.reset();
			myOutputStream.writeObject(myObject);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		}

	}
	public void run(){
		System.out.println("Listening for messages from server . . . ");
		try{
			while(!receivingdone){
				System.out.println("Inside while Loop: ");
				myObject = (ExtendedChatMessage)myInputStream.readObject();
				if(myObject.getIndicate()!=null){
					System.out.println(myObject.getIndicate());
					if(myObject.getIndicate().equals("NAME")){
					ta.append(myObject.getName()+" Joined the Room \n");
					//namelist.addElement(myObject.getName());
					System.out.println("Original Userlist : "+myObject.getUserlist());
	
					namelist = myObject.getUserlist();
					System.out.println(namelist);
					userlist.invalidate();
					userlist.setListData(namelist);
					userlist.validate();
					
					}
					else if( myObject.getIndicate().equals("WB")){
					System.out.println("Inside If");
					Points p = new Points(myObject.getX(), myObject.getY(), myObject.getNewx(), myObject.getNewy());
					points.add(p);
					System.out.println(myObject.getX()+" , "+myObject.getY()+" , "+myObject.getNewx()+" , "+myObject.getNewy());
					wb.repaint();
				}
					else if(myObject.getIndicate().equals("HISTORY")){
						if(myObject.getHistory() != null){
							for(String hist: myObject.getHistory()){
								ta.append(hist+"\n");
							}
						}
							
					}
				}
			else
				{
					ta.append(myObject.getName()+": "+myObject.getMessage() + "\n");
				}
			}
		}catch(IOException ioe){
			System.out.println("IOE: " + ioe.getMessage());
		}catch(ClassNotFoundException cnf){
			System.out.println(cnf.getMessage());
		}
	}

	public static void main(String[] arg){

		Client c = new Client();

	}
}




