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

class DAIServerClientTest {



    static DAIServerClient daiServerClient;
    Reading reading;

    CharSequence data;



    @BeforeAll
    static void InitBeforeAll(){
        daiServerClient = new DAIServerClient();
    }

    @BeforeEach
    void init() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        long epochTime = Instant.now().getEpochSecond();

        Temperature temperature = new Temperature(epochTime, 22);
       /* Temperature temperature1 = new Temperature(epochTime,(float)Math.floor(Math.random()*100));
        Temperature temperature2 = new Temperature(epochTime,(float)Math.floor(Math.random()*100));*/


        Humidity humidity = new Humidity(epochTime, (float) Math.floor(Math.random() * 100));
       /* Humidity humidity1 = new Humidity(epochTime,(float)Math.floor(Math.random()*100));
        Humidity humidity2 = new Humidity(epochTime,(float)Math.floor(Math.random()*100));*/


        CarbonDioxide carbonDioxide = new CarbonDioxide(epochTime, (float) Math.floor(Math.random() * 100));
        /*CarbonDioxide carbonDioxide1 = new CarbonDioxide(epochTime,(float)Math.floor(Math.random()*100));
        CarbonDioxide carbonDioxide3 = new CarbonDioxide(epochTime,(float)Math.floor(Math.random()*100));*/


        Light light = new Light(epochTime, (float) Math.floor(Math.random() * 100));
       /* Light light1 = new Light(epochTime,(float)Math.floor(Math.random()*100));
        Light light4 = new Light(epochTime,(float)Math.floor(Math.random()*100));*/


        List<Temperature> temperatures = new ArrayList<>();
        temperatures.add(temperature);
       /*  temperatures.add(temperature1);
         temperatures.add(temperature2);*/

        List<Humidity> humidities = new ArrayList<>();
        humidities.add(humidity);
       /*  humidities.add(humidity1);
         humidities.add(humidity2);*/

        List<CarbonDioxide> carbonDioxides = new ArrayList<>();
        carbonDioxides.add(carbonDioxide);
        /* carbonDioxides.add(carbonDioxide1);
         carbonDioxides.add(carbonDioxide3);
*/
        List<Light> lights = new ArrayList<>();
        lights.add(light);
       /*  lights.add(light1);
         lights.add(light4);*/


        reading = new Reading("0004A30B00259D2C", temperatures, humidities, lights, carbonDioxides);


        data = "{\"cmd\":\"rx\",\"seqno\":936,\"EUI\":\"0004A30B00251192\",\"ts\":1653997514706,\"fcnt\":1,\"port\":2,\"freq\":867300000,\"rssi\":-110,\"snr\":-2,\"toa\":0,\"dr\":\"SF12 BW125 4/5\",\"ack\":false,\"bat\":255,\"offline\":false,\"data\":\"002f00d4000000f7\"}";
          }




   @Test
   void TestConstruction() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
       var TestReading = daiServerClient.CreateReadingObject(data);
       assertEquals(47.0,TestReading.HumidityList.get(0).Value);
       assertEquals(21.200000762939453,TestReading.TemperatureList.get(0).Value);
       assertEquals(0.0,TestReading.CarbonDioxideList.get(0).Value);
       assertEquals(247.0,TestReading.LightLists.get(0).Value);
    }


    @Test
    void serverReplyValueForMotor() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {
        var reply = daiServerClient.PostReading(reading);
        assertEquals(200,reply);


    }


}