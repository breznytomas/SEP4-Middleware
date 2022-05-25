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

        String indented = (new JSONObject(data.toString())).toString(4);

        ObjectFromLorawan response = gson.fromJson(indented, ObjectFromLorawan.class);

        /*System.out.println(response.getData());
        System.out.println(response.getEUI());*/

            if (response.getCmd().equals("rx")) {

                long epochTime = Instant.now().getEpochSecond();

                String[] array = response.getData().split("(?<=\\G.{2})");

                Model.Temperature temperature;
                Humidity humidity;
                CarbonDioxide carbonDioxide;
                Light light;

                //34567654

                //make sure that every value shares the same timestamp, crate timestamp variable and assign it to all the


                System.out.println("Humidity " + (Integer.parseInt(array[0], 16)) / 10);
                var hum1 = (Integer.parseInt(array[0], 16)) / 10;
                var strings = Integer.toString(hum1);
                var floats = Float.parseFloat(strings);
                humidity = new Humidity(epochTime, floats);


                //CO2
                System.out.println("CO2 " + Integer.parseInt(array[1], 16));
                var CO2 = Integer.parseInt(array[1], 16);
                var CO2String = Integer.toString(CO2);
                var CO2Float = Float.parseFloat(CO2String);
                carbonDioxide = new CarbonDioxide(epochTime, CO2Float);


                //Temperature
                System.out.println("Temperature " + (Integer.parseInt(array[2], 16)) / 10);
                var Temp = Integer.parseInt(array[2], 16) / 10;
                var TempString = Integer.toString(Temp);
                var TempFloat = Float.parseFloat(TempString);
                temperature = new Temperature(epochTime, TempFloat);

                //Light
                System.out.println("Light " + Integer.parseInt(array[3], 16));
                var Light = Integer.parseInt(array[3], 16);
                var LightString = Integer.toString(Light);
                var LightFloat = Float.parseFloat(LightString);
                light = new Light(epochTime, LightFloat);


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


    public float PostReading(Reading reading) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, InterruptedException {
        var uri = new URI("https://localhost:7218/api/Reading");

        System.out.println("Post reading activated");


        //COde for SSL to work
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        HttpClient client = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();
        // code for SSL end

        String JsonString = gson.toJson(reading);
        System.out.println("JSON from reading object");
        System.out.println(JsonString);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json; charset=utf-8;")
                .POST(HttpRequest.BodyPublishers.ofString(JsonString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());



        System.out.println("Status code of response: "+response.statusCode());
        System.out.println("Response JSON");
        System.out.println(response.body());

        return Float.parseFloat(response.body());
    }





    public void TestAPIGET() throws IOException, InterruptedException {
        System.out.println("Test API");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://smart-greenhouse-data-server.herokuapp.com/api/Light?boardId=7"))
                .header("X-App", "Demo")
                .GET()
                .build();

        var client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> asString = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, asString);

        int statusCode = response.statusCode();
        System.out.printf("Status Code: %s%n", statusCode);
        HttpHeaders headers = response.headers();
        System.out.printf("Response Headers: %s%n", headers);
        System.out.println(response.body());
    }
}
