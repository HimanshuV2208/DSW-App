package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SignInActivity extends AppCompatActivity {

    private TextView notRegText;
    private EditText inputEmail, inputPassword;
    private AppCompatButton signInBtn;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInBtn = (AppCompatButton)findViewById(R.id.signInButton);
        notRegText = (TextView)findViewById(R.id.notRegTxt);
        inputEmail = (EditText)findViewById(R.id.emailEditText);
        inputPassword = (EditText)findViewById(R.id.passwordEditText);

        //go from sign in to sign up
        Intent goSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
        notRegText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goSignUp);
            }
        });

        //On button press
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                boolean canContinue = validateEmail(email) && validatePassword(password);
            }
        });

    }

    //checks if the input email is valid or not.
    private boolean validateEmail(String email){
        if(email.isEmpty() || email.equals("")){
            Toast.makeText(getApplicationContext(), "E-Mail cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(email.toLowerCase().contains("@mnit.ac.in")){
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Only MNITians can login!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //checks if the input password is valid or not.
    private boolean validatePassword(String password){
        if(password.isEmpty() || password.equals("")){
            Toast.makeText(getApplicationContext(), "Password cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}