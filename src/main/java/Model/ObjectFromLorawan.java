package Model;

public class ObjectFromLorawan {

        private float rssi;
        private float seqno;
        private String data;
        private float toa;
        private float freq;
        private boolean ack;
        private float fcnt;
        private String dr;
        private boolean offline;
        private float bat;
        private float port;
        private float snr;
        private String EUI;
        private String cmd;
        private float ts;


        // Getter Methods

        public float getRssi() {
            return rssi;
        }

        public float getSeqno() {
            return seqno;
        }

        public String getData() {
            return data;
        }

        public float getToa() {
            return toa;
        }

        public float getFreq() {
            return freq;
        }

        public boolean getAck() {
            return ack;
        }

        public float getFcnt() {
            return fcnt;
        }

        public String getDr() {
            return dr;
        }

        public boolean getOffline() {
            return offline;
        }

        public float getBat() {
            return bat;
        }

        public float getPort() {
            return port;
        }

        public float getSnr() {
            return snr;
        }

        public String getEUI() {
            return EUI;
        }

        public String getCmd() {
            return cmd;
        }

        public float getTs() {
            return ts;
        }

        // Setter Methods

        public void setRssi(float rssi) {
            this.rssi = rssi;
        }

        public void setSeqno(float seqno) {
            this.seqno = seqno;
        }

        public void setData(String data) {
            this.data = data;
        }

        public void setToa(float toa) {
            this.toa = toa;
        }

        public void setFreq(float freq) {
            this.freq = freq;
        }

        public void setAck(boolean ack) {
            this.ack = ack;
        }

        public void setFcnt(float fcnt) {
            this.fcnt = fcnt;
        }

        public void setDr(String dr) {
            this.dr = dr;
        }

        public void setOffline(boolean offline) {
            this.offline = offline;
        }

        public void setBat(float bat) {
            this.bat = bat;
        }

        public void setPort(float port) {
            this.port = port;
        }

        public void setSnr(float snr) {
            this.snr = snr;
        }

        public void setEUI(String EUI) {
            this.EUI = EUI;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public void setTs(float ts) {
            this.ts = ts;
        }
    }

