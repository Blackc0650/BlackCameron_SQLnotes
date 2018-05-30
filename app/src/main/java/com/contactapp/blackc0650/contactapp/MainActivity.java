package com.contactapp.blackc0650.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    public static final String EXTRA_MESSAGE = "com.contactapp.blackc0650.contactapp.MESSAGE";
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
    public void viewData(View view) {
        Cursor res = databaseHelper.getAllData();
        Log.d("ContactApp","MainActivity: viewData: received cursor " + res.getCount());
        if(res.getCount() == 0) {
            showMessage("Error","No data found in the database");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while(res.moveToNext()) {
            stringBuffer.append("Contact ID: " + res.getString(0) + "\n");
            stringBuffer.append("Name: " + res.getString(1) + "\n");
            stringBuffer.append("Phone: " + res.getString(2) + "\n");
            stringBuffer.append("Address: " + res.getString(3) + "\n");
            //append res column 0,1,2,3 to string buffer

        }
        Log.d("ContactApp","MainActivity: assembled stringbuffer");
        showMessage("Data",stringBuffer.toString());
    }
    private void showMessage(String title, String message) {
        Log.d("ContactApp","MainActivity: showMessage: building alert dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void searchRecord(View view) {
        Log.d("ContactApp","MainActivity: Launching Search Activity");
        Intent intent = new Intent(this, SearchActivity.class);
        StringBuffer stringBuffer = new StringBuffer();
        Cursor res = databaseHelper.getAllData();
        if(res.getCount() == 0) {
            showMessage("Error","No data found in the database");
            return;
        }
        while(res.moveToNext()) {
            if(res.getString(1).equals(editName.getText().toString())) {
                Log.d("ContactApp", "MainActivity: searchData: Passed");
                stringBuffer.append("Contact ID: " + res.getString(0) + "\n");
                stringBuffer.append("Name: " + res.getString(1) + "\n");
                stringBuffer.append("Phone: " + res.getString(2) + "\n");
                stringBuffer.append("Address: " + res.getString(3) + "\n");
            }
        }
        Log.d("ContactApp","MainActivity: assembled stringbuffer");
        intent.putExtra(EXTRA_MESSAGE, stringBuffer.toString());
        startActivity(intent);
    }
}
