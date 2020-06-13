package com.example.parseemailverification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
        edtUsername = findViewById(R.id.edtUsername);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
    public void signUpIsPressed(View btnView){
        Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();
        try{
            ParseUser user = new ParseUser();
            user.setEmail(edtEmail.getText().toString());
            user.setPassword(edtPassword.getText().toString());
            user.setUsername(edtUsername.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        ParseUser.logOut();
                        alertDisplayer("Account created successfully ! ", "Please verify your email before login", false);
                    }
                    else {
                        ParseUser.logOut();
                        alertDisplayer("Error : Account creation failed ! ", "Account could not be created : "+e.getMessage(), true);
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void alertDisplayer(String title,String message, final boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (!error){
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}