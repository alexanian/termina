package org.sfsi.termina;

import com.bluelinelabs.logansquare.LoganSquare;

import org.sfsi.termina.models.AgeWarning;
import org.sfsi.termina.models.OptionDetailsResponse;
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

public class TerminaNetwork {
    private static TerminaNetwork sTerminaNetwork;
    private final OkHttpClient mClient;
    private final String mUrl = "https://tranquil-retreat-70199.herokuapp.com";

    private OptionsResponse mOptionsResponse;
    private OptionDetailsResponse mOptionDetailsResponse;
    public int mDays;
    public String mState;
    public int mAge;

    public static synchronized TerminaNetwork getInstance() {
        if (sTerminaNetwork == null) {
            sTerminaNetwork = new TerminaNetwork();
        }
        return sTerminaNetwork;
    }

    private TerminaNetwork() {
        mClient = new OkHttpClient();
    }

    public void requestOptions(int days, String state, int age, final Runnable callback) throws Exception {
        mDays = days;
        mState = state;
        mAge = age;
        Request request = new Request.Builder()
                .url(mUrl + "/options?age=" + age + "&state=" + state + "&days_since=" + days)
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
                callback.run();
            }
        });
    }

    public void requestOptionDetails() throws Exception {
        Request request = new Request.Builder()
                .url(mUrl + "/options/copy")
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
                mOptionDetailsResponse = LoganSquare.parse(responseString, OptionDetailsResponse.class);
            }
        });
    }

    public ArrayList<Option> getOptions() {
        return mOptionsResponse.options;
    }

    public OptionDetailsResponse getOptionDetailsResponse() {
        return mOptionDetailsResponse;
    }

    public AgeWarning getAgeWarning() {
        return mOptionsResponse.age_warning;
    }
}
