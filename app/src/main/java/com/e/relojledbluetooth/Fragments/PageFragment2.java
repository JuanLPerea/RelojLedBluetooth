package com.e.relojledbluetooth.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.relojledbluetooth.Clases.Ajustes;
import com.e.relojledbluetooth.Clases.GuardarSharedPrefs;
import com.e.relojledbluetooth.MainActivity;
import com.e.relojledbluetooth.R;

public class PageFragment2 extends Fragment {

    RadioGroup modoRadioRG;
    CheckBox repetirCB;
    EditText textoET;
    Button botonEnviarBTN;
    Ajustes ajustes;
    RadioButton modoNormalRB, modoMiniRB, modoAleatorioRB, modoFechaRB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page2, container, false);

        modoRadioRG = rootView.findViewById(R.id.radioGroupModo);
        repetirCB = rootView.findViewById(R.id.checkTexto);
        textoET = rootView.findViewById(R.id.textoEnviarET);
        botonEnviarBTN = rootView.findViewById(R.id.enviar_textoBTN);
        modoNormalRB = rootView.findViewById(R.id.radioButtonNormal);
        modoMiniRB = rootView.findViewById(R.id.radioButtonMini);
        modoAleatorioRB = rootView.findViewById(R.id.radioButtonAleat);
        modoFechaRB = rootView.findViewById(R.id.radioButtonFecha);

        // Cargamos los ajustes del Shared Preferences
        cargarAjustes();

        modoRadioRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Miapp", "Seleccionado: " + checkedId);

                if(((MainActivity) getActivity()).getFragmentActivo() == 2) {
                    switch (checkedId) {
                        case R.id.radioButtonNormal:
                            ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 1);
                            guardarAjustes();
                            break;
                        case R.id.radioButtonMini:
                            ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 2);
                            guardarAjustes();
                            break;
                        case R.id.radioButtonAleat:
                            ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 3);
                            guardarAjustes();
                            break;
                        case R.id.radioButtonFecha:
                            ((MainActivity) getActivity()).enviarComandoBluetooth("#M#" + 4);
                            guardarAjustes();
                            break;
                    }
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

    private void cargarAjustes() {
        ajustes = GuardarSharedPrefs.cargarDatos(getContext());
        switch (ajustes.getModoReloj()) {
            case 1:
                modoNormalRB.setSelected(true);
                break;
            case 2:
                modoMiniRB.setSelected(true);
                break;
            case 3:
                modoAleatorioRB.setSelected(true);
                break;
            case 4:
                modoFechaRB.setSelected(true);
                break;
        }
    }

    private void guardarAjustes() {
        int seleccionado = 0;

        switch (modoRadioRG.getCheckedRadioButtonId()) {
            case R.id.radioButtonNormal:
                seleccionado = 1;
                break;
            case R.id.radioButtonMini:
                seleccionado = 2;
                break;
            case R.id.radioButtonAleat:
                seleccionado = 3;
                break;
            case R.id.radioButtonFecha:
                seleccionado = 4;
                break;
        }
        ajustes.setModoReloj(seleccionado);
        GuardarSharedPrefs.guardarDatos(getContext(), ajustes);
    }

    @Override
    public void onPause() {
        super.onPause();
        guardarAjustes();
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarAjustes();
        ((MainActivity) getActivity()).setFragmentActivo(2);
    }
}
