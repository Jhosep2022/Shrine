package com.google.codelabs.mdc.java.shrine;


import android.os.Bundle;
import android.text.Editable;
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
import com.google.codelabs.mdc.java.shrine.Model.ReqRegistro;
import com.google.codelabs.mdc.java.shrine.Model.RptaRegistro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroFragment extends Fragment {
    private TextView mRptaJson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_registro, container, false);

        final TextInputLayout emailTextInput = view.findViewById(R.id.register_email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.register_email_edit_text);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.register_password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.register_password_edit_text);
        MaterialButton registerButton = view.findViewById(R.id.register_button);
        mRptaJson = view.findViewById(R.id.txtRptaJson);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInputValid(emailEditText.getText(), passwordEditText.getText())) {
                    // Manejar errores de validación de entrada
                    return;
                }

                registerUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        return view;
    }

    private boolean isInputValid(@Nullable Editable email, @Nullable Editable password) {
        return email != null && !email.toString().isEmpty()
                && password != null && password.length() >= 8;
    }

    private void registerUser(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://JosselinSanchez.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Apitdam2024iuss apitdam2024iuss = retrofit.create(Apitdam2024iuss.class);
        ReqRegistro reqRegister = new ReqRegistro();
        reqRegister.setEmail(email);
        reqRegister.setPassword(password);
        Call<RptaRegistro> call = apitdam2024iuss.registro(reqRegister);
        call.enqueue(new Callback<RptaRegistro>() {
            @Override
            public void onResponse(Call<RptaRegistro> call, Response<RptaRegistro> response) {
                if (!response.isSuccessful()) {
                    mRptaJson.setText("Error: " + response.code());
                    return;
                }
                RptaRegistro rptaRegister = response.body();
                mRptaJson.setText("User registered successfully: " + rptaRegister.getMessage());

                // Navegar a otro fragmento si es necesario
            }

            @Override
            public void onFailure(Call<RptaRegistro> call, Throwable t) {
                mRptaJson.setText(t.getMessage());
            }
        });
    }
}