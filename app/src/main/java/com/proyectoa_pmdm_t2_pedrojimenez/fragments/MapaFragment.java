package com.proyectoa_pmdm_t2_pedrojimenez.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proyectoa_pmdm_t2_pedrojimenez.R;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Centro;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Graph;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.APIRestService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MapaFragment extends Fragment {
    protected SupportMapFragment mapa;
    private ArrayList<Graph> list;

    public MapaFragment() {
        // Required empty public constructor
    }

    public static MapaFragment newInstance(ArrayList<Graph> list) {
        MapaFragment mapFrag = new MapaFragment();
        Bundle args = new Bundle();
        mapFrag.setArguments(args);
        args.putSerializable("list", list);
        return mapFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Creamos la vista del fragmento
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        mapa = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);

        return view;
    }

    public void actualizarMapa(APIRestService ars, double lat, double lon, int dist, boolean filt) {
        Call<Centro> call;
        if (filt) {
            call = ars.getDataFilter(lat, lon, dist);
        }
        else {
            call = ars.getData();
        }

        call.enqueue(new retrofit2.Callback<Centro>() {
            @Override
            public void onResponse(Call<Centro> call, Response<Centro> response) {
                if (response.isSuccessful()) {
                    list = (ArrayList<Graph>) response.body().getGraph();
                    cambiarMapa();
                    if (list.size() == 0)
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

    private void cambiarMapa() {
        mapa.getMapAsync(googleMap ->  {
            googleMap.clear();

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            LatLng latLng = null;
            for (Graph g : list) {
                latLng = new LatLng(g.getLocation().getLatitude(), g.getLocation().getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).title(g.getTitle()));
                builder.include(latLng);
            }
            if (latLng != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
            }
        });
    }
}
