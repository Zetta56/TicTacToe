import javax.swing.SwingUtilities;

public class Main {
   public static void main(String[] args) {
      // Runs game on Swing's event-dispatching thread (not its own thread)
      SwingUtilities.invokeLater(new Runnable() {
         // Runnable calls run on a new thread
         public void run() {
            new GUI();
         }
      });
   }
}