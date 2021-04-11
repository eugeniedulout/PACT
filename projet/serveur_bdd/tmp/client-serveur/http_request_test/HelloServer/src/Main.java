import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Main {

    public static void main(String args[]) {

        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();
        keys.add("var");
        values.add("sth");
        String url = "https://foodgps.r2.enst.fr/hello_world.php";
        String retour = post(url, keys, values);
        System.out.println(retour);

    }


    public static String post(String adress, ArrayList<String> keys, ArrayList<String> values) {
        String result = "";

        OutputStreamWriter writer = null;
        BufferedReader reader = null;

        try {
            String data = "";
            for(int i=0; i< keys.size(); i++) {
                if(i!=0) data+="&";
                data+= URLEncoder.encode(keys.get(i), "UTF-8")+"="+URLEncoder.encode(values.get(i), "UTF-8");
            }
            URL url = new URL(adress);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(data);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
                result+=line;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { writer.close();} catch (Exception e) {}
            try { reader.close();} catch (Exception e) {}
        }
        return result;
    }
}
