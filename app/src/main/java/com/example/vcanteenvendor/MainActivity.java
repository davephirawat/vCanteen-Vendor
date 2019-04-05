package com.example.vcanteenvendor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    Button orderStatusButton; //ORDER STATUS
    Button menuButton; //MENU
    Button salesRecordButton; //SALES RECORD
    Button settingsButton; //SETTINGS

    Button doneButton;
    Button cancelButton;
    TextView orderNo;
    TextView foodName;
    TextView foodExtra;

    ListView orderListListView;

    OrderList List;
    List<Order> orderList;
    private ProgressDialog progressDialog;

    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        orderStatusButton= (Button) findViewById(R.id.orderStatusButton);
        menuButton= (Button) findViewById(R.id.menuButton);
        salesRecordButton= (Button) findViewById(R.id.salesRecordButton);
        settingsButton= (Button) findViewById(R.id.settingsButton);

        doneButton = (Button) findViewById(R.id.doneButton) ;
        cancelButton = (Button) findViewById(R.id.cancelButton);
        orderNo = (TextView) findViewById(R.id.orderNo);
        foodName = (TextView) findViewById(R.id.foodName);
        foodExtra = (TextView) findViewById(R.id.foodExtra);

        orderListListView = findViewById(R.id.orderlist);


        //////////////////////////////////////////   Navigation   //////////////////////////////////////

        /*orderStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });*/

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });

        salesRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSalesRecord();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });







        //////////////////////////////////////////   Order Adapter   //////////////////////////////////////


        List = new OrderList(orderList);



        orderLoadUp(); //GET DATA FROM JSON

        /*ListAdapter testAdapter = new OrderAdapter(this, List); //Put the arraylist here
        orderListListView.setAdapter(testAdapter);*/



    }

    private void orderLoadUp() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);

        String url="https://vcanteen.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<OrderList> call = jsonPlaceHolderApi.getOrder(1); //SET LOGIC TO INSERT ID HERE


        call.enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {

                if (!response.isSuccessful()) {
                    System.out.println("\n\n\n\n********************"+ "Code: " + response.code() +"********************\n\n\n\n");
                    return;
                }
                List = response.body();
                ListAdapter testAdapter = new OrderAdapter(MainActivity.this, List); //Put the arraylist here
                orderListListView.setAdapter(testAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {
                //vendorProfile.setText(t.getMessage());
                System.out.println("\n\n\n\n********************"+ t.getMessage() +"********************\n\n\n\n");

            }
        });

    }


    //////////////////////////////////////////   Navigation(cont.)   //////////////////////////////////////
    /*public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    public void goToMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void goToSalesRecord() {
        Intent intent = new Intent(this, SalesRecordActivity.class);
        startActivity(intent);
    }

    public void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}
