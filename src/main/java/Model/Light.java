package Model;

public class Light {


    public long Id;
    public long Timestamp;
    public float Value;


    public Light(long timestamp, float value) {

        Timestamp = timestamp;
        Value = value;
    }
}
