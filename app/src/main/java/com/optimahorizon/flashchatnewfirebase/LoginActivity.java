 package com.optimahorizon.flashchatnewfirebase;

 import android.app.AlertDialog;
 import android.content.Intent;
import android.os.Bundle;
 import android.util.Log;
 import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


 public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private FirebaseAuth mAuth;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.optimahorizon.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.equals("") || password.equals("")) {
            return;
        } else {
            Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();
        }
        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            Log.d("ChatApp", "attemptLogin: " + task.isSuccessful());

            if (!task.isSuccessful()) {
                Log.d("ChatApp", "Problem signing in: " + task.getException());
                showErrorDialog("There was a problem signing in!");
            } else {
                Intent intent = new Intent(LoginActivity.this, MainChatActivity.class);
                finish();
                startActivity(intent);
            }
        });


    }

    // TODO: Show error on screen with an alert dialog
    public void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Ooops!")
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .setIcon(R.drawable.common_google_signin_btn_icon_disabled)
                .show();
    }



}