package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private TextView signUpText;
    private EditText inputEmail, inputPassword;
    private AppCompatButton signInBtn;

    private String email, password;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInBtn = findViewById(R.id.signInButton);
        signUpText = findViewById(R.id.signUpTxt);
        inputEmail = findViewById(R.id.emailEditText);
        inputPassword = findViewById(R.id.passwordEditText);

        //go from sign in to sign up
        Intent goSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goSignUp);
            }
        });

        //go from sign in to home screen
        Intent goHome = new Intent(SignInActivity.this, HomeActivity.class);

        //On button press
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                boolean canContinue = validateEmail(email) && validatePassword(password);
                if(canContinue) {

                    // TODO: 17-11-2022 remove this after testing for permissions
                    boolean isAdmin = email.equalsIgnoreCase("2020uee1445@mnit.ac.in");
                    goHome.putExtra("isAdmin", isAdmin);

                    rootNode= FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("users");
                    Query checkuser=reference.orderByChild("name").equalTo(email);
                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                //gets the current user
                                UserHelper current_user = snapshot.child(validId(email)).getValue(UserHelper.class);
                                String pas = current_user.getPassword();
                                if(pas.equals(password))
                                {
                                    Toast.makeText(getApplicationContext(), "Welcome!",
                                            Toast.LENGTH_SHORT).show();
                                    goHome.putExtra("class", current_user);
                                    startActivity(goHome);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Invalid Password!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "User does not exist",
                                        Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Please sign up first.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Try after sometime",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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

    //checks if the input password is valid or not.
    private boolean validatePassword(String password){
        if(password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Password cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //remove @mnit.ac.in and special chars
    String validId(String str)
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