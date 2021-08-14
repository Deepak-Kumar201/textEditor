package notepad;

import javax.swing.*;


import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.prefs.Preferences;


class mainFrame extends JFrame{
	public static textArea mTextArea;
	public menubar mainMenubar=null;
	public static Preferences preferences=Preferences.userRoot();
	public static Preferences node=preferences.node("notepad/dk");
	public static char[] operators= {
			'+','-','/','*','%','&','>','<','=','!','|',':'
	};
	
	public mainFrame() {
		int extState=node.getInt("extendedState", JFrame.MAXIMIZED_BOTH);
		setExtendedState(extState);
		BorderLayout bdLayout=new BorderLayout();
		setLayout(bdLayout);
		Image icon=new ImageIcon("icon.png").getImage();
		setIconImage(icon);
		
		int width=node.getInt("widht", 600);
		int height=node.getInt("height", 400);
		
		setSize(width,height);
		
		int xCoord=node.getInt("xcoord", 100);
		int yCoord=node.getInt("ycoord", 100);
		
		setLocation(xCoord, yCoord);
		
		
		mTextArea=new textArea();
		
		add(mTextArea.txtScrollPane,BorderLayout.CENTER);
		
		
		//menu bar
		mainMenubar=new menubar();
		setJMenuBar(mainMenubar.mainMenu);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				node.putInt("xcoord", getX());
				node.putInt("ycoord", getY());
				node.putInt("extendedState", getExtendedState());
				node.putInt("width", getWidth());
				node.putInt("height", getHeight());
				if(!(menubar.text).equals(mainFrame.mTextArea.mainArea.getText())) {
					saveDiag svdDiag= new saveDiag();
					svdDiag.setVisible(true);
				}
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}


class Main{
	public static mainFrame createWinFrame;
	public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
	//setting Look and feel of menu bar
		var l=UIManager.getInstalledLookAndFeels();
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		//creating the panel and th main frame
		createWinFrame=new mainFrame();
		
		FontMetrics fm = createWinFrame.getFontMetrics(new Font("Arial",Font.PLAIN,16));
		int x = fm.stringWidth("Untitled - Deepak Kumar");
		int y = fm.stringWidth(" ");
		int z = Main.createWinFrame.getWidth()/2 - (x/2);
		int w = z/y;
		String pad ="";
		for (int i=0; i!=(int)(5.4*w); i++) pad +=" "; 
		Main.createWinFrame.setTitle(pad+"Untitled - Deepak Kumar");
		
		String folderString=mainFrame.node.get("folder", null);
		
		if(folderString!=null) {
			try {
				new openedFolder(new File(folderString));
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}
		
		createWinFrame.setVisible(true);
		
	}
}