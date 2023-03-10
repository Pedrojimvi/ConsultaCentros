package com.proyectoa_pmdm_t2_pedrojimenez.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.proyectoa_pmdm_t2_pedrojimenez.R;

public class FiltroDialogFragment extends DialogFragment {
    OnXListener listener;
    EditText edTxtLat;
    EditText edTxtLon;
    EditText edTxtDis;
    static Double lat = 0.0;
    static Double lon = 0.0;
    static int dis = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_filtro, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Creación del título del diálogo con el color correspondiente y negrita
        SpannableString titulo = new SpannableString(getResources().getString(R.string.seleccionar_filtro));
        titulo.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, titulo.length(), 0);
        titulo.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), 0, titulo.length(), 0);
        builder.setTitle(titulo);
        builder.setView(v);

        edTxtLat = v.findViewById(R.id.edTxtLat);
        edTxtLon = v.findViewById(R.id.edTxtLon);
        edTxtDis = v.findViewById(R.id.edTxtDis);

        //Modificación de los EditText para que muestren los valores que se han filtrado anteriormente
        if (lat != 0.0) {
            edTxtLat.setText(String.valueOf(lat));
        }
        if (lon != 0.0) {
            edTxtLon.setText(String.valueOf(lon));
        }
        if (dis != 0) {
            edTxtDis.setText(String.valueOf(dis));
        }

        builder.setPositiveButton(R.string.aceptar, null);
        builder.setNegativeButton(R.string.cancelar, (dialog, i) -> dialog.dismiss());

        AlertDialog ad = builder.create();
        ad.setCanceledOnTouchOutside(false);

        ad.setOnShowListener(dialog -> ad.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            boolean isValido = true;

            //Se comprueba que los valores introducidos son correctos
            if (edTxtLat.getText().toString().isBlank()) {
                edTxtLat.setError(getResources().getString(R.string.error_campo_vacio));
                isValido = false;
            }
            else {
                try {
                    lat = Double.parseDouble(edTxtLat.getText().toString());
                }
                catch (NumberFormatException e) {
                    edTxtLat.setError(getResources().getString(R.string.punto_introducido));
                    isValido = false;
                }
            }

            if (edTxtLon.getText().toString().isBlank()) {
                edTxtLon.setError(getResources().getString(R.string.error_campo_vacio));
                isValido = false;
            }
            else {
                try {
                    lon = Double.parseDouble(edTxtLon.getText().toString());
                }
                catch (NumberFormatException e) {
                    edTxtLon.setError(getResources().getString(R.string.punto_introducido));
                    isValido = false;
                }
            }

            if (edTxtDis.getText().toString().isBlank()) {
                edTxtDis.setError(getResources().getString(R.string.error_campo_vacio));
                isValido = false;
            }
            else {
                try {
                    dis = Integer.parseInt(edTxtDis.getText().toString());
                }
                catch (NumberFormatException e) {
                    edTxtDis.setError(getResources().getString(R.string.numero_invalido));
                    isValido = false;
                }
            }

            if (isValido) {
                //Si lo son se pasan los valores al método del listener
                listener.onAceptarXListener(lat, lon, dis);
                ad.dismiss();
            }
        }));

        return ad;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnXListener) {
            listener = (OnXListener) activity;
        }
        else {
            throw new RuntimeException(activity.toString() + " must implement OnXListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (listener != null) {
            listener = null;
        }
    }
}
