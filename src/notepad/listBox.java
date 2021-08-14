package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

class listBox extends JDialog{
	JScrollPane fontScrollPane;
	JList<String>fontJList;
	Font notepadFont=new Font("Arial",0,15);
	JList<String> stylesJList;
	JLabel previewJLabel=new JLabel();
	JComboBox<Integer> sizeCombo;
	
	listBox(){
		notepadFont=mainFrame.mTextArea.mainArea.getFont();
	}
	
	public void listBar() {
		//accessing the fonts from system
		List<String> lst=new ArrayList<String>();
		Font[] fntFonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for(int i=0;i<fntFonts.length;i++) {
			lst.add(fntFonts[i].getName());
		}
		//creating JList form the got fonts
		fontJList=new JList<>(lst.toArray(new String[lst.size()]));
		fontJList.setVisibleRowCount(10);
		fontJList.setFixedCellWidth(200);
		Font listFont=new Font("Arial",0,14);
		fontJList.setFont(listFont);
		fontJList.setBorder(BorderFactory.createLineBorder(Color.black));
		//action listner to change the font style
		fontJList.addListSelectionListener((event)->{
			notepadFont=new Font(fontJList.getSelectedValue(),notepadFont.getStyle(),notepadFont.getSize());
			previewJLabel.setFont(notepadFont);
		});
		
		//scroll bar for list box
		 fontScrollPane=new JScrollPane(fontJList);
	}
	
	public void setFontSize() {
		sizeCombo=new JComboBox<>();
		for(int i=0;i<114;i+=3)sizeCombo.addItem(i);
//		sizeCombo.setSize(200,50);
		sizeCombo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5,20,5,20)));
		sizeCombo.setFocusable(false);
		sizeCombo.setEditable(true);
		Font listFont=new Font("Arial",0,14);
		sizeCombo.setFont(listFont);
//		panel.add(sizeCombo);
		sizeCombo.addActionListener((event)->{
			notepadFont=new Font(notepadFont.getName(),notepadFont.getStyle(),(int) sizeCombo.getSelectedItem());
			previewJLabel.setFont(notepadFont);
		});
	}
	
