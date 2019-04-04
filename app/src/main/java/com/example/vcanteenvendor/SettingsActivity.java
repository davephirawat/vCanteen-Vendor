package com.example.vcanteenvendor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^[a-zA-Z0-9@!#$%^&+-=](?=\\S+$).{7,}$");

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
    private EditText currPassBox;
    private EditText newPassBox;
    private EditText confirmNewPassBox;
    private Button confirmChangePass;

    private Button clearCurrPass;
    private Button clearNewPass;
    private Button clearConfirmPass;

    private TextView errorCurrPass;
    private TextView errorNewPass;
    private TextView errorConfirmPass;

    private Button closeDialog;
    private SharedPreferences sharedPref;


    private RequestQueue mQueue;
    private String url="FROM ENDPOINTS";

    String email;

    Dialog dialog;

    Vendor vendor;
    VendorInfoArray vendorInfoArray;

    ProgressDialog progressDialog;

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

                closeDialog = changePassDialog.findViewById(R.id.close_dialog);

                confirmChangePass.setEnabled(true);
                clearCurrPass.setEnabled(true);
                clearNewPass.setEnabled(true);
                clearConfirmPass.setEnabled(true);

                changePassDialog.show();

                confirmChangePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        progressDialog = new ProgressDialog(SettingsActivity.this);
                        progressDialog = ProgressDialog.show(SettingsActivity.this
                                , "",
                                "Loading. Please wait...", true);

                        String currPass = currPassBox.getText().toString();
                        String newPass = newPassBox.getText().toString();
                        String confirmPass = confirmNewPassBox.getText().toString();

                        if (currPass.equals(newPass)) {
                            errorCurrPass.setText("Your new password can't be the same as your current passaword.");
                            errorCurrPass.setVisibility(View.VISIBLE);
                            currPassBox.setText("");
                            newPassBox.setText("");
                            confirmNewPassBox.setText("");
                            progressDialog.dismiss();

                        } else if (!(newPass.equals(confirmPass))) {
                            errorConfirmPass.setText("Password doesn't match. Please try again.");
                            errorConfirmPass.setVisibility(View.VISIBLE);
                            //currentPassword.setText("");
                            newPassBox.setText("");
                            confirmNewPassBox.setText("");
                            progressDialog.dismiss();

                        } else if (newPass.length() < 8 || newPass.length() > 20) {
                            errorNewPass.setText("Invalid Password. Please try again.");
                            errorNewPass.setVisibility(View.VISIBLE);
                            //currentPassword.setText("");
                            newPassBox.setText("");
                            confirmNewPassBox.setText("");
                            progressDialog.dismiss();
                        } else if (!PASSWORD_PATTERN.matcher(newPass).matches()) {
                            errorNewPass.setText("Invalid Password. Please try again.");
                            errorNewPass.setVisibility(View.VISIBLE);
                            //currentPassword.setText("");
                            newPassBox.setText("");
                            confirmNewPassBox.setText("");
                            progressDialog.dismiss();
                        } else {
                            errorNewPass.setVisibility(View.GONE);
                            errorConfirmPass.setVisibility(View.GONE);
                            errorCurrPass.setVisibility(View.GONE);

                            url="https://vcanteen.herokuapp.com/";

                            Gson gson = new GsonBuilder().serializeNulls().create();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(url)
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .build();
                            final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                            ChangePass postData = new ChangePass(newPass, email);
                            System.out.println(postData.toString());
                            Call<Void> call = jsonPlaceHolderApi.resetPass(postData);

                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() != 200) {
                                        // ERROR
                                        errorConfirmPass.setText("Current password is incorrect. Please try again.");
                                        errorConfirmPass.setVisibility(View.VISIBLE);
                                        confirmNewPassBox.setText("");
                                        currPassBox.setText("");
                                        newPassBox.setText("");
                                        progressDialog.dismiss();
                                    } else {
                                        // SUCCESS
                                        Toast.makeText(getApplicationContext(), "Password successfully changed.", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }

                    }
                });

                clearCurrPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currPassBox.setText("");
                    }
                });

                clearNewPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newPassBox.setText("");
                    }
                });

                clearConfirmPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmNewPassBox.setText("");
                    }
                });

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePassDialog.dismiss();
                    }
                });
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

        progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog = ProgressDialog.show(SettingsActivity.this, "",
                "Loading. Please wait...", true);

        url="https://vcanteen.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String token = sharedPref.getString("token", "empty token");
        int vendor_id =  sharedPref.getInt("vendor_id", 0);
        System.out.println(vendor_id);
        Call<VendorInfoArray> call = jsonPlaceHolderApi.getVendorInfo(vendor_id);

        progressDialog.dismiss();

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
                    email = vendor.getVendorEmail();
                    System.out.println(email);
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

                progressDialog.dismiss();
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
        LoginManager.getInstance().logOut();
        sharedPref.edit().putString("token", "NO TOKEN JA EDOK").commit();
        sharedPref.edit().putInt("vendor_id", 0).commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
   }

}
