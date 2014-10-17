package trackingnumberwatcher;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TrackingNumberWatcherDBConn
{
    private Connection c = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private static final String jdbc = "org.sqlite.JDBC";
    private static final String connFile = "jdbc:sqlite:data.db";
    private static final String tableCodName = "cod_rastreio"; //mudar that name???
    private static final String tableDadoName = "dado";
    private int id_cod;
    private JSON json;
    private String lastAcao; //lastDate
    
    public TrackingNumberWatcherDBConn(){
        abrirConexao();
    }
    
    private void abrirConexao(){
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);
            stmt = c.createStatement();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TrackingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TrackingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void fecharConexao(){
        try
        {
            if(c != null)
                c.close();
            if(stmt != null)
                stmt.close();
            if(rs != null)
                rs.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TrackingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void sqlQuery(String query, String acao){
        try
        {
            stmt.executeUpdate(query);
        }  
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }   
    public void initiateDB(){   
        sqlQuery("CREATE TABLE IF NOT EXISTS " + tableCodName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, cod TEXT NOT NULL, nome TEXT NOT NULL, hash INTEGER)", "Criando tabela cod.");
        sqlQuery("CREATE TABLE IF NOT EXISTS " + tableDadoName + "(id INTEGER PRIMARY KEY AUTOINCREMENT, id_cod INTEGER NOT NULL, data DATE NOT NULL, local TEXT NOT NULL, acao TEXT NOT NULL, detalhes TEXT NOT NULL)", "Criando tabela Dado.");
    }
    public boolean insertNewCod(String cod, String nome){
        out("Inserindo novo código...");
        boolean isThere = false;
        rs = responseQuery("SELECT cod FROM " + tableCodName + " WHERE cod = '" + cod + "';");
        try
        {
            while(rs.next())
            {
                String str = rs.getString("cod");
                if(str.equals(cod))
                    isThere = true;
            }
            if(!isThere)
                sqlQuery("INSERT INTO " + tableCodName + " (cod, nome) VALUES ('"+ cod +"','"+ nome +"');", "Inserindo cod e nome.");
            else
                JOptionPane.showMessageDialog(null, "O código informado já foi cadastrado.", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TrackingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isThere;
    }
    public int getIdCod(String cod){
        int id = 0;
        try
        {
            rs = responseQuery("SELECT id FROM " + tableCodName + " WHERE cod = '"+ cod + "'");
            if(rs.next())
                id = rs.getInt("id");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TrackingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    public void insertNewData(String cod_rastreio){
        id_cod = getIdCod(cod_rastreio);
        json = new JSON();
        if(id_cod == 0)
            JOptionPane.showMessageDialog(null, "Houve um problema na inserção dos dados.", "Erro: getId", JOptionPane.ERROR_MESSAGE);
        else
        {
            json.setJSONDataInArray(id_cod, cod_rastreio);
            ArrayList al = json.getJSONArray();
            for (int i = 0; i < al.size();)
            {
		insertJSONData(id_cod, 
                        al.get(i++).toString(), 
                        al.get(i++).toString(), 
                        al.get(i++).toString(), 
                        al.get(i++).toString());
            }
            insertHash(json.getHash(json.getJSONString()), cod_rastreio);
        }
    }
    public void insertHash(long hash, String cod){
        out("Inserindo/Updating hash...");
        sqlQuery("UPDATE " + tableCodName + " SET hash = " + hash + " WHERE cod = '" + cod + "'", "Atualizando tabela cod: inserindo hash.");
    }
    public void insertJSONData(int id_cod, String data, String local, String acao, String detalhes){
        out("Inserindo os dados no tabela DADO....");
        sqlQuery("INSERT INTO " + tableDadoName + " (id_cod, data, local, acao, detalhes) VALUES ('" + id_cod + "', '" + data + "', '" + local + "', '" + acao + "', '" + detalhes + "');", "Inserindo dados na tabela dado.");
    }
    public ResultSet responseQuery(String query){
        try
        {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        
        return rs;
    }
    public ResultSet selectFromCod(){
        rs = responseQuery("SELECT * FROM " + tableCodName + ";");
        return rs;
    }
    public String lastMovementation(String cod_rastreio){      
        id_cod = getIdCod(cod_rastreio);
        if(id_cod == 0)
            JOptionPane.showMessageDialog(null, "Houve um problema na recuperaçao dos dados.", "ERRO: Última movimentação", JOptionPane.ERROR_MESSAGE);
        else
        {
            try
            {
                rs = responseQuery("SELECT data, acao FROM " + tableDadoName + " WHERE id_cod = "+ id_cod);
                while(rs.next())
                {
                    //lastDate = rs.getString("data");
                    lastAcao = rs.getString("acao");
                }
            }
            catch (Exception e)
            {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        }
        return lastAcao; 
    }
    public void delete(String cod_rastreio, boolean eraseAll){
        id_cod = getIdCod(cod_rastreio);
        sqlQuery("DELETE FROM " + tableDadoName + " WHERE id_cod = '" + id_cod + "'", "Deletando da tabela dado.");
        if(eraseAll)
            sqlQuery("DELETE FROM " + tableCodName + " WHERE cod = '" + cod_rastreio + "'", "Deletando da tabela cod.");
    }
    public String getSQLDetails(int id){
        return("SELECT data, local, acao, detalhes FROM " + tableDadoName + " WHERE id_cod = " + id);
    }
    public String getNameFromCod(String cod){
        String resp = "3RR0!";
        try {
            rs = responseQuery("SELECT nome FROM " + tableCodName + " WHERE cod = '" + cod + "'");
            rs.next();
            resp = rs.getString("nome");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resp;
    }
    public int getLocalHash(String cod){
        int resp = 1;
        try
        {
            rs = responseQuery("SELECT hash FROM " + tableCodName + " WHERE cod = '" + cod + "'");
            rs.next();
            resp = rs.getInt("hash");
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return resp;
    }
    public void showDetailsFrame(String cod){
        try {
            ArrayList al = new ArrayList();
            rs = responseQuery(getSQLDetails(getIdCod(cod)));
            while(rs.next())
            {
                al.add(rs.getString("data"));
                al.add(rs.getString("local"));
                al.add(rs.getString("acao"));
                al.add(rs.getString("detalhes"));
            }
            TrackingNumberWatcherDetailsFrame detailsFrame = new TrackingNumberWatcherDetailsFrame(getNameFromCod(cod), cod, al);
            detailsFrame.setLocationRelativeTo(null);
            detailsFrame.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(TrackingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void printCodTable(){
        out("----------------------Cod----------------------");
        try
        {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableCodName + ";");
            while ( rs.next() )
            {
                int id = rs.getInt("id");
                String  name = rs.getString("cod");
                String age  = rs.getString("nome");
                int hash = rs.getInt("hash");
                out("ID = " + id );
                out("cod = " + name );
                out("nome = " + age );
                out("hash = " + hash);

                System.out.println();
            }
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public void printDadoTable(){
        out("----------------------Dado----------------------");
        try
        {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableDadoName + ";");
            while ( rs.next() )
            {
                int id = rs.getInt("id");
                int  id_codi = rs.getInt("id_cod");
                String data  = rs.getString("data");
                String local = rs.getString("local");
                String acao = rs.getString("acao");
                String detalhes = rs.getString("detalhes");
                
                out( "ID = " + id );
                out( "id_cod = " + id_codi );
                out( "data = " + data );
                out( "local = " + local );
                out( "acao = " + acao );
                out( "detalhes = " + detalhes );
                
                System.out.println();
            }
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    private void out(String out){
        System.out.println(out);
    }
}
