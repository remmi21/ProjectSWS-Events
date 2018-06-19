package event;

import java.util.ArrayList;

public class ActionAPI {
    private String url;
    private String action;
    private int argNum;
    private ArrayList<String> parameters;

    public ActionAPI( String action,String url, int argNum, ArrayList<String> parameters) {
        this.url = url;
        this.action = action;
        this.argNum = argNum;
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getArgNum() {
        return argNum;
    }

    public void setArgNum(int argNum) {
        this.argNum = argNum;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setUsage(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

}
