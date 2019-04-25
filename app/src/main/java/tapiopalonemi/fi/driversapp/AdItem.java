package tapiopalonemi.fi.driversapp;

public class AdItem {
    private String name;
    private String map;
    private String webPage;
    private String call;
    private int image;

    public AdItem(String name, String map, String webPage, String call, int image) {
        this.name = name;
        this.map = map;
        this.webPage = webPage;
        this.call = call;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
