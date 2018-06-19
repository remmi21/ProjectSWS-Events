import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// TODO: exception handling

public class Client {
    private String apiEndpoint="http://localhost:8080";

    public URL creatURL(String request_url, String action, Integer argNum, ArrayList<String> parameters) throws MalformedURLException {
        URL url=null;
        String tmp= action.replace("\"","");
        Scanner scan = new Scanner(System.in);
        System.out.println(action);
        switch(tmp){
            case "GET":
                System.out.print("Enter the id for the event you search for"+'\n'+"id=");
                String id = scan.next();

                url=new URL(apiEndpoint+request_url+id);
                return url;

            case "PUT":

                if(request_url.contains(":id:")) {
                    System.out.print("Enter the id for the event you want to edit" + '\n' + "id=");
                    String eid = scan.next();

                    request_url = request_url.replace(":id:", eid);

                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(request_url);
                    stringBuilder.append("?");

                    int j=0;
                    for (int i = 1; i < parameters.size(); i++) {
                        System.out.print(parameters.get(i).replaceAll("\"", "").replaceAll("]", "")+"=");

                        if(j==0) { // first parameter in body
                            stringBuilder.append(parameters.get(i).replaceAll("\"", "").replaceAll("]", "")+"="+scan.next());
                        } else {
                            stringBuilder.append("&"+parameters.get(i).replaceAll("\"", "").replaceAll("]", "")+"="+scan.next());
                        }

                        j++;
                    }

                    String final_url = stringBuilder.toString();
                    url = new URL(apiEndpoint + final_url);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(request_url);
                    stringBuilder.append("?");

                    int j=0;
                    for (int i = 1; i < parameters.size(); i++) {
                        System.out.print(parameters.get(i).replaceAll("\"", "").replaceAll("]", "")+"=");

                        if(j==0) { // first parameter in body
                            stringBuilder.append(parameters.get(i).replaceAll("\"", "").replaceAll("]", "")+"="+scan.next());
                        } else {
                            stringBuilder.append("&"+parameters.get(i).replaceAll("\"", "").replaceAll("]", "")+"="+scan.next());
                        }

                        j++;
                    }

                    String final_url = stringBuilder.toString();
                    url = new URL(apiEndpoint + final_url);
                }

                return url;

            case "POST":
                break;
            case "DELETE":
                break;
            default:
                break;
        }
        return null;
    }

    public JsonNode getJsonrequest(String request_url, String action, String argNum, String parameters) throws IOException {
        String tmp= request_url.replace("\"","");
        URL url;
        if(Integer.parseInt(argNum)==0) {
            url = new URL(apiEndpoint + tmp);
        }else{
            ArrayList<String> params = new ArrayList<String>(Arrays.asList(parameters.split(",")));
            url = creatURL(tmp,action,Integer.parseInt(argNum),params);
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
        JsonNode json_respons;
        while(requesting) {
            System.out.println("Please choose a possible action:");
            System.out.println("-1) Quit");
            for (int i = 0; i < json.size(); i++) {
                if (i > 9) {
                    System.out.println(Integer.toString(i) + ") " + json.get(i).get("action").toString().replaceAll("\"", "") + json.get(i).get("url").toString().replaceAll("\"", ""));
                } else {
                    System.out.println(" "+Integer.toString(i) + ") " + json.get(i).get("action").toString().replaceAll("\"", "") + json.get(i).get("url").toString().replaceAll("\"", ""));
                }
            }
            input = scan.nextInt();
            switch (input){
                case -1:
                    requesting=false;
                    break;
                default:
                    json_respons=c.getJsonrequest(json.get(input).get("url").toString(),json.get(input).get("action").toString(),
                            json.get(input).get("argNum").toString(),json.get(input).get("parameters").toString());
                    System.out.println(json_respons);
                    break;
            }
        }

    }
}
