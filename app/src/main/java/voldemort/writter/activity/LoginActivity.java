package voldemort.writter.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import voldemort.writter.R;
import voldemort.writter.http.HttpEndpoints;
import voldemort.writter.http.client.HttpClient;
import voldemort.writter.http.client.HttpResponse;
import voldemort.writter.utils.TokenUtils;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AsyncTask<Void, Void, HttpResponse> mAuthTask = null;

    private EditText mUsernameView;
    private EditText mPasswordView;
    private Button mSignInButton;
    private Button mRegisterButton;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.activity_login_title);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = findViewById(R.id.info);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener((view) -> attemptLogin());

        mRegisterButton = findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(this::navigateToRegistrationActivity);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.progress_spinner);

        // Check if user was already logged in, and redirect them to the main activity.
        String token = TokenUtils.getToken();
        if (!TextUtils.isEmpty(token)) {
            validateToken(token);
        }

    }

    /**
     * Attempt to validate the user using saved token.
     */
    private void validateToken(String token) {
        if (mAuthTask != null) {
            return;
        }
        showProgress(true);
        mAuthTask = HttpClient.Post(
                HttpEndpoints.WRITTER_SERVER_API + "/login/validate",
                token,
                this::onLoginSuccess,
                (view) -> {
                    mAuthTask = null;
                    showProgress(false);
                    TokenUtils.deleteToken();
                }
        );
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.activity_login_error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.activity_login_error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.activity_login_error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don'HttpPostRequest attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", username);
            credentials.put("password", password);

            Log.d("HELLO", "Attempting login with " + credentials);

            mAuthTask = HttpClient.Post(
                    HttpEndpoints.WRITTER_SERVER_API + "/login",
                    credentials,
                    this::onLoginSuccess,
                    this::onLoginError
            );
        }
    }

    private boolean isUsernameValid(String username) {
        // TODO Implement this
        return true;
    }

    private boolean isPasswordValid(String password) {
        // TODO Implement this
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     * Auto-generated by Android Studio.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }

    private void onLoginSuccess(HttpResponse httpResponse) {
        mAuthTask = null;

        Intent intent = new Intent(this, MainActivity.class);

        // Kill activity stack so that the user cannot go back using the back button.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        String token = httpResponse.getResponseBody();
        Log.d("HELLO", token);

        // Store the token so that other activities can use it.
        TokenUtils.saveToken(token);

        startActivity(intent);
    }

    private void onLoginError(HttpResponse httpResponse) {
        mAuthTask = null;
        showProgress(false);

        if (httpResponse != null) {
            if (httpResponse.getStatus() == 401) {
                mPasswordView.setError(httpResponse.getResponseBody());
                mPasswordView.requestFocus();
            }
            else {
                Toast.makeText(getApplicationContext(), httpResponse.getResponseBody(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "An unknown error has occurred", Toast.LENGTH_SHORT).show();
        }

    }

    private void navigateToRegistrationActivity(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

}

