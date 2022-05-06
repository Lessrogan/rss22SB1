package fr.univrouen.rss22.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Application {

	//ATTRIBUTS
	
	private RequestManager requestManager;
	private JFrame mainFrame;
	private JButton send;
	private JButton importer;
	private JFileChooser fileChooser;
	private JButton write;
	private JButton settings;
	private JRadioButton get;
	private JRadioButton post;
	private JRadioButton delete;
	private JTextField url;
	private JTextArea response;
	private JScrollPane responseScrollPane;
	private ButtonGroup methods;
	private JLabel responseStatus;
	
	 
	 //CONSTRUCTEURS
	 
	public Application() {
		createModel();
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
	 
	private void createModel() {
		requestManager = new RequestManager();
	}
	 
	private void createView() {
		 mainFrame = new JFrame("Application");
	     final int frameWidth = 750;
	     final int frameHeight = 340;
	     mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
	     
	     send = new JButton("Send");
	     importer = new JButton("Import");
	     fileChooser = new JFileChooser();
	     write = new JButton("Write");
	     settings = new JButton("Settings");
	     
	     methods = new ButtonGroup();
	     get = new JRadioButton("GET");
	     get.setActionCommand("GET");
	     if (methods.getSelection() == null) get.setSelected(true);
	     post = new JRadioButton("POST");
	     post.setActionCommand("POST");
	     delete = new JRadioButton("DELETE");
	     delete.setActionCommand("DELETE");

	     methods.add(get);
	     methods.add(post);
	     methods.add(delete);
	     
	     url = new JTextField("http://127.0.0.1:8080/");
	     response = new JTextArea("Server response");
		 response.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	     response.setEditable(false);
	     responseScrollPane = new JScrollPane(response);
	     responseStatus = new JLabel("Status :");
	        
	}
	
	private void placeComponents() {
		 JPanel mainPanel = new JPanel(new GridLayout(1,2)); {
			 JPanel txtPanel = new JPanel(new BorderLayout()); {
				 txtPanel.add(url, BorderLayout.NORTH);
				 txtPanel.add(responseScrollPane, BorderLayout.CENTER);
				 txtPanel.add(responseStatus, BorderLayout.SOUTH);
			 }
			 JPanel buttonPanel = new JPanel(new GridLayout(0, 1)); {
				 JPanel methodPanel = new JPanel(new GridLayout(1,0)); {
					 methodPanel.add(get);
					 methodPanel.add(post);
					 methodPanel.add(delete);
				 }
				 buttonPanel.add(methodPanel);
				 buttonPanel.add(importer);
				 //buttonPanel.add(write);
				 buttonPanel.add(send);
				 buttonPanel.add(settings);
				 
				 
			 }
			mainPanel.add(txtPanel);
			mainPanel.add(buttonPanel);
		 }
		 mainFrame.add(mainPanel);
		
	}
	private void createController() {
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ButtonModel selection = methods.getSelection();
				HTTPResponse httpResponse = requestManager.send(url.getText(), selection.getActionCommand());
				response.setText(httpResponse.getContent());
				responseStatus.setText("Status: " + String.valueOf(httpResponse.getStatus()));
			}
		});
		importer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(new JFrame());

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            requestManager.setFile(file);
		            importer.setText("Import\n" + "(" + file.getName() + ")");
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
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
