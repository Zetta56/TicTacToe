/** Player representing participants in TicTacToe game */
public class Player {
  private String symbol;
  private boolean isComputer;

  public Player(String symbol, boolean isComputer) {
    this.symbol = symbol;
    this.isComputer = isComputer;
  }

  public String getSymbol() {
    return symbol;
  }

  public boolean isComputer() {
    return isComputer;
  }
}
