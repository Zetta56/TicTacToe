package src;
import java.awt.*;
import javax.swing.*;

/** GUI used to render TicTacToe window */
// JFrame is a top-level container representing the app's window
public class GUI extends JFrame {
   // Rendering Pane (actually a JPanel, but declaring with parent Container adds flexibility)
   private Container pane;
   // Main Menu
   private JMenuBar menuBar; // Top bar in window with multiple menus
   private JMenu fileMenu; // Dropdown list with multiple items
   private JMenuItem newGameItem;
   private JMenuItem quitItem;
   private JMenuItem settingsItem;
   private Settings settings;
   // Game Tab
   private JButton[][] board; // Button component detecting player clicks
   private TicTacToe game;
   
   public GUI() {
      super();
      // Window Settings
      this.setTitle("Tic Tac Toe");
      this.setSize(500, 500);
      this.setResizable(false);
      this.setLocationRelativeTo(null); // Centers Jframe on screen
      this.setVisible(true);
      this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close JFrame instance
      
      // Components
      initializeMenu();
      settings = new Settings(this);
      settings.applySettings(); // Initialize board with default settings
      settings.setVisible(true);
   }
   
   /** Creates and styles TicTacToe board */
   public void initializeBoard() {
      // Reset and get JFrame container
      this.setContentPane(new JPanel());
      pane = this.getContentPane();
      // Makes pane render a grid of buttons
      int order = settings.getOrder();
      pane.setLayout(new GridLayout(order, order));
      board = new JButton[order][order];
      // Populate button properties
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            JButton button = new JButton();  // Defaults text to empty string
            button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 300 / order));
            button.setFocusPainted(false);   // Remove highlighting
            button.addActionListener(e -> game.playTurn((JButton)e.getSource()));
            board[row][col] = button;
            pane.add(button);
         }
      }
      // Render layout changes and reload game logic
      validate();
      game = new TicTacToe(board, settings);
   }

   /** Creates menu-bar with menus and menu items */
   private void initializeMenu() {
      // Top-level Menus
      menuBar = new JMenuBar();
      this.setJMenuBar(menuBar);
      fileMenu = new JMenu("File");
      menuBar.add(fileMenu);

      // New-Game Button
      newGameItem = new JMenuItem("New Game");
      newGameItem.addActionListener(e -> resetBoard());
      fileMenu.add(newGameItem);

      // Settings Button
      settingsItem = new JMenuItem("Settings");
      settingsItem.addActionListener(e -> settings.setVisible(true));
      fileMenu.add(settingsItem);

      // Quit Button
      quitItem = new JMenuItem("Quit");
      quitItem.addActionListener(e -> System.exit(0));
      fileMenu.add(quitItem);
   }
   
   /** Resets game state and board text */
   private void resetBoard() {
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            board[row][col].setText("");
         }
      }
      game = new TicTacToe(board, settings);
   }
}