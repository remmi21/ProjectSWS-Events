package event;

public class ActionAPI {
    private String url;
    private String action;
    private int argNum;
    private String usage;

    public ActionAPI( String action,String url, int argNum, String usage) {
        this.url = url;
        this.action = action;
        this.argNum = argNum;
        this.usage = usage;
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

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

}
