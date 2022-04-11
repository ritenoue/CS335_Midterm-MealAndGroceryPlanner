package MealMate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

 
/* 
 * ButtonDemo.java requires the following files:
 *   images/right.gif
 *   images/middle.gif
 *   images/left.gif
 */
public class MakeButton extends JPanel
                        implements ActionListener {
    protected JButton b1, b2, b3;
    
    public MakeButton() {
    	//ImageIcon leftButtonIcon = createImageIcon("images/right.gif");
        //ImageIcon middleButtonIcon = createImageIcon("images/middle.gif");
        //ImageIcon rightButtonIcon = createImageIcon("images/left.gif")
    	
        //b1 = new JButton("Disable middle button", leftButtonIcon);
        b1 = new JButton("View Pantry");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("view pantry");
        b1.setBackground(Color.decode("#CBF2CF"));
        b1.setFont(new Font("Lato", Font.BOLD, 40));

        //b2 = new JButton("Middle button", middleButtonIcon);
        b2 = new JButton("View Recipes");

        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);
        b2.setActionCommand("view recipe");
        b2.setBackground(Color.decode("#CBF2CF"));
        b2.setFont(new Font("Lato", Font.BOLD, 40));


 
        //b3 = new JButton("Enable middle button", rightButtonIcon);
        b3 = new JButton("View Grocery List");

        //Use the default text position of CENTER, TRAILING (RIGHT).
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand("view grocery list");
        b3.setEnabled(true);
        b3.setBackground(Color.decode("#CBF2CF"));
        b3.setFont(new Font("Lato", Font.BOLD, 40));


 
        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
 
        b1.setToolTipText("Click this button to view pantry.");
        b2.setToolTipText("Click this button to view recipes.");
        b3.setToolTipText("Click this button to view grocery lists.");
 
        //Add Components to this container, using the default FlowLayout.
        add(b1);
        add(b2);
        add(b3);
    }
 
    
    public void actionPerformed(ActionEvent e) {
  ////****THS IS THE GOOD GOODS :))****///
        if ("view pantry".equals(e.getActionCommand())) {  
            MealMate.viewPantry(false);
        } 
        if ("view recipe".equals(e.getActionCommand())) {  
            MealMate.viewRecipe(false);
        } 
        if ("view grocery list".equals(e.getActionCommand())) {  
            MealMate.viewGroceryList(false);
    	} else {
        	b2.setEnabled(true);
            b1.setEnabled(true);
            b3.setEnabled(true);
        }
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MakeButton.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    
    private static void createAndShowGUI() {
    	 
        //Create and set up the window.
        JFrame frame = new JFrame("MealMate");
        frame.setBounds(1000,1000,1000,1000); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //how to close program when you click X on popup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 

        //Create and set up the content pane.
        MakeButton newContentPane = new MakeButton();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
}
    
 
