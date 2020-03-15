package com.e.relojledbluetooth.Clases;

import android.content.Context;
import android.content.SharedPreferences;

import com.e.relojledbluetooth.MainActivity;

public class GuardarSharedPrefs {


    public static Ajustes cargarDatos(Context context) {

        Ajustes ajustesGuardados = new Ajustes();

        SharedPreferences fichero = context.getSharedPreferences("AJUSTES", Context.MODE_PRIVATE);
       // SharedPreferences.Editor editor = fichero.edit();

        ajustesGuardados.setModoReloj(fichero.getInt("MODORELOJ" , 0));
        ajustesGuardados.setAlarma(fichero.getString("ALARMA" , ""));





        return ajustesGuardados;
    }


}
