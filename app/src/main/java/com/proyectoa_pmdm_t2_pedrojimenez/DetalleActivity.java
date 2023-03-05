package com.proyectoa_pmdm_t2_pedrojimenez;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Centro;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Graph;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.APIRestService;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleActivity extends AppCompatActivity {
    TextView txtNomCen;
    TextView txtDes;
    TextView txtDir;
    TextView txtCod;
    TextView txtLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        txtNomCen = findViewById(R.id.txtNomCen);
        txtDes = findViewById(R.id.txtDes);
        txtDir = findViewById(R.id.txtDir);
        txtCod = findViewById(R.id.txtCod);
        txtLoc = findViewById(R.id.txtLoc);
        
        CallAPI();
    }

    private void CallAPI() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIRestService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        APIRestService service = retrofit.create(APIRestService.class);
        //Obtenemos el id del centro
        String idFinal = id.substring(id.lastIndexOf("/") + 1);
        Call<Centro> call = service.getInfo(idFinal);
        call.enqueue(new Callback<Centro>() {
            @Override
            public void onResponse(Call<Centro> call, Response<Centro> response) {
                List<Graph> graphs = response.body().getGraph();
                if (graphs != null && !graphs.isEmpty()) {
                    Graph graph = graphs.get(0);
                    txtNomCen.setText(graph.getTitle());
                    txtDir.setText(graph.getAddress().getStreetAddress());
                    txtCod.setText(graph.getAddress().getPostalCode());
                    txtLoc.setText(graph.getAddress().getLocality());
                    txtDes.setText(graph.getOrganization().getOrganizationDesc());
                }
            }

            @Override
            public void onFailure(Call<Centro> call, Throwable t) {
                Toast.makeText(DetalleActivity.this, R.string.error_api, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
