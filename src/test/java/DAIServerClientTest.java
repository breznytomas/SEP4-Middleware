import Model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DAIServerClientTest {



    static DAIServerClient daiServerClient;
    Reading reading;

    CharSequence data;



    @BeforeEach
    void init() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {

        daiServerClient = new DAIServerClient();
        long epochTime = Instant.now().getEpochSecond();

        Temperature temperature = new Temperature(epochTime, 22);

        Humidity humidity = new Humidity(epochTime, (float) Math.floor(Math.random() * 100));

        CarbonDioxide carbonDioxide = new CarbonDioxide(epochTime, (float) Math.floor(Math.random() * 100));

        Light light = new Light(epochTime, (float) Math.floor(Math.random() * 100));

        List<Temperature> temperatures = new ArrayList<>();
        temperatures.add(temperature);

        List<Humidity> humidities = new ArrayList<>();
        humidities.add(humidity);

        List<CarbonDioxide> carbonDioxides = new ArrayList<>();
        carbonDioxides.add(carbonDioxide);

        List<Light> lights = new ArrayList<>();
        lights.add(light);


        reading = new Reading("0004A30B00251192", temperatures, humidities, lights, carbonDioxides);
    }




   @Test
   void TestReadingConstruction() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
       data = "{\"cmd\":\"rx\",\"seqno\":936,\"EUI\":\"0004A30B00251192\",\"ts\":1653997514706,\"fcnt\":1,\"port\":2,\"freq\":867300000,\"rssi\":-110,\"snr\":-2,\"toa\":0,\"dr\":\"SF12 BW125 4/5\",\"ack\":false,\"bat\":255,\"offline\":false,\"data\":\"002f00d400d100f7\"}";
       var TestReading = daiServerClient.CreateReadingObject(data);
       assertEquals(47.0f,TestReading.HumidityList.get(0).Value);
       assertEquals(21.2f,TestReading.TemperatureList.get(0).Value);
       assertEquals(209.0f,TestReading.CarbonDioxideList.get(0).Value);
       assertEquals(247.0f,TestReading.LightLists.get(0).Value);
    }

    @Test
    void TestReadingConstruction2() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        data = "{\"cmd\":\"rx\",\"seqno\":936,\"EUI\":\"0004A30B00251192\",\"ts\":1653997514706,\"fcnt\":1,\"port\":2,\"freq\":867300000,\"rssi\":-110,\"snr\":-2,\"toa\":0,\"dr\":\"SF12 BW125 4/5\",\"ack\":false,\"bat\":255,\"offline\":false,\"data\":\"01aa02bb03cc04dd\"}";
        var TestReading = daiServerClient.CreateReadingObject(data);
        assertEquals(426.0f,TestReading.HumidityList.get(0).Value);
        assertEquals(69.9f,TestReading.TemperatureList.get(0).Value);
        assertEquals(972.0f,TestReading.CarbonDioxideList.get(0).Value);
        assertEquals(1245.0f,TestReading.LightLists.get(0).Value);
    }



    @Test
    void serverReplyValueForMotorNoAction() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        reading.TemperatureList.get(0).Value=13;
        var reply = daiServerClient.PostReading(reading);
        assertNotEquals(12,reply);
    }

    @Test
    void serveReplyValueForMotor5percentUp() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        reading.TemperatureList.get(0).Value=(float)52.5;
        var reply = daiServerClient.PostReading(reading);
        assertEquals(50,reply);
    }

    @Test
    void serverReplyValueMotor10PercentUp() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        reading.TemperatureList.get(0).Value=(float)55;
        var reply = daiServerClient.PostReading(reading);
        assertEquals(100,reply);
    }

    @Test
    void serverReplyValueMotor15PercentUp() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        reading.TemperatureList.get(0).Value=(float)57.5;
        var reply = daiServerClient.PostReading(reading);
        assertEquals(150,reply);
    }

    @Test
    void serverReplyValueMotor20PercentUp() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        reading.TemperatureList.get(0).Value=(float)60;
        var reply = daiServerClient.PostReading(reading);
        assertEquals(200,reply);
    }

    @Test
    void serverReplyValueMotorMinusEdgeValue() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        reading.TemperatureList.get(0).Value=(float)-100;
        var reply = daiServerClient.PostReading(reading);
        assertEquals(0,reply);
    }






}