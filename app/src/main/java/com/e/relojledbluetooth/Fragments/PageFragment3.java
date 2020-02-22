package com.e.relojledbluetooth.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.relojledbluetooth.R;

public class PageFragment3 extends Fragment {

    ImageButton aumentarIV, disminuirIV;
    TextView horasTV, minutosTV;
    int minutosAlarma = 0, horaAlarma = 0;
    int seleccionado = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page3, container, false);

        aumentarIV = rootView.findViewById(R.id.aumentarBTN);
        disminuirIV = rootView.findViewById(R.id.disminuirBTN);
        horasTV = rootView.findViewById(R.id.horaAlarmaTV);
        minutosTV = rootView.findViewById(R.id.minutosAlarmaTV);

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


        return rootView;

    }


}
