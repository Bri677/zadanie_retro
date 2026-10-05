package com.example.zadanie;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnWyswietl;
    private TextView tvLista;
    private JsonPlaceHolderApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnWyswietl = findViewById(R.id.btnWyswietl);
        tvLista = findViewById(R.id.tvLista);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Bri677/Json2/main/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(JsonPlaceHolderApi.class);

        btnWyswietl.setOnClickListener(view -> pobierzWiadomosci());
    }

    private void pobierzWiadomosci() {
        api.getWiadomosci().enqueue(new Callback<List<Wiadomosc>>() {
            @Override
            public void onResponse(Call<List<Wiadomosc>> call, Response<List<Wiadomosc>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(MainActivity.this,
                            "Błąd: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (Wiadomosc w : response.body()) {
                    sb.append(w.getTytul()).append("\n")
                            .append(w.getTresc()).append("\n")
                            .append(w.getData()).append("\n\n");
                }
                tvLista.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<List<Wiadomosc>> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Brak połączenia: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}