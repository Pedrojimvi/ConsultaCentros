package com.proyectoa_pmdm_t2_pedrojimenez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.proyectoa_pmdm_t2_pedrojimenez.Fragments.FiltroDialogFragment;

public class MainActivity extends AppCompatActivity {
    Button btnSel;
    Button btnCon;
    TextView txtLat;
    TextView txtLon;
    TextView txtDis;

    Double lat = 0.0;
    Double lon = 0.0;
    String dis = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSel = findViewById(R.id.btnSel);
        btnCon = findViewById(R.id.btnCon);
        txtLat = findViewById(R.id.txtLat);
        txtLon = findViewById(R.id.txtLon);
        txtDis = findViewById(R.id.txtDis);

        btnSel.setOnClickListener(v -> {
            FiltroDialogFragment dialog = new FiltroDialogFragment();
            dialog.show(getSupportFragmentManager(), "FiltroDialogFragment");

            onAceptarXListener(lat, lon, dis);
        });

        btnCon.setOnClickListener(v -> {

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAceptarXListener(Double lat, Double lon, String dis) {
        this.lat = lat;
        this.lon = lon;
        this.dis = dis;

        txtLat.setText("Latitud: " + lat);
        txtLon.setText("Longitud: " + lon);
        txtDis.setText("Distancia: " + dis);
    }
}