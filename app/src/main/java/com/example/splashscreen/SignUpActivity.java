package com.example.splashscreen;

import android.Manifest;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputConfirmPassword;

    private String email, password, confirmPassword, nameStr, techClubStr, culClubStr, hostelStr, phoneNoStr, otpStr;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private EditText name, phoneNo, otpTxt;
    private AutoCompleteTextView techClub, culClub, hostel;

    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        //drop down menu for cultural clubs
        String[] culturalClubs = getApplicationContext().getResources().getStringArray(R.array.cultural_clubs);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, culturalClubs);
        culClub = (AutoCompleteTextView) findViewById(R.id.culturalClubEditText);
        culClub.setAdapter(adapter1);

        //drop down menu for technical clubs
        String[] technicalClubs = getApplicationContext().getResources().getStringArray(R.array.technical_clubs);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_item, technicalClubs);
        techClub = (AutoCompleteTextView) findViewById(R.id.technicalClubEditText);
        techClub.setAdapter(adapter2);

        //drop down menu for hostels
        String[] hostelsName = getApplicationContext().getResources().getStringArray(R.array.hostels);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_item, hostelsName);
        hostel = (AutoCompleteTextView) findViewById(R.id.hostelEditText);
        hostel.setAdapter(adapter3);

        TextView signInTxt = findViewById(R.id.clickableTxt);
        AppCompatButton signUpBtn = findViewById(R.id.signUpButton);
        inputEmail = findViewById(R.id.emailEditText);
        inputPassword = findViewById(R.id.passwordEditText);
        inputConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        name = findViewById(R.id.nameEditText);
        phoneNo = findViewById(R.id.phoneEditText);
        otpTxt = findViewById(R.id.otpEditText);
        AppCompatButton getOTP = findViewById(R.id.getOTPButton);

        //go from sign up to sign in
        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //on sendOtp button press
        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNoStr = phoneNo.getText().toString();
                if(phoneNoStr.length()!=10){
                    Toast.makeText(getApplicationContext(), "Enter valid mobile number!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    sendOtp(phoneNoStr);
                }
            }
        });

        //on sign up button press
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                confirmPassword = inputConfirmPassword.getText().toString();
                nameStr = name.getText().toString();
                techClubStr = techClub.getText().toString();
                culClubStr = culClub.getText().toString();
                hostelStr = hostel.getText().toString();
                otpStr = otpTxt.getText().toString();

                boolean canContinue = validateEmail(email) && validatePasswords(password, confirmPassword)
                        && verifyOtp(otpStr);
                if(canContinue){
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");
                    UserHelper helperClass = createUser();
                    reference.child(validId(email)).setValue(helperClass);
                    Toast.makeText(getApplicationContext(), "You are added to our database.",
                            Toast.LENGTH_SHORT).show();
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
        String nstr=str.substring(0, l-11).toLowerCase(), finalStr = "";
        for(char c : nstr.toCharArray()){
            if(Character.isLetterOrDigit(c))
                finalStr+=c;
        }
        return finalStr;
    }

    private UserHelper createUser(){
        return new UserHelper(email, password, nameStr, techClubStr, culClubStr, hostelStr, phoneNoStr);
    }

    public void sendOtp(String phn)
    {
        int granted=0;
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},granted);
        otp = Integer.toString((int) (Math.random() * 1000000));
        String message = " is your OTP. If you did not request this OTP kindly inform concerned authority.";
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage("+91"+phn,null,otp.concat(message),null,null);
    }
    private boolean verifyOtp(String otpEntered)
    {
        if(otpEntered.equalsIgnoreCase(otp)) return true;
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid OTP!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}