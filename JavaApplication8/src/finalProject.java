import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class finalProject {
    File records;
    public finalProject() {
        // Creating the Frame
        JFrame frame = new JFrame("Final Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        
        /**
         * Creating Map objects
         * phoneBook key = name val = number
         * rphoneBook key = number val = name
         */
        
        Map<String,String>phoneBook;
        phoneBook = new HashMap<>();
        
        Map<String,String>rphoneBook;
        rphoneBook = new HashMap<>();

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
        
        //function to add button
        addB.addActionListener((ActionEvent e) -> 
        {
            if(nameTF.getText().isEmpty() || phoneTF.getText().isEmpty())
            {
                System.out.println("text is not empty");
                JOptionPane.showMessageDialog(null,"Please Enter A Name And PhoneNumber");
            }
            else
            {
                System.out.println("info saved");
                phoneBook.put(nameTF.getText(), phoneTF.getText());
                rphoneBook.put(phoneTF.getText(), nameTF.getText()); 
                
                writeMapToFile(phoneBook,"output.txt");

                nameTF.setText("");
                phoneTF.setText("");
                System.out.println(phoneBook.keySet());
            }
            
        });
        

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
    
    /**
     * This method will write to a file
     * @param d
     * Takes in HashMap it will write to the file from
     * @param filename 
     * name of the file it will store information too
     */
     public static void writeMapToFile(Map<String,String>d,String filename)
     {
        System.out.println("method called");
        System.out.println(filename);
        try 
        {
            System.out.println("we in dis");
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            Set<String> keys = d.keySet();
            for(String name: keys)
            {
                writer.println(name+"|"+d.get(name));
            }
            writer.close();
        } 
        catch (Exception e)
        {
            System.out.println("Problem writing to file: "+e);
        }
    }
     
    /**
     * This method will return a Map that can read from a file
     * @param filename
     * @return 
     */
    public static Map<String,String> readFile( String filename )
    {
        Map <String,String> x = new HashMap<String,String>();
        try
        {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
          String line = scanner.nextLine();
          int delimiter = line.indexOf("|");
          String key = line.substring(0,delimiter);
          String value = line.substring(delimiter+1);
          x.put(key,value);
        }
        scanner.close();
        } 
        catch (FileNotFoundException e)
        {
            System.out.println("Problem reading map from file "+e);
        }
        return x;
    } 
    
    
    

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new finalProject();
        });
    }
}
