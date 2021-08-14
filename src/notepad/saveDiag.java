package notepad;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.sun.media.sound.Toolkit;

class saveDiag extends JDialog{
	public JButton saveButton=new JButton("Save");
	public JButton donotButton=new JButton("Don't Save");
	public JButton cancelButton=new JButton("Cancel");
	
	public GridBagConstraints createLayout(int width,int height,int x,int y,int inset) {
		GridBagConstraints gbd=new GridBagConstraints();
		gbd.gridwidth=width;
		gbd.gridheight=height;
		gbd.gridx=x;
		gbd.gridy=y;
		gbd.insets=new Insets(inset, inset, inset, inset);
		return gbd;
	}
	public saveDiag() {
		Image icoImage =new ImageIcon("icon.png").getImage();
		setIconImage(icoImage);
		GridBagLayout digLayout=new GridBagLayout();
		int rows[]= {100,100};
		int column[]= {100,100,100,100};
		setLayout(digLayout);
		JLabel savefileJLabel=new JLabel("<html><h3>you haven't saved you work</h3></html>");
		savefileJLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10),BorderFactory.createEmptyBorder(10,10,10,10)));
		setModal(true);
		setSize(500,200);
		
		//button properties
		saveButton.setMinimumSize(new Dimension(100,35));
		saveButton.setMaximumSize(new Dimension(100,35));
		saveButton.setPreferredSize(new Dimension(100,35));
		saveButton.setFocusable(false);
		

		donotButton.setMinimumSize(new Dimension(100,35));
		donotButton.setMaximumSize(new Dimension(100,35));
		donotButton.setPreferredSize(new Dimension(100,35));
		donotButton.setFocusable(false);
		

		cancelButton.setMinimumSize(new Dimension(100,35));
		cancelButton.setMaximumSize(new Dimension(100,35));
		cancelButton.setPreferredSize(new Dimension(100,35));
		cancelButton.setFocusable(false);
		
		Dimension winDimension=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((winDimension.width-getWidth())/2,(winDimension.height-getHeight())/2);
		
		//setting buttons;
		GridBagConstraints labelConstraints=createLayout(4, 1, 0, 0, 10);
		add(savefileJLabel,labelConstraints);
		GridBagConstraints saveConstraints=createLayout(1, 1, 1, 1, 10);
		GridBagConstraints donotConstraints=createLayout(1, 1, 2, 1, 10);
		GridBagConstraints cancelConstraints=createLayout(1, 1, 3, 1, 10);
		add(saveButton,saveConstraints);
		add(donotButton,donotConstraints);
		add(cancelButton,cancelConstraints);
		
		donotButton.addActionListener((event)->{
			setVisible(false);
			System.exit(0);
		});
		saveButton.addActionListener((event)->{		
			menubar.saveFile();
			setVisible(false);
			System.exit(0);
		});
		cancelButton.addActionListener((event)->{
			setVisible(false);
		});
	}
}