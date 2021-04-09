import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    //URLS
    public static final String SERVER_URL = "https://foodgps.r2.enst.fr/";
    public static final String USER_FONCTIONS = "http/user_functions.php";

    //ArrayList declaration
    private ArrayList<String> keys = new ArrayList<String>();
    private ArrayList<String> values = new ArrayList<String>();

    public String getUserName(int id) {
        keys.add("action");
        values.add("get_username");
        keys.add("id");
        values.add(String.valueOf(id));
        String[] answer = postTreatment(post(SERVER_URL+USER_FONCTIONS, keys, values));

        return answer==null?null:answer[0];
    }

    public String hello_world() {
        keys.add("var");
        values.add("str");
        String answer = post(SERVER_URL+"hello_world.php",keys, values);
        keys.clear();
        values.clear();

        return answer;
    }


    /**
     * Send a POST request to the server
     * @param address   the server address
     * @param keys      the POST's variables keys
     * @param values    the POST's variables values
     *
     * @return the server's answer
     */
    public String post(String address, ArrayList<String> keys, ArrayList<String> values) {
        String result = "";
        OutputStreamWriter writer = null;
        BufferedReader reader = null;

        try {
            //Request creation
            String data = "";
            for(int i=0; i < keys.size(); i++) {
                if(i!=0) data+="&";
                data+= URLEncoder.encode(keys.get(i), "UTF-8")+"="+URLEncoder.encode(values.get(i), "UTF-8");
            }

            //Connexion to the Server
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            //Send request
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(data);
            writer.flush();

            //Read answer
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = reader.readLine()) != null)
                result+=line;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {writer.close();}catch (Exception e){}
            try {reader.close();}catch (Exception e){}
        }

        return result;
    }

    private String[] postTreatment(String answer) {
        String[] results = answer.split("%");
        if(results[0].matches("false"))
            return null;
        keys.clear();
        values.clear();
        return Arrays.copyOfRange(results, 1, results.length);
    }
}
