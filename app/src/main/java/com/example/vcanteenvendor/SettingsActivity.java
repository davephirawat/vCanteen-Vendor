package com.example.vcanteenvendor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {


    Button orderStatusButton; //ORDER STATUS
    Button menuButton; //MENU
    Button salesRecordButton; //SALES RECORD
    Button settingsButton; //SETTINGS
    Button changePasswordButton;

    Button signOutButton;
    Switch vendorStatusToggle;
    TextView statusText;
    TextView vendorProfile;

    EditText vendorNameInput;
    EditText vendorEmailInput;

    TextView checkCUNex;
    TextView checkScb;
    TextView checkKplus;
    TextView checkTrueMoney;

    ImageView vendorProfilePicture;


    Button changePass;
    Dialog changePassDialog;

    /// FOR CHANGEPASS DIALOG ///
    EditText currPassBox;
    EditText newPassBox;
    EditText confirmNewPassBox;
    Button confirmChangePass;

    Button clearCurrPass;
    Button clearNewPass;
    Button clearConfirmPass;

    TextView errorCurrPass;
    TextView errorNewPass;
    TextView errorConfirmPass;

    private RequestQueue mQueue;
    private String url="FROM ENDPOINTS";

    Vendor vendor;
    VendorInfoArray vendorInfoArray;

    RequestOptions option = new RequestOptions().centerCrop();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        orderStatusButton= (Button) findViewById(R.id.orderStatusButton);
        menuButton= (Button) findViewById(R.id.menuButton);
        salesRecordButton= (Button) findViewById(R.id.salesRecordButton);
        settingsButton= (Button) findViewById(R.id.settingsButton);

        signOutButton=(Button) findViewById(R.id.signOutButton);
        vendorStatusToggle = (Switch) findViewById(R.id.vendorStatusToggle);
        statusText = (TextView) findViewById(R.id.statusText);
        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);

        vendorNameInput = (EditText) findViewById(R.id.vendorNameInput);
        vendorEmailInput = (EditText) findViewById(R.id.vendorEmailInput);

        vendorProfile = (TextView) findViewById(R.id.vendorProfile);

        checkCUNex = (TextView) findViewById(R.id.checkCUNex);
        checkScb = (TextView) findViewById(R.id.checkScb);
        checkKplus = (TextView) findViewById(R.id.checkKplus);
        checkTrueMoney = (TextView) findViewById(R.id.checkTrueMoney);

        vendorProfilePicture = findViewById(R.id.vendorProfilePicture);


        //////////////////////////////////////////   JSON START UP   //////////////////////////////////////



        //mQueue = Volley.newRequestQueue(this);



        accountJSONLoadUp();




        vendorNameInput = (EditText) findViewById(R.id.vendorNameInput);
        vendorEmailInput = (EditText) findViewById(R.id.vendorEmailInput);

        vendorProfile = (TextView) findViewById(R.id.vendorProfile);

        checkCUNex = (TextView) findViewById(R.id.checkCUNex);
        checkScb = (TextView) findViewById(R.id.checkScb);
        checkKplus = (TextView) findViewById(R.id.checkKplus);
        checkTrueMoney = (TextView) findViewById(R.id.checkTrueMoney);

        vendorProfilePicture = findViewById(R.id.vendorProfilePicture);


        changePass = findViewById(R.id.changePasswordButton);

        //////////////////////////////////////////   JSON START UP   //////////////////////////////////////



        //mQueue = Volley.newRequestQueue(this);



        accountJSONLoadUp();





        //////////////////////////////////////////   Navigation   //////////////////////////////////////
        orderStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

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

        /*settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });*/


        signOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SettingsActivity.this);
                //dialog.setTitle("Devahoy");
                dialog.setContentView(R.layout.dialog_red);

                final TextView title = (TextView) dialog.findViewById(R.id.dialogTitle);
                final TextView content = (TextView) dialog.findViewById(R.id.dialogContent);
                Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
                Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);


                content.setText("Log out of vCanteen?");
                content.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/SF-Pro-Text-Bold.otf"));
                positiveButton.setText("LOG OUT");
                title.setVisibility(View.GONE);


                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        logOut();
                        Toast.makeText(getApplicationContext(), "LOG OUT SUCCESS!",  Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });



        vendorStatusToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!vendorStatusToggle.isChecked()){
                    vendorStatusToggle.setChecked(true);


                    final Dialog dialog = new Dialog(SettingsActivity.this);

                    dialog.setContentView(R.layout.dialog_red);

                    final TextView title = (TextView) dialog.findViewById(R.id.dialogTitle);
                    final TextView content = (TextView) dialog.findViewById(R.id.dialogContent);
                    Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
                    final Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);


                    title.setText("Closing Menu");
                    content.setText(R.string.closing_vendor);
                    positiveButton.setText("close menu");
                    //content.setGravity(Gravity.LEFT);


                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vendorStatusToggle.setChecked(true);
                            dialog.dismiss();
                        }
                    });

                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            positiveButton.setBackgroundResource(R.drawable.button_grey_rounded);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    vendorStatusToggle.setChecked(false);
                                    openCloseVendor("CLOSE");
                                    Toast.makeText(getApplicationContext(), "VENDOR CLOSED!",  Toast.LENGTH_SHORT).show();
                                    positiveButton.setBackgroundResource(R.drawable.button_red_rounded);
                                    dialog.dismiss();
                                    statusText.setText("CLOSED");
                                    statusText.setTextColor(Color.parseColor("#828282"));

                                }
                            }, 2000);

                        }
                    });

                    dialog.show();

                } else {

                    openCloseVendor("OPEN");
                    statusText.setText("OPEN");
                    statusText.setTextColor(getResources().getColor(R.color.pinkPrimary));
                }

            }
        });

        ///////////////////// CHANGE PASSWORD ////////////////////////
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassDialog = new Dialog(SettingsActivity.this);
                changePassDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                changePassDialog.setContentView(R.layout.change_password_dialog);

                currPassBox = changePassDialog.findViewById(R.id.currentPasswordBox);
                newPassBox = changePassDialog.findViewById(R.id.newPasswordBox);
                confirmNewPassBox = changePassDialog.findViewById(R.id.confirmNewPasswordBox);
                confirmChangePass = changePassDialog.findViewById(R.id.confirmChangePasswordButton);

                clearCurrPass = changePassDialog.findViewById(R.id.clearTextButtonCurrentPW);
                clearNewPass = changePassDialog.findViewById(R.id.clearTextButtonNewPW);
                clearConfirmPass = changePassDialog.findViewById(R.id.clearTextButtonConfirmNewPW);

                errorCurrPass = changePassDialog.findViewById(R.id.errorCurrPass);
                errorNewPass = changePassDialog.findViewById(R.id.errorNewPass);
                errorConfirmPass = changePassDialog.findViewById(R.id.errorConfirmPass);

                confirmChangePass.setEnabled(true);
                clearCurrPass.setEnabled(true);
                clearNewPass.setEnabled(true);
                clearConfirmPass.setEnabled(true);


            }
        });

    }

    private void openCloseVendor(String vendorStatus) {

        url="https://vcanteen.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Void> call = jsonPlaceHolderApi.editVendorStatus(1, vendorStatus);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (!response.isSuccessful()) {
                    vendorNameInput.setText("Code: " + response.code());
                    return;
                }


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                vendorProfile.setText(t.getMessage());


            }
        });

    }


    private void accountJSONLoadUp() {

        url="https://vcanteen.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<VendorInfoArray> call = jsonPlaceHolderApi.getVendorInfo(1);

        call.enqueue(new Callback<VendorInfoArray>() {
            @Override
            public void onResponse(Call<VendorInfoArray> call, Response<VendorInfoArray> response) {

                if (!response.isSuccessful()) {
                    vendorNameInput.setText("Code: " + response.code());
                    vendorNameInput.setText("");
                    return;
                }

                vendorInfoArray = response.body();


                if (vendorInfoArray != null){

                    vendor = vendorInfoArray.vendorInfo.get(0);
                    vendorNameInput.setText(vendor.getVendorName());
                    vendorEmailInput.setText(vendor.getVendorEmail());
                    Glide.with(SettingsActivity.this).load(vendor.getVendorImage()).apply(option).into(vendorProfilePicture);
                    //This array always have 1 member, so use get(1).

                } else {
                    vendorNameInput.setText("Receive Null");
                }




                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "CU_NEX")){
                    checkCUNex.setVisibility(View.VISIBLE);
                }

                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "SCB_EASY")){
                    checkScb.setVisibility(View.VISIBLE);
                }

                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "K_PLUS")){
                    checkKplus.setVisibility(View.VISIBLE);
                }

                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "TRUEMONEY_WALLET")){
                    checkTrueMoney.setVisibility(View.VISIBLE);
                }


                if(vendor.getVendorStatus().equals("OPEN")){
                    vendorStatusToggle.setChecked(true);
                    statusText.setText("OPEN");
                    statusText.setTextColor(getResources().getColor(R.color.pinkPrimary));
                }else{
                    vendorStatusToggle.setChecked(false);
                    statusText.setText("CLOSED");
                    statusText.setTextColor(Color.parseColor("#828282"));
                }


            }

            @Override
            public void onFailure(Call<VendorInfoArray> call, Throwable t) {
                vendorProfile.setText(t.getMessage());


            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SettingsActivity.this);
                //dialog.setTitle("Devahoy");
                dialog.setContentView(R.layout.change_password_dialog);
                dialog.show();

                Button close_dialog = (Button) dialog.findViewById(R.id.close_dialog);
                TextView changePasswordTitle = (TextView)dialog.findViewById(R.id.changePasswordTitle);
                TextView currentPassword = (TextView)dialog.findViewById(R.id.currentPassword);


            }
        });

    }



    private void testPut() {

        url="https://api.jsonbin.io/";

        vendor.setVendorEmail("kuy");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Vendor> call = jsonPlaceHolderApi.getVendor2(1, vendor);

        call.enqueue(new Callback<Vendor>() {
            @Override
            public void onResponse(Call<Vendor> call, Response<Vendor> response) {

                if (!response.isSuccessful()) {
                    vendorNameInput.setText("Code: " + response.code());
                    return;
                }

                vendor = response.body();
                vendorNameInput.setText(vendor.getVendorName());
                vendorEmailInput.setText(vendor.getVendorEmail());

                /*if(vendor.findServiceProviderFromList(vendor.getVendorPaymentMethod())){
                    checkCUNex.setVisibility(View.VISIBLE);
                    vendorProfile.setText(vendor.getVendorPaymentMethod().toString());
                }*/


            }

            @Override
            public void onFailure(Call<Vendor> call, Throwable t) {
                vendorProfile.setText(t.getMessage());
                //System.out.println("\n\n\n\n"+ t.getMessage() +"\n\n\n\n");

            }
        });


    }

    private void accountJSONLoadUp() {

        url="https://vcanteen.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<VendorInfoArray> call = jsonPlaceHolderApi.getVendorInfo(1);

        call.enqueue(new Callback<VendorInfoArray>() {
            @Override
            public void onResponse(Call<VendorInfoArray> call, Response<VendorInfoArray> response) {

                if (!response.isSuccessful()) {
                    vendorNameInput.setText("Code: " + response.code());
                    return;
                }

                vendorInfoArray = response.body();


                if (vendorInfoArray != null){

                    vendor = vendorInfoArray.vendorInfo.get(0);
                    vendorNameInput.setText(vendor.getVendorName());
                    vendorEmailInput.setText(vendor.getVendorEmail());
                    Glide.with(SettingsActivity.this).load(vendor.getVendorImage()).apply(option).into(vendorProfilePicture);
                    //This array always have 1 member, so use get(1).

                } else {
                    vendorNameInput.setText("Receive Null");
                }

                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "CU_NEX")){
                    checkCUNex.setVisibility(View.VISIBLE);
                }

                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "SCB_EASY")){
                    checkScb.setVisibility(View.VISIBLE);
                }

                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "K_PLUS")){
                    checkKplus.setVisibility(View.VISIBLE);
                }

                if(vendorInfoArray.findServiceProviderFromList(vendorInfoArray.getVendorPaymentMethod(), "TRUEMONEY_WALLET")){
                    checkTrueMoney.setVisibility(View.VISIBLE);
                }

                /*if(vendor.findServiceProviderFromList(vendor.getVendorPaymentMethod(), "CU_NEX")){
                    checkCUNex.setVisibility(View.VISIBLE);
                }

                if(vendor.findServiceProviderFromList(vendor.getVendorPaymentMethod(), "SCB_EASY")){
                    checkScb.setVisibility(View.VISIBLE);
                }

                if(vendor.findServiceProviderFromList(vendor.getVendorPaymentMethod(), "K_PLUS")){
                    checkKplus.setVisibility(View.VISIBLE);
                }

                if(vendor.findServiceProviderFromList(vendor.getVendorPaymentMethod(), "TRUEMONEY_WALLET")){
                    checkTrueMoney.setVisibility(View.VISIBLE);
                }*/


            }

            @Override
            public void onFailure(Call<VendorInfoArray> call, Throwable t) {
                vendorProfile.setText(t.getMessage());
                //System.out.println("\n\n\n\n"+ t.getMessage() +"\n\n\n\n");

            }
        });

        /*JSONObject postparams = new JSONObject();
        try {
            postparams.put("vendorID", "00001"); //vendorId get from other source later
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        /*JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("vendorPaymentMethod");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject vendorPaymentMethod = jsonArray.getJSONObject(i);
                                String account = vendorPaymentMethod.getString("account");


                                *//*mTextViewResult.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");*//*
                            }


                            vendorNameInput.setText(response.getString("vendorName"));
                            vendorEmailInput.setText(response.getString("vendorEmail"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);*/

    }




    //////////////////////////////////////////   Navigation(cont.)   //////////////////////////////////////
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void goToSalesRecord() {
        Intent intent = new Intent(this, SalesRecordActivity.class);
        startActivity(intent);
    }

    public void logOut(){
       Intent intent = new Intent(this, LoginActivity.class);
       startActivity(intent);
   }

}
