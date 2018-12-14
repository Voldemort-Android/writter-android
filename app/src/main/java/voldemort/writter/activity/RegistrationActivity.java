package voldemort.writter.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import voldemort.writter.R;
import voldemort.writter.data.model.User;
import voldemort.writter.http.HttpEndpoints;
import voldemort.writter.http.client.HttpClient;
import voldemort.writter.http.client.HttpResponse;
import voldemort.writter.utils.TokenUtils;

public class RegistrationActivity extends AppCompatActivity {

    // TODO Move these to a constants file.
    private final int minUsernameLength = 4;
    private final int minPasswordLength = 2;

    private AsyncTask<Void, Void, HttpResponse> mAuthTask = null;

    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordVerifyView;
    private Button mRegisterButton;
    private Button mLoginButton;
    private View mProgressView;
    private View mRegistrationFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        mFirstNameView = findViewById(R.id.first_name);
        mLastNameView = findViewById(R.id.last_name);
        mEmailView = findViewById(R.id.email);
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mPasswordVerifyView = findViewById(R.id.confirm_password);

        mRegisterButton = findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener((view) -> attemptRegistration());

        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this::navigateToLoginActivity);

        mRegistrationFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.progress_spinner);

    }

    private void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mEmailView.setError(null);
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mPasswordVerifyView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordVerify = mPasswordVerifyView.getText().toString();
        String email = mEmailView.getText().toString();
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();

        // Validate first name
        String error = validateName(firstName);
        if (error != null) {
            mFirstNameView.setError(error);
            mFirstNameView.requestFocus();
            return;
        }

        // Validate last name
        error = validateName(lastName);
        if (error != null) {
            mLastNameView.setError(error);
            mLastNameView.requestFocus();
            return;
        }

        // Validate email
        error = validateEmail(email);
        if (error != null) {
            mEmailView.setError(error);
            mEmailView.requestFocus();
            return;
        }

        // Validate username
        error = validateUsername(username);
        if (error != null) {
            mUsernameView.setError(error);
            mUsernameView.requestFocus();
            return;
        }

        // Validate password
        error = validatePassword(password);
        if (error != null) {
            mPasswordView.setError(error);
            mPasswordView.requestFocus();
            return;
        }

        // Validate password confirmation
        if (!Objects.equals(password, passwordVerify)) {
            mPasswordVerifyView.setError("Passwords do not match");
            mPasswordVerifyView.requestFocus();
            return;
        }

        showProgress(true);

        User userDetails = new User();
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setUsername(username);
        userDetails.setEmail(email);
        userDetails.setPassword(password);

        mAuthTask = HttpClient.Put(
                HttpEndpoints.WRITTER_SERVER_API + "/register",
                userDetails,
                this::onRegistrationSuccess,
                this::onRegistrationError
        );

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

            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegistrationFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }

    private void onRegistrationSuccess(HttpResponse httpResponse) {
        mAuthTask = null;

        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);

        // Kill activity stack so that the user cannot go back using the back button.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        String token = httpResponse.getResponseBody();
        Log.d("HELLO", token);

        // Store the token so that other activities can use it.
        TokenUtils.saveToken(RegistrationActivity.this, token);

        startActivity(intent);
    }

    private void onRegistrationError(HttpResponse httpResponse) {
        mAuthTask = null;
        showProgress(false);

        if (httpResponse != null) {
            Toast.makeText(getApplicationContext(), httpResponse.getResponseBody(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "An unknown error has occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToLoginActivity(View view) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private String validateUsername(String username) {

        if (TextUtils.isEmpty(username)) {
            return "Username is blank or missing";
        }

        if (username.length() < minUsernameLength) {
            return "Username is too short";
        }

        if (!username.matches("^[a-zA-Z0-9]*$")) {
            return "Username cannot contain spaces or special characters";
        }

        return null;
    }

    private String validatePassword(String password) {

        if (TextUtils.isEmpty(password)) {
            return "Password is blank or missing";
        }

        if (password.length() < minPasswordLength) {
            return "Password is too short";
        }

        return null;
    }

    private String validateEmail(String email) {

        if (TextUtils.isEmpty(email)) {
            return "Email address is blank or missing";
        }

        // Regex source: https://stackoverflow.com/a/44674038
        if (!email.matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$")) {
            return "Invalid email address";
        }

        return null;
    }

    private String validateName(String name) {

        if (TextUtils.isEmpty(name)) {
            return "Your name is blank or missing";
        }

        if (!name.matches("^[a-zA-Z]*$")) {
            return "Your name can only contain letters";
        }

        return null;
    }


}
