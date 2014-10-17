package trackingnumberwatcher;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.net.URLConnection;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TrackingNumberWatcherView extends FrameView {

    private ResultSet rs;
    private DefaultTableModel model;
    private TrackingNumberWatcherDBConn dbConn = new TrackingNumberWatcherDBConn();
    private JDialog aboutBox;
    private String lastDate, codValue;
    private long attRate = 1; //hora(s)
    private Timer timer = new Timer();
    private TrayIcon trayicon;
    private PopupMenu popupmenu;
    private MenuItem menuItemSair, menuItemAbrir, menuItemNome, menuItemSobre, menuItemAtt;
    private JFrame mainFrame = TrackingNumberWatcherApp.getApplication().getMainFrame();
    private TrackingNumberWatcherApp appFrame = TrackingNumberWatcherApp.getApplication();
    private JSON json = new JSON();
    private boolean flag = false;
    
    public TrackingNumberWatcherView(SingleFrameApplication app){
        super(app);
        if(isInternetConn())
        {
            initComponents();
            dbConn.initiateDB();
            loadSavedData();
            attTimer();
        
            if(!SystemTray.isSupported())
                JOptionPane.showMessageDialog(mainFrame,"Não será possivel esconder o programa na barra de notificação.","ERRO",JOptionPane.ERROR_MESSAGE);
            else
                implementTray();
        }
        else
        {
           JOptionPane.showMessageDialog(mainFrame,"É necessário conexão com a internet. O programa esta sendo encerrado...","ERRO",JOptionPane.WARNING_MESSAGE); 
           System.exit(0);
        }
        //SessionStorage off
        Logger logger = Logger.getLogger(org.jdesktop.application.SessionStorage.class.getName());
        logger.setLevel(Level.OFF);
    }
    private boolean isInternetConn(){
        boolean connectivity = false;
        try
        {
            URL url = new URL("http://www.google.com/");
            URLConnection conn = url.openConnection();
            conn.connect();
            connectivity = true;
        }
        catch (Exception e)
        {
            connectivity = false;
            System.out.println("mia nets: " + e);
        }
        return connectivity;
    }
    private void implementTray(){
        popupmenu = new PopupMenu();
        
        menuItemNome = new MenuItem(" Rastreeitor ");
        menuItemSair = new MenuItem(" Sair ");
        menuItemAbrir = new MenuItem(" Mostrar ");
        menuItemSobre = new MenuItem(" Sobre ");
        menuItemAtt = new MenuItem(" Atualizar ");
        
        menuItemSair.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent exx)
            {
                timer.cancel();
                SystemTray.getSystemTray().remove(trayicon);
                System.exit(0);
            }
        });
        menuItemAbrir.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent exx)
            {
                openFromTray();
            }
        });
        menuItemSobre.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent exx)
            {
                showAboutBox();
            }
        });
        menuItemAtt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent exx)
            {
                checkMovementation();
            }
        });
        popupmenu.add(menuItemNome).setEnabled(false);
        popupmenu.addSeparator();
        popupmenu.add(menuItemAbrir);
        popupmenu.add(menuItemAtt);
        popupmenu.add(menuItemSobre);
        popupmenu.addSeparator();
        popupmenu.add(menuItemSair);
        
        trayicon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("icon.png"),"Rastreeitor",popupmenu);
        out(System.getProperty("user.dir") + System.getProperty("file.separator") + "icon.png");
        trayicon.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    openFromTray();
                }
            }
            
            public void actionPerformed(ActionEvent e) {
                    System.out.println("Message Clicked");
                }
        });
        try
        {
            SystemTray.getSystemTray().add(trayicon);
        }
        catch(AWTException e)
        {
            JOptionPane.showMessageDialog(mainFrame, "Não foi possivel esconder o programa.", ":'(", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public void openFromTray(){
        mainFrame.setContentPane(mainPanel);
        appFrame.show(mainFrame);
        //SystemTray.getSystemTray().remove(trayicon);
    }
    private void attTimer(){
        timer.schedule( new TimerTask()
        {
            public void run()
            {
                out("atualizando a cada " + attRate + " hora(s)");
                checkMovementation();
            }
        }, 0, attRate*60*60*1000);
    }
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            aboutBox = new TrackingNumberWatcherAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        TrackingNumberWatcherApp.getApplication().show(aboutBox);
    }
    private void loadSavedData(){
        try
        {
            out("Loading saved data or refreshing table.");
            model = (DefaultTableModel) codsTable.getModel();
            model.setRowCount(0);
            
            rs = dbConn.selectFromCod();
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
            JOptionPane.showMessageDialog(mainFrame, "Nao foi possivel ler os dados no banco de dados.", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(TrackingNumberWatcherView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        contents = new javax.swing.JPanel();
        codsTableScrollPane = new javax.swing.JScrollPane();
        codsTable = new javax.swing.JTable();
        buttonsPanel = new javax.swing.JPanel();
        adicionarButton = new javax.swing.JButton();
        detalhesButton = new javax.swing.JButton();
        removerButton = new javax.swing.JButton();
        atualizarLabel = new javax.swing.JLabel();
        atualizarCombo = new javax.swing.JComboBox();
        esconderButton = new javax.swing.JButton();
        sairButton = new javax.swing.JButton();
        atualizarButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        addInfo = new javax.swing.JPanel();
        ultimaAttLabel = new javax.swing.JLabel();
        ultimaAttValue = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N

        contents.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contents.setName("contents"); // NOI18N

        codsTableScrollPane.setName("codsTableScrollPane"); // NOI18N

        codsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        codsTable.setName("codsTable"); // NOI18N
        codsTable.getTableHeader().setReorderingAllowed(false);
        codsTableScrollPane.setViewportView(codsTable);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(trackingnumberwatcher.TrackingNumberWatcherApp.class).getContext().getResourceMap(TrackingNumberWatcherView.class);
        codsTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("codsTable.columnModel.title0")); // NOI18N
        codsTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("codsTable.columnModel.title1")); // NOI18N
        codsTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("codsTable.columnModel.title2")); // NOI18N

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

        atualizarLabel.setFont(new java.awt.Font("Dialog", 1, 10));
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

        jSeparator1.setName("jSeparator1"); // NOI18N

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
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
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
                .addGap(19, 19, 19)
                .addComponent(atualizarLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(atualizarCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(atualizarButton)
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
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
                .addComponent(codsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        contentsLayout.setVerticalGroup(
            contentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(codsTableScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        addInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addInfo.setName("addInfo"); // NOI18N

        ultimaAttLabel.setText(resourceMap.getString("ultimaAttLabel.text")); // NOI18N
        ultimaAttLabel.setName("ultimaAttLabel"); // NOI18N

        ultimaAttValue.setText(resourceMap.getString("ultimaAttValue.text")); // NOI18N
        ultimaAttValue.setName("ultimaAttValue"); // NOI18N

        javax.swing.GroupLayout addInfoLayout = new javax.swing.GroupLayout(addInfo);
        addInfo.setLayout(addInfoLayout);
        addInfoLayout.setHorizontalGroup(
            addInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addInfoLayout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(ultimaAttLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ultimaAttValue)
                .addContainerGap())
        );
        addInfoLayout.setVerticalGroup(
            addInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ultimaAttValue)
                .addComponent(ultimaAttLabel))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        setComponent(mainPanel);
    }// </editor-fold>//GEN-END:initComponents
    private void adicionarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarButtonActionPerformed
        String nome = "", codigoRastreio = "";
        
        codigoRastreio = (String)JOptionPane.showInputDialog(mainFrame,"Informe o código de Rastreio: ","Adicionando novo objeto",JOptionPane.PLAIN_MESSAGE);
        codigoRastreio = codigoRastreio.toUpperCase();
        flag = json.isValid(codigoRastreio);
        if(flag)
        {
            nome = (String)JOptionPane.showInputDialog(mainFrame,"Ok, agora informe o nome de identificaçao: ","Adicionando novo objeto",JOptionPane.PLAIN_MESSAGE);
            if(!dbConn.insertNewCod(codigoRastreio, nome))
                dbConn.insertNewData(codigoRastreio);
        }  
        else
            JOptionPane.showMessageDialog(mainFrame, "Erro: Código de rastreio inválido.", "ERRO", JOptionPane.ERROR_MESSAGE);
        loadSavedData();
    }//GEN-LAST:event_adicionarButtonActionPerformed
    private void out(String out){System.out.println(out);}
    private void detalhesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalhesButtonActionPerformed
        if(codsTable.getSelectedRow() != -1)
        {
            codValue = getCodFromCollum();
            dbConn.showDetailsFrame(getCodFromCollum());
        }
        else
            JOptionPane.showMessageDialog(mainFrame, "Selecione um objeto antes.", "ERRO", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_detalhesButtonActionPerformed
    private void sairButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairButtonActionPerformed
        timer.cancel();
        SystemTray.getSystemTray().remove(trayicon);
        System.exit(0);
    }//GEN-LAST:event_sairButtonActionPerformed
    private void removerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerButtonActionPerformed
        codValue = "";
        if(codsTable.getSelectedRow() != -1)
        {
            codValue = getCodFromCollum();
        
            dbConn.delete(codValue, true);
            loadSavedData();
        }
        else
            JOptionPane.showMessageDialog(mainFrame, "Selecione um objeto antes.", "ERRO", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_removerButtonActionPerformed
    private void esconderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_esconderButtonActionPerformed
        appFrame.hide(this);
        mainFrame.setVisible(false); //redundancia da POG over here.
        trayicon.displayMessage(":D","Estou aqui.",TrayIcon.MessageType.NONE);
        
    }//GEN-LAST:event_esconderButtonActionPerformed
    private void atualizarComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarComboActionPerformed
        timer.cancel();
        timer = new Timer();
        attRate = atualizarCombo.getSelectedIndex() + 1;
        attTimer();
    }//GEN-LAST:event_atualizarComboActionPerformed
    private void atualizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarButtonActionPerformed
        checkMovementation();
    }//GEN-LAST:event_atualizarButtonActionPerformed
    private void checkMovementation(){
        out("Checkando atualização");
        try
        {
            out("" + new Date());
            ultimaAttValue.setText(new Date().toString());
            rs = dbConn.selectFromCod();
            while(rs.next())
            {
                String  cod = rs.getString("cod");
                long webHash = json.getWebHash(cod);
                long localHash = dbConn.getLocalHash(cod);
                out("web  : " + webHash + "\nlocal: " + localHash);
                if(webHash != localHash)
                {
                    out("Atualizando dados...");
                    dbConn.delete(cod, false);//remover das tabelas o cod
                    dbConn.insertNewData(cod);//re-inserir o cod na tabela
                    loadSavedData();
                    trayicon.displayMessage("Atualizou!", rs.getString("nome") +" tem novidades!",TrayIcon.MessageType.INFO);
                }
            }
            
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(mainFrame, "Houve um erro ao tentar checkar as atualizações.", "ERRO", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(TrackingNumberWatcherView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String getCodFromCollum(){
        return model.getValueAt(codsTable.getSelectedRow(), 1).toString();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addInfo;
    private javax.swing.JButton adicionarButton;
    private javax.swing.JButton atualizarButton;
    private javax.swing.JComboBox atualizarCombo;
    private javax.swing.JLabel atualizarLabel;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JTable codsTable;
    private javax.swing.JScrollPane codsTableScrollPane;
    private javax.swing.JPanel contents;
    private javax.swing.JButton detalhesButton;
    private javax.swing.JButton esconderButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton removerButton;
    private javax.swing.JButton sairButton;
    private javax.swing.JLabel ultimaAttLabel;
    private javax.swing.JLabel ultimaAttValue;
    // End of variables declaration//GEN-END:variables
}
