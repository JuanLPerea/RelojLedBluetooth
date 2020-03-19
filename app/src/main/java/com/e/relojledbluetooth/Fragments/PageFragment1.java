package com.e.relojledbluetooth.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.relojledbluetooth.Clases.Ajustes;
import com.e.relojledbluetooth.Clases.GuardarSharedPrefs;
import com.e.relojledbluetooth.MainActivity;
import com.e.relojledbluetooth.R;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PageFragment1 extends Fragment {

    private Timer myTimer;
    private TextView horaTV, tiempoApagarTV;
    private String hora_txt;
    private Button ponerEnHora;
    private ImageButton  aumentarTiempo, disminuirTiempo;
    private int tiempoApagar, brillo;
    private SeekBar brilloBar;
    private Ajustes ajustes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page1, container, false);
        horaTV = rootView.findViewById(R.id.hora_actual_txt);
        ponerEnHora = rootView.findViewById(R.id.poner_en_horaBTN);
        aumentarTiempo = rootView.findViewById(R.id.aumentarBTN);
        disminuirTiempo = rootView.findViewById(R.id.disminuirBTN);
        tiempoApagarTV = rootView.findViewById(R.id.apagar_cada);
        brilloBar = rootView.findViewById(R.id.seekBrillo);

        // Cargamos los ajustes del Shared Preferences
        cargarAjustes();

        // Listeners para los controles
        ponerEnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).enviarComandoBluetooth("#H#" + hora_txt);
                guardarAjustes();
            }
        });

        aumentarTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiempoApagar += 5;
                if (tiempoApagar % 5 != 0) tiempoApagar = 0;
                if (tiempoApagar > 60) tiempoApagar = 0;
                tiempoApagarTV.setText(tiempoApagar + " Seg.");
                ((MainActivity) getActivity()).enviarComandoBluetooth("#A#" + tiempoApagar);
                guardarAjustes();
            }
        });

        disminuirTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiempoApagar -= 5;
                if (tiempoApagar % 5 != 0) tiempoApagar = 0;
                if (tiempoApagar < 0) tiempoApagar = 60;
                tiempoApagarTV.setText(tiempoApagar + " Seg.");
                ((MainActivity) getActivity()).enviarComandoBluetooth("#A#" + tiempoApagar);
                guardarAjustes();
            }
        });

        brilloBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                brillo = (int) (brilloBar.getProgress()/ 6.66666666667);
                ((MainActivity) getActivity()).enviarComandoBluetooth("#B#" + brillo);
                // Log.d("Miapp", brillo + "");
                guardarAjustes();
            }
        });

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);

        return rootView;
    }

    private void cargarAjustes() {
        ajustes = GuardarSharedPrefs.cargarDatos(getContext());
        tiempoApagar = ajustes.getApagarEnSeg();
        brillo = ajustes.getBrillo();
        tiempoApagarTV.setText(tiempoApagar + "");
        brilloBar.setProgress((int)(brillo * 6.66666666667));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      //  TabLayout tabLayout = view.findViewById(R.id.tab_layout);
     //   tabLayout.setupWithViewPager();

    }


    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
          getActivity().runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here
            // Recuperamos la hora del sistema
            Date hora = new Date();
            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss <> d/M/yyyy");
            DateFormat hourFormatBluetooth = new SimpleDateFormat("HH#mm#ss#d#M#yyyy#u");

            String mostrarHora = hourFormat.format(hora);
            hora_txt = hourFormatBluetooth.format(hora);
            mostrarHora = hourFormat.format((hora));
            horaTV.setText(mostrarHora);
            // // Log.d("Miapp" , "Son las: " + hora_txt);

        }
    };

    @Override
    public void onStop() {
        super.onStop();
        myTimer.cancel();
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().runOnUiThread(Timer_Tick);
        cargarAjustes();
        // Log.d("Miapp", "Fragment 1 reanudado");

    }


    private void guardarAjustes() {
        ajustes.setBrillo(brillo);
        ajustes.setApagarEnSeg(tiempoApagar);
        GuardarSharedPrefs.guardarDatos(getContext(), ajustes);
    }

    @Override
    public void onPause() {
        super.onPause();
        guardarAjustes();
    }


}