//	public void styleList
	public void setStyleList() {
		String[] str={"Regural","Bold","Italic","Bold Italic"};
		stylesJList=new JList<String>(str);
		stylesJList.setFixedCellWidth(150);
		stylesJList.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),BorderFactory.createEmptyBorder(10,10,10,10)));
		Font listFont=new Font("Arial",0,14);
		stylesJList.setFont(listFont);
		stylesJList.addListSelectionListener((event)->{
			String string=stylesJList.getSelectedValue();
			if(string.equals("Regural")) {
				notepadFont=new Font(notepadFont.getName(),0,(int) sizeCombo.getSelectedItem());
			}else if(string.equals("Bold")) {
				notepadFont=new Font(notepadFont.getName(),Font.BOLD,(int) sizeCombo.getSelectedItem());
			}else if(string.equals("Italic")) {
				notepadFont=new Font(notepadFont.getName(),Font.ITALIC,(int) sizeCombo.getSelectedItem());
			}else {
				notepadFont=new Font(notepadFont.getName(),Font.BOLD+Font.ITALIC,(int) sizeCombo.getSelectedItem());
			}
			previewJLabel.setFont(notepadFont);
		});
	}
	public void setPrevie(){
		
		previewJLabel.setText("AaBbYyZz");
		previewJLabel.setHorizontalAlignment(SwingConstants.CENTER);;
		previewJLabel.setBorder(BorderFactory.createTitledBorder("Preview"));
		previewJLabel.setMinimumSize(new Dimension(300, 120));
		previewJLabel.setPreferredSize(new Dimension(300, 120));
		previewJLabel.setMaximumSize(new Dimension(300, 120));
		previewJLabel.setFont(new Font("Arial",0,15));
	}
	
	public GridBagConstraints createConstraints(int width,int height, int gridX, int gridY, int inset) {
		GridBagConstraints basicBagConstraints=new GridBagConstraints();
		basicBagConstraints.gridwidth=width;
		basicBagConstraints.gridheight=height;
		basicBagConstraints.gridx=gridX;
		basicBagConstraints.gridy=gridY;
		basicBagConstraints.insets=new Insets(inset, inset, inset, inset);
		return basicBagConstraints;
	}
	
	public void setItems() {
		JPanel pan=new JPanel();
		GridBagLayout gbdBagLayout=new GridBagLayout();
		int[] colWidth={200,150};
		gbdBagLayout.columnWidths= colWidth;
		int[] rowHeight= {50,50,100,100};
		gbdBagLayout.rowHeights=rowHeight;
		
		
		listBar();
		pan.setLayout(gbdBagLayout);
		GridBagConstraints fontlstBagConstraints=createConstraints(1, 3, 0, 0, 10);
		pan.add(fontScrollPane,fontlstBagConstraints);
		
		setFontSize();
		GridBagConstraints sizeBagConstraints=createConstraints(1, 1, 1, 0, 10);
		pan.add(sizeCombo,sizeBagConstraints);
		
		setStyleList();
		GridBagConstraints styBagConstraints=createConstraints(1, 2, 1, 1, 10);
		pan.add(stylesJList,styBagConstraints);
		
		setPrevie();
		GridBagConstraints labelBagConstraints=createConstraints(2, 2, 0, 3, 40);

		pan.add(previewJLabel,labelBagConstraints);

		fontJList.setSelectedIndex(mainFrame.node.getInt("fontInd", 4));
		sizeCombo.setSelectedIndex(mainFrame.node.getInt("sizeInd", 5));
		stylesJList.setSelectedIndex(mainFrame.node.getInt("styleInd", 0));
		
		// control button
		JButton okButton = new JButton("Ok");
		JButton applyButton=new JButton("Apply");
		okButton.setFocusable(false);
		okButton.setMinimumSize(new Dimension(100, 30));
		okButton.setPreferredSize(new Dimension(100, 30));
		okButton.setMaximumSize(new Dimension(100, 30));
		okButton.setFont(new Font("Arial",0,13));
		applyButton.setFocusable(false);
		applyButton.setMinimumSize(new Dimension(100, 30));
		applyButton.setPreferredSize(new Dimension(100, 30));
		applyButton.setMaximumSize(new Dimension(100, 30));
		applyButton.setFont(new Font("Arial",0,13));
		
		
		//okButton even
		okButton.addActionListener((event)->{

			mainFrame.node.putInt("fontInd", fontJList.getSelectedIndex());
			mainFrame.node.put("fontName",fontJList.getSelectedValue());
			mainFrame.node.putInt("stylesVal", notepadFont.getStyle());
			mainFrame.node.putInt("styleInd", stylesJList.getSelectedIndex());
			mainFrame.node.putInt("sizeVal", (int) sizeCombo.getSelectedItem());
			mainFrame.node.putInt("sizeInd", (int) sizeCombo.getSelectedIndex());
			
			mainFrame.mTextArea.mainArea.setFont(notepadFont);
			setVisible(false);
		});
		
		
		JPanel btnPanel=new JPanel();
		
		GridBagLayout btnGBD=new GridBagLayout();
		
		btnPanel.setLayout(btnGBD);
		
		GridBagConstraints btnok=createConstraints(1, 1, 0, 0, 10);
		GridBagConstraints btnapply=createConstraints(1, 1, 1, 0, 10);


		btnPanel.add(okButton,btnok);
		btnPanel.add(applyButton,btnapply);
		setLayout(new BorderLayout());
		add(pan,BorderLayout.CENTER);
		add(btnPanel,BorderLayout.SOUTH);
		setSize(450,520);
		setResizable(false);
	}
	
}
