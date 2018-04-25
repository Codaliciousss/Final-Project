import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class finalProject {
    /**
     * Creating Map objects
     * fileBook holds all records of the file
     */
    private Map<String,String> fileBook = new HashMap<>();
    private DefaultTableModel model = new DefaultTableModel();

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
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        m11.addActionListener(e -> {
            int returnValue = fc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    fileBook = readFile(fc.getSelectedFile().getName());
                    // Clear and fill in table
                    model.setRowCount(0);
                    for(Map.Entry<String, String> entry : fileBook.entrySet()) {
                        model.addRow(new Object[]{entry.getKey(), entry.getValue()});
                    }
                } catch (Exception ex) {
                    System.out.println("Problem reading map from file " + ex);
                }
            }
        });

        /**
         *  Table
         *  
         *  This creates a non-editable table for displaying info
         */
        JTable table = new JTable() {
            public boolean isCellEditable(int row, int column) {                
                return false;               
            }
        };
        table.setModel(model);
        model.addColumn("Name");
        model.addColumn("Phone number");
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
        
        // Function to add button
        addB.addActionListener((ActionEvent e) -> 
        {
            if(nameTF.getText().isEmpty() || phoneTF.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(frame.getContentPane(),
                                            "Please enter a name and phone number",
                                            "Missing info",
                                            JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                String name = nameTF.getText().replaceAll("[^\\w\\s]", "")
                                                .replaceAll("[^a-zA-Z\\s]", "");
                String phone = phoneTF.getText().replaceAll("\\D", "").length() == 10?
                                phoneTF.getText().replaceAll("\\D", ""): "";

                if (!name.isEmpty() && !phone.isEmpty()) {
                    int n = JOptionPane.showConfirmDialog(frame.getContentPane(),
                                                "Are you sure you want to add the following contact?\n" +
                                                name + "\n" + phone,
                                                "Confirmation",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);
                    if (n == JOptionPane.YES_OPTION) {
                        fileBook.put(name, phone);
                        writeMapToFile(fileBook,"output.txt");
                        model.addRow(new Object[]{name, phone});
                        nameTF.setText("");
                        phoneTF.setText("");
                        System.out.println(fileBook.keySet());
                    }
                    else {
                        return;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(frame.getContentPane(),
                                            "Please enter a valid name and phone number(10 digits)",
                                            "Warning",
                                            JOptionPane.WARNING_MESSAGE);
                }      
            }
            
        });
        

        /** 
         *  SearchBar
         *  
         *  This search bar searches records against the hashmap
         *  for names and phone numbers.
         *  The search method loops through the hashmap to look for
         *  similar name and phone number.
         */
        Button searchB = new Button("Search");
        TextField searchTF = new TextField(50);
        search.add(searchTF);
        search.add(searchB);

        // Function to search button
        searchB.addActionListener(e -> {
            String criteriaName;
            String criteriaPhone;
            String input;
            // Checking pre-condition
            if (searchTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame.getContentPane(),
                                            "Please search either a name or phone number or both",
                                            "Warning",
                                            JOptionPane.WARNING_MESSAGE);
                return;
            }
            else if (fileBook.isEmpty()) {
                JOptionPane.showMessageDialog(frame.getContentPane(),
                                            "There are no available records to be searched\n" +
                                            "Please import records first",
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
            // Removes all non-digits and checks if it's 10 digits
            criteriaPhone = input.replaceAll("\\D", "").length() == 10?
                            input.replaceAll("\\D", "") :
                            "-1";
            // Begin Search
            model.setRowCount(0);
            // Searching with name
            if (criteriaName.isEmpty()) {
                System.out.println("Not searching this");
            }
            else {
                // Search using pattern-matcher
                // Searching for [Aa]*
                Pattern pattern = Pattern.compile("[" + criteriaName.substring(0,1).toUpperCase() +
                                                criteriaName.substring(0,1).toLowerCase() + "]" +
                                                criteriaName.substring(1) + ".*");
                System.out.println("Searching for " + criteriaName);
                for(Map.Entry<String, String> entry : fileBook.entrySet()) {
                    Matcher matcher = pattern.matcher(entry.getKey());
                    if (matcher.matches()) {
                        model.addRow(new Object[]{entry.getKey(), entry.getValue()});
                    }
                }
            }
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
     public void writeMapToFile(Map<String,String>d,String filename)
     {
        System.out.println("method called: writeMapToFile");
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
    public static Map<String,String> readFile( String filename ) throws Exception
    {
        Map <String,String> x = new HashMap<String,String>();
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
        return x;
    } 
    
    
    

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new finalProject();
        });
    }
}
