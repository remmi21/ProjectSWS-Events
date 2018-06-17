import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Client {
    private String apiEndpoint="http://localhost:8080";

    public URL creatURL(String request_url, String action,Integer argNum) throws MalformedURLException {
        URL url;
        String tmp= action.replace("\"","");
        Scanner scan = new Scanner(System.in);
        System.out.println(action);
        switch(tmp){
            case "GET":
                System.out.println("Enter an id for the item you search for");
                String id = scan.next();
                url=new URL(apiEndpoint+request_url+id);
                return url;
        }
        return null;
    }

    public JsonNode getJsonrequest(String request_url, String action, String argNum) throws IOException {
        String tmp= request_url.replace("\"","");
        URL url;
        if(Integer.parseInt(argNum)==0) {
            url = new URL(apiEndpoint + tmp);
        }else{
            url = creatURL(tmp,action,Integer.parseInt(argNum));
            System.out.println(url);
        }
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
        JsonNode json_response;
        while(requesting) {
            System.out.println("Please choose a possible action:");
            System.out.println("-1) Quit");
            for (int i = 0; i < json.size(); i++) {
                if (i > 9) {
                    System.out.println(Integer.toString(i) + ") " + json.get(i).get("action") + json.get(i).get("url"));
                } else {
                    System.out.println(" "+Integer.toString(i) + ") " + json.get(i).get("action") + json.get(i).get("url"));
                }
            }
            input = scan.nextInt();
            switch (input){
                case -1:
                    requesting=false;
                    break;
                case 0:
                    json_response=c.getJsonrequest(json.get(0).get("url").toString(),json.get(0).get("action").toString(),
                            json.get(0).get("argNum").toString());
                    System.out.println(json_response);
                    break;
                case 1:
                    json_response=c.getJsonrequest(json.get(input).get("url").toString(),json.get(input).get("action").toString(),
                            json.get(input).get("argNum").toString());
                    System.out.println(json_response);
                    break;
            }
        }

    }
}
