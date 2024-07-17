package com.google.codelabs.mdc.java.shrine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    private TextView textViewWelcome;
    private ImageView imageViewProfile, imageViewSettings;
    private Button buttonOrder, buttonJoin;
    private ImageView imageViewHome, imageViewScan, imageViewMenu, imageViewBenefits, imageViewSettingsBottom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        textViewWelcome = view.findViewById(R.id.textViewWelcome);
        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        imageViewSettings = view.findViewById(R.id.imageViewSettings);
        buttonOrder = view.findViewById(R.id.buttonOrder);
        buttonJoin = view.findViewById(R.id.buttonJoin);
        imageViewHome = view.findViewById(R.id.imageViewHome);
        imageViewScan = view.findViewById(R.id.imageViewScan);
        imageViewMenu = view.findViewById(R.id.imageViewMenu);
        imageViewBenefits = view.findViewById(R.id.imageViewBenefits);
        imageViewSettingsBottom = view.findViewById(R.id.imageViewSettingsBottom);

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pídelo aquí", Toast.LENGTH_SHORT).show();
            }
        });

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Únete", Toast.LENGTH_SHORT).show();
            }
        });

        // Agregar listeners para los iconos de la barra de navegación inferior si es necesario
        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para el botón de inicio
            }
        });

        imageViewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para el botón de escanear
            }
        });

        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductGridFragment();
            }
        });

        imageViewBenefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para el botón de beneficios
            }
        });

        imageViewSettingsBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para el botón de ajustes
            }
        });

        return view;
    }

    @SuppressLint("ResourceType")
    private void loadProductGridFragment() {
        ProductGridFragment productGridFragment = new ProductGridFragment();

        // Obtener el FragmentManager del fragmento actual
        FragmentManager fragmentManager = getParentFragmentManager();

        // Iniciar la transacción de fragmento
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar cualquier fragmento actual por el ProductGridFragment
        transaction.replace(R.layout.shr_product_card, productGridFragment); // el contenedor donde se mostrará el fragmento

        // Añadir la transacción a la pila de retroceso (back stack) para que el usuario pueda navegar hacia atrás
        transaction.addToBackStack(null);

        // Confirmar la transacción
        transaction.commit();
    }
}
