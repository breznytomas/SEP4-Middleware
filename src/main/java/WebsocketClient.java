//import org.json.JSONObject;

import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class WebsocketClient implements WebSocket.Listener {
    private WebSocket server = null;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private DAIServerClient daiServerClient;


    // Send down-link message to device
    // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md

    public void sendDownLink(String jsonTelegram) {
        server.sendText(jsonTelegram,true);
    }


    public WebsocketClient(String url) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create(url), this);

        server = ws.join();
        daiServerClient = new DAIServerClient();
    }

    //onOpen()
    public void onOpen(WebSocket webSocket) {
        // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);

        System.out.println("WebSocket Listener has been opened for requests.");
    }

    //onError()
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("A " + error.getCause() + " exception was thrown.");
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    };
    //onClose()
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed!");
        System.out.println("Status:" + statusCode + " Reason: " + reason);
        return new CompletableFuture().completedFuture("onClose() completed.").thenAccept(System.out::println);
    };
    //onPing()
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Ping: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Ping completed.").thenAccept(System.out::println);
    };
    //onPong()
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Pong: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Pong completed.").thenAccept(System.out::println);
    };
    //onText()
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {

        try {
            var reading=  daiServerClient.CreateReadingObject(data);

            if (reading!=null) {
                var numberForMotor = daiServerClient.PostReading(reading);
                var jsonTelegram = BuildResponseJsonTelegram(numberForMotor, reading);

                sendDownLink(jsonTelegram);
            }


        } catch (NoSuchAlgorithmException | URISyntaxException | IOException | InterruptedException |
                 KeyManagementException e) {
            throw new RuntimeException(e);
        }

        System.out.println(data.toString());
        webSocket.request(1);
        return new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
    };

    public String BuildResponseJsonTelegram(int numberForMotor, Reading reading){
            String uplinkData;

        if (numberForMotor<10){
            uplinkData ="000" + Integer.toHexString(numberForMotor);
       }
       else{
            uplinkData = "00" + Integer.toHexString(numberForMotor);
       }

        System.out.println(uplinkData);
        DownlinkTelegram downlinkTelegram = new DownlinkTelegram("tx", reading.BoardId, 2,true, uplinkData);
        System.out.println(gson.toJson(downlinkTelegram));
        return gson.toJson(downlinkTelegram);

    }

 }


