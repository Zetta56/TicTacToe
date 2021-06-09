import java.awt.*;
import java.awt.event.*; // java.awt.* doesn't import subpackages
import javax.swing.*;

/**
 * GUI used to render TicTacToe window
 */
// JFrame is a top-level container for Swing apps
public class GUI extends JFrame {
   // Basic rendering container
   private Container pane;
   // Button component for detecting player clicks
   private JButton[][] board;
   // Top bar in window with multiple menus
   private JMenuBar menuBar;
   // Dropdown list with multiple items
   private JMenu menu;
   // Clickable items in menus
   private JMenuItem newGame;
   private JMenuItem quit;
   private String currentPlayer;
   private boolean finished;
   
   public GUI() {
      super();
      // Layout
      pane = this.getContentPane(); // Sets pane to JFrame container
      pane.setLayout(new GridLayout(3, 3)); // Makes pane render a grid
      board = new JButton[3][3];
      initializeMenuBar();
      initializeBoard();
      
      // Game State
      currentPlayer = "X";
      finished = false;
      
      // Window Settings
      this.setTitle("Tic Tac Toe");
      this.setSize(500, 500);
      this.setResizable(false);
      this.setVisible(true);
      // Closes JFrame instance (as opposed to exiting entire app)
      this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   }
   
   private void initializeMenuBar() {
      // Initialize menu bar components
      menuBar = new JMenuBar();
      menu = new JMenu("File"); // Labeled as 'File'
      newGame = new JMenuItem("New Menu");
      // Anonymous inner class that implements ActionListener
      // Used as shortcut for declaring and instantiating class
      newGame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            resetBoard();
         }
      });
      quit = new JMenuItem("Quit");
      quit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0); // Terminates entire program
         }
      });
      
      // Link menu bar components together
      menu.add(newGame);
      menu.add(quit);
      menuBar.add(menu);
      setJMenuBar(menuBar);
   }
   
   private void initializeBoard() {
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            JButton button = new JButton();  // Defaults to empty string text
            button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
            board[row][col] = button;
            button.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  if(((JButton)e.getSource()).getText().equals("") && !finished) {
                     button.setText(currentPlayer);
                     checkWinner();
                     checkPossibleMoves();
                     switchPlayer();
                  }
               }
            });
            pane.add(button);
         }
      }
   }
   
   private void resetBoard() {
      currentPlayer = "X";
      finished = false;
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            board[row][col].setText("");
         }
      }
   }
   
   private void switchPlayer() {
      if(currentPlayer.equals("X")) {
         currentPlayer = "O";
      } else {
         currentPlayer = "X";
      }
   }
   
   // Note: 'And' operator gets evaluated before 'Or'
   private void checkWinner() {
      // Check rows
      if(matchesPlayer(0, 0) && matchesPlayer(0, 1) && matchesPlayer(0, 2) ||
         matchesPlayer(1, 0) && matchesPlayer(1, 1) && matchesPlayer(1, 2) ||
         matchesPlayer(2, 0) && matchesPlayer(2, 1) && matchesPlayer(2, 2) ||
         // Check columns
         matchesPlayer(0, 0) && matchesPlayer(1, 0) && matchesPlayer(2, 0) ||
         matchesPlayer(0, 1) && matchesPlayer(1, 1) && matchesPlayer(2, 1) ||
         matchesPlayer(0, 2) && matchesPlayer(1, 2) && matchesPlayer(2, 2) ||
         // Check diagonals
         matchesPlayer(0, 0) && matchesPlayer(1, 1) && matchesPlayer(2, 2) ||
         matchesPlayer(0, 2) && matchesPlayer(1, 1) && matchesPlayer(2, 0)
      ) {
         // Displays message, centered at screen (not any particular frame)
         JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won");
         finished = true;
      }
   }
   
   private void checkPossibleMoves() {
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            if(board[row][col].getText().equals("") || finished) {
               return;
            }
         }
      }
      JOptionPane.showMessageDialog(null, "It's a tie");
      finished = true;
   }
   
   private boolean matchesPlayer(int row, int column) {
      return (board[row][column].getText().equals(currentPlayer));
   }
}