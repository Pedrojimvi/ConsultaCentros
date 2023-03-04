package com.proyectoa_pmdm_t2_pedrojimenez.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoa_pmdm_t2_pedrojimenez.R;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Centro;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Graph;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.APIRestService;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListadoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView rcV;
    private CentroAdapter adapter;
    private ArrayList<Graph> centrosList;
    LinearLayoutManager lLM;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListadoFragment() {
        // Required empty public constructor
    }


    public static ListadoFragment newInstance(String param1, String param2) {
        ListadoFragment listFrag = new ListadoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        // Inicializamos el Retrofit
        Retrofit rF = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = rF.create(APIRestService.class);
        Call<Centro> call = ars.getData();
        call.enqueue(new Callback<Centro>() {
            @Override
            public void onResponse(Call<Centro> call, Response<Centro> response) {
                if (response.isSuccessful()) {
                    Centro centrosRes = response.body();
                    // Agregamos los datos al ArrayList con los centros
                    centrosList.addAll(centrosRes.getGraph());
                    cargarRV(centrosList);
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

        return v;
    }

    private void cargarRV(ArrayList<Graph> results) {
        lLM = new LinearLayoutManager(getActivity());
        adapter = new CentroAdapter((ArrayList<Graph>) results);

        rcV.setHasFixedSize(true);
        rcV.setLayoutManager(lLM);
        rcV.setAdapter(adapter);
    }
}
