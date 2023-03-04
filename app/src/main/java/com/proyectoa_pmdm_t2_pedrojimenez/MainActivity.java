package com.proyectoa_pmdm_t2_pedrojimenez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.proyectoa_pmdm_t2_pedrojimenez.fragments.FiltroDialogFragment;
import com.proyectoa_pmdm_t2_pedrojimenez.fragments.ListadoFragment;
import com.proyectoa_pmdm_t2_pedrojimenez.fragments.OnXListener;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.APIRestService;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.RetrofitClient;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnXListener {
    Button btnSel;
    Button btnCon;
    TextView txtLat;
    TextView txtLon;
    TextView txtDis;
    FrameLayout frLay;
    FragmentManager fm;

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

        fm = getSupportFragmentManager();

        iniRf();

        btnSel.setOnClickListener(v -> {
            txtLat.setText("");
            txtLon.setText("");
            txtDis.setText("");
            frLay.removeAllViews();

            FiltroDialogFragment dialog = new FiltroDialogFragment();
            dialog.show(getSupportFragmentManager(), "FiltroDialogFragment");
        });

        btnCon.setOnClickListener(v -> {
            ListadoFragment listFrag = new ListadoFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frLay, listFrag);
            ft.addToBackStack(null);
            ft.commit();
        });
    }

    private void iniRf() {
        Retrofit rf = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestServ = rf.create(APIRestService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAceptarXListener(Double lat, Double lon, int dis) {
        txtLat.setText(getString(R.string.lat) + lat);
        txtLon.setText(getString(R.string.lon) + lon);
        txtDis.setText(String.format(getString(R.string.dis), dis));
    }
}