import Model.*;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DAIServerClient {
    Gson gson = new Gson();



    public static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };



    public Reading CreateReadingObject(CharSequence data) throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, KeyManagementException {

        System.out.println(data);

        String indented = (new JSONObject(data.toString())).toString(4);

        ObjectFromLorawan response = gson.fromJson(indented, ObjectFromLorawan.class);

        System.out.println("the data in raw form");
        System.out.println(response.getData());

            if (response.getCmd().equals("rx")) {

                long epochTime = Instant.now().getEpochSecond();

                String[] array = response.getData().split("(?<=\\G.{4})");

                Model.Temperature temperature;
                Humidity humidity;
                CarbonDioxide carbonDioxide;
                Light light;


                //Humidity
                //System.out.println("Humidity " + (Integer.parseInt(array[0], 16)));
                Integer hum1 = (Integer.parseInt(array[0], 16));
                humidity = new Humidity(epochTime, hum1.floatValue());
                System.out.println("Humidity: "+humidity.Value);

                //Temperature
                //System.out.println("Temperature " + (Integer.parseInt(array[1], 16)));
                Integer Temp = Integer.parseInt(array[1], 16);
                float temp1 = Temp.floatValue()/10;
                temperature = new Temperature(epochTime, temp1);
                System.out.println("Temperature: "+temperature.Value);

                //CO2
                //System.out.println("CO2 " + Integer.parseInt(array[2], 16));
                Integer CO2 = Integer.parseInt(array[2], 16);
                carbonDioxide = new CarbonDioxide(epochTime, CO2.floatValue());
                System.out.println("CarbonDioxide: "+ carbonDioxide.Value);

                //Light
                //System.out.println("Light " + Integer.parseInt(array[3], 16));
                Integer Light = Integer.parseInt(array[3], 16);
                light = new Light(epochTime, Light.floatValue());
                System.out.println("Light: " + light.Value);


                List<Temperature> temperatures = new ArrayList<>();
                temperatures.add(temperature);
                List<Humidity> humidities = new ArrayList<>();
                humidities.add(humidity);
                List<CarbonDioxide> carbonDioxides = new ArrayList<>();
                carbonDioxides.add(carbonDioxide);
                List<Light> lights = new ArrayList<>();
                lights.add(light);


                Reading reading = new Reading(response.getEUI(), temperatures, humidities, lights, carbonDioxides);

                return reading;
            }
        return null;

    }


    public int PostReading(Reading reading) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, InterruptedException {
        var uri = new URI("https://localhost:7218/api/Reading");

        //COde for SSL to work
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        HttpClient client = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

        String JsonString = gson.toJson(reading);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json; charset=utf-8;")
                .POST(HttpRequest.BodyPublishers.ofString(JsonString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Integer.parseInt(response.body());
    }
}
