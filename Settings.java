import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

// JDialog is a top-level container for pop-up modals
public class Settings extends JDialog {
   private Container pane;
   private GUI gui;
   private JSpinner sizeField;
   private ButtonGroup opponentField;

   public Settings(GUI gui, String title) {
      super(gui);
      // Window
      this.gui = gui;
      this.setTitle(title);
      this.setSize(300, 220);
      this.setResizable(false);
      this.setModalityType(ModalityType.APPLICATION_MODAL); // Blocks main GUI
      this.setLocationRelativeTo(this.getParent()); // Centered over GUI
      this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close only the settings window
      this.getRootPane().setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createEmptyBorder(10, 10, 10, 10), // Margin
         BorderFactory.createTitledBorder("Configuration")
      ));

      // Content Layout
      pane = this.getContentPane();
      pane.setLayout(new BorderLayout());
      this.initializeInputs();
      this.initializeButtons();
   }

   /** Saves settings and re-initializes board with them */
   public void applySettings() {
      gui.setOrder((int)sizeField.getValue());
      gui.initializeBoard();
      dispose();
   }
   
   /** Creates settings fields and corresponding labels */
   private void initializeInputs() {
      // Create a form panel (used to group and layout rendered components)
      JPanel panel = new JPanel();
      pane.add(panel, BorderLayout.CENTER);
      
      // Size Field
      sizeField = new JSpinner();
      sizeField.setValue(3);
      JLabel sizeLabel = new JLabel("Size: ");
      sizeLabel.setLabelFor(sizeField);

      // Opponent Field
      JLabel opponentLabel = new JLabel("Opponent: ");
      opponentField = new ButtonGroup();
      // CPU Option (adding label and button to panel for rendering)
      JPanel cpuOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel cpuLabel = new JLabel("Player vs Computer");
      JRadioButton cpuButton = new JRadioButton();
      cpuButton.setSelected(true);
      opponentField.add(cpuButton);
      cpuOption.add(cpuButton);
      cpuOption.add(cpuLabel);
      // Human Option
      JPanel humanOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel humanLabel = new JLabel("Player vs Player");
      JRadioButton humanButton = new JRadioButton();
      opponentField.add(humanButton);
      humanOption.add(humanButton);
      humanOption.add(humanLabel);
      
      // Layout
      GridBagConstraints gbc = new GridBagConstraints();
      JComponent[] components = {sizeLabel, sizeField, opponentLabel,
         cpuOption, null, humanOption};
      panel.setLayout(new GridBagLayout());
      gbc.insets = new Insets(0, 10, 0, 10); // Add padding
      gbc.anchor = GridBagConstraints.WEST;
      gbc.fill = GridBagConstraints.BOTH;
      for(int row = 0; row < (components.length + 1) / 2; row++) {
         for(int col = 0; col < 2; col++) {
            int index = col + (row * 2);
            gbc.gridx = col;
            gbc.gridy = row;
            // Stretch input column horizontally
            gbc.weightx = (col == 0) ? 0.1 : 1.0;
            if(index < components.length && components[index] != null) {
               panel.add(components[index], gbc);
            }
         }
      }
   }
   
   /** Creates cancel and save buttons */
   private void initializeButtons() {
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
}