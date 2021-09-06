package src;
import javax.swing.*;

/** Controller managing TicTacToe game logic */
public class TicTacToe {
   private JButton[][] board;
   private Settings settings;
   private Player player1;
   private Player player2;
   private Player currentPlayer;
   private boolean finished;

   public TicTacToe(JButton[][] board, Settings settings) {
      this.board = board;
      this.settings = settings;
      this.player1 = new Player(this, board, "X", settings.getGamemode().equals("cvc"));
      this.player2 = new Player(this, board, "O", !settings.getGamemode().equals("pvp"));
      this.currentPlayer = settings.getFirst().equals("x") ? player1 : player2;
      this.finished = false;
      if(currentPlayer.isComputer()) {
         currentPlayer.generateMove();
      }
   }

   public boolean getFinished() {
      return finished;
   }

   public void playTurn(JButton button) {
      if(button.getText().equals("") && !finished) {
         button.setText(currentPlayer.getSymbol());
         checkMatching();
         checkTie();
         currentPlayer = currentPlayer.getSymbol().equals("X") ? player2 : player1;
         if(currentPlayer.isComputer()) {
            currentPlayer.generateMove();
         }
      }
   }
   
   public void checkTie() {
      for(int row = 0; row < board.length; row++) {
         for(int col = 0; col < board[0].length; col++) {
            if(board[row][col].getText().equals("") || finished) {
               return;
            }
         }
      }
      JOptionPane.showMessageDialog(null, "It's a tie");
      settings.setVisible(true);
      finished = true;
   }
   
   public void checkMatching() {
      if(matchingRow() || matchingColumn() || matchingDiagonal()) {
         String message = "Player " + currentPlayer.getSymbol() + " has won";
         JOptionPane.showMessageDialog(null, message);
         settings.setVisible(true);
         finished = true;
      }
   }
   
   private boolean matchingRow() {
      for(int row = 0; row < board.length; row++) {
         boolean matching = true;
         for(int col = 0; col < board[0].length; col++) {
            if(!board[row][col].getText().equals(currentPlayer.getSymbol())) {
               matching = false;
               break;
            }
         }
         if(matching) {
            return true;
         }
      }
      return false;
   }
   
   private boolean matchingColumn() {
      for(int col = 0; col < board[0].length; col++) {
         boolean matching = true;
         for(int row = 0; row < board.length; row++) {
            if(!board[row][col].getText().equals(currentPlayer.getSymbol())) {
               matching = false;
               break;
            }
         }
         if(matching) {
            return true;
         }
      }
      return false;
   }
   
   private boolean matchingDiagonal() {
      // Checks the 2 diagonals in square board
      for(int diagonal = 1; diagonal <= 2; diagonal++) {
         boolean matching = true;
         for(int i = 0; i < board.length; i++) {
            // Reverses row if checking bottomleft-topright diagonal (diagonal 2)
            int row = diagonal == 2 ? board.length - 1 - i : i;
            if(!board[row][i].getText().equals(currentPlayer.getSymbol())) {
               matching = false;
               break;
            }
         }
         if(matching) {
            return true;
         }
      }
      return false;
   }
}