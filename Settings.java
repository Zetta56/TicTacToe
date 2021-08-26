import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

public class Settings extends JDialog {
   private Container pane;
   private GUI gui;
   private JSpinner sizeField;
   private JSpinner fractalField;

   public Settings(GUI gui) {
      super(gui);
      // Window
      this.gui = gui;
      this.setTitle("Settings");
      this.setSize(300, 100);
      this.setModalityType(ModalityType.APPLICATION_MODAL); // Blocks main GUI
      this.setLocationRelativeTo(this.getParent()); // Centered over GUI
      this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close only the settings window

      // Content Layout
      pane = this.getContentPane();
      pane.setLayout(new BorderLayout());
      this.initializeInputs();
      this.initializeButtons();
      this.applySettings();
   }
   
   public void initializeInputs() {
      // Create a form panel
      JPanel panel = new JPanel();
      panel.setLayout(new SpringLayout());
      
      // Size Field
      sizeField = new JSpinner();
      sizeField.setValue(3);
      // Uses JLabel's trailing (right) side to position it
      JLabel sizeLabel = new JLabel("Size: ", JLabel.TRAILING);
      sizeLabel.setLabelFor(sizeField);
      panel.add(sizeLabel);
      panel.add(sizeField);
      
      // Fractal Field
      // fractalField = new JSpinner();
      // fractalField.setValue(1);
      // JLabel fractalLabel = new JLabel("Fractals: ", JLabel.TRAILING);
      // fractalLabel.setLabelFor(fractalField);
      // panel.add(fractalLabel);
      // panel.add(fractalField);
      
      // Confirm and add centered form layout (w/ rows, columns, padding, etc.)
      SpringUtilities.makeCompactGrid(panel, 1, 2, 5, 5, 5, 5);
      pane.add(panel, BorderLayout.CENTER);
   }
   
   public void initializeButtons() {
      // Row of Buttons
      JPanel panel = new JPanel();
      pane.add(panel, BorderLayout.PAGE_END);
      
      // Cancel Button
      JButton cancel = new JButton("Cancel");
      cancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // Reset field values if they were changed before canceling
            sizeField.setValue(gui.getOrder());
            dispose();  // Close settings window
         }
      });
      panel.add(cancel);
      
      // Submit Button
      JButton submit = new JButton("Submit");
      submit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            applySettings();
         }
      });
      panel.add(submit);
   }

   public void applySettings() {
      gui.setOrder((int)sizeField.getValue());
      // Re-create board if one already exists
      if(gui.getBoard() != null) {
         gui.initializeBoard();
      }
      dispose();
   }
}