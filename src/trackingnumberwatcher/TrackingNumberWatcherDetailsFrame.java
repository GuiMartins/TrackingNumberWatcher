package trackingnumberwatcher;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TrackingNumberWatcherDetailsFrame extends javax.swing.JFrame {
    private DefaultTableModel model;
    
    public TrackingNumberWatcherDetailsFrame(String nome, String cod, ArrayList al){
        initComponents();
        loadData(nome, cod, al);
    }
    private void loadData(String nome, String cod, ArrayList al){
        model = (DefaultTableModel) detailsTable.getModel();
        model.setRowCount(0);
        tNome.setText(nome);
        tCodigo.setText(cod);
        for (int i = 0; i < al.size();)
        {
            model.insertRow(model.getRowCount(), new Object[]
            {
                al.get(i++).toString(),
                al.get(i++).toString(),
                al.get(i++).toString(),
                al.get(i++).toString()
            });
        }
        //table fix
        detailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        detailsTable.setFillsViewportHeight(true);
        
        update();
    }
    
    
    private void update() {
        System.out.println("updating");

        adjustJTableRowSizes(detailsTable);
        for (int i = 0; i < detailsTable.getColumnCount(); i++) {
            adjustColumnSizes(detailsTable, i, 2);
        }
    }
    private void adjustJTableRowSizes(JTable jTable) {
        for (int row = 0; row < jTable.getRowCount(); row++) {
            int maxHeight = 0;
            for (int column = 0; column < jTable.getColumnCount(); column++) {
                TableCellRenderer cellRenderer = jTable.getCellRenderer(row, column);
                Object valueAt = jTable.getValueAt(row, column);
                Component tableCellRendererComponent = cellRenderer.getTableCellRendererComponent(jTable, valueAt, false, false, row, column);
                int heightPreferable = tableCellRendererComponent.getPreferredSize().height;
                maxHeight = Math.max(heightPreferable, maxHeight);
            }
            jTable.setRowHeight(row, maxHeight);
        }

    }

    public void adjustColumnSizes(JTable table, int column, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn col = colModel.getColumn(column);
        int width;

        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;

        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, column);
            comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, column), false, false, r, column);
            int currentWidth = comp.getPreferredSize().width;
            width = Math.max(width, currentWidth);
        }

        width += 2 * margin;

        col.setPreferredWidth(width);
        col.setWidth(width);
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //nothing to see here...
    }//GEN-LAST:event_formWindowClosing
    public static void main(String args[]) {
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
