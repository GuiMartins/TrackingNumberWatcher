package trackingnumberwatcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSON
{
    private URL url;
    private BufferedReader in;
    private String line, all;
    private ArrayList al = new ArrayList();
    
    public boolean isValid(String url){
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
    public void setJSONDataInArray(int id_cod, String cod_rastreio){
        JSONParser parser = new JSONParser();
        JSONArray a;
        all = "";
        try
        {
            String url_link = "http://developers.agenciaideias.com.br/correios/rastreamento/json/" + cod_rastreio;
            this.url = new URL(url_link);
            in = new BufferedReader(new InputStreamReader(this.url.openStream()));
            while ((line = in.readLine()) != null)
            {
                all += line;
            }
            in.close();
            a = (JSONArray) parser.parse(all);
            for (Object o : a)
            {
                JSONObject person = (JSONObject) o;
                al.add((String) person.get("data"));    
                al.add((String) person.get("local"));
                al.add((String) person.get("acao"));
                al.add((String) person.get("detalhes"));
            }
        }
        catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList getJSONArray(){
        return al;
    }
    public String getJSONString(){
        return all;
    }
    public long getHash(String all){
        return all.length();
    }
    public long getWebHash(String cod){
        all = "";
        try
        {
            String url_link = "http://developers.agenciaideias.com.br/correios/rastreamento/json/" + cod;
            this.url = new URL(url_link);
            in = new BufferedReader(new InputStreamReader(this.url.openStream()));
            while ((line = in.readLine()) != null)
            {
                all += line;
            }
            in.close();
        }
        catch (IOException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return all.length();
    }
}
