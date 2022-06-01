import Model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {

  public static String uri = "wss://iotnet.cibicom.dk/app?token=vnoUBQAAABFpb3RuZXQuY2liaWNvbS5ka4lPPjDJdv8czIiFOiS49tg=";

  public static DAIServerClient daiServerClient = new DAIServerClient();
  public static WebsocketClient websocketClient;


    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {


     websocketClient = new WebsocketClient(uri);

       /* int sample= 200;

        String sometext;

        sometext = Integer.toHexString(sample);
        System.out.println(sometext);
        sometext = "00"+sometext;

        System.out.println(sometext);*/


            /*GenerateData();*/


         while(true){
             Thread.sleep(1000);
         }

     }

     public static void GenerateData() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {

     while(true) {

      long epochTime = Instant.now().getEpochSecond();


      Temperature temperature = new Temperature(epochTime, (float) 421);
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


         Reading reading = new Reading("0004A30B00251192", temperatures, humidities, lights, carbonDioxides);

        var response = daiServerClient.PostReading(reading);
        System.out.println("This is the response: "+response);
        var jsonTelegram = websocketClient.BuildResponseJsonTelegram(response, reading);
        websocketClient.sendDownLink(jsonTelegram);


         System.out.println("S L E E P I N G");
         Thread.sleep(15000);
         System.out.println("B A C K  O N");

     }


     }




}
