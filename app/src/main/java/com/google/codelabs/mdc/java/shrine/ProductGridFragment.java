package com.google.codelabs.mdc.java.shrine;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.codelabs.mdc.java.shrine.Interface.Apitdam2024iuss;
import com.google.codelabs.mdc.java.shrine.Model.Product;
import com.google.codelabs.mdc.java.shrine.Model.RptaLeerProductos;
import com.google.codelabs.mdc.java.shrine.network.ProductEntry;
import com.google.codelabs.mdc.java.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductGridFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false);

        // Set up the toolbar
        setUpToolbar(view);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById(R.id.product_grid).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
        }

        // Cargar productos desde la API
        cargarProductos();

        return view;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.shr_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    private void cargarProductos() {
        SharedPreferences sh = getActivity().getSharedPreferences("DatosSeguridad", Context.MODE_PRIVATE);
        String tokenJWT = sh.getString("tokenJWT", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://JosselinSanchez.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Apitdam2024iuss apitdam2024iuss = retrofit.create(Apitdam2024iuss.class);
        Call<RptaLeerProductos> call = apitdam2024iuss.leerProductos("JWT " + tokenJWT);
        call.enqueue(new Callback<RptaLeerProductos>() {
            @Override
            public void onResponse(Call<RptaLeerProductos> call, Response<RptaLeerProductos> response) {
                if (!response.isSuccessful()) {
                    // Manejar código de error aquí
                    return;
                }
                RptaLeerProductos rptaLeerProductos = response.body();
                if (rptaLeerProductos != null && rptaLeerProductos.getData() != null) {
                    List<ProductEntry> products = new ArrayList<>();
                    for (Product product : rptaLeerProductos.getData()) {
                        ProductEntry productEntry = new ProductEntry(
                                product.getNombre(),
                                Uri.parse(product.getUrl()),
                                product.getUrl(),
                                product.getPrecio(),
                                product.getDescripcion()
                        );
                        products.add(productEntry);
                    }
                    StaggeredProductCardRecyclerViewAdapter adapter = new StaggeredProductCardRecyclerViewAdapter(products);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<RptaLeerProductos> call, Throwable t) {
                // Manejar error de conexión aquí
            }
        });
    }
}
