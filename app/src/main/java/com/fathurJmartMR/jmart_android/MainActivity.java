package com.fathurJmartMR.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fathurJmartMR.jmart_android.model.Account;

public class MainActivity extends AppCompatActivity {

    private TextView textMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMain = findViewById(R.id.textMain);
        Account loggedAccount = LoginActivity.getLoggedAccount();
        textMain.setText(loggedAccount.name);
    }
}