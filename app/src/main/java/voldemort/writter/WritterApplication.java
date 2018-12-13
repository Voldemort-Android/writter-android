package voldemort.writter;

import android.app.Application;
import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WritterApplication extends Application {

    private static Context context;

    private volatile static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onCreate() {
        super.onCreate();
        WritterApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return WritterApplication.context;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

}
