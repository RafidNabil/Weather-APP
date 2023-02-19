package weatherproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Formatter;
import java.util.Scanner;
import static weatherproject.JSONDataCollection.values;
import static weatherproject.JSONDataCollection.variables;

class functions {

    public static String getValue(String fstring, String fs) {

        String data = "";
        int indx;
        if (fs.equals("main")) {
            indx = fstring.indexOf(fs, fstring.indexOf(fs) + 10);
        } else {
            indx = fstring.indexOf(fs);
        }

        indx += fs.length() + 2;
        while (fstring.charAt(indx) != ',') {
            if (fstring.charAt(indx) != '\"' && fstring.charAt(indx) != '}' && fstring.charAt(indx) != ']') {
                data = data + fstring.charAt(indx);
            }
            indx++;
        }
        //long tmp = Long.parseLong(data);
        //java.util.Date time = new java.util.Date((long)tmp*1000);

        return data;
    }

    public static void writeFile(String arr[]) {
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

    public static void readFile() {
        try {
            File file = new File("data.txt");
            Scanner scan = new Scanner(file);

            while (scan.hasNext()) {
                System.out.println(scan.next());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

public class JASONBulkData {

    //http://api.openweathermap.org/data/2.5/forecast?q=dhaka&cnt=40&appid=85e2bf12407292d2571cae8391915d14&units=metric
    //0           1            2           3          4             5            6            7           8       9     10       11      12     13
    static String variables[] = {"temp", "feels_like", "temp_min", "temp_max", "pressure", "description", "humidity", "visibility", "speed", "deg", "gust", "icon", "main", "dt"};
    static String days[][] = new String[5][14];

    public static String API_KEY = "85e2bf12407292d2571cae8391915d14";
    public static String LOCATION = JSONDataCollection.LOCATION;
    //public static String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=london&cnt=40&appid=85e2bf12407292d2571cae8391915d14&units=metric";
    public static String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + LOCATION + "&cnt=40&appid=85e2bf12407292d2571cae8391915d14&units=metric";

    public static void getData(String urlstr) {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlstr);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            String fstring = new String(result);
            System.out.println(fstring);

            String dates[] = new String[6];

            String s = functions.getValue(fstring, "dt");
            dates[0] = s;

            for (int i = 0; i < 5; i++) {
                long v = Long.parseLong(s);
                v += 86400;
                s = Long.toString(v);
                dates[i + 1] = s;
            }

            String ss[] = new String[5];

            for (int i = 0; i < 5; i++) {
                ss[i] = fstring.substring(fstring.indexOf(dates[i]), fstring.indexOf("dt", fstring.indexOf(dates[i])));
            }
//            for (int i = 0; i < 5; i++) {
//                System.out.println(ss[i]);
//            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 14; j++) {
                    days[i][j] = functions.getValue(ss[i], variables[j]);
                }
            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 14; j++) {
                    System.out.print(days[i][j] + " ");
                }
                System.out.println("");
            }

            //functions.writeFile(values);
            //functions.readFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
