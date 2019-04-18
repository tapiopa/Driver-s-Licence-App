package tapiopalonemi.fi.driversapp;

public class DrivingInfo {

    private int tipID;
    private String tipString;
    private int tipPage;

    public DrivingInfo(int tipID, String tipString, int tipPage) {
        this.tipID = tipID;
        this.tipString = tipString;
        this.tipPage = tipPage;
    }

    public DrivingInfo() {
    }

    public int getTipID() {
        return tipID;
    }

    public void setTipID(int tipID) {
        this.tipID = tipID;
    }

    public String getTipString() {
        return tipString;
    }

    public void setTipString(String tipString) {
        this.tipString = tipString;
    }

    public int getTipPage() {
        return tipPage;
    }

    public void setTipPage(int tipPage) {
        this.tipPage = tipPage;
    }
}
