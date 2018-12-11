package voldemort.writter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import voldemort.writter.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordVerifyView;
    private Button mSignInButton;
    private Button mRegisterButton;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.activity_registration_title);
        setContentView(R.layout.activity_registration);
    }
}
