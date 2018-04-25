import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;

public class finalProject {
    File records;
    public finalProject() {
        // Creating the Frame
        JFrame frame = new JFrame("Final Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        /**
         *  Panel
         */
        JPanel search = new JPanel(new FlowLayout());
        JPanel display = new JPanel(new GridLayout(0,1));
        JPanel add = new JPanel(new FlowLayout());


        /**
         *  Menu Bar
         */
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenuItem m11 = new JMenuItem("Open");
        mb.add(m1);
        mb.add(Box.createHorizontalGlue());
        m1.add(m11);

        /**
         *  File Chooser
         */
        JFileChooser fc = new JFileChooser();
        m11.addActionListener(e -> {
            int returnValue = fc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                records = fc.getSelectedFile();
            }
        });

        /**
         *  Table
         */
        JTable table = new JTable(50, 2);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMaximumSize(new Dimension(800,300));
        display.add(scrollPane);
        
        /**
         *  AddBar
         *  
         *  This add bar add a record to the hashmap
         */
        Button addB = new Button("Add");
        TextField nameTF = new TextField(20);
        TextField phoneTF = new TextField(20);
        add.add(new Label("Name"));
        add.add(nameTF);
        add.add(new Label("Phone number"));
        add.add(phoneTF);
        add.add(addB);
        

        /** 
         *  SearchBar
         *  
         *  This search bar searches records against the hashmap
         *  for names and phone numbers
         */
        Button searchB = new Button("Search");
        TextField searchTF = new TextField(60);
        search.add(searchTF);
        search.add(searchB);



        


        frame.add(mb);  // Menu bar
        frame.add(display);   // Table
        frame.add(add);     // Add bar
        frame.add(search);  // Search bar
        frame.setVisible(true);      
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new finalProject();
        });
    }
}
