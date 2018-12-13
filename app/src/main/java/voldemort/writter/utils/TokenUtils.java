package voldemort.writter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public final class TokenUtils {

    private static final String SHARED_PREFERENCES_NAME = "authentication";

    private static final String TOKEN_KEY_NAME = "token";

    private TokenUtils() {

    }

    public static void saveToken(Context context, @NonNull String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit();
        editor.putString(TOKEN_KEY_NAME, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        return pref.getString(TOKEN_KEY_NAME, null);
    }

    public static void deleteToken(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit();
        editor.remove(TOKEN_KEY_NAME);
        editor.apply();
    }

}
