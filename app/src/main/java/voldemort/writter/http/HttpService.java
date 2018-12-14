package voldemort.writter.http;

import android.content.Context;

public abstract class HttpService {

    protected Context mContext;

    protected HttpService(Context context) {
        mContext = context;
    }

}
