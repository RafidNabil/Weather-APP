package weatherproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;
import static weatherproject.JSONDataCollection.values;
import static weatherproject.JSONDataCollection.variables;

class functions1 {

    public static String getValue(String fstring, String fs) {
        String data = "";
        int indx = fstring.indexOf(fs);
        indx += fs.length() + 2;
        while (fstring.charAt(indx) != ',') {
            if (fstring.charAt(indx) != '\"' && fstring.charAt(indx) != '}'&& fstring.charAt(indx) != ']') {
                data = data + fstring.charAt(indx);
            }
            indx++;
        }
        //long tmp = Long.parseLong(data);
        //java.util.Date time = new java.util.Date((long)tmp*1000);
        return data;
    }
    
    public static void writeFile(String arr[]){
        try {
                Formatter format = new Formatter("data.txt");
                for (int i = 0; i < 16; i++) {
                    format.format("%s\r\n", values[i]);
                }
                format.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
    
    public static void readFile(){
        try {
                File file = new File("data.txt");
                Scanner scan = new Scanner(file);
                
                while(scan.hasNext())
                {
                    System.out.println(scan.next());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}

public class JSONDataCollection {
                                 //0      1       2      3          4            5          6            7           8          9            10         11        12        13        14       15
    static String variables[] = {"lon", "lat", "main", "temp", "feels_like", "temp_min", "temp_max", "pressure", "humidity", "visibility", "speed", "country", "sunrise", "sunset", "name", "icon"};
    static String values[] = new String[16];
    
   

    //api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=85e2bf12407292d2571cae8391915d14
    //http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=85e2bf12407292d2571cae8391915d14
    public static String API_KEY = "85e2bf12407292d2571cae8391915d14";
    public static String LOCATION ;
    public static String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&APPID=" + API_KEY;
    

    public static void getData1(String urlString) {
        try {
            StringBuilder result = new StringBuilder();
            URL url1 = new URL(urlString);
            URLConnection conn = url1.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            //System.out.println(result);
            String fstring = new String(result);
            //System.out.println(fstring);

            for (int i = 0; i < 16; i++) {
                values[i] = functions1.getValue(fstring, variables[i]);
            }

            functions1.writeFile(values);
            //functions1.readFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void getData1() {
        try {
            StringBuilder result = new StringBuilder();
            URL url1 = new URL(urlString);
            URLConnection conn = url1.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            //System.out.println(result);
            String fstring = new String(result);
            //System.out.println(fstring);

            for (int i = 0; i < 16; i++) {
                values[i] = functions1.getValue(fstring, variables[i]);
            }

            functions1.writeFile(values);
            //functions1.readFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
