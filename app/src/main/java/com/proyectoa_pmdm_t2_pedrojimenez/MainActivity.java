package com.proyectoa_pmdm_t2_pedrojimenez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.proyectoa_pmdm_t2_pedrojimenez.fragments.FiltroDialogFragment;
import com.proyectoa_pmdm_t2_pedrojimenez.fragments.ListadoFragment;
import com.proyectoa_pmdm_t2_pedrojimenez.fragments.MapaFragment;
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
    double lat;
    double lon;
    int dis;

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

        btnSel.setOnClickListener(v -> {
            txtLat.setText("");
            txtLon.setText("");
            txtDis.setText("");

            FiltroDialogFragment dialog = new FiltroDialogFragment();
            dialog.show(getSupportFragmentManager(), null);
        });

        btnCon.setOnClickListener(v -> {
            ListadoFragment lf = new ListadoFragment();
            cargarFragment(lf);
            lf.actualizarListado(iniRf(), lat, lon, dis, compFilt());
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lista:
                txtLat.setText("");
                txtLon.setText("");
                txtDis.setText("");

                ListadoFragment lf = new ListadoFragment();
                btnCon.setText(R.string.consultar_listado);
                cargarFragment(lf);

                btnCon.setOnClickListener(v -> {
                    lf.actualizarListado(iniRf(), lat, lon, dis, compFilt());
                });
                return super.onOptionsItemSelected(item);
            case R.id.mapa:
                txtLat.setText("");
                txtLon.setText("");
                txtDis.setText("");

                MapaFragment mp = new MapaFragment();
                btnCon.setText(R.string.consultar_mapa);
                cargarFragment(mp);

                btnCon.setOnClickListener(v -> {
                    mp.actualizarMapa(iniRf(), lat, lon, dis, compFilt());
                });
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean compFilt() {
        return !txtLat.getText().toString().isBlank() && !txtLon.getText().toString().isBlank() && !txtDis.getText().toString().isBlank();
    }

    public APIRestService iniRf() {
        Retrofit rf = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = rf.create(APIRestService.class);

        return ars;
    }

    private void cargarFragment(Fragment fr) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frLay, fr);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAceptarXListener(Double lat, Double lon, int dis) {
        this.lat = lat;
        this.lon = lon;
        this.dis = dis;
        txtLat.setText(getString(R.string.lat) + lat);
        txtLon.setText(getString(R.string.lon) + lon);
        txtDis.setText(String.format(getString(R.string.dis), dis));
    }
}