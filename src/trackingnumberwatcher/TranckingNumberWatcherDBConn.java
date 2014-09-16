/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackingnumberwatcher;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author guilherme
 */
public class TranckingNumberWatcherDBConn
{
    private Connection c = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private static String jdbc = "org.sqlite.JDBC";
    private static String connFile = "jdbc:sqlite:/home/guilherme/Desktop/tnw.db";
    private static String codTableName = "cod_rastreio"; //mudar that name???
    private static String dadoTableName = "dado";
    private int id_cod;
    private String cod;
    private JSON json;
    
    
    public void initiateDB()
    {   
        System.out.println("Criando as tabelas do barulho...");
        sqlQuery("CREATE TABLE IF NOT EXISTS " + codTableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, cod TEXT NOT NULL, nome TEXT NOT NULL)");
        sqlQuery("CREATE TABLE IF NOT EXISTS " + dadoTableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT, id_cod INTEGER NOT NULL, data TEXT NOT NULL, local TEXT NOT NULL, acao TEXT NOT NULL, detalhes TEXT NOT NULL)");
    }
    
    private void sqlQuery(String query)
    {
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);
            c.setAutoCommit(false);
            System.out.println("SQL query over here.");
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
        this.cod = cod;
        sqlQuery("INSERT INTO " + codTableName + " (cod, nome) VALUES ('"+ cod +"', '"+ nome +"');");
    }
    
    public void insertNewData(String cod_rastreio)
    {
        id_cod = getId_cod(cod_rastreio);
        json = new JSON();
        json.getData(id_cod, cod_rastreio);
    }
    
    public int getId_cod(String cod)
    {
        System.out.println("Getting cod ID...");
        int id = 0;
        try
        {
            ResultSet response = responseQuery("SELECT id FROM " + codTableName + " WHERE cod = '"+ cod + "'");
            //response = responseQuery("SELECT id FROM " + codTableName + " WHERE cod = 'RI025832971CN';");
            if(response.next())
            {
                id = response.getInt("id");
            }
            else
                id = -1;
            c.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TranckingNumberWatcherDBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("----" + id + "----");
        return id;
    }
    
    public void insertJSONData(int id_cod, String data, String local, String acao, String detalhes)
    {
        System.out.println("Here comes the data insertion");
        sqlQuery("INSERT INTO " + dadoTableName + " (id_cod, data, local, acao, detalhes) VALUES ('" + id_cod + "', '" + data + "', '" + local + "', '" + acao + "', '" + detalhes + "');");
    }
    
    public ResultSet responseQuery(String query)
    {
        System.out.println("Retornando algo pra alguem.");
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);

            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println("Opa! Fechar a conexao!");
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return rs;
    }
    
    public ResultSet refreshTable()
    {
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);

            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM cod_rastreio;" );
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return rs;
    }
    
    public void teste() //print table
    {
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);

            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM cod_rastreio;" );
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
    
    public void teste2() //print table
    {
        System.out.println("----------------------teste 2 here----------------------");
        try
        {
            Class.forName(jdbc);
            c = DriverManager.getConnection(connFile);

            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM dado;" );
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
