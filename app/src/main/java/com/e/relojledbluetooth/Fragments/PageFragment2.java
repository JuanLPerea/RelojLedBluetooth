package com.e.relojledbluetooth.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.relojledbluetooth.MainActivity;
import com.e.relojledbluetooth.R;

public class PageFragment2 extends Fragment {

    RadioGroup modoRadioRG;
    CheckBox repetirCB;
    EditText textoET;
    Button botonEnviarBTN;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page2, container, false);

        modoRadioRG = rootView.findViewById(R.id.radioGroupModo);
        repetirCB = rootView.findViewById(R.id.checkTexto);
        textoET = rootView.findViewById(R.id.textoEnviarET);
        botonEnviarBTN = rootView.findViewById(R.id.enviar_textoBTN);

        modoRadioRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Miapp", "Seleccionado: " + checkedId);
                switch (checkedId) {
                    case R.id.radioButtonNormal:
                        ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 1);
                        break;
                    case R.id.radioButtonMini:
                        ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 2);
                        break;
                    case R.id.radioButtonAleat:
                        ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 3);
                        break;
                    case R.id.radioButtonFecha:
                        ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 4);
                        break;
                }
            }
        });

        botonEnviarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoEnviado = textoET.getText().toString();
                if (repetirCB.isChecked()) {
                    ((MainActivity) getActivity()).enviarComandoBluetooth("#T#" + textoEnviado);
                } else {
                    ((MainActivity) getActivity()).enviarComandoBluetooth("#t#" + textoEnviado);
                }

            }
        });


        return rootView;

    }


}
