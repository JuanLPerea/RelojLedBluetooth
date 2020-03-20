package com.e.relojledbluetooth.Clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuardarSharedPrefs {

    public static Ajustes cargarDatos(Context context) {

        Ajustes ajustesGuardados = null;

        SharedPreferences fichero = context.getSharedPreferences("AJUSTES", Context.MODE_PRIVATE);

        if (fichero == null) {
            Log.d("MiApp", "No existen ajustes guardados");
        } else {

            ajustesGuardados = new Ajustes();

            ajustesGuardados.setModoReloj(fichero.getInt("MODORELOJ" , 0));
            ajustesGuardados.setApagarEnSeg(fichero.getInt("APAGARSEG" , 0));
            ajustesGuardados.setBrillo(fichero.getInt("BRILLO" , 0));
            ajustesGuardados.setAlarma(fichero.getString("ALARMA" , ""));
            ajustesGuardados.setTocarHoras(fichero.getBoolean("TOCARHORAS" , true));

            // Las animaciones las guardamos como Strings separadas por el caracter '#'
            List<String> listaStringsAnimaciones = new ArrayList<>();
            String strTemp = "";
            strTemp = fichero.getString("ANIMACION", "");
            listaStringsAnimaciones = Arrays.asList(strTemp.split("\\s*#\\s*"));

            // Construimos una lista de objetos tipo Animación
            // Haciendo un split en cada elemento de la lista anterior
            // el primer elemento es el nombre de la animación
            // los datos están separados por comas ',' los separamos mediante otro split
            Animacion animacionTMP = null;
            List<Animacion> listaAnimaciones = new ArrayList<>();
            List<String> listaDatos = null;


            if (listaStringsAnimaciones.size() > 1) {
                for (String stringAnimTMP : listaStringsAnimaciones) {
                    int[] valoresAnimacion = new int[192];
                    listaDatos = new ArrayList<>();
                    listaDatos = Arrays.asList(stringAnimTMP.split("\\s*,\\s*"));
                    animacionTMP = new Animacion();
                    animacionTMP.setNombre(listaDatos.get(0));
                    // Empezamos en la posición 1 del array que es el primer dato, quitamos 1 en el indice para compensar que el primer dato es el nombre
                    for (int posicion = 1; posicion < listaDatos.size() ; posicion++) {
                        valoresAnimacion[posicion-1] = Integer.parseInt(listaDatos.get(posicion));
                    }
                    animacionTMP.setLed(valoresAnimacion);
                    animacionTMP.setEsNuevo(false);
                    listaAnimaciones.add(animacionTMP);
                }

                ajustesGuardados.setAnimaciones(listaAnimaciones);
            } else {
                animacionTMP = new Animacion();
                animacionTMP.setNombre("Original");
                int dataset[] = {3,6,12,24,48,96,192,96 , 6,12,24,48,96,192,96,48 ,
                        12,24,48,96,192,96,48,0 ,24,48,96,192,96,48,0,12 ,
                        48,96,192,96,48,0,12,6 ,96,192,96,48,0,12,6,3 ,
                        192,96,48,0,12,6,3,6 , 96,48,0,12,6,3,6,12 ,
                        48,0,12,6,3,6,12,24 , 0,12,6,3,6,12,24,48 ,
                        12,6,3,6,12,24,48,96 , 6,3,6,12,24,48,96,192 ,
                        192,96,48,0,12,6,3,6 , 96,48,0,12,6,3,6,12 ,
                        48,0,12,6,3,6,12,24 , 0,12,6,3,6,12,24,48 ,
                        12,6,3,6,12,24,48,96 , 6,3,6,12,24,48,96,192 ,
                        3,6,12,24,48,96,192,96 , 6,12,24,48,96,192,96,48 ,
                        12,24,48,96,192,96,48,0 , 24,48,96,192,96,48,0,12 ,
                        48,96,192,96,48,0,12,6 , 96,192,96,48,0,12,6,3 };

                animacionTMP.setLed( dataset);
                animacionTMP.setEsNuevo(false);
                listaAnimaciones.add(animacionTMP);
                ajustesGuardados.setAnimaciones(listaAnimaciones);
            }

        }

        return ajustesGuardados;
    }

    public static void guardarDatos(Context context, Ajustes ajustes) {
        SharedPreferences fichero = context.getSharedPreferences("AJUSTES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fichero.edit();

        editor.putInt("MODORELOJ", ajustes.getModoReloj());
        editor.putInt("APAGARSEG", ajustes.getApagarEnSeg());
        editor.putInt("BRILLO", ajustes.getBrillo());
        editor.putString("ALARMA", ajustes.getAlarma());
        editor.putBoolean("TOCARHORAS", ajustes.isTocarHoras());

        // Construimos un string muuuuy largo con las animaciones
        String animaciones = "";
        for (Animacion animTMP : ajustes.getAnimaciones()) {
            animaciones += animTMP.getNombre();
            for (int valor : animTMP.getLed()) {
                animaciones += ",";
                animaciones += valor;
            }
            animaciones += "#";
        }

        editor.putString("ANIMACION", animaciones);
      //  Log.d("Miapp" , "Animaciones" + animaciones);
        editor.commit();

    }


}
