import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GUI used to render TicTacToe window
 */
// JFrame is a top-level container representing the app's window
public class GUI extends JFrame {
   // Rendering Pane (actually a JPanel, but declaring with parent Container adds flexibility)
   private Container pane;
   // Main Menu
   private JMenuBar menuBar; // Top bar in window with multiple menus
   private JMenu menu; // Dropdown list with multiple items
   private JMenuItem newGame;
   private JMenuItem quit;
   private JMenuItem settings;
   private Settings settingsDialog;
   // Game Tab
   private JButton[][] board; // Button component detecting player clicks
   private TicTacToe game;
   private int order;
   
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
      settingsDialog = new Settings(this, "Settings");
      settingsDialog.applySettings(); // Initialize board with default settings
      settingsDialog.setVisible(true);
   }

   public int getOrder() {
      return order;
   }

   public void setOrder(int order) {
      if(order > 0) {
         this.order = order;
      }
   }
   
   /** Creates and styles tictactoe board */
   public void initializeBoard() {
      // Reset and get JFrame container
      this.setContentPane(new JPanel());
      pane = this.getContentPane();
      // Makes pane render a grid of buttons
      pane.setLayout(new GridLayout(order, order));
      board = new JButton[order][order];
      // Populate button properties
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            JButton button = new JButton();  // Defaults to empty string text
            button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 300 / order));
            button.setFocusPainted(false);   // Remove highlighting
            button.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  game.playTurn((JButton)e.getSource());
               }
            });
            board[row][col] = button;
            pane.add(button);
         }
      }
      // Render layout changes and reload game logic
      game = new TicTacToe(board);
      validate();
   }

   /** Creates menu-bar with menus and menu items */
   private void initializeMenu() {
      // Top-level Menus
      menuBar = new JMenuBar();
      this.setJMenuBar(menuBar);
      menu = new JMenu("File");
      menuBar.add(menu);

      // New-Game Button
      newGame = new JMenuItem("New Game");
      // Anonymous inner class is a shortcut for declaring and instantiating ActionListener-derived class
      newGame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            resetBoard();
         }
      });
      menu.add(newGame);

      // Settings Button
      settings = new JMenuItem("Settings");
      settings.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            settingsDialog.setVisible(true);
         }
      });
      menu.add(settings);

      // Quit Button
      quit = new JMenuItem("Quit");
      quit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0); // Terminates entire program
         }
      });
      menu.add(quit);
   }
   
   /** Resets game state and board text */
   private void resetBoard() {
      game = new TicTacToe(board);
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            board[row][col].setText("");
         }
      }
   }
}