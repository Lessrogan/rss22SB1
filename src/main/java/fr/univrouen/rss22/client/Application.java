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
import javax.swing.ButtonModel;
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
	private ButtonGroup methods;
	
	 
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
	     
	     send = new JButton("Send");
	     importer = new JButton("Import");
	     write = new JButton("Write");
	     settings = new JButton("Settings");
	     
	     methods = new ButtonGroup();
	     get = new JRadioButton("GET");
	     get.setActionCommand("get");
	     post = new JRadioButton("POST");
	     post.setActionCommand("post");

	     methods.add(get);
	     methods.add(post);
	     
	     url = new JTextField("127.0.0.1:8080/");
	     response = new JTextArea();
	     response.setEditable(false);
	        
	}
	private void placeComponents() {
		 JPanel mainPanel = new JPanel(new GridLayout(1,3)); {
			 JPanel txtPanel = new JPanel(new GridLayout(2,0)); {
				 txtPanel.add(url);
				 txtPanel.add(response);                                
			 }
			 JPanel buttonPanel = new JPanel(new GridLayout(4, 0)); {
				 JPanel methodPanel = new JPanel(new GridLayout(2,0)); {
					 methodPanel.add(get);
					 methodPanel.add(post);
				 }
				 buttonPanel.add(methodPanel);
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
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ButtonModel selection = methods.getSelection();
				System.out.println("[ SEND ]\n" + response.getText() + "\n(TO)\n" + url.getText() + "(" + (selection != null ? selection.getActionCommand(): "none") + ")");
			}
		});
		
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
