package trackingnumberwatcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class TrackingNumberWatcherView extends FrameView {

    private ResultSet rs;
    private DefaultTableModel model;
    private TrackingNumberWatcherDBConn dbConn = new TrackingNumberWatcherDBConn();
    private TrackingNumberWatcherDetailsFrame detailsFrame;
    private JDialog addFrame, aboutBox;
    private String lastDate, codValue;
    private int attRate = 1; //1h
    private Timer timer = new Timer();
    
    public TrackingNumberWatcherView(SingleFrameApplication app) {
        super(app);
        initComponents();
        dbConn.initiateDB();
        loadSavedData();
        attCheck();
    }

    private void attCheck()
    {
        timer.schedule( new TimerTask()
        {
            public void run()
            {
                System.out.println("------------------------------------\n" + new Date());
                System.out.println("atualizando a cada " + attRate + " hora(s)");
                checkMovementation();
            }
        }, 0, attRate*360000);
    }
    
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = TrackingNumberWatcherApp.getApplication().getMainFrame();
            aboutBox = new TrackingNumberWatcherAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        TrackingNumberWatcherApp.getApplication().show(aboutBox);
    }
    
    private void loadSavedData()
    {
        try
        {
            System.out.println("Loading saved data or refreshing table.");
            model = (DefaultTableModel) mainTable.getModel();
            model.setRowCount(0);
            
            rs = dbConn.getNameAndCod();
            while(rs.next())
            {
                String nome  = rs.getString("nome");
                String  cod = rs.getString("cod");
                lastDate = dbConn.lastMovementation(cod);
                model.insertRow(model.getRowCount(), new Object[] {nome, cod, lastDate});
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

        mainPanel = new javax.swing.JPanel();
        contents = new javax.swing.JPanel();
        tableScrollPane = new javax.swing.JScrollPane();
        mainTable = new javax.swing.JTable();
        buttonsPanel = new javax.swing.JPanel();
        adicionarButton = new javax.swing.JButton();
        detalhesButton = new javax.swing.JButton();
        removerButton = new javax.swing.JButton();
        atualizarLabel = new javax.swing.JLabel();
        atualizarCombo = new javax.swing.JComboBox();
        esconderButton = new javax.swing.JButton();
        sairButton = new javax.swing.JButton();
        atualizarButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        contents.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contents.setName("contents"); // NOI18N

        tableScrollPane.setName("tableScrollPane"); // NOI18N

        mainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Código", "Última movimentação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mainTable.setName("mainTable"); // NOI18N
        mainTable.getTableHeader().setReorderingAllowed(false);
        tableScrollPane.setViewportView(mainTable);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(trackingnumberwatcher.TrackingNumberWatcherApp.class).getContext().getResourceMap(TrackingNumberWatcherView.class);
        mainTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("mainTable.columnModel.title0")); // NOI18N
        mainTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("mainTable.columnModel.title1")); // NOI18N
        mainTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("mainTable.columnModel.title2")); // NOI18N

        buttonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonsPanel.setName("buttonsPanel"); // NOI18N

        adicionarButton.setText(resourceMap.getString("adicionarButton.text")); // NOI18N
        adicionarButton.setName("adicionarButton"); // NOI18N
        adicionarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarButtonActionPerformed(evt);
            }
        });

        detalhesButton.setText(resourceMap.getString("detalhesButton.text")); // NOI18N
        detalhesButton.setName("detalhesButton"); // NOI18N
        detalhesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalhesButtonActionPerformed(evt);
            }
        });

        removerButton.setText(resourceMap.getString("removerButton.text")); // NOI18N
        removerButton.setName("removerButton"); // NOI18N
        removerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerButtonActionPerformed(evt);
            }
        });

        atualizarLabel.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        atualizarLabel.setText(resourceMap.getString("atualizarLabel.text")); // NOI18N
        atualizarLabel.setToolTipText(resourceMap.getString("atualizarLabel.toolTipText")); // NOI18N
        atualizarLabel.setName("atualizarLabel"); // NOI18N

        atualizarCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 hora", "2 horas", "3 horas", "4 horas", "5 horas", "6 horas" }));
        atualizarCombo.setName("atualizarCombo"); // NOI18N
        atualizarCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarComboActionPerformed(evt);
            }
        });

        esconderButton.setText(resourceMap.getString("esconderButton.text")); // NOI18N
        esconderButton.setName("esconderButton"); // NOI18N
        esconderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                esconderButtonActionPerformed(evt);
            }
        });

        sairButton.setText(resourceMap.getString("sairButton.text")); // NOI18N
        sairButton.setToolTipText(resourceMap.getString("sairButton.toolTipText")); // NOI18N
        sairButton.setName("sairButton"); // NOI18N
        sairButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairButtonActionPerformed(evt);
            }
        });

        atualizarButton.setText(resourceMap.getString("atualizarButton.text")); // NOI18N
        atualizarButton.setName("atualizarButton"); // NOI18N
        atualizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adicionarButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(sairButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(esconderButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(detalhesButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(removerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(atualizarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(atualizarCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 100, Short.MAX_VALUE)
                    .addComponent(atualizarButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adicionarButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(detalhesButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(atualizarLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(atualizarCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(atualizarButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(esconderButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sairButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout contentsLayout = new javax.swing.GroupLayout(contents);
        contents.setLayout(contentsLayout);
        contentsLayout.setHorizontalGroup(
            contentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        contentsLayout.setVerticalGroup(
            contentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tableScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(trackingnumberwatcher.TrackingNumberWatcherApp.class).getContext().getActionMap(TrackingNumberWatcherView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void adicionarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarButtonActionPerformed
        if (addFrame == null)
        {
            JFrame mainFrame = TrackingNumberWatcherApp.getApplication().getMainFrame();
            addFrame = new TrackingNumberWatcherAddFrame(mainFrame);
            addFrame.setLocationRelativeTo(mainFrame);
        }
        TrackingNumberWatcherApp.getApplication().show(addFrame);
        loadSavedData();
    }//GEN-LAST:event_adicionarButtonActionPerformed

    private void out(String out){System.out.println(out);}
    
    private void detalhesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalhesButtonActionPerformed
        if(mainTable.getSelectedRow() != -1)
        {
            codValue = getCodFromCollum();
            detailsFrame = new TrackingNumberWatcherDetailsFrame(codValue);
            detailsFrame.setLocationRelativeTo(null);
            detailsFrame.setVisible(true);
        }
        else
            out("Chora e clica em algo seu einimal!");
    }//GEN-LAST:event_detalhesButtonActionPerformed

    private void sairButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_sairButtonActionPerformed

    private void removerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerButtonActionPerformed
        model = (DefaultTableModel) mainTable.getModel();
        
        codValue = getCodFromCollum();
        dbConn.delete(codValue, true);
        loadSavedData();
    }//GEN-LAST:event_removerButtonActionPerformed
    
    private void esconderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_esconderButtonActionPerformed
        
    }//GEN-LAST:event_esconderButtonActionPerformed

    private void atualizarComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarComboActionPerformed
        timer.cancel();
        timer = new Timer();
        attRate = atualizarCombo.getSelectedIndex() + 1;
        attCheck();
    }//GEN-LAST:event_atualizarComboActionPerformed

    private void atualizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarButtonActionPerformed
        checkMovementation();
    }//GEN-LAST:event_atualizarButtonActionPerformed

    private void checkMovementation()
    {
        out("Checkando atualização");
        JSON json = new JSON();
        try
        {
            rs = dbConn.getNameAndCod();
            while(rs.next())
            {
                String  cod = rs.getString("cod");
                int webHash = json.getWebHash(cod);
                int localHash = dbConn.getLocalHash(cod);
                out("web: " + webHash + "\tlocal: " + localHash);
                if(webHash != localHash)
                {
                    out("Processando alterações...");
                    dbConn.delete(cod, false);//remover das tabelas o cod
                    dbConn.insertNewData(cod);//re-inserir o cod na tabela
                    loadSavedData();
                    out(cod + " MUDOU AMIGO!");//avisar os amiguinhos
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TrackingNumberWatcherView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getCodFromCollum()
    {
        return model.getValueAt(mainTable.getSelectedRow(), 1).toString();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarButton;
    private javax.swing.JButton atualizarButton;
    private javax.swing.JComboBox atualizarCombo;
    private javax.swing.JLabel atualizarLabel;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JPanel contents;
    private javax.swing.JButton detalhesButton;
    private javax.swing.JButton esconderButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable mainTable;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton removerButton;
    private javax.swing.JButton sairButton;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables
}
