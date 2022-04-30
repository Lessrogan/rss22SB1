package fr.univrouen.rss22.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Application {

	//ATTRIBUTS
	
	private JFrame mainFrame;
	private JButton send;
	private JButton importer;
	private JButton write;
	private JButton settings;
	private JRadioButton get;
	private JRadioButton post;
	private JTextField url;
	private JTextArea response;
	
	
	 
	 //CONSTRUCTEURS
	 
	public Application() {
		//createModel();
		createView();
	    placeComponents();
	    createController();
	 }
	 
	 //COMMANDES
	 
	 public void display() {
	        //refresh();
	        mainFrame.pack();
	        mainFrame.setLocationRelativeTo(null);
	        mainFrame.setVisible(true);
	    }

	//OUTILS
	 
	private void createView() {
		 mainFrame = new JFrame("Application");
	     final int frameWidth = 750;
	     final int frameHeight = 340;
	     mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
	     
	     send = new JButton("send");
	     importer = new JButton("Import");
	     write = new JButton("Write");
	     settings = new JButton("Settings");
	     
	     get = new JRadioButton("GET");
	     post = new JRadioButton("POST");
	     
	     url = new JTextField();
	     response = new JTextArea();
	        
	}
	private void placeComponents() {
		 JPanel mainPanel = new JPanel(new GridLayout(1,3)); {
			 JPanel txtPanel = new JPanel(new GridLayout(2,0)); {
				 txtPanel.add(url);
				 txtPanel.add(response);
			 }
			 JPanel buttonPanel = new JPanel(new GridLayout(4, 0)); {
				 JPanel methodePanel = new JPanel(new GridLayout(2,0)); {
					 methodePanel.add(get);
					 methodePanel.add(post);
				 }
				 buttonPanel.add(methodePanel);
				// buttonPanel.add(send);
				 buttonPanel.add(importer);
				 buttonPanel.add(write);
				 buttonPanel.add(settings);
				 
				 
			 }
			mainPanel.add(txtPanel);
			mainPanel.add(buttonPanel);
			mainPanel.add(send);
		 }
		 mainFrame.add(mainPanel);
		
	}
	private void createController() {
		// TODO Auto-generated method stub
		
	}
	
    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application().display();
            }
        });
    }
}
