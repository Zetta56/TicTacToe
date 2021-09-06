package src;
import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

/** Interactive modal used to change game settings */
// JDialog is a top-level container for pop-up modals
public class Settings extends JDialog {
   private Container pane;
   private GUI gui;
   private JSpinner orderField;
   private ButtonGroup gamemodeField, firstField;
   private int order;
   private String gamemode;
   private String first;

   public Settings(GUI gui) {
      super(gui);
      // Window
      this.gui = gui;
      this.setTitle("Settings");
      this.setSize(350, 300);
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
      this.initializeFields();
      this.initializeButtons();
   }

   public int getOrder() {
      return order;
   }

   public String getGamemode() {
      return gamemode;
   }

   public String getFirst() {
      return first;
   }

   /** Saves settings and re-initializes board with them */
   public void applySettings() {
      if((int)orderField.getValue() > 0) {
         order = (int)orderField.getValue();
      }
      gamemode = gamemodeField.getSelection().getActionCommand();
      first = firstField.getSelection().getActionCommand();
      gui.initializeBoard();
      dispose();
   }
   
   /** Creates settings fields and corresponding labels */
   private void initializeFields() {
      // Order Field
      JLabel orderLabel = new JLabel("Rows & Columns: ");
      orderField = new JSpinner();

      // First Field
      JLabel firstLabel = new JLabel("First Player: ");
      firstField = new ButtonGroup();
      // X Option (adding components to panel for organized rendering)
      JPanel xOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel xLabel = new JLabel("X");
      JRadioButton xButton = new JRadioButton();
      xButton.setActionCommand("x");
      xOption.add(xButton);
      xOption.add(xLabel);
      firstField.add(xButton);
      // O Option
      JPanel oOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel oLabel = new JLabel("O");
      JRadioButton oButton = new JRadioButton();
      oButton.setActionCommand("o");
      oOption.add(oButton);
      oOption.add(oLabel);
      firstField.add(oButton);

      // Gamemode Field
      JLabel gamemodeLabel = new JLabel("Gamemode: ");
      gamemodeField = new ButtonGroup();
      // Player Vs Computer Option (note: both will be considered Player objects)
      JPanel pvcOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel pvcLabel = new JLabel("Player vs Computer");
      JRadioButton pvcButton = new JRadioButton();
      pvcButton.setActionCommand("pvc");
      pvcButton.setSelected(true);
      pvcOption.add(pvcButton);
      pvcOption.add(pvcLabel);
      gamemodeField.add(pvcButton);
      // Player Vs Player Option
      JPanel pvpOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel pvpLabel = new JLabel("Player vs Player");
      JRadioButton pvpButton = new JRadioButton();
      pvpButton.setActionCommand("pvp");
      pvpOption.add(pvpButton);
      pvpOption.add(pvpLabel);
      gamemodeField.add(pvpButton);
      // Computer Vs Computer Option
      JPanel cvcOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JLabel cvcLabel = new JLabel("Computer vs Computer");
      JRadioButton cvcButton = new JRadioButton();
      cvcButton.setActionCommand("cvc");
      cvcOption.add(cvcButton);
      cvcOption.add(cvcLabel);
      gamemodeField.add(cvcButton);
      
      // Layout
      JComponent[] components = {orderLabel, orderField, firstLabel, xOption,
         null, oOption, gamemodeLabel, pvcOption, null, pvpOption, null, cvcOption};
      this.layoutFields(components);

      // Default Values
      orderField.setValue(3);
      xButton.setSelected(true);
      pvcButton.setSelected(true);

      // Reconcile Fields and Stored Values
      this.addWindowListener(new WindowAdapter() {
         // Anonymous inner class used as shortcut to declare and instantiate class
         // Since WindowAdapter has multiple methods, lambdas can't be used here
         @Override
         public void windowActivated(WindowEvent e) {
            orderField.setValue((Object)order);
            getRadioButton(firstField, first).setSelected(true);
            getRadioButton(gamemodeField, gamemode).setSelected(true);
         }
      });
   }

   private void layoutFields(JComponent[] components) {
      GridBagConstraints gbc = new GridBagConstraints();
      JPanel panel = new JPanel();
      pane.add(panel, BorderLayout.CENTER);
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
      cancel.addActionListener(e -> dispose()); // Close settings window
      panel.add(cancel);
      
      // Submit Button
      JButton submit = new JButton("Play");
      submit.addActionListener(e -> applySettings());
      panel.add(submit);
   }
   
   /** Gets radio button from button group using matching action command */
   private JRadioButton getRadioButton(ButtonGroup buttonGroup, String actionCommand) {
      for(Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();
          if(button.getActionCommand().equals(actionCommand)) {
              return (JRadioButton)button;
          }
      }
      return null;
  }
}