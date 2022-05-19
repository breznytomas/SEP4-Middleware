import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {


        String uri = "wss://iotnet.cibicom.dk/app?token=vnoUBQAAABFpb3RuZXQuY2liaWNvbS5ka4lPPjDJdv8czIiFOiS49tg=";

         WebsocketClient websocketClient = new WebsocketClient(uri);
         DAIServerClient daiServerClient = new DAIServerClient();


         /*long epochTime = Instant.now().getEpochSecond();


        Temperature temperature = new Temperature(epochTime,(float)Math.floor(Math.random()*100));
        Temperature temperature1 = new Temperature(epochTime,(float)Math.floor(Math.random()*100));
        Temperature temperature2 = new Temperature(epochTime,(float)Math.floor(Math.random()*100));





        Humidity humidity = new Humidity(epochTime,(float)Math.floor(Math.random()*100));
        Humidity humidity1 = new Humidity(epochTime,(float)Math.floor(Math.random()*100));
        Humidity humidity2 = new Humidity(epochTime,(float)Math.floor(Math.random()*100));


         CarbonDioxide carbonDioxide = new CarbonDioxide(epochTime,(float)Math.floor(Math.random()*100));
        CarbonDioxide carbonDioxide1 = new CarbonDioxide(epochTime,(float)Math.floor(Math.random()*100));
        CarbonDioxide carbonDioxide3 = new CarbonDioxide(epochTime,(float)Math.floor(Math.random()*100));



         Light light = new Light(epochTime,(float)Math.floor(Math.random()*100));
        Light light1 = new Light(epochTime,(float)Math.floor(Math.random()*100));
        Light light4 = new Light(epochTime,(float)Math.floor(Math.random()*100));



         List<Temperature> temperatures = new ArrayList<>();
         temperatures.add(temperature);
         temperatures.add(temperature1);
         temperatures.add(temperature2);

         List<Humidity> humidities = new ArrayList<>();
         humidities.add(humidity);
         humidities.add(humidity1);
         humidities.add(humidity2);

         List<CarbonDioxide> carbonDioxides = new ArrayList<>();
         carbonDioxides.add(carbonDioxide);
         carbonDioxides.add(carbonDioxide1);
         carbonDioxides.add(carbonDioxide3);

         List<Light> lights = new ArrayList<>();
         lights.add(light);
         lights.add(light1);
         lights.add(light4);


         Reading reading = new Reading("0004A30B00259D2C",temperatures,humidities,lights,carbonDioxides);

        daiServerClient.PostReading(reading);*/


         while(true){
             Thread.sleep(1000);
         }

     }


}