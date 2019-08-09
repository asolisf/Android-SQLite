package com.alansolisflores.sqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private CarSQLiteHelper carSQLiteHelper;

    private SQLiteDatabase db;

    private EditText nameEditText;

    private EditText colorEditText;

    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.nameEditText = findViewById(R.id.nameEditText);
        this.colorEditText = findViewById(R.id.colorEditText);
        this.buttonAdd = findViewById(R.id.addButton);

        carSQLiteHelper = new CarSQLiteHelper(AddActivity.this,
                "CarsDB",null,1);

        db = carSQLiteHelper.getWritableDatabase();

        buttonAdd.setOnClickListener(AddActivity.this);
    }

    private long addCar(String name, String color){
        if(db != null){

            ContentValues contentValues = new ContentValues();
            contentValues.put("name",name);
            contentValues.put("color",color);

            return db.insert("Cars",null,contentValues);
        }else{
            return -1;
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        String name = this.nameEditText.getText().toString();
        String color = this.colorEditText.getText().toString();

        if(!name.isEmpty() && !color.isEmpty()){
            long result = addCar(this.nameEditText.getText().toString(),this.colorEditText.getText().toString());

            if(result >= 0){
                Toast.makeText(AddActivity.this,"Car added successfully",Toast.LENGTH_SHORT).show();
                this.nameEditText.setText("");
                this.colorEditText.setText("");
            }else{
                Toast.makeText(AddActivity.this,"Error inserting "+name,Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(AddActivity.this,"Complete form, please.",Toast.LENGTH_SHORT).show();
        }
    }
}
