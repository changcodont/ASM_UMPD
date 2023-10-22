/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package EduSys.Poly.UI;

import EduSys.utils.XImage;

/**
 *
 * @author 84934
 */
public class LoadMenu extends javax.swing.JFrame  implements Runnable {

    /**
     * Creates new form LoadDiem
     */
    public LoadMenu() {
            initComponents();
        Thread t = new Thread(this);
             setLocationRelativeTo(null);

        t.start();
                setIconImage(XImage.getAppicon());

    }

         @Override

    public void run() {
              for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(20);

                if (i == 100) {
                    Thread.sleep(1000); // Wait for 1 second before closing the frame
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lblphan_tram.setText(i + "%");

            lblcount.setText("Đang tải dữ liệu..."); // Display loading message
            pgbload.setValue(i);
        }

        lblcount.setText("Tải dữ liệu thành công."); // Display success message

        EduSysJFrame diem = new EduSysJFrame();
        diem.setVisible(true);
        dispose();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pgbload = new javax.swing.JProgressBar();
        lblcount = new javax.swing.JLabel();
        lblphan_tram = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(pgbload, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 170, 330, 40));

        lblcount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblcount.setForeground(new java.awt.Color(255, 255, 255));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("EduSys/Poly/UI/Bundle"); // NOI18N
        lblcount.setText(bundle.getString("LoadMenu.lblcount.text")); // NOI18N
        getContentPane().add(lblcount, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 221, 239, 27));

        lblphan_tram.setFont(new java.awt.Font("Harrington", 1, 24)); // NOI18N
        lblphan_tram.setForeground(new java.awt.Color(255, 255, 255));
        lblphan_tram.setText(bundle.getString("LoadMenu.lblphan_tram.text")); // NOI18N
        getContentPane().add(lblphan_tram, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 107, 101, 27));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/loginnnnnnnn.png"))); // NOI18N
        jLabel1.setText(bundle.getString("LoadMenu.jLabel1.text")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(LoadMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoadMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoadMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoadMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoadMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblcount;
    private javax.swing.JLabel lblphan_tram;
    private javax.swing.JProgressBar pgbload;
    // End of variables declaration//GEN-END:variables
}
