package com.example.fallalertsos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterContacts extends AppCompatActivity {

    EditText pn1,pn2,pn3,pn4;
    Button btn1,btn2;
    DatabaseHelper DB;
    int id_To_Update = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_contacts);
        pn1= findViewById(R.id.c1);
        pn2=findViewById(R.id.c2);
        pn3 = findViewById(R.id.c3);
        pn4 = findViewById(R.id.c4);
        btn1 = findViewById(R.id.button_save);
        btn2 = findViewById(R.id.button_update);


        DB = new DatabaseHelper(this);

        int nor = DB.numberOfRows();
        if(nor==0){
            Toast.makeText(this, "DO NOT WORRY I AM HERE", Toast.LENGTH_SHORT).show();
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String c1 = pn1.getText().toString();
                    String c2 = pn2.getText().toString();
                    String c3 = pn3.getText().toString();
                    String c4 = pn4.getText().toString();

                    Boolean checkinsert = DB.insertContact(c1,c2,c3,c4);
                    if(checkinsert == true){
                        Toast.makeText(RegisterContacts.this, "SOS CONTACTS SAVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        btn1.setEnabled(false);
                        btn1.setVisibility(v.INVISIBLE);
                    }
                    else{
                        Toast.makeText(RegisterContacts.this, "FAILED TO INSERT", Toast.LENGTH_SHORT).show();
                    }



                }
            });
//

        }
        else{
//            Toast.makeText(this, "OHH NOOOO!", Toast.LENGTH_SHORT).show();
            View b = findViewById(R.id.button_save);
            b.setVisibility(View.GONE);

            int Value = 1;
            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = DB.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String pn1 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn1));
                String pn2 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn2));
                String pn3 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn3));
                String pn4 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn4));

                if (!rs.isClosed()) {
                    rs.close();
//                    Toast.makeText(RegisterContacts.this, "HELLO", Toast.LENGTH_SHORT).show();
                }
                EditText cc1 = (EditText) findViewById(R.id.c1);
                EditText cc2 = (EditText) findViewById(R.id.c2);
                EditText cc3 = (EditText) findViewById(R.id.c3);
                EditText cc4 = (EditText) findViewById(R.id.c4);
                cc1.setText(pn1);
                cc2.setText(pn2);
                cc3.setText(pn3);
                cc4.setText(pn4);

//                Toast.makeText(RegisterContacts.this, pn1 + pn2 + pn3, Toast.LENGTH_SHORT).show();
            }
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id =1;
                    String c1 = pn1.getText().toString();
                    String c2 = pn2.getText().toString();
                    String c3 = pn3.getText().toString();
                    String c4 = pn4.getText().toString();
                    Boolean checkupdate = DB.updateContact(id,c1,c2,c3,c4);
                    if(checkupdate == true){
                        Toast.makeText(RegisterContacts.this, "SOS CONTACTS UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(RegisterContacts.this, "FAILED TO UPDATE", Toast.LENGTH_SHORT).show();
                    }
                }
            });


//            Toast.makeText(this, "NHI KREGA BHAIII", Toast.LENGTH_SHORT).show();
////            Toast.makeText(this, "MAIN HUN"+nor, Toast.LENGTH_SHORT).show();
        }

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String c1 = pn1.getText().toString();
//                String c2 = pn2.getText().toString();
//                String c3 = pn3.getText().toString();
//                String c4 = pn4.getText().toString();
//
//                Boolean checkinsert = DB.insertContact(c1,c2,c3,c4);
//                if(checkinsert == true){
//                    Toast.makeText(RegisterContacts.this, "SOS CONTACTS SAVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(RegisterContacts.this, "FAILED TO INSERT", Toast.LENGTH_SHORT).show();
//                }
//                btn1.setEnabled(false);
//                btn1.setVisibility(v.INVISIBLE);
//                btn2.setVisibility(v.VISIBLE);
//
//            }
//        });

//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id =1;
//                String c1 = pn1.getText().toString();
//                String c2 = pn2.getText().toString();
//                String c3 = pn3.getText().toString();
//                String c4 = pn4.getText().toString();
//                Boolean checkupdate = DB.updateContact(id,c1,c2,c3,c4);
//                if(checkupdate == true){
//                    Toast.makeText(RegisterContacts.this, "SOS CONTACTS UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
//
//                }
//                else{
//                    Toast.makeText(RegisterContacts.this, "FAILED TO UPDATE", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }
}