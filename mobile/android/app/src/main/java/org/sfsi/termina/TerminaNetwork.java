package org.sfsi.termina;

import com.bluelinelabs.logansquare.LoganSquare;

import org.sfsi.termina.models.AgeWarning;
import org.sfsi.termina.models.Option;
import org.sfsi.termina.models.OptionsResponse;

import java.io.IOException;
import java.util.ArrayList;

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

    private OptionsResponse mOptionsResponse;

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
                .url("https://peaceful-peak-13670.herokuapp.com/options?age=14&state=AL&days_since=20")
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
                String responseString = response.body().string();
                System.out.println(responseString);
                mOptionsResponse = LoganSquare.parse(responseString, OptionsResponse.class);
            }
        });
    }

    public ArrayList<Option> getOptions() {
        return mOptionsResponse.options;
    }

    public AgeWarning getAgeWarning() {
        return mOptionsResponse.age_warning;
    }
}
