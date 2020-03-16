package com.e.relojledbluetooth.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.relojledbluetooth.Clases.Ajustes;
import com.e.relojledbluetooth.Clases.GuardarSharedPrefs;
import com.e.relojledbluetooth.MainActivity;
import com.e.relojledbluetooth.R;

public class PageFragment3 extends Fragment {

    ImageButton aumentarIV, disminuirIV;
    TextView horasTV, minutosTV;
    Switch switchAlarma;
    CheckBox checkL, checkM, checkX, checkJ, checkV, checkS, checkD, sonidoHoras;
    int minutosAlarma = 0, horaAlarma = 0;
    int seleccionado = 0;
    Ajustes ajustes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page3, container, false);

        aumentarIV = rootView.findViewById(R.id.aumentarBTN);
        disminuirIV = rootView.findViewById(R.id.disminuirBTN);
        horasTV = rootView.findViewById(R.id.horaAlarmaTV);
        minutosTV = rootView.findViewById(R.id.minutosAlarmaTV);
        switchAlarma = rootView.findViewById(R.id.switchAlarma);
        checkL = rootView.findViewById(R.id.checkBoxL);
        checkM = rootView.findViewById(R.id.checkBoxM);
        checkX = rootView.findViewById(R.id.checkBoxX);
        checkJ = rootView.findViewById(R.id.checkBoxJ);
        checkV = rootView.findViewById(R.id.checkBoxV);
        checkS = rootView.findViewById(R.id.checkBoxS);
        checkD = rootView.findViewById(R.id.checkBoxD);
        sonidoHoras = rootView.findViewById(R.id.sonidoHoras);

        cargarAjustes();

        horasTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seleccionado != 1) {
                    seleccionado = 1;
                    horasTV.setBackgroundColor(Color.YELLOW);
                    minutosTV.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    horasTV.setBackgroundColor(Color.TRANSPARENT);
                    seleccionado = 0;
                }
            }
        });

        minutosTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seleccionado != 2) {
                    seleccionado = 2;
                    minutosTV.setBackgroundColor(Color.YELLOW);
                    horasTV.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    minutosTV.setBackgroundColor(Color.TRANSPARENT);
                    seleccionado = 0;
                }

            }
        });

        aumentarIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (seleccionado) {
                    case 2:
                        minutosAlarma++;
                        if (minutosAlarma > 59) minutosAlarma = 0;
                        if (minutosAlarma < 10) {
                            minutosTV.setText("0" + minutosAlarma);
                        } else {
                            minutosTV.setText("" + minutosAlarma);
                        }
                        break;
                    case 1:
                        horaAlarma++;
                        if (horaAlarma > 23) horaAlarma = 0;
                        if (horaAlarma < 10) {
                            horasTV.setText("0" + horaAlarma);
                        } else {
                            horasTV.setText("" + horaAlarma);
                        }

                        break;
                    case 0:
                        Toast.makeText(getActivity(), "Toca en la hora o los minutos para cambiarlo", Toast.LENGTH_LONG).show();
                        break;
                }

            }
        });

        disminuirIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (seleccionado) {
                    case 2:
                        minutosAlarma--;
                        if (minutosAlarma < 0) minutosAlarma = 59;
                        if (minutosAlarma < 10) {
                            minutosTV.setText("0" + minutosAlarma);
                        } else {
                            minutosTV.setText("" + minutosAlarma);
                        }
                        break;
                    case 1:
                        horaAlarma--;
                        if (horaAlarma < 0) horaAlarma = 23;
                        if (horaAlarma < 10) {
                            horasTV.setText("0" + horaAlarma);
                        } else {
                            horasTV.setText("" + horaAlarma);
                        }

                        break;
                    case 0:
                        Toast.makeText(getActivity(), "Toca en la hora o los minutos para cambiarlo", Toast.LENGTH_LONG).show();
                        break;
                }

            }
        });

        switchAlarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                minutosTV.setBackgroundColor(Color.TRANSPARENT);
                horasTV.setBackgroundColor(Color.TRANSPARENT);
                seleccionado = 0;

                if (switchAlarma.isChecked()) {
                    // Activar la alarma
                    ((MainActivity) getActivity()).enviarComandoBluetooth("#S#" + textoAlarma());
                } else {
                    // Desactivar la alarma
                    ((MainActivity) getActivity()).enviarComandoBluetooth("#s#");
                }
            guardarAjustes();
            }
        });

        sonidoHoras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sonidoHoras.isChecked()) {
                    // Tocar un sonido cada hora
                    ((MainActivity) getActivity()).enviarComandoBluetooth("#C#");
                } else {
                    ((MainActivity) getActivity()).enviarComandoBluetooth("#c#");
                }
                guardarAjustes();
            }
        });


        return rootView;

    }

    private void cargarAjustes() {
        ajustes = GuardarSharedPrefs.cargarDatos(getContext());

        if (!ajustes.getAlarma().equals("")) {
            horaAlarma = Integer.parseInt(ajustes.getAlarma().substring(0,2));
            minutosAlarma = Integer.parseInt(ajustes.getAlarma().substring(3,5));
            horasTV.setText(horaAlarma + "");
            minutosTV.setText(minutosAlarma + "");

            if (ajustes.getAlarma().contains("L")){
                checkL.setChecked(true);
            } else {
                checkL.setChecked(false);
            }
            if (ajustes.getAlarma().contains("M")){
                checkM.setChecked(true);
            } else {
                checkM.setChecked(false);
            }
            if (ajustes.getAlarma().contains("X")){
                checkX.setChecked(true);
            } else {
                checkX.setChecked(false);
            }
            if (ajustes.getAlarma().contains("J")){
                checkJ.setChecked(true);
            } else {
                checkJ.setChecked(false);
            }
            if (ajustes.getAlarma().contains("V")){
                checkV.setChecked(true);
            } else {
                checkV.setChecked(false);
            }
            if (ajustes.getAlarma().contains("S")){
                checkS.setChecked(true);
            } else {
                checkS.setChecked(false);
            }
            if (ajustes.getAlarma().contains("D")){
                checkD.setChecked(true);
            } else {
                checkD.setChecked(false);
            }

            switchAlarma.setChecked(true);
        } else {
            switchAlarma.setChecked(false);
        }

        if(ajustes.isTocarHoras()) {
            sonidoHoras.setChecked(true);
        } else {
            sonidoHoras.setChecked(false);
        }
    }

    private void guardarAjustes() {
        ajustes.setAlarma(textoAlarma());
        if (sonidoHoras.isChecked()) {
            ajustes.setTocarHoras(true);
        } else {
            ajustes.setTocarHoras(false);
        }
        GuardarSharedPrefs.guardarDatos(getContext(), ajustes);
    }

    private String textoAlarma(){
        String textoHoras = horasTV.getText().toString();
        if (textoHoras.length() < 2) {
            textoHoras = "0" + textoHoras;
        }
        String textoMinutos = minutosTV.getText().toString();
        if (textoHoras.length() < 2) textoHoras = "0" + textoHoras;

        String textoAlarma = textoHoras + ":" + textoMinutos + ":00";

        if (checkL.isChecked()) {
            textoAlarma = textoAlarma + "L";
        }
        if (checkM.isChecked()){
            textoAlarma = textoAlarma + "M";
        }
        if (checkX.isChecked()){
            textoAlarma = textoAlarma + "X";
        }
        if (checkJ.isChecked()){
            textoAlarma = textoAlarma + "J";
        }
        if (checkV.isChecked()){
            textoAlarma = textoAlarma + "V";
        }
        if (checkS.isChecked()){
            textoAlarma = textoAlarma + "S";
        }
        if (checkD.isChecked()){
            textoAlarma = textoAlarma + "D";
        }
        // Si no hay ningún check seleccionado la alarma suena todos los días
        if (!checkL.isChecked() && !checkM.isChecked() &&!checkX.isChecked() &&!checkJ.isChecked() &&!checkV.isChecked() &&!checkS.isChecked() &&!checkD.isChecked() ) {
            textoAlarma = textoAlarma + "LMXJVSD";
        }

        return textoAlarma;

    }

    @Override
    public void onStop() {
        super.onStop();
        guardarAjustes();
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarAjustes();
    }
}
