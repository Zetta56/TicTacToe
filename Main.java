import javax.swing.SwingUtilities;
import src.GUI;

/** Main Program Runner */
public class Main {
   public static void main(String[] args) {
      // Runs swing app on Swing's event-dispatching thread (EDT)
      SwingUtilities.invokeLater(() -> new GUI());
   }
}