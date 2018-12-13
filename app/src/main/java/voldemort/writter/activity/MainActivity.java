package voldemort.writter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import voldemort.writter.R;
import voldemort.writter.http.HttpEndpoints;
import voldemort.writter.http.client.AuthHttpClient;
import voldemort.writter.utils.TokenUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthHttpClient.Get(
                MainActivity.this,
                HttpEndpoints.WRITTER_SERVER_API + "/example/whoami",
                (res) -> Log.d("HELLO", res.getResponseBody())
        );

    }
}
