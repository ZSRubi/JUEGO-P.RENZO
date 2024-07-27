package juegorencito;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Splash extends javax.swing.JFrame {
    
     private final Color mBlack = null; // Cambiar el nombre para reflejar el cambio a negro
    private Point mPoint;
    
       public Splash() {
    // Inicializar el color negro sin transparencia
    Color blackColor = Color.BLACK;
    
    setUndecorated(true);
    initComponents();
    setLocationRelativeTo(null);
    
    // Establecer el fondo negro para el JFrame
    setBackground(blackColor);

    // Configurar el fondo del panel principal a negro
    pnlBackground.setBackground(blackColor); // Establecer fondo negro
    pnlBackground.setOpaque(true); // Hacer el panel opaco
    pnlBackground.setBorder(null);

    // Configurar el icono de salir con fondo negro
    pnlCerrar.setBackground(blackColor); // Establecer fondo negro
    pnlCerrar.setOpaque(true); // Hacer el panel opaco
    pnlCerrar.setBorder(null);

    // Pintar el fondo del JFrame y hacerlo transparente 
    ImageDecore mFondo = new ImageDecore(pnlBackground, "/icons/B.jpg");
    pnlBackground.add(mFondo).repaint();

    // Pintar el icono de salir del JFrame
    ImageDecore mSalir = new ImageDecore(pnlCerrar, "/icons/salir.png");
    pnlCerrar.add(mSalir).repaint();

    // Iniciar el progress bar
    ProgressBarInicado();

        // Iniciar el progress bar
        ProgressBarInicado();

    }
    
    private void ProgressBarInicado(){
        // Iniciar el progress bar a partir de un Timer
        Timer mTimer = new Timer(50, (ActionEvent e) -> {
            pbCarga.setValue(pbCarga.getValue() + 1); // Valor del progess bar
            pbCarga.setBackground(Color.orange); // Fondo del progress bar que se irá rellenando
            pbCarga.setStringPainted(true); // Activar texto dentro del Progress bar
            pbCarga.setString("Loading... " + pbCarga.getValue() + "%"); // Texto que irá en el progress bar
            
        });
        
        mTimer.start();
    }
    
 
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        pnlBackground = new javax.swing.JPanel();
        pnlCerrar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pbCarga = new javax.swing.JProgressBar();

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBackground.setBackground(new java.awt.Color(0, 102, 102));
        pnlBackground.setForeground(new java.awt.Color(0, 204, 204));
        pnlBackground.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlBackgroundMouseDragged(evt);
            }
        });
        pnlBackground.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlBackgroundMousePressed(evt);
            }
        });

        pnlCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlCerrarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlCerrarLayout = new javax.swing.GroupLayout(pnlCerrar);
        pnlCerrar.setLayout(pnlCerrarLayout);
        pnlCerrarLayout.setHorizontalGroup(
            pnlCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        pnlCerrarLayout.setVerticalGroup(
            pnlCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("JUEGO RENCITO");

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pbCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))))
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(0, 75, Short.MAX_VALUE))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(pnlCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pbCarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlBackgroundMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBackgroundMousePressed
        mPoint = evt.getPoint(); // Obtener el pundo de partida del movimiento
        getComponentAt(mPoint);
    }//GEN-LAST:event_pnlBackgroundMousePressed

    private void pnlBackgroundMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBackgroundMouseDragged
        // Obtener las posiciones actuales del JFrame en X, Y
        int CurrentX = this.getLocation().x;
        int CurrentY = this.getLocation().y;
        // Obtener el movimiento del del JFrame  en X, Y
        int MoveX = (CurrentX + evt.getX()) - (CurrentX + mPoint.x);
        int MoveY = (CurrentY + evt.getY()) - (CurrentY + mPoint.y);
        
        // Calcular las nuevas posisiones 
        int x = CurrentX + MoveX;
        int y = CurrentY + MoveY;
        
        // Asignar las posisiones al JFrame para generar el movimiento
        this.setLocation(x, y);
        
    }//GEN-LAST:event_pnlBackgroundMouseDragged

    private void pnlCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCerrarMouseClicked
       System.exit(0);
    }//GEN-LAST:event_pnlCerrarMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Splash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar pbCarga;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlCerrar;
    // End of variables declaration//GEN-END:variables
}
