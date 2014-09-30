package trackingnumberwatcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author guilherme
 */
public class TrackingNumberWatcherDetailsFrame extends javax.swing.JFrame {
    private TrackingNumberWatcherDBConn dbConn = new TrackingNumberWatcherDBConn();
    private DefaultTableModel model;
    private ResultSet rs;
    
    /** Creates new form TrackingNumberWatcherDetailsFrame */
    public TrackingNumberWatcherDetailsFrame(String codValue) {
        initComponents();
        this.setName("123123123");
        loadData(codValue);
    }

    private void loadData(String codValue)
    {
        int id = dbConn.getId_cod(codValue);
        try
        {
            model = (DefaultTableModel) detailsTable.getModel();
            model.setRowCount(0);
            tNome.setText(dbConn.getNameFromCod(codValue));
            tCodigo.setText(codValue);
            rs = dbConn.getDetails(id);
            while(rs.next())
            {
                String data  = rs.getString("data");
                String local = rs.getString("local");
                String acao = rs.getString("acao");
                String detalhes = rs.getString("detalhes");
                
                System.out.println();
                model.insertRow(model.getRowCount(), new Object[] {data, local, acao, detalhes});
            }
        }     
        catch (SQLException ex)
        {
            Logger.getLogger(TrackingNumberWatcherView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        detailsTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lNome = new javax.swing.JLabel();
        lCodigo = new javax.swing.JLabel();
        tNome = new javax.swing.JLabel();
        tCodigo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        detailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Data", "Local", "Ação", "Detalhes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        detailsTable.setName("detailsTable"); // NOI18N
        jScrollPane1.setViewportView(detailsTable);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(trackingnumberwatcher.TrackingNumberWatcherApp.class).getContext().getResourceMap(TrackingNumberWatcherDetailsFrame.class);
        detailsTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("detailsTable.columnModel.title0")); // NOI18N
        detailsTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("detailsTable.columnModel.title1")); // NOI18N
        detailsTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("detailsTable.columnModel.title2")); // NOI18N
        detailsTable.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("detailsTable.columnModel.title3")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        jPanel1.setName("jPanel1"); // NOI18N

        lNome.setLabelFor(tNome);
        lNome.setText(resourceMap.getString("lNome.text")); // NOI18N
        lNome.setName("lNome"); // NOI18N

        lCodigo.setLabelFor(tCodigo);
        lCodigo.setText(resourceMap.getString("lCodigo.text")); // NOI18N
        lCodigo.setName("lCodigo"); // NOI18N

        tNome.setFont(resourceMap.getFont("tNome.font")); // NOI18N
        tNome.setText(resourceMap.getString("tNome.text")); // NOI18N
        tNome.setName("tNome"); // NOI18N

        tCodigo.setFont(resourceMap.getFont("tCodigo.font")); // NOI18N
        tCodigo.setText(resourceMap.getString("tCodigo.text")); // NOI18N
        tCodigo.setName("tCodigo"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(lCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tCodigo)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lNome)
                    .addComponent(tNome)
                    .addComponent(lCodigo)
                    .addComponent(tCodigo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addContainerGap())
        );

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
            java.util.logging.Logger.getLogger(TrackingNumberWatcherDetailsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrackingNumberWatcherDetailsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrackingNumberWatcherDetailsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrackingNumberWatcherDetailsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable detailsTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lCodigo;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel tCodigo;
    private javax.swing.JLabel tNome;
    // End of variables declaration//GEN-END:variables
}
