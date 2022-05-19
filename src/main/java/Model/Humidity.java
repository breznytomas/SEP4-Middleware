package Model;

public class Humidity {


    public long Id;
    public long Timestamp;
    public float Value;

    public Humidity(long timestamp, float value) {
        Timestamp = timestamp;
        Value = value;
    }
}
