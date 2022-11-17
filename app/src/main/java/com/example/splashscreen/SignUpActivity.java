package com.example.splashscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextView alrRegText, signInTxt;
    private AppCompatButton signUpBtn;
    private EditText inputEmail, inputPassword, inputConfirmPassword;

    private String email, password, confirmPassword;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        alrRegText = findViewById(R.id.alrRegTxt);
        signInTxt = findViewById(R.id.clickableTxt);
        signUpBtn = findViewById(R.id.signUpButton);
        inputEmail = findViewById(R.id.emailEditText);
        inputPassword = findViewById(R.id.passwordEditText);
        inputConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        //go from sign up to sign in
        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //on button press
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                confirmPassword = inputConfirmPassword.getText().toString();
                boolean canContinue = validateEmail(email) && validatePasswords(password, confirmPassword);
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                UserHelper helperClass = new UserHelper(email, password);
                reference.child(validId(email)).setValue(helperClass);
                Toast.makeText(getApplicationContext(), "You are added to our database",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //checks if the input email is valid or not.
    private boolean validateEmail(String email){
        if(email.isEmpty()){
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

    //checks if the input passwords are valid or not.
    private boolean validatePasswords(String pass1, String pass2){
        if(pass1.isEmpty() ||pass2.isEmpty()){
            Toast.makeText(getApplicationContext(), "Password cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pass1.length()<6 || pass2.length()<6){
            Toast.makeText(getApplicationContext(), "Password must have at least 6 characters!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pass1.equals(pass2)){
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Passwords must be same!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //remove @mnit.ac.in and special chars
    private String validId(String str)
    {
        int l = str.length();
        String nstr=str.substring(0, l-11), finalStr = "";
        for(char c : nstr.toCharArray()){
            if(Character.isLetterOrDigit(c))
                finalStr+=c;
        }
        return finalStr;
    }
}