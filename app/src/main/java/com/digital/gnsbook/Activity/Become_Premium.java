package com.digital.gnsbook.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.digital.gnsbook.Config.DbHelper;
import com.httpgnsbook.gnsbook.R;

public class Become_Premium extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_premium);
    }

    public void select(View view) {
        startActivity(new Intent(getApplicationContext(), Companypage.class).putExtra(DbHelper.COLUMN_ID, "4"));

    }
}
