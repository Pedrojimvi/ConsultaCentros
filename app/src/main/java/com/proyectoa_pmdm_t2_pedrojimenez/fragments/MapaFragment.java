package com.proyectoa_pmdm_t2_pedrojimenez.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils.APIRestService;

public class MapaFragment extends Fragment {
public MapaFragment() {
        // Required empty public constructor
    }

    public static MapaFragment newInstance() {
        MapaFragment mapFrag = new MapaFragment();
        Bundle args = new Bundle();
        mapFrag.setArguments(args);
        return mapFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void actualizarMapa(APIRestService ars, double lat, double lon, int dist, boolean filt) {

    }
}
