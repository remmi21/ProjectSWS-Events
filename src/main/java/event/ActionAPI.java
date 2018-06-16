package event;

public class ActionAPI {
    private String url;
    private String action;
    private int argNum;

    public ActionAPI( String action,String url, int argNum) {
        this.url = url;
        this.action = action;
        this.argNum = argNum;
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
}
