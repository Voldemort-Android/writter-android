package voldemort.writter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtParser;
import voldemort.writter.WritterApplication;
import voldemort.writter.data.model.User;

public final class TokenUtils {

    private static final String SHARED_PREFERENCES_NAME = "authentication";

    private static final String TOKEN_KEY_NAME = "token";

    private static final String JWT_PREFIX = "Bearer ";

    private volatile static ObjectMapper mapper;

    private TokenUtils() {

    }

    public static void saveToken(@NonNull String token) {
        Context context = WritterApplication.getAppContext();
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit();
        editor.putString(TOKEN_KEY_NAME, token);
        editor.apply();
    }

    public static String getToken() {
        Context context = WritterApplication.getAppContext();

        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        return pref.getString(TOKEN_KEY_NAME, null);
    }

    public static void deleteToken() {
        Context context = WritterApplication.getAppContext();
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit();
        editor.remove(TOKEN_KEY_NAME);
        editor.apply();
    }

    public static User getCurrentUser() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return null;
        }
        if (token.startsWith(JWT_PREFIX)) {
            token = token.substring(JWT_PREFIX.length());
        }

        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

        Claims claims = Jwts.parser()
                .parseClaimsJwt(unsignedToken)
                .getBody();

        Object user = claims.get("user");
        try {
            // Serialize the map into a string.
            String serializedUser = getMapper().writeValueAsString(user);

            // Deserialize the string into a User object.
            return getMapper().readValue(serializedUser, User.class);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = WritterApplication.getMapper();
        }
        return mapper;
    }

}
