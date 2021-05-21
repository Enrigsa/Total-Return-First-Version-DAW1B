/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import com.google.gson.JsonArray;
import java.io.IOException;
import java.net.ProtocolException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static returns.Returns.getPriceDates;
import static returns.Returns.getPrices;

/**
 *
 * @author enrig
 */
public class frmReturns extends javax.swing.JFrame {

    /**
     * Creates new form frmReturns
     */
    public frmReturns() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        textTicker = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textInitialDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textFinalDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        textAccumulatedReturn = new javax.swing.JTextField();
        textAnnualReturn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textInitialPrice = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        textFinalPrice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textSumDividends = new javax.swing.JTextField();
        textTime = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textTicker.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textTicker.setText("---");
        textTicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textTickerActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Fecha inicial:");

        textInitialDate.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textInitialDate.setText("yyyy-mm-dd");
        textInitialDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textInitialDateActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Fecha final:");

        textFinalDate.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textFinalDate.setText("yyyy-mm-dd");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel1.setText("Ticker: ");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton1.setText("Calcular Rentabilidad");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(31, 31, 31))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textTicker)
                            .addComponent(textInitialDate)
                            .addComponent(textFinalDate, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jButton1)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textTicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textInitialDate)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFinalDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Rentabilidad anualizada:");

        textAccumulatedReturn.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        textAnnualReturn.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textAnnualReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textAnnualReturnActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Rentabilidad acumulada:");

        textInitialPrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textInitialPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textInitialPriceActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Precio inicial ajustado:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setText("Precio final:");

        textFinalPrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textFinalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFinalPriceActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Tiempo (años):");

        textSumDividends.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textSumDividends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSumDividendsActionPerformed(evt);
            }
        });

        textTime.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        textTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textTimeActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Suma de dividendos:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textSumDividends, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(textTime)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(96, 96, 96))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(textInitialPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(textFinalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textAccumulatedReturn)
                    .addComponent(textAnnualReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(textInitialPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(textFinalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(textSumDividends, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(textAccumulatedReturn))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(textAnnualReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(106, Short.MAX_VALUE))))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel8.setText("Datos y rentabilidades obtenidos para el ticker solicitado:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel9.setText("Introduzca los datos para los que desea calcular la rentabilidad:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel8)
                .addGap(35, 35, 35)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textTickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textTickerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textTickerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String ticker = textTicker.getText();
        String iDateUser = textInitialDate.getText();
        String fDateUser = textFinalDate.getText();
        
        //Es necesario controlar el error de introducción de la fecha. Debe ser más tardía la fecha final.
        
        try {
            Operation calculatedData = Operation.calculations(ticker, iDateUser, fDateUser);
            textInitialPrice.setText(calculatedData.initialPrice);
            textFinalPrice.setText(calculatedData.finalPrice);
            textSumDividends.setText(calculatedData.sumDividends + "");
            textTime.setText(calculatedData.time + "");
            textAccumulatedReturn.setText(calculatedData.accumulatedReturn + "");
            textAnnualReturn.setText(calculatedData.annualReturn + "");
            
        } catch (IOException ex) {
            Logger.getLogger(frmReturns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(frmReturns.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void textInitialDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textInitialDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textInitialDateActionPerformed

    private void textTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textTimeActionPerformed

    private void textSumDividendsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSumDividendsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSumDividendsActionPerformed

    private void textFinalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFinalPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFinalPriceActionPerformed

    private void textInitialPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textInitialPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textInitialPriceActionPerformed

    private void textAnnualReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textAnnualReturnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textAnnualReturnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ParseException, ProtocolException, IOException {
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
            java.util.logging.Logger.getLogger(frmReturns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmReturns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmReturns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmReturns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmReturns().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField textAccumulatedReturn;
    private javax.swing.JTextField textAnnualReturn;
    private javax.swing.JTextField textFinalDate;
    private javax.swing.JTextField textFinalPrice;
    private javax.swing.JTextField textInitialDate;
    private javax.swing.JTextField textInitialPrice;
    private javax.swing.JTextField textSumDividends;
    private javax.swing.JTextField textTicker;
    private javax.swing.JTextField textTime;
    // End of variables declaration//GEN-END:variables
}
