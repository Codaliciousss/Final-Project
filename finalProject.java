import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class finalProject {
    public static void main(String args[]) {

        // Creating the Frame
        JFrame frame = new JFrame("Final Projedct");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        // Panels

        JPanel search = new JPanel(); // the panel is not visible in output
        JPanel display = new JPanel(new GridLayout(0,1));



        // Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        mb.add(m1);
        JMenuItem m11 = new JMenuItem("Open");
        m1.add(m11);
        frame.add(mb);

        // Table w scroll pane

        JTable table = new JTable(50, 2);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMaximumSize(new Dimension(800,300));
        display.add(scrollPane);
        frame.add(display);

        frame.pack();
        frame.setVisible(true);



        // search bar (add event listener)
        JButton label = new JButton("Search");
        JTextField tf = new JTextField(10);

        // JButton reset = new JButton("Reset");
        search.add(label);
        search.add(tf);
        frame.add(search);
        // panel.add(send);
        // panel.add(reset);

        // JPanel


        //JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        // frame.getContentPane().add(BorderLayout.SOUTH, search);
      //  frame.getContentPane().add(BorderLayout.[]k[]], mb);
        //frame.getContentPane().add(BorderLayout.CENTER, ta);
        // frame.setVisible(true);
    }
}
