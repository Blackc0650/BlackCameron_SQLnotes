package com.contactapp.blackc0650.contactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    EditText editName;
    EditText editPhone;
    EditText editAddress;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);
        button = findViewById(R.id.button);
        databaseHelper = new DatabaseHelper(this);
        Log.d("ContactApp","MainActivity: instantiated DatabaseHelper");
    }
    public void addData(View view) {
        Log.d("ContactApp","MainActivity: adding data");
        boolean isInserted = databaseHelper.insertData(editName.getText().toString(),editPhone.getText().toString(),editAddress.getText().toString());
        if(isInserted == true)
            Toast.makeText(this,"Passed - contact inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Failed - contact was not inserted",Toast.LENGTH_LONG).show();
    }
}
