import javax.swing.SwingUtilities;

public class Main {
   public static void main(String[] args) {
      // Runs swing app on Swing's event-dispatching thread
      SwingUtilities.invokeLater(new Runnable() {
         // Runnable calls run on a new EDT thread
         public void run() {
            new GUI();
         }
      });
   }
}