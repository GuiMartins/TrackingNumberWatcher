package trackingnumberwatcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSON
{
    private URL url;
    private BufferedReader in;
    private String line, all = "";
    private TrackingNumberWatcherDBConn dbConn = new TrackingNumberWatcherDBConn();
    private int hash;
    
    public boolean isValid(String url)
    {
        boolean flag = false;
        try
        {
            url = "http://developers.agenciaideias.com.br/correios/rastreamento/json/" + url;
            this.url = new URL(url);
            in = new BufferedReader(new InputStreamReader(this.url.openStream()));
            if("<!DOCTYPE html>".equals(line = in.readLine()))
            {
                System.out.println("You cant build there!");
                flag = false;
            }
            else
            {
                System.out.println("The goddammit json is right.");
                flag = true;
            }
            in.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    public void getData(int id_cod, String cod_rastreio)
    {
        JSONParser parser = new JSONParser();
        JSONArray a;
        try
        {
            String url = "http://developers.agenciaideias.com.br/correios/rastreamento/json/" + cod_rastreio;
            this.url = new URL(url);
            in = new BufferedReader(new InputStreamReader(this.url.openStream()));
            while ((line = in.readLine()) != null)
            {
                all += line;
            }
            in.close();
            hash = all.hashCode();
            a = (JSONArray) parser.parse(all);
            for (Object o : a)
            {
                JSONObject person = (JSONObject) o;

                String data = (String) person.get("data");
                String local = (String) person.get("local");      
                String acao = (String) person.get("acao");
                String detalhes = (String) person.get("detalhes");
                
                dbConn.insertJSONData(id_cod, data, local, acao, detalhes);
            }
            dbConn.insertHash(hash, cod_rastreio);
        }
        catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getWebHash(String cod)
    {
        try
        {
            String url = "http://developers.agenciaideias.com.br/correios/rastreamento/json/" + cod;
            this.url = new URL(url);
            in = new BufferedReader(new InputStreamReader(this.url.openStream()));
            while ((line = in.readLine()) != null)
            {
                all += line;
            }
            in.close();
            hash = all.hashCode();
        }
        catch (IOException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }
    
    // TRASH_LINE_TRASH_LINE_TRASH_LINE_TRASH_LINE_TRASH_LINE_TRASH_LINE_TRASH_LINE_TRASH_LINE_TRASH_LINE
    
    public void readJSONFileFromURL() throws FileNotFoundException, ParseException, org.json.simple.parser.ParseException
    {
        System.out.println("Reading");
        try
        {
            url = new URL("http://developers.agenciaideias.com.br/correios/rastreamento/json/RI025832971CN");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null)
            {
                all += line;
            }
            in.close();
            
//            readingJSON(all);
//            
//            System.out.println(all);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }
    
    public void readingJSON(String file) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException
    {
        int count = 0;
        JSONParser parser=new JSONParser();
        JSONArray a = (JSONArray) parser.parse(file);

        for (Object o : a)
        {
            JSONObject person = (JSONObject) o;

            String data = (String) person.get("data");
            System.out.println(data);

            String local = (String) person.get("local");
            System.out.println(local);

            String acao = (String) person.get("acao");
            System.out.println(acao);
            
            String detalhes = (String) person.get("detalhes");
            System.out.println(detalhes);
            count++;
            System.out.println();
        }
        System.out.println(count + "\n");
    }
}
