package com.google.codelabs.mdc.java.shrine;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.codelabs.mdc.java.shrine.Interface.Apitdam2024iuss;
import com.google.codelabs.mdc.java.shrine.Model.ReqAuth;
import com.google.codelabs.mdc.java.shrine.Model.RptaAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {
    private TextView mRptaJson;
    public String TokenJWT;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_login_fragment, container, false);
        final TextInputLayout usernameTextInput = view.findViewById(R.id.username_text_input);
        final TextInputEditText usernameEditText = view.findViewById(R.id.username_edit_text);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = view.findViewById(R.id.next_button);
        MaterialButton registerButton = view.findViewById(R.id.register_button);  // Referencia al botón de registrar
        mRptaJson = view.findViewById(R.id.txtRptaJson);

        // Snippet from "Navigate to the next Fragment" section goes here.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.shr_error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    obtenerJWT(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });

        // Configuración del botón de registrar
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new RegistroFragment(), false); // Navega al fragmento de registro
            }
        });

        return view;
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    private void obtenerJWT(String p_username, String p_password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://JosselinSanchez.pythonanywhere.com/")  // Cambiado a HTTPS
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Apitdam2024iuss apitdam2024iuss = retrofit.create(Apitdam2024iuss.class);
        ReqAuth reqAuth = new ReqAuth();
        reqAuth.setUsername(p_username);
        reqAuth.setPassword(p_password);
        Call<RptaAuth> call = apitdam2024iuss.auth(reqAuth);
        call.enqueue(new Callback<RptaAuth>() {
            @Override
            public void onResponse(Call<RptaAuth> call, Response<RptaAuth> response) {
                if(!response.isSuccessful()){
                    mRptaJson.setText("Codigo oJWT: " + response.code());
                    return;
                }
                RptaAuth rptaAuth = response.body();
                TokenJWT = rptaAuth.getAccess_token();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DatosSeguridad", MODE_PRIVATE);
                SharedPreferences.Editor miEditor = sharedPreferences.edit();
                miEditor.putString("tokenJWT", TokenJWT);
                miEditor.apply();

                ((NavigationHost) getActivity()).navigateTo(new HomeFragment(), false); // Navigate to the next Fragment
            }

            @Override
            public void onFailure(Call<RptaAuth> call, Throwable t) {
                mRptaJson.setText(t.getMessage());
            }
        });
    }
}
