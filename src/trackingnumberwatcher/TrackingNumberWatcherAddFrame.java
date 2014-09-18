package trackingnumberwatcher;


/**
 *
 * @author guilherme
 */
public class TrackingNumberWatcherAddFrame extends javax.swing.JDialog {

    private JSON json = new JSON();
    private TrackingNumberWatcherDBConn dbConn = new TrackingNumberWatcherDBConn();
    private String cod_rastreio;
    private String nome;
    private boolean flag = false;
    
    /** Creates new form TrackingNumberWatcherAddFrame */
    public TrackingNumberWatcherAddFrame(java.awt.Frame parent) {
        super(parent);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        labelNomeDeIdentificacao = new javax.swing.JLabel();
        tNomeDeIdentificacao = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        CancelarButton = new javax.swing.JButton();
        labelCodigoDeRastreio = new javax.swing.JLabel();
        tCodigoDeRastreio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(trackingnumberwatcher.TrackingNumberWatcherApp.class).getContext().getResourceMap(TrackingNumberWatcherAddFrame.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setModal(true);
        setName("Form"); // NOI18N
        setResizable(false);

        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("mainPanel.border.title"))); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        labelNomeDeIdentificacao.setText(resourceMap.getString("labelNomeDeIdentificacao.text")); // NOI18N
        labelNomeDeIdentificacao.setName("labelNomeDeIdentificacao"); // NOI18N

        tNomeDeIdentificacao.setColumns(15);
        tNomeDeIdentificacao.setText(resourceMap.getString("tNomeDeIdentificacao.text")); // NOI18N
        tNomeDeIdentificacao.setName("tNomeDeIdentificacao"); // NOI18N

        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        CancelarButton.setText(resourceMap.getString("CancelarButton.text")); // NOI18N
        CancelarButton.setName("CancelarButton"); // NOI18N
        CancelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarButtonActionPerformed(evt);
            }
        });

        labelCodigoDeRastreio.setText(resourceMap.getString("labelCodigoDeRastreio.text")); // NOI18N
        labelCodigoDeRastreio.setName("labelCodigoDeRastreio"); // NOI18N

        tCodigoDeRastreio.setText(resourceMap.getString("tCodigoDeRastreio.text")); // NOI18N
        tCodigoDeRastreio.setName("tCodigoDeRastreio"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(labelCodigoDeRastreio)
                        .addGap(37, 37, 37)
                        .addComponent(tCodigoDeRastreio, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(labelNomeDeIdentificacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tNomeDeIdentificacao, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(CancelarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(okButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeDeIdentificacao)
                    .addComponent(tNomeDeIdentificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodigoDeRastreio)
                    .addComponent(tCodigoDeRastreio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(CancelarButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarButtonActionPerformed
        dispose();
    }//GEN-LAST:event_CancelarButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        cod_rastreio = tCodigoDeRastreio.getText().toString().toUpperCase();
        nome = tNomeDeIdentificacao.getText().toString();
        
        dispose();
        
        flag = json.isValid(cod_rastreio);
        
        if(flag)
        {
            dbConn.insertNewCod(cod_rastreio, nome);
            dbConn.insertNewData(cod_rastreio);
        }
        else
            System.out.println("Yeah, he is goddam' right.");
    }//GEN-LAST:event_okButtonActionPerformed
    
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
            java.util.logging.Logger.getLogger(TrackingNumberWatcherAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrackingNumberWatcherAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrackingNumberWatcherAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrackingNumberWatcherAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
//                JFrame mainFrame = TrackingNumberWatcherApp.getApplication().getMainFrame();
//                new TrackingNumberWatcherAddFrame(mainFrame).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelarButton;
    private javax.swing.JLabel labelCodigoDeRastreio;
    private javax.swing.JLabel labelNomeDeIdentificacao;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField tCodigoDeRastreio;
    private javax.swing.JTextField tNomeDeIdentificacao;
    // End of variables declaration//GEN-END:variables
}
