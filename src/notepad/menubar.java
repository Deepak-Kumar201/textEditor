package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import com.sun.accessibility.internal.resources.accessibility;
import com.sun.source.tree.WhileLoopTree;



import java.util.*;




public class menubar {
	public JMenuBar mainMenu;
	public static File seletedfile=null;
	public static String text="";
	public static JFileChooser fileChooser=new JFileChooser();
	Image icoImage =new ImageIcon("icon.png").getImage();
	
	public static Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
	public menubar() {
		mainMenu=new JMenuBar();
		mainMenu.setBorder(BorderFactory.createEmptyBorder());
		String folderString=mainFrame.node.get("folder", null);
		if(folderString!=null) fileChooser.setCurrentDirectory(new File(folderString));
	//file menu
		JMenu file=new JMenu("File");
		//file item;
		JMenuItem newItem=new JMenuItem("New");
		JMenuItem openItem=new JMenuItem("Open");
		JMenuItem openFolder=new JMenuItem("Open Folder");
		JMenuItem saveItem=new JMenuItem("Save");
		JMenuItem saveAsItem=new JMenuItem("Save As");
		JMenuItem exitItem=new JMenuItem("Exit");
		saveItem.setPreferredSize(new Dimension(250,30));
		newItem.setPreferredSize(new Dimension(250,30));
		openFolder.setPreferredSize(new Dimension(250,30));
		saveAsItem.setPreferredSize(new Dimension(250,30));
		exitItem.setPreferredSize(new Dimension(250,30));
		openItem.setPreferredSize(new Dimension(250,30));
		file.add(newItem);
		file.add(openItem);
		file.add(openFolder);
		file.add(saveItem);
		file.add(saveAsItem);
		file.addSeparator();
		file.add(exitItem);
	//edit menu
		JMenu editJMenu=new JMenu("Edit");
		//edit item
		
		JMenuItem undoItem=new JMenuItem("Undo");
		JMenuItem RedoItem=new JMenuItem("Redo");
		JMenuItem copyItem=new JMenuItem("Copy");
		JMenuItem pasteItem=new JMenuItem("Paste");
		JMenuItem cutItem=new JMenuItem("Cut");
		JMenuItem DeleteItem=new JMenuItem("Delete");
		JMenuItem fontItem=new JMenuItem("Font");
		JMenuItem commetItem=new JMenuItem("Toggle Comment");
		JMenuItem uncommetItem=new JMenuItem("Uncomment");
		undoItem.setPreferredSize(new Dimension(250,30));
		RedoItem.setPreferredSize(new Dimension(250,30));
		copyItem.setPreferredSize(new Dimension(250,30));
		cutItem.setPreferredSize(new Dimension(250,30));
		pasteItem.setPreferredSize(new Dimension(250,30));
		DeleteItem.setPreferredSize(new Dimension(250,30));
		fontItem.setPreferredSize(new Dimension(250,30));
		commetItem.setPreferredSize(new Dimension(250,30));
		uncommetItem.setPreferredSize(new Dimension(250,30));
		
		editJMenu.add(undoItem);
		editJMenu.add(RedoItem);
		editJMenu.addSeparator();
		editJMenu.add(copyItem);
		editJMenu.add(pasteItem);
		editJMenu.add(cutItem);
		editJMenu.add(DeleteItem);
		editJMenu.addSeparator();
		editJMenu.add(fontItem);
		editJMenu.add(commetItem);
		editJMenu.add(uncommetItem);
	
		
		
		
		
		
		mainMenu.add(file);
		mainMenu.add(editJMenu);
		
		
		//actions for menu items
		newItem.addActionListener((event)->{
			mainFrame.mTextArea.mainArea.setText("");
			seletedfile=null;
			Font f = Main.createWinFrame.getFont();
			FontMetrics fm = Main.createWinFrame.getFontMetrics(f);
			int x = fm.stringWidth("Untitled - Deepak Kumar");
			int y = fm.stringWidth(" ");
			int z = Main.createWinFrame.getWidth()/2 - (x/2);
			int w = z/y;
			String pad ="";
			for (int i=0; i!=w; i++) pad +=" "; 
			Main.createWinFrame.setTitle(pad+"Untitled - Deepak Kumar");
		});
		
		openItem.addActionListener((event)->{
//			fileChooser.setFileFilter(new FileNameExtensionFilter("TextFiles", "txt"));
			fileChooser.showOpenDialog(null);

			seletedfile=fileChooser.getSelectedFile();
//			String opended=seletedfile.getAbsolutePath();
			System.out.print(seletedfile);
			
			Scanner openfile;
			try {
				openfile = new Scanner(seletedfile);
				StringBuilder fileTxtBuilder=new StringBuilder("");
				while(openfile.hasNextLine()) {
					fileTxtBuilder.append(openfile.nextLine());
					fileTxtBuilder.append("\n");
				}
				mainFrame.mTextArea.mainArea.setText(fileTxtBuilder.toString());
			} catch (FileNotFoundException e) {
				mainFrame.mTextArea.mainArea.setText("Corrupt file");
			}
			text=mainFrame.mTextArea.mainArea.getText();
			copyCutFormat();
		});
		saveItem.addActionListener((event)->{
			saveFile();
			text=mainFrame.mTextArea.mainArea.getText();
		});
		saveAsItem.addActionListener((event)->{
			fileChooser.showSaveDialog(null);
			seletedfile=fileChooser.getSelectedFile();
			
			Font f = Main.createWinFrame.getFont();
			FontMetrics fm = Main.createWinFrame.getFontMetrics(f);
			int x = fm.stringWidth(seletedfile.getName()+" - Deepak Kumar");
			int y = fm.stringWidth(" ");
			int z = Main.createWinFrame.getWidth()/2 - (x/2);
			int w = z/y;
			String pad ="";
			for (int i=0; i!=w; i++) pad +=" "; 
			Main.createWinFrame.setTitle(pad+seletedfile.getName()+" - Deepak Kumar");
			try {
				seletedfile.createNewFile();
				FileWriter writeFile=new FileWriter(seletedfile);
				writeFile.write(mainFrame.mTextArea.mainArea.getText());
				writeFile.close();
			} catch (IOException e) {
				System.out.println("Full");
			}
			text=mainFrame.mTextArea.mainArea.getText();
			
		});
		exitItem.addActionListener((event)->{
			if(!text.equals(mainFrame.mTextArea.mainArea.getText())) {
				saveDiag svdDiag= new saveDiag();
				svdDiag.setVisible(true);
			}else System.exit(0);
		});
		
		
		//font setting
		fontItem.addActionListener((event)->{
			listBox fontBox=new listBox();
			fontBox.setItems();
			fontBox.setVisible(true);
		});
		copyItem.addActionListener((event)->{
			StringSelection str=new StringSelection(mainFrame.mTextArea.mainArea.getSelectedText());
			clipboard.setContents(str,null);
			copyCutFormat();
		});
		cutItem.addActionListener((event)->{
			StringSelection str=new StringSelection(mainFrame.mTextArea.mainArea.getSelectedText());
			clipboard.setContents(str,null);
			mainFrame.mTextArea.mainArea.replaceSelection("");
			copyCutFormat();
		});
		pasteItem.addActionListener((event)->{
			Clipboard c=Toolkit.getDefaultToolkit().getSystemClipboard();

            try {
				mainFrame.mTextArea.mainArea.replaceSelection((String) c.getData(DataFlavor.stringFlavor));
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            copyCutFormat();
		});
		
		DeleteItem.addActionListener((event)->{
			mainFrame.mTextArea.mainArea.replaceSelection("");
			copyCutFormat();
		});
		
		commetItem.addActionListener((event)->{
			mainFrame.mTextArea.commentOut();
		});
		undoItem.addActionListener((evnet)->{
			Deque<String> undodeque=mainFrame.mTextArea.txtAct.undoDeque;
			Deque<String> redodeque=mainFrame.mTextArea.txtAct.redoDeque;
			if(undodeque.isEmpty());
			else {
				String string=undodeque.removeLast();
				redodeque.addLast(string);
				mainFrame.mTextArea.mainArea.setText(string);
			}
		});
		RedoItem.addActionListener((event)->{
			Deque<String> undodeque=mainFrame.mTextArea.txtAct.undoDeque;
			Deque<String> redodeque=mainFrame.mTextArea.txtAct.redoDeque;
			System.out.println(redodeque.size());
			if(redodeque.size()==0);
			else {
				String string=redodeque.removeLast();
				System.out.println(string);
				undodeque.addLast(string);
				mainFrame.mTextArea.mainArea.setText(string);
			}
		});
		uncommetItem.addActionListener((event)->{
			mainFrame.mTextArea.uncomment();
		});
		
		openFolder.addActionListener((event)->{
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.showOpenDialog(null);
			File folder=fileChooser.getSelectedFile();
			try {
				new openedFolder(folder);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				mainFrame.mTextArea.mainArea.setText("File not supported");
			}
			mainFrame.node.put("folder", folder.getAbsolutePath());
		});
		
	}
	
	
	
	//save file funtion
	public static void saveFile() {
		if(seletedfile==null) {
			fileChooser.showSaveDialog(null); 
			seletedfile=fileChooser.getSelectedFile();
			Font f = Main.createWinFrame.getFont();
			FontMetrics fm = Main.createWinFrame.getFontMetrics(f);
			int x = fm.stringWidth(seletedfile.getName()+" - Deepak Kumar");
			int y = fm.stringWidth(" ");
			int z = Main.createWinFrame.getWidth()/2 - (x/2);
			int w = z/y;
			String pad ="";
			for (int i=0; i!=w; i++) pad +=" "; 
			Main.createWinFrame.setTitle(pad+seletedfile.getName()+" - Deepak Kumar");
			try {
				seletedfile.createNewFile();
			} catch (IOException e1) {
				System.out.print("Memory full");
			}
			try {
				FileWriter saveFile=new FileWriter(seletedfile);
				saveFile.write(mainFrame.mTextArea.mainArea.getText());
				saveFile.close();
			} catch (IOException e) {
				try {
					seletedfile.createNewFile();
					FileWriter saveFile=new FileWriter(seletedfile);
					saveFile.write(mainFrame.mTextArea.mainArea.getText());
					saveFile.close();
				} catch (IOException e1) {
					System.out.print("Memory full");
				}
			}
			
		}else {
			FileWriter saveFile;
			try {
				saveFile = new FileWriter(seletedfile);
				saveFile.write(mainFrame.mTextArea.mainArea.getText());
				saveFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void copyCutFormat() {
		mainFrame.mTextArea.indentation=new ArrayList<Integer>();
		ArrayList<Integer> indentation=mainFrame.mTextArea.indentation;
		String bodyString=mainFrame.mTextArea.mainArea.getText();
		String[] splitAry=bodyString.split("\n");

		
		ArrayList<Character> operators=new ArrayList<Character>(15);
		for(var i:mainFrame.operators) operators.add(i);
		
		String finalString="";
		int len=splitAry.length;
		for(int i=0;i<len;i++) splitAry[i]=splitAry[i].trim();
		
		finalString+=splitAry[0]+"\n";
		indentation.add(0);
		//indenting the lines
		
		for(int i=1;i<len;i++) {
			int lenLast=splitAry[i-1].length(), j;
			int lenCur=splitAry[i].length();
			char cur='a',last='a';
			
			if(lenCur>0)cur=splitAry[i].charAt(0);
			if(lenLast>0) last=splitAry[i-1].charAt(lenLast-1);
			
			if(cur=='}'||cur==')'||cur==']') indentation.add(indentation.get(i-1)-1);
			else if(last=='{'||last=='['||last=='('||last==':') indentation.add(indentation.get(i-1)+1);
			else indentation.add(indentation.get(i-1));
			
			StringBuilder stringBuilder=new StringBuilder();
			if(lenCur>0) {
				stringBuilder.append(splitAry[i].charAt(0));
				
			}
			for(j=1;j<lenCur-2;j++) {
				char ch_1=splitAry[i].charAt(j-1);
				char ch1=splitAry[i].charAt(j);
				char ch2=splitAry[i].charAt(j+1);
				char ch_2=splitAry[i].charAt(j+2);
				
				if(operators.contains(ch1)&&operators.contains(ch2)) {
					if(ch_1!=' ')stringBuilder.append(" "+ch1+ch2);
					else if(ch_1==' ') {
						stringBuilder.append(ch1);
						stringBuilder.append(ch2);
					}
					if(ch_2!=' ')stringBuilder.append(" ");
					j++;
				}else if((ch1==';'||ch1==',')&&ch2!=' ')stringBuilder.append(ch1+" ");
				else {
					if(operators.contains(ch1)&&ch_1!=' '&&!operators.contains(ch_1)) stringBuilder.append(" ");
					stringBuilder.append(ch1);
					if(ch1=='='&&ch2!=' ')stringBuilder.append(" ");
				}
			}
			while(j<lenCur) stringBuilder.append(splitAry[i].charAt(j++));
			
			String temp="";
			lenCur=indentation.get(i);
			for(j=0;j<lenCur;j++)temp+='\t';
			
			
			
			finalString+=temp+stringBuilder+"\n";
		}
		
		mainFrame.mTextArea.mainArea.setText(finalString);
		
		
	}

}




//
//finalString+=splitAry[0]+"\n";
//indentation.add(0);
//int size=splitAry.length;
//for(int i=1;i<size;i++) {
//	
//	String str=splitAry[i].trim();
//	String i_1=splitAry[i-1].trim();
////indentation of current line will be decided by last line
////if [,{,( then increase by 1
//	int len=i_1.length();
//	if(len>0) {
//		char ch=i_1.charAt(len-1);
//
//		if(ch=='{'||ch=='['||ch=='('||ch==':') {
//			indentation.add(indentation.get(i-1)+1);		//increasing indentation		
//		}
//		
////if not form {[( then line indentation may decrease if it ends with })]
//		else if(str.length()>0) {
//			char first=str.charAt(0);
//			if(first=='}'||first==']'||first==')') {
//				indentation.add(indentation.get(i-1)-1); 	//decreasing indentation
//			}
////if line doesn;t end with }]) then indentation is same no need to change
//			else indentation.add(indentation.get(i-1));	
//		}
////if line is also same then also no need to chage the indentation
//		else indentation.add(indentation.get(i-1));
//	}
//	else if(str.length()>0) {
//		System.out.println(str);
//		char first=str.charAt(0);
//		if(first=='}'||first==']'||first==')') {
//			indentation.add(indentation.get(i-1)-1); 	//decreasing indentation
//		}indentation.add(indentation.get(i-1));
//	}
////none condition saticified then indentation is same
//	else indentation.add(indentation.get(i-1));
////adding line tp replaceable text
//	String temp="";
//	int ind=indentation.get(i);
//	for(int j=0;j<ind;j++) {
//		temp+="\t";
//	}
////adding to final text
//	finalString+=temp+str+"\n";
//}
//mainFrame.mTextArea.mainArea.setText(finalString);
