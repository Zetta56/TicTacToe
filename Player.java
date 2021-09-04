import javax.swing.*;
import java.awt.event.*;

/** Player representing participants in TicTacToe game */
public class Player {
  private TicTacToe game;
  private JButton[][] board;
  private String symbol;
  private boolean isComputer;

  public Player(TicTacToe game, JButton[][] board, String symbol, boolean isComputer) {
    this.game = game;
    this.board = board;
    this.symbol = symbol;
    this.isComputer = isComputer;
  }

  public String getSymbol() {
    return symbol;
  }

  public boolean isComputer() {
    return isComputer;
  }

  public void generateMove() {
    // Add slight delay to computer player's moves
    Timer timer = new Timer(200, new ActionListener(){
      @Override
      public void actionPerformed( ActionEvent e ){
        JButton selected;
        do {
          int randomRow = (int)(Math.random() * board.length);
          int randomCol = (int)(Math.random() * board[0].length);
          selected = board[randomRow][randomCol];
        } while (!selected.getText().equals("") && !game.getFinished());
        // Directly calling playTurn() because buttons' action-listeners aren't loaded in time on first run
        game.playTurn(selected);
      }
    } );
    timer.setRepeats(false);
    timer.start();
    
  }
}
