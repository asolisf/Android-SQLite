package com.alansolisflores.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CarSQLiteHelper carSQLiteHelper;

    private SQLiteDatabase db;

    private List<Car> carList;

    private ListView carsListView;

    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.carsListView = findViewById(R.id.carsListView);

        this.carSQLiteHelper = new CarSQLiteHelper(MainActivity.this,
                "CarsDB",null,1);

        this.db = carSQLiteHelper.getWritableDatabase();
        this.carSQLiteHelper.onCreate(db);
    }



    private List<Car> getCars(){
        Cursor cursor = this.db.rawQuery("SELECT * FROM Cars",null);

        List<Car> cars = new ArrayList<Car>();

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast() == false){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String color = cursor.getString(2);
                cars.add(new Car(id,name,color));
                cursor.moveToNext();
            }
        }

        return cars;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.addItem:
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.carList = getCars();

        if(this.customAdapter == null){
            this.customAdapter = new CustomAdapter(carList,MainActivity.this,R.layout.linear_car_item);
            carsListView.setAdapter(this.customAdapter);
        }else{
            this.customAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        this.db.close();
        super.onDestroy();
    }
}
