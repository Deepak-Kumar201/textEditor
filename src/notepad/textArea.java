package notepad;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

class textActions extends KeyAdapter{
	public Deque<String> undoDeque=new LinkedList<>();
	public Deque<String> redoDeque=new LinkedList<>();
	public ArrayList<String> hints=new ArrayList<>();
	public boolean ignore=false;
	public String word="";
	@Override
	public void keyReleased(KeyEvent e) {
		char ch=e.getKeyChar();
		int len=mainFrame.mTextArea.mainArea.getText().length();
		if(ch==' '||ch=='\n') {
			if(undoDeque.size()>1000) undoDeque.removeFirst();
			undoDeque.addLast(mainFrame.mTextArea.mainArea.getText());
			if(!ignore) {
				if(word.length()>0)hints.add(word);
			}
			word="";
		}
		
		redoDeque=new LinkedList<>();
		if(ch=='\'') {
			mainFrame.mTextArea.mainArea.replaceSelection("\'");
			mainFrame.mTextArea.mainArea.setCaretPosition(mainFrame.mTextArea.mainArea.getCaretPosition()-1);
		}else if(ch=='\"') {
			mainFrame.mTextArea.mainArea.replaceSelection("\"");
			mainFrame.mTextArea.mainArea.setCaretPosition(mainFrame.mTextArea.mainArea.getCaretPosition()-1);
		}else if(ch=='(') {
			mainFrame.mTextArea.mainArea.replaceSelection(")");
			mainFrame.mTextArea.mainArea.setCaretPosition(mainFrame.mTextArea.mainArea.getCaretPosition()-1);
		}else if(ch=='{') {
			mainFrame.mTextArea.mainArea.replaceSelection("}");
			mainFrame.mTextArea.mainArea.setCaretPosition(mainFrame.mTextArea.mainArea.getCaretPosition()-1);
		}else if(ch=='[') {
			mainFrame.mTextArea.mainArea.replaceSelection("]");
			mainFrame.mTextArea.mainArea.setCaretPosition(mainFrame.mTextArea.mainArea.getCaretPosition()-1);
		}
		
		if(ch=='\n') {
			int ind=mainFrame.mTextArea.mainArea.getSelectionStart();
			char string=mainFrame.mTextArea.mainArea.getText().substring(ind-2,ind-1).charAt(0);
			
			int line=mainFrame.mTextArea.getLineNo()+1;
			if(string=='{'||string=='['||string=='('||string==':') {
				System.out.println(line);
				if(line>0) {
					mainFrame.mTextArea.indentation.add(line,mainFrame.mTextArea.indentation.get(line-1)+1);
					
					mainFrame.mTextArea.indentation.add(line+1,mainFrame.mTextArea.indentation.get(line-1));

					String replace="";
					int level=mainFrame.mTextArea.indentation.get(line);
					for(int i=0;i<level;i++) mainFrame.mTextArea.mainArea.replaceSelection("\t");
					replace+='\n';
					level=mainFrame.mTextArea.indentation.get(line+1);
					for(int i=0;i<level;i++) {
						replace+='\t';
					}
					
					mainFrame.mTextArea.mainArea.replaceSelection(replace);
					mainFrame.mTextArea.mainArea.setCaretPosition(mainFrame.mTextArea.mainArea.getCaretPosition()-level-1);
					
					
				}
			}else {
				if(line!=0)mainFrame.mTextArea.indentation.add(line,mainFrame.mTextArea.indentation.get(line-1));
				else mainFrame.mTextArea.indentation.add(line,0);
				String replace="";
				int level=mainFrame.mTextArea.indentation.get(line);
				for(int i=0;i<level;i++) mainFrame.mTextArea.mainArea.replaceSelection("\t");
				mainFrame.mTextArea.mainArea.replaceSelection(replace);
			}
			
		}
	}
}


public class textArea {
	public JTextArea mainArea;
	public JScrollPane txtScrollPane;
	public textActions txtAct;
	public ArrayList<Integer> indentation=new ArrayList<>();
	public textArea() {
		String fontStyle=mainFrame.node.get("fontName", "Arial");
		int style=mainFrame.node.getInt("stylesVal", 0);
		int size=mainFrame.node.getInt("sizeVal", 15);
		indentation.add(0);
		mainArea=new JTextArea();
		mainArea.setFont(new Font(fontStyle,style,size));
		mainArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),BorderFactory.createEmptyBorder(5,5,5,5)));
		txtAct=new textActions();
		mainArea.addKeyListener(txtAct);
		txtScrollPane=new JScrollPane(mainArea);
		mainArea.setTabSize(2);
		
	}
	
	public void commentOut() {
		int st;
		int start = mainArea.getSelectionStart();
		int end=mainArea.getSelectionEnd();
		int lineEnd=start;

		String fullCont=mainArea.getText();
		String toReplace="";
		int len=fullCont.length();
		
		while(start-1>=0&&fullCont.charAt(start-1)!='\n') start--;
		st=start;
		
		while(lineEnd<=end) {
			while(start-1>=0&&fullCont.charAt(start-1)!='\n') start--;
			while(lineEnd<len&&fullCont.charAt(lineEnd)!='\n') lineEnd++;
			toReplace+="// "+fullCont.substring(start,lineEnd)+"\n";
			lineEnd++;start=lineEnd;
		}

		mainArea.select(st, lineEnd);
		mainArea.replaceSelection(toReplace);	
	}
	
	public void uncomment() {
		int st;
		int start = mainArea.getSelectionStart();
		int end=mainArea.getSelectionEnd();
		int lineEnd=start;

		String fullCont=mainArea.getText();
		String toReplace="";
		int len=fullCont.length();
		
		while(start-1>=0&&fullCont.charAt(start-1)!='\n') start--;
		st=start;
		
		while(lineEnd<=end) {
			while(start-1>=0&&fullCont.charAt(start-1)!='\n') start--;
			while(lineEnd<len&&fullCont.charAt(lineEnd)!='\n') lineEnd++;
			String tempString=fullCont.substring(start,lineEnd);
			if(tempString.charAt(2)==' ')toReplace+=tempString.substring(3)+"\n";
			else toReplace+=tempString.substring(2)+"\n";
			lineEnd++;start=lineEnd;
		}

		mainArea.select(st, lineEnd);
		mainArea.replaceSelection(toReplace);	
	}
	
	public int getLineNo() {
		String[] arr=mainFrame.mTextArea.mainArea.getText().split("\n");
		int len=0, req=mainFrame.mTextArea.mainArea.getSelectionStart();
		for(int i=0;i<arr.length;i++) {
			len+=arr[i].length()+1;
			if(len>=req) return i;
		}
		return 0;
	}
	
	
}
 