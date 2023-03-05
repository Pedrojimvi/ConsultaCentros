package com.proyectoa_pmdm_t2_pedrojimenez.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoa_pmdm_t2_pedrojimenez.MainActivity;
import com.proyectoa_pmdm_t2_pedrojimenez.R;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Centro;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Graph;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.APIRestService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoFragment extends Fragment {
    private RecyclerView rcV;
    private CentroAdapter adapter;
    private ArrayList<Graph> centrosList;
    LinearLayoutManager lLM;

    public ListadoFragment() {
        // Required empty public constructor
    }

    public static ListadoFragment newInstance() {
        ListadoFragment listFrag = new ListadoFragment();
        Bundle args = new Bundle();
        listFrag.setArguments(args);
        return listFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listado, container, false);

        rcV = v.findViewById(R.id.rcV);
        rcV.setLayoutManager(new LinearLayoutManager(getActivity()));
        centrosList = new ArrayList<>();
        adapter = new CentroAdapter(centrosList);
        rcV.setAdapter(adapter);

        return v;
    }

    public void actualizarListado(APIRestService ars, double lat, double lon, int dist, boolean filt) {
        Call<Centro> call;
        if (filt) {
            call = ars.getDataFilter(lat, lon, dist);
        }
        else
            call = ars.getData();

        call.enqueue(new Callback<Centro>() {
            @Override
            public void onResponse(Call<Centro> call, Response<Centro> response) {
                if (response.isSuccessful()) {
                    // Agregamos los datos al ArrayList con los centros
                    Centro centrosRes = response.body();
                    centrosList.addAll(centrosRes.getGraph());
                    cargarRV(centrosList);
                    if (centrosList.size() == 0)
                        Toast.makeText(getActivity(), R.string.error_no_datos, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), R.string.error_datos, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Centro> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.error_datos, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarRV(ArrayList<Graph> results) {
        lLM = new LinearLayoutManager(getActivity());
        adapter = new CentroAdapter((ArrayList<Graph>) results);

        rcV.setHasFixedSize(true);
        rcV.setLayoutManager(lLM);
        rcV.setAdapter(adapter);
    }
}
