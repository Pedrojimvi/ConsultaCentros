package com.proyectoa_pmdm_t2_pedrojimenez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.proyectoa_pmdm_t2_pedrojimenez.fragments.FiltroDialogFragment;
import com.proyectoa_pmdm_t2_pedrojimenez.fragments.OnXListener;

public class MainActivity extends AppCompatActivity implements OnXListener {
    Button btnSel;
    Button btnCon;
    TextView txtLat;
    TextView txtLon;
    TextView txtDis;
    FrameLayout frLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSel = findViewById(R.id.btnSel);
        btnCon = findViewById(R.id.btnCon);
        txtLat = findViewById(R.id.txtLat);
        txtLon = findViewById(R.id.txtLon);
        txtDis = findViewById(R.id.txtDis);
        frLay = findViewById(R.id.frLay);

        btnSel.setOnClickListener(v -> {
            txtLat.setText("");
            txtLon.setText("");
            txtDis.setText("");
            frLay.removeAllViews();

            FiltroDialogFragment dialog = new FiltroDialogFragment();
            dialog.show(getSupportFragmentManager(), "FiltroDialogFragment");
        });

        btnCon.setOnClickListener(v -> {

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAceptarXListener(Double lat, Double lon, int dis) {
        if (lat != 0.0) {
            txtLat.setText(getString(R.string.lat) + lat);
        }
        if (lon != 0.0) {
            txtLon.setText(getString(R.string.lon) + lon);
        }
        if (dis != 0.0) {
            txtDis.setText(String.format(getString(R.string.dis), dis));
        }
    }
}