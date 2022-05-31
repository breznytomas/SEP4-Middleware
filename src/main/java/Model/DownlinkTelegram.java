package Model;

public class DownlinkTelegram {


    public DownlinkTelegram(String cmd, String EUI, int port, boolean confirmed, String data) {
        this.cmd = cmd;
        this.EUI = EUI;
        this.port = port;
        this.confirmed = confirmed;
        this.data = data;
    }

    private String cmd;

    private String EUI;

    private int port;

    private boolean confirmed;

    private String data;



}
