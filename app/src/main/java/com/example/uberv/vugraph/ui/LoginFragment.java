package com.example.uberv.vugraph.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberv.vugraph.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginFragment extends Fragment {
    public static final String LOG_TAG=LoginFragment.class.getSimpleName();

    public static final String BUTTON_LINK_TO_REGISTER_SCREEN="BUTTON_LINK_TO_REGISTER_SCREEN";
    public static final String BUTTON_LINK_TO_LOGIN_SCREEN="BUTTON_LINK_TO_LOGIN_SCREEN";
    public static final String BUTTON_LOGIN="BUTTON_LOGIN";
    public static final String BUTTON_REGISTER="BUTTON_REGISTER";
    public static final String KEY_EMAIL="KEY_EMAIL";
    public static final String KEY_PASSWORD="EMAIL";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.btnLogin)
    Button loginButton;
    @BindView(R.id.emailEt)
    EditText emailEt;
    @BindView(R.id.passwordEt)
    EditText passwordEt;
    @BindView(R.id.errorTv)
    TextView errorTv;
    @BindView(R.id.btnLinkToRegisterScreen)
    Button linkToRegisterScreenButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public LoginFragment() {
        // Required empty public constructor
        Log.d("LoginFragment","LoginFragment()");
    }

    public static LoginFragment newInstance(String param1, String param2) {
        Log.d("LoginFragment","newInstace()");
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("LoginFragment","onCreateView()");
        Toast.makeText(getContext(), "onCreateView", Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this,view);

        linkToRegisterScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(BUTTON_LINK_TO_REGISTER_SCREEN,null);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the user input
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                // TODO validate user data
                View focusView=null;
                if(!isPasswordValid(password)){
                    passwordEt.setError("password is not valid!");
                    focusView=passwordEt;
                }
                if(!isEmailValid(email)){
                    emailEt.setError("Email is not valid!");
                    focusView=emailEt;
                }

                if(focusView==null){
                    // data is valid
                    // block the login button
                    Bundle userData = new Bundle();
                    userData.putString(KEY_PASSWORD,password);
                    userData.putString(KEY_EMAIL,email);
                    mListener.onFragmentInteraction(BUTTON_LOGIN,userData);
                }else{
                    // point the user to error
                    focusView.requestFocus();
                }

            }

            
        });

        return view;
    }

    public void setErrorMessage(String error){
        errorTv.setText(error);
    }

    private boolean isEmailValid(String email) {
        if(email==null){
            return false;
        } else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    private boolean isPasswordValid(String password) {
        if(password==null){
            return false;
        }else{
            return password.length()>=6;
        }
    }

    private boolean isFullnameValid(String fullname){
        return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String command) {
        if (mListener != null) {
            mListener.onFragmentInteraction(command,null);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String command, Bundle data);
    }
}
