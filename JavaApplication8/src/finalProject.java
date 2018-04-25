import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class finalProject {
    /**
     * Creating Map objects
     * phoneBook key = name val = number
     * rphoneBook key = number val = name
     */
    Map<String,String> fileBook = new HashMap<>();
    DefaultTableModel model = new DefaultTableModel();

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
                fileBook = readFile(fc.getSelectedFile().getName());
                // Fill in table
                for(Map.Entry<String, String> entry : fileBook.entrySet()) {
                    model.addRow(new Object[]{entry.getKey(), entry.getValue()});
                }
            }
        });

        /**
         *  Table
         */
        JTable table = new JTable();
        table.setModel(model);
        model.addColumn("Name");
        model.addColumn("Phone number");

        for(Map.Entry<String, String> entry : fileBook.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

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
                fileBook.put(nameTF.getText(), phoneTF.getText());

                writeMapToFile(fileBook,"output.txt");
                model.addRow(new Object[]{nameTF.getText(), phoneTF.getText()});

                nameTF.setText("");
                phoneTF.setText("");
                System.out.println(fileBook.keySet());
            }
            
        });
        

        /** 
         *  SearchBar
         *  
         *  This search bar searches records against the hashmap
         *  for names and phone numbers
         */
        Button searchB = new Button("Search");
        TextField searchTF = new TextField(50);
        search.add(searchTF);
        search.add(searchB);

        // Function to search button
        searchB.addActionListener(e -> {
            String criteriaName;
            int criteriaPhone;
            String input;
            // Checking pre-condition
            if (searchTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame.getContentPane(),
                                            "Please search either a name or phone number or both",
                                            "Warning",
                                            JOptionPane.WARNING_MESSAGE);
                return;
            }
            else {
                // Removes all non-word and non-whitespace
                input = searchTF.getText().replaceAll("[^\\w\\s]", "");
            }
            // Parsing
            // Removes all non-characters and non-whitespace
            criteriaName = input.replaceAll("[^a-zA-Z\\s]", "");
            System.out.println(criteriaName);
            // Removes all non-digits and checks if its 10 digits
            criteriaPhone = Integer.parseInt(input.replaceAll("\\D", "").isEmpty()?
                            "-1" : input.replaceAll("\\D", "").length() != 10?
                            "-1" : input.replaceAll("\\D", ""));
            System.out.println(criteriaPhone);
        });

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
            while (scanner.hasNext())
            {
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
