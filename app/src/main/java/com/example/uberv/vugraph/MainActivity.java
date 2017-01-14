package com.example.uberv.vugraph;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apigee.sdk.ApigeeClient;
import com.apigee.sdk.data.client.ApigeeDataClient;
import com.apigee.sdk.data.client.callbacks.ApiResponseCallback;
import com.apigee.sdk.data.client.response.ApiResponse;
import com.example.uberv.vugraph.api.ApiBAAS;
import com.example.uberv.vugraph.ui.LoginFragment;
import com.example.uberv.vugraph.ui.RegisterFragment;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ApiBAAS mApiBAAS;

//    private boolean registerMode=false;

    @BindView(R.id.container)
    FrameLayout container;
//    @BindView(R.id.btnLoginRegister)
//    Button loginRegisterButton;
//    @BindView(R.id.registerButton)
//    Button registerButton;
//    @BindView(R.id.usernameEt)
//    TextView usernameEt;
//    @BindView(R.id.passwordEt)
//    TextView passwordEt;

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        if(savedInstanceState==null) {
            loginFragment = LoginFragment.newInstance("Yes", "No");
            getSupportFragmentManager().beginTransaction().add(R.id.container, loginFragment,"LOGIN_FRAGMENT").commit();
        }else{
            // restore reference to the fragment
            loginFragment= (LoginFragment) getSupportFragmentManager().findFragmentByTag("LOGIN_FRAGMENT");
        }
        mApiBAAS=ApiBAAS.getInstance(this);


        //mApigeeDataClient.authorizeAppUser();

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // get user input
//                String username = usernameEt.getText().toString().trim();
//                String rawPassword = usernameEt.getText().toString().trim();
//
//                // Encrypt data
//                try {
//                    Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (NoSuchPaddingException e) {
//                    e.printStackTrace();
//                }
//
//                mApiBAAS.getApigeeDataClient().authorizeAppUserAsync("DominatorVasja1337", "root", new ApiResponseCallback() {
//
//                    //If authorizeAppUserAsync fails, catch the error
//                    @Override
//                    public void onException(Exception e) {
//                        // Error
//                        Log.d(LOG_TAG,e.toString());
//                    }
//
//                    //If successful, handle the response object
//                    @Override
//                    public void onResponse(ApiResponse response) {
//                        try {
//                            if (response != null) {
//                                // Success - access token is returned in the response
//                                Log.d(LOG_TAG,response.toString());
//                                String accessToken = response.getAccessToken();
//                            }
//                        } catch (Exception e) {
//                            // Fail - most likely a bad username/password
//                        }
//                    }
//                });
//
//            }
//        });

    }

    @Override
    public void onFragmentInteraction(String command, Bundle data) {
        switch (command) {
            case LoginFragment.BUTTON_LINK_TO_REGISTER_SCREEN:
                // show register screen
                Toast.makeText(this, "link to register screen", Toast.LENGTH_SHORT).show();

                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.container,RegisterFragment.newInstance(null,null))
                        .commit();

                break;
            case LoginFragment.BUTTON_LINK_TO_LOGIN_SCREEN:
                // link to login screen
                Toast.makeText(this, "link to login screen", Toast.LENGTH_SHORT).show();
                break;
            case LoginFragment.BUTTON_LOGIN:
                // log the user in
                if (data != null) {
                    String email = data.getString(LoginFragment.KEY_EMAIL);
                    String password = data.getString(LoginFragment.KEY_PASSWORD);
                    if (email != null && password != null){
                        // TODO log the user in
                        Toast.makeText(this, "logging in, email="+email+", password="+password, Toast.LENGTH_SHORT).show();

                        mApiBAAS.getApigeeDataClient().authorizeAppUserAsync(email,password, new ApiResponseCallback() {
                            @Override
                            public void onResponse(ApiResponse apiResponse) {
                                Toast.makeText(MainActivity.this, "onResponse", Toast.LENGTH_SHORT).show();
                                if(!apiResponse.completedSuccessfully()){
                                    String apiError = apiResponse.getError();
                                    String errorMessage="Could not log You in :(";
                                    if(apiError!=null){
                                        switch(apiError){
                                            case "invalid_grant":
                                                errorMessage="Invalid email or password";
                                                break;
                                        }
                                    }
                                    loginFragment.setErrorMessage(errorMessage);
                                }
                            }

                            @Override
                            public void onException(Exception e) {
                                Log.d(LOG_TAG,e.toString());
                            }
                        });

                    }
                }
                break;
        }
    }
}
