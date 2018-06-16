import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Client {
    private String apiEndpoint="http://localhost:8090";

    public static JsonNode getJsonrequest(String request_url, String action, String argNum,Client c) throws IOException {
        String tmp= request_url.replace("\"","");
        URL url = new URL(c.apiEndpoint+tmp);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(is);
        return json;
    }


    public static void main(String[] args) throws IOException {
        Client c = new Client();
        String urlString = c.apiEndpoint+"/";
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(is);
        Scanner scan = new Scanner(System.in);
        boolean requesting=true;
        int input;
        JsonNode json_respons;
        while(requesting) {
            System.out.println("Please choose a possible action:");
            System.out.println("10 Quite");
            for (int i = 0; i < json.size(); i++) {
                System.out.println(Integer.toString(i) + ") " + json.get(i).get("action") + json.get(i).get("url"));
            }
            input = scan.nextInt();
            switch (input){
                case 10:
                    requesting=false;
                    break;
                case 0:
                    json_respons=getJsonrequest(json.get(0).get("url").toString(),json.get(0).get("action").toString(),
                            json.get(0).get("argNum").toString(),c);
                    System.out.println(json_respons);
                    break;
            }
        }

    }
}
