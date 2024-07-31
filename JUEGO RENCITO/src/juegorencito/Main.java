package juegorencito;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        
        Runnable mRun = () -> {
            
            // JFrame que funge como splash
            Splash mSplash = new Splash();
            mSplash.setVisible(true);
            
            try {
                Thread.sleep(5000); // 5000 milisegundos equivale a 5 segundos.
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            mSplash.dispose();
            
            // Iniciar la clase Inicio después de cerrar el Splash
            BatallaNavalGUI.main(null);
        };
        
        Thread mHiloSplash = new Thread(mRun);
        mHiloSplash.start();
    }
}
