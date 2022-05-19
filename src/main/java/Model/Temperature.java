package Model;

public class Temperature {

    public long Id;
    public long Timestamp;
    public float Value;


    public Temperature( long timestamp, float value) {
        Timestamp = timestamp;
        Value = value;
    }
}
