package org.sfsi.termina;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Smule on 3/4/17.
 */

public class TerminaNetwork {
    private static TerminaNetwork sTerminaNetwork;
    private final OkHttpClient mClient;

    public static synchronized TerminaNetwork getInstance() {
        if (sTerminaNetwork == null) {
            sTerminaNetwork = new TerminaNetwork();
        }
        return sTerminaNetwork;
    }

    private TerminaNetwork() {
        mClient = new OkHttpClient();
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://172.31.32.145:3000/options?age=15&state=HI&days_since=150")
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });
    }
}
