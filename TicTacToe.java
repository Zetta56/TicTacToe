import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
   private String player;
   private boolean finished;
   private JButton[][] board;

   public TicTacToe(JButton[][] board) {
      this.board = board;
      this.player = "X";
      this.finished = false;
   }

   public void playTurn(ActionEvent e) {
      JButton button = (JButton)e.getSource();
      if(button.getText().equals("") && !finished) {
         button.setText(player);
         checkWinner();
         checkTie();
         player = player.equals("X") ? "O" : "X"; // Switch players
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
      finished = true;
   }
   
   public void checkWinner() {
      if(matchingRow() || matchingColumn() || matchingDiagonal()) {
         JOptionPane.showMessageDialog(null, "Player " + player + " has won");
         finished = true;
      }
   }
   
   private boolean matchingRow() {
      for(int row = 0; row < board.length; row++) {
         boolean matching = true;
         for(int col = 0; col < board[0].length; col++) {
            if(!board[row][col].getText().equals(player)) {
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
            if(!board[row][col].getText().equals(player)) {
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
            if(!board[row][i].getText().equals(player)) {
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