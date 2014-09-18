package trackingnumberwatcher;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author guilherme
 */
public class TrackingNumberWatcherDBConn
{
    private Connection c = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private static final String jdbc = "org.sqlite.JDBC";
    private static final String connFile = "jdbc:sqlite:/home/guilherme/Desktop/tnw.db";
    private static final String tableCodName = "cod_rastreio"; //mudar that name???
    private static final String tableDadoName = "dado";
    private int id_cod;
    private JSON json;
    private String lastDate, lastAcao;
    
    
    public void initiateDB()
    {   
        System.out.println("Criando as tabelas do barulho...");
        sqlQuery("CREATE TABLE IF NOT EXISTS " + tableCodName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, cod TEXT NOT NULL, nome TEXT NOT NULL)");
        sqlQuery("CREATE TABLE IF NOT EXISTS " + tableDadoName + "(id INTEGER PRIMARY KEY AUTOINCREMENT, id_cod INTEGER NOT NULL, data DATE NOT NULL, local TEXT NOT NULL, acao TEXT NOT NULL, detalhes TEXT NOT NULL)");
    }
    
    private void sqlQuery(String query)
    {
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            c.close();
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public void insertNewCod(String cod, String nome)
    {
        System.out.println("Here comes the cod insertion");
        sqlQuery("INSERT INTO " + tableCodName + " (cod, nome) VALUES ('"+ cod +"', '"+ nome +"');");
    }
    
    public void insertNewData(String cod_rastreio)
    {
        id_cod = getId_cod(cod_rastreio);
        json = new JSON();
        if(id_cod == 0)
            System.out.println("id veio zerado, fera.");
        else
            json.getData(id_cod, cod_rastreio);
    }
    
    public int getId_cod(String cod)
    {
        System.out.println("Getting cod ID...");
        int id = 0;
        try
        {
            ResultSet response = responseQuery("SELECT id FROM " + tableCodName + " WHERE cod = '"+ cod + "'");
            if(response.next())
                id = response.getInt("id");
            c.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TrackingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public void insertJSONData(int id_cod, String data, String local, String acao, String detalhes)
    {
        System.out.println("Here comes the data insertion");
        sqlQuery("INSERT INTO " + tableDadoName + " (id_cod, data, local, acao, detalhes) VALUES ('" + id_cod + "', '" + data + "', '" + local + "', '" + acao + "', '" + detalhes + "');");
    }
    
    public ResultSet responseQuery(String query)
    {
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);

            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return rs;
    }
    
    public ResultSet getNameAndCod() throws SQLException
    {
        rs = responseQuery("SELECT * FROM " + tableCodName + ";");
        return rs;
    }
    
    public String lastMovementation(String cod_rastreio)
    {      
        id_cod = getId_cod(cod_rastreio);
        System.out.println("id = " + id_cod);
        if(id_cod == 0)
            System.out.println("Chora pro id que veio zerado!");
        else
        {
            try
            {
                System.out.println("Calculating last movementation date.");
                rs = responseQuery("SELECT data, acao FROM " + tableDadoName + " WHERE id_cod = "+ id_cod);
                while(rs.next())
                {
                    lastDate = rs.getString("data");
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
    
    public void delete(String collumName, String value)
    {
        out("Here comes the deletion!");
        id_cod = getId_cod(value);
        sqlQuery("DELETE FROM " + tableDadoName + " WHERE id_cod = '" + id_cod + "'");
        sqlQuery("DELETE FROM " + tableCodName + " WHERE " + collumName + " = '" + value + "'");
    }
    
    private void out(String out){System.out.println(out);}
    
    public void printCodTable()
    {
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableCodName + ";");
            while ( rs.next() )
            {
                int id = rs.getInt("id");
                String  name = rs.getString("cod");
                String age  = rs.getString("nome");
                System.out.println( "ID = " + id );
                System.out.println( "cod = " + name );
                System.out.println( "nome = " + age );

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public void printDadoTable()
    {
        System.out.println("----------------------Dado----------------------");
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);

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
                
                System.out.println( "ID = " + id );
                System.out.println( "id_cod = " + id_codi );
                System.out.println( "data = " + data );
                System.out.println( "local = " + local );
                System.out.println( "acao = " + acao );
                System.out.println( "detalhes = " + detalhes );
                
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
