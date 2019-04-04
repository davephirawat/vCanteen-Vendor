package com.example.vcanteenvendor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passField;
    private Button loginBtn;
    private Button fbBtn;
    private Button forgotPass;
    private TextView errorMessage;
    private Button showPass;
    private CallbackManager callbackManager;

    private Dialog recoverPassDialog;
    private Button sendRecoverBtn;
    private TextView backRecoverBtn;
    private EditText emailRecoverField;
    private TextView errorEmailRecover;
    private TextView errorEmpty;

    private Dialog confirmRecoverPass;
    private Button cancelConfirmRecover;
    private Button sendRecoverPass;

    private String email;
    private String passwd;
    private String account_type;

    private SharedPreferences sharedPref;
    private ProgressDialog progressDialog;

    private final String URL = "https://vcanteen.herokuapp.com/";
    private boolean isHidden = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);

        emailField = findViewById(R.id.email_field);
        passField = findViewById(R.id.password_field);
        loginBtn = findViewById(R.id.login_button);
        fbBtn = findViewById(R.id.facebook_button);
        forgotPass = findViewById(R.id.forgot_pw_button);
        errorMessage = findViewById(R.id.errorLogin);
        showPass = findViewById(R.id.show_pw_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailField.getText().toString().equals("") && passField.getText().toString().equals("")) {
                    errorMessage.setText("Please fill both Email and Password.");
                    errorMessage.setVisibility(View.VISIBLE);
                    return;
                }
                email = emailField.getText().toString();
                passwd = passField.getText().toString();
                account_type = "NORMAL";
                sendJSON(email, passwd);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fblogin();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverPassDialog = new Dialog(LoginActivity.this);
                recoverPassDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                recoverPassDialog.setContentView(R.layout.dialog_forgot_pass);

                sendRecoverBtn = recoverPassDialog.findViewById(R.id.send_recover_btn);
                backRecoverBtn = recoverPassDialog.findViewById(R.id.back_recover);
                emailRecoverField = recoverPassDialog.findViewById(R.id.recover_email);
                errorEmailRecover = recoverPassDialog.findViewById(R.id.error_recover);
                errorEmpty = recoverPassDialog.findViewById(R.id.error_email_notfound);

                sendRecoverBtn.setEnabled(true);
                backRecoverBtn.setEnabled(true);
                emailRecoverField.setEnabled(true);

                emailRecoverField.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().length() > 0)
                            errorEmpty.setVisibility(View.GONE);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                sendRecoverBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (emailRecoverField.getText().toString().equals("")) {
                            errorEmpty.setVisibility(View.VISIBLE);
                            return;
                        }
                        confirmRecoverPass = new Dialog(LoginActivity.this);
                        confirmRecoverPass.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        confirmRecoverPass.setContentView(R.layout.dialog_confirm_forgot_pass);

                        cancelConfirmRecover = confirmRecoverPass.findViewById(R.id.confirm_cancel_recover);
                        sendRecoverPass = confirmRecoverPass.findViewById(R.id.confirm_recover_pass);

                        cancelConfirmRecover.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirmRecoverPass.dismiss();
                            }
                        });

                        sendRecoverPass.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressDialog = new ProgressDialog(LoginActivity.this);
                                progressDialog = ProgressDialog.show(LoginActivity.this, "",
                                        "Loading. Please wait...", true);
                                // HTTP PUT email
                                String emailSent = emailRecoverField.getText().toString();
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(URL)
                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                        .build();
                                final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                                RecoverPass postData = new RecoverPass(emailSent);
                                System.out.println(postData.toString());
                                Call<Void> call = jsonPlaceHolderApi.recoverPass(postData);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        System.out.println(response.code());
                                        if (response.code() != 200) {
                                            // Error
                                            progressDialog.dismiss();
                                            confirmRecoverPass.dismiss();
                                            errorEmailRecover.setVisibility(View.VISIBLE);
                                        } else {
                                            // Success
                                            Toast.makeText(getApplicationContext(), "A new Password has been sent to your Email.", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            confirmRecoverPass.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });


                            }
                        });

                        confirmRecoverPass.show();
                    }


                });

                backRecoverBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recoverPassDialog.dismiss();
                    }
                });

                recoverPassDialog.show();
            }
        });

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    passField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHidden = false;
                } else {
                    passField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHidden = true;
                }
            }
        });

    }

    private void Fblogin() {
        callbackManager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");

                                            String jsonresult = String.valueOf(json);
                                            System.out.println("JSON Result" + jsonresult);

                                            email = json.optString("email");
//                                                Toast.makeText(LoginActivity.this, email, Toast.LENGTH_LONG).show();
                                            String str_id = json.optString("id");
                                            String str_firstname = json.optString("first_name");
                                            String str_lastname = json.optString("last_name");

                                            account_type = "FACEBOOK";
                                            passwd = null;
                                            sendJSON(email, passwd);

                                        }

                                    }

                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,first_name, last_name, email,link, picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void sendJSON(String email, String pass) {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog = ProgressDialog.show(LoginActivity.this, "",
                "Loading. Please wait...", true);
        LoginVendor loginVendor = new LoginVendor(email, passwd);
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<LoginResponse> call = jsonPlaceHolderApi.sendLogin(loginVendor);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() != 200) {
                    // Fail
                    if (account_type.equals("FACEBOOK"))
                        sharedPref.edit().putString("token", "NO TOKEN JA EDOK").commit();
                    progressDialog.dismiss();
                    errorMessage.setText("Email or Password is Incorrect");
                    errorMessage.setVisibility(View.VISIBLE);
                    if (account_type.equals("FACEBOOK"))
                        LoginManager.getInstance().logOut();
                } else {
                    // Success

                    // save vendor_id, token
                    sharedPref.edit().putString("token", response.body().getToken()).commit();
                    sharedPref.edit().putInt("vendor_id", response.body().getVendor_id()).commit();
                    System.out.println(response.body().getVendor_id());
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

        ImageView chefpic = findViewById(R.id.chefpic);
        RequestOptions option = new RequestOptions().centerCrop();
        Glide.with(LoginActivity.this).load(R.drawable.hero_image).apply(option).into(chefpic);
    }
}
