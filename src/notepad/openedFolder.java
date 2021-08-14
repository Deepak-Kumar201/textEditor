package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;



public class openedFolder {
	File open=null;
	File sfile=null;
	public void createTree(File folder,DefaultMutableTreeNode root) {
		File[] files=folder.listFiles();
		for(var i:files) {
			if(i.isFile())root.add(new DefaultMutableTreeNode(i.getName()));
		}
		for(var i:files) {
			if(i.isDirectory()) {
				DefaultMutableTreeNode second=new DefaultMutableTreeNode(i.getName());
				root.add(second);
				createTree(i, second);
			}
		}
		return;
	}
	public openedFolder(File folder) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		var l=UIManager.getInstalledLookAndFeels();
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		JPanel panel=new JPanel();
		
		DefaultMutableTreeNode folderNode=new DefaultMutableTreeNode(folder.getName());
		createTree(folder,folderNode);
		
		Dimension win=Toolkit.getDefaultToolkit().getScreenSize();
		JScrollPane panelPane=new JScrollPane(panel);
		Dimension dm=new Dimension(200,win.height);
		
		JTree folderTree=new JTree(folderNode);
//		folderTree.setRootVisible(false);
		
//setting panel size
		panel.setSize(dm);
		panel.setPreferredSize(dm);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Image icon=new ImageIcon("icon.png").getImage();
		
		icon=icon.getScaledInstance(15, 15, 1);
		Icon icnIcon=new ImageIcon(icon); 
		
		DefaultTreeCellRenderer renderer=new DefaultTreeCellRenderer();
		renderer.setLeafIcon(icnIcon);
		
//		renderer.setOpenIcon(icnIcon);
//		renderer.setClosedIcon(icnIcon);

		folderTree.setCellRenderer(renderer);

		dm=new Dimension(400,win.height);
		folderTree.putClientProperty("JTree.lineStyle", "None");
		folderTree.setSize(dm);
		folderTree.setPreferredSize(dm);
		folderTree.setMaximumSize(dm);
		
		
		
		
//event listnerfor tree
		folderTree.addTreeSelectionListener((evnet)->{
			String fileString=folder.getAbsolutePath();
			TreePath pathStrings=folderTree.getSelectionPath();
			int n=pathStrings.getPathCount();
			for(int i=1;i<n;i++)fileString=fileString+"\\"+pathStrings.getPathComponent(i);
			open=new File(fileString);
			if(!open.isDirectory()) {
				fileString="";
				try {
					Scanner file=new Scanner(open);
					while(file.hasNextLine()) {
						fileString+=file.nextLine()+"\n";
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mainFrame.mTextArea.mainArea.setText(fileString);
				menubar.seletedfile=open;
				menubar.copyCutFormat();
				menubar.text=mainFrame.mTextArea.mainArea.getText();
				
				Font f = Main.createWinFrame.getFont();
				FontMetrics fm = Main.createWinFrame.getFontMetrics(f);
				int x = fm.stringWidth(open.getName()+" - Deepak Kumar");
				int y = fm.stringWidth(" ");
				int z = Main.createWinFrame.getWidth()/2 - (x/2);
				int w = z/y;
				String pad ="";
				for (int i=0; i!=w; i++) pad +=" "; 
				Main.createWinFrame.setTitle(pad+open.getName()+" - Deepak Kumar");
			}
		});
		
		
		panel.add(folderTree);
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.setBackground(Color.white);
		
		Main.createWinFrame.add(panel,BorderLayout.WEST);
		Main.createWinFrame.setVisible(true);
		
	}
	
}
