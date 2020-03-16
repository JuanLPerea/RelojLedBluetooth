package com.e.relojledbluetooth.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.relojledbluetooth.Clases.Ajustes;
import com.e.relojledbluetooth.Clases.Animacion;
import com.e.relojledbluetooth.Clases.GuardarSharedPrefs;
import com.e.relojledbluetooth.MainActivity;
import com.e.relojledbluetooth.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageFragment4 extends Fragment implements View.OnClickListener {

    ViewGroup rootView;
    TableLayout table_base;
    LinearLayout row1, row2, row3, row4, row5, row6, row7, row8;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45, b46, b47, b48, b49, b50, b51, b52, b53, b54, b55, b56, b57, b58, b59, b60, b61, b62, b63, b64;
    ImageButton borrar, avanzar, retroceder, grabar, enviar;
    TextView numAnim;
    Animacion animacion;
    Ajustes ajustes;
    int numeroPantalla;
    int dataset[];
    int width, height;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = (ViewGroup)inflater.inflate(R.layout.page4, container, false);

        animacion = new Animacion();
        numeroPantalla = 0;

        // Cargar del Shared Preferences si existe alguna animación guardada y de paso todos los ajustes de la aplicación
        cargarAjustes();

        inicializarVistas();

        dibujarPantalla(0);
        
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apagarBotones();
            }
        });

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroPantalla ++;
                if (numeroPantalla == 12) numeroPantalla = 0;
                numAnim.setText((numeroPantalla + 1) + "");
                dibujarPantalla(numeroPantalla);
            }
        });

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroPantalla --;
                if (numeroPantalla == -1) numeroPantalla = 11;
                numAnim.setText((numeroPantalla + 1) + "");
                dibujarPantalla(numeroPantalla);
            }
        });

        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAnimaciones();
            }
        });

        return rootView;

    }

    private void menuAnimaciones() {
        PopupMenu popupMenu = new PopupMenu(getContext(), grabar);

        popupMenu.getMenuInflater().inflate(R.menu.grabar_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("Miapp" , "Click en " + item);
                switch (item.getItemId()) {
                    case R.id.cargar_anim:
                        // Cargar una animación guardada --------------------------------------
                        final Dialog cargarDialog = new Dialog(getContext());
                        cargarDialog.setContentView(R.layout.cargar_dialog);

                        ListView listView =cargarDialog.findViewById(R.id.lista_animacionesLV);

                        final List<Animacion> animaciones = ajustes.getAnimaciones();
                        List<String> nombresAnimaciones = new ArrayList<>();
                        for (Animacion animaTMP : animaciones) {
                            nombresAnimaciones.add(animaTMP.getNombre());
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, nombresAnimaciones);
                        listView.setAdapter(dataAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 dataset = animaciones.get(position).getLed();
                                 cargarDialog.dismiss();
                            }
                        });

                        cargarDialog.show();
                        break;

                // Guardar animación -------------------------------
                    case R.id.guardar_anim:

                        break;

                // Borrar una animación -----------------------------
                    case R.id.borrar_anim:
                        break;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void cargarAjustes() {
        ajustes = GuardarSharedPrefs.cargarDatos(getContext());
        if (ajustes == null) {
            ajustes = new Ajustes();
            Log.d("Miapp", "Ajustes creados");
        } else {
            // Por defecto cargamos la primera animación que haya guardada
            dataset = ajustes.getAnimaciones().get(0).getLed();
            Log.d("Miapp", "Ajustes cargados");
        }
    }

    private void guardarAjustes() {

        animacion = new Animacion(dataset , "Original");
        List listaAnimaciones = new ArrayList<>();
        listaAnimaciones.add(animacion);
        Animacion animacion1 = new Animacion(dataset, "Segunda");
        listaAnimaciones.add(animacion1);
        ajustes.setAnimaciones(listaAnimaciones);

        GuardarSharedPrefs.guardarDatos(getContext(), ajustes);

    }

    private void inicializarVistas() {
        
        borrar = rootView.findViewById(R.id.borrarBTN);
        avanzar = rootView.findViewById(R.id.avanzarBTN);
        retroceder = rootView.findViewById(R.id.retrocederBTN);
        grabar = rootView.findViewById(R.id.grabarBTN);
        enviar = rootView.findViewById(R.id.enviarBTN);
        
        numAnim = rootView.findViewById(R.id.num_animTV);
        
        table_base = rootView.findViewById(R.id.table_layout_base);

        row1 = rootView.findViewById(R.id.row_1);
        row2 = rootView.findViewById(R.id.row_2);
        row3 = rootView.findViewById(R.id.row_3);
        row4 = rootView.findViewById(R.id.row_4);
        row5 = rootView.findViewById(R.id.row_5);
        row6 = rootView.findViewById(R.id.row_6);
        row7 = rootView.findViewById(R.id.row_7);
        row8 = rootView.findViewById(R.id.row_8);


        b1 = rootView.findViewById(R.id.boton1);
        b2 = rootView.findViewById(R.id.boton2);
        b3 = rootView.findViewById(R.id.boton3);
        b4 = rootView.findViewById(R.id.boton4);
        b5 = rootView.findViewById(R.id.boton5);
        b6 = rootView.findViewById(R.id.boton6);
        b7 = rootView.findViewById(R.id.boton7);
        b8 = rootView.findViewById(R.id.boton8);
        b9 = rootView.findViewById(R.id.boton9);
        b10 = rootView.findViewById(R.id.boton10);
        b11 = rootView.findViewById(R.id.boton11);
        b12 = rootView.findViewById(R.id.boton12);
        b13 = rootView.findViewById(R.id.boton13);
        b14 = rootView.findViewById(R.id.boton14);
        b15 = rootView.findViewById(R.id.boton15);
        b16 = rootView.findViewById(R.id.boton16);
        b17 = rootView.findViewById(R.id.boton17);
        b18 = rootView.findViewById(R.id.boton18);
        b19 = rootView.findViewById(R.id.boton19);
        b20 = rootView.findViewById(R.id.boton20);
        b21 = rootView.findViewById(R.id.boton21);
        b22 = rootView.findViewById(R.id.boton22);
        b23 = rootView.findViewById(R.id.boton23);
        b24 = rootView.findViewById(R.id.boton24);
        b25 = rootView.findViewById(R.id.boton25);
        b26 = rootView.findViewById(R.id.boton26);
        b27 = rootView.findViewById(R.id.boton27);
        b28 = rootView.findViewById(R.id.boton28);
        b29 = rootView.findViewById(R.id.boton29);
        b30 = rootView.findViewById(R.id.boton30);
        b31 = rootView.findViewById(R.id.boton31);
        b32 = rootView.findViewById(R.id.boton32);
        b33 = rootView.findViewById(R.id.boton33);
        b34 = rootView.findViewById(R.id.boton34);
        b35 = rootView.findViewById(R.id.boton35);
        b36 = rootView.findViewById(R.id.boton36);
        b37 = rootView.findViewById(R.id.boton37);
        b38 = rootView.findViewById(R.id.boton38);
        b39 = rootView.findViewById(R.id.boton39);
        b40 = rootView.findViewById(R.id.boton40);
        b41 = rootView.findViewById(R.id.boton41);
        b42 = rootView.findViewById(R.id.boton42);
        b43 = rootView.findViewById(R.id.boton43);
        b44 = rootView.findViewById(R.id.boton44);
        b45 = rootView.findViewById(R.id.boton45);
        b46 = rootView.findViewById(R.id.boton46);
        b47 = rootView.findViewById(R.id.boton47);
        b48 = rootView.findViewById(R.id.boton48);
        b49 = rootView.findViewById(R.id.boton49);
        b50 = rootView.findViewById(R.id.boton50);
        b51 = rootView.findViewById(R.id.boton51);
        b52 = rootView.findViewById(R.id.boton52);
        b53 = rootView.findViewById(R.id.boton53);
        b54 = rootView.findViewById(R.id.boton54);
        b55 = rootView.findViewById(R.id.boton55);
        b56 = rootView.findViewById(R.id.boton56);
        b57 = rootView.findViewById(R.id.boton57);
        b58 = rootView.findViewById(R.id.boton58);
        b59 = rootView.findViewById(R.id.boton59);
        b60 = rootView.findViewById(R.id.boton60);
        b61 = rootView.findViewById(R.id.boton61);
        b62 = rootView.findViewById(R.id.boton62);
        b63 = rootView.findViewById(R.id.boton63);
        b64 = rootView.findViewById(R.id.boton64);


        DisplayMetrics metrics = new DisplayMetrics();
        ((MainActivity) getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
         width = metrics.widthPixels - (metrics.widthPixels / 3); // ancho absoluto en pixels
         height = metrics.heightPixels - (metrics.heightPixels / 3); // alto absoluto en pixels

        Log.d("Miapp", width + " x " + height);

        ViewGroup.LayoutParams params = table_base.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = width;
        params.width = width;
        table_base.setLayoutParams(params);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        b13.setOnClickListener(this);
        b14.setOnClickListener(this);
        b15.setOnClickListener(this);
        b16.setOnClickListener(this);
        b17.setOnClickListener(this);
        b18.setOnClickListener(this);
        b19.setOnClickListener(this);
        b20.setOnClickListener(this);
        b21.setOnClickListener(this);
        b22.setOnClickListener(this);
        b23.setOnClickListener(this);
        b24.setOnClickListener(this);
        b25.setOnClickListener(this);
        b26.setOnClickListener(this);
        b27.setOnClickListener(this);
        b28.setOnClickListener(this);
        b29.setOnClickListener(this);
        b30.setOnClickListener(this);
        b31.setOnClickListener(this);
        b32.setOnClickListener(this);
        b33.setOnClickListener(this);
        b34.setOnClickListener(this);
        b35.setOnClickListener(this);
        b36.setOnClickListener(this);
        b37.setOnClickListener(this);
        b38.setOnClickListener(this);
        b39.setOnClickListener(this);
        b40.setOnClickListener(this);
        b41.setOnClickListener(this);
        b42.setOnClickListener(this);
        b43.setOnClickListener(this);
        b44.setOnClickListener(this);
        b45.setOnClickListener(this);
        b46.setOnClickListener(this);
        b47.setOnClickListener(this);
        b48.setOnClickListener(this);
        b49.setOnClickListener(this);
        b50.setOnClickListener(this);
        b51.setOnClickListener(this);
        b52.setOnClickListener(this);
        b53.setOnClickListener(this);
        b54.setOnClickListener(this);
        b55.setOnClickListener(this);
        b56.setOnClickListener(this);
        b57.setOnClickListener(this);
        b58.setOnClickListener(this);
        b59.setOnClickListener(this);
        b60.setOnClickListener(this);
        b61.setOnClickListener(this);
        b62.setOnClickListener(this);
        b63.setOnClickListener(this);
        b64.setOnClickListener(this);


   }


    @Override
    public void onClick(View v) {
       Log.d("Miapp" ,  "Pulsado: " + v.getId());

        switch (v.getTag().toString()) {
            case "A":
                v.setBackgroundColor(Color.RED);
                v.setTag("R");
                break;
            case "R":
                v.setBackgroundColor(Color.GREEN);
                v.setTag("G");
                break;
            case "G":
                v.setBackgroundColor(Color.YELLOW);
                v.setTag("Y");
                break;
            case "Y":
                v.setBackgroundColor(Color.TRANSPARENT);
                v.setTag("A");
                break;
            default:
                break;
        }

        // Modificar el array de la animación en memoria
        guardarPantalla(numeroPantalla);


    }

    
    public void apagarBotones(){
        b1.setBackgroundColor(Color.TRANSPARENT);
        b2.setBackgroundColor(Color.TRANSPARENT);
        b3.setBackgroundColor(Color.TRANSPARENT);
        b4.setBackgroundColor(Color.TRANSPARENT);
        b5.setBackgroundColor(Color.TRANSPARENT);
        b6.setBackgroundColor(Color.TRANSPARENT);
        b7.setBackgroundColor(Color.TRANSPARENT);
        b8.setBackgroundColor(Color.TRANSPARENT);
        b9.setBackgroundColor(Color.TRANSPARENT);
        b10.setBackgroundColor(Color.TRANSPARENT);
        b11.setBackgroundColor(Color.TRANSPARENT);
        b12.setBackgroundColor(Color.TRANSPARENT);
        b13.setBackgroundColor(Color.TRANSPARENT);
        b14.setBackgroundColor(Color.TRANSPARENT);
        b15.setBackgroundColor(Color.TRANSPARENT);
        b16.setBackgroundColor(Color.TRANSPARENT);
        b17.setBackgroundColor(Color.TRANSPARENT);
        b18.setBackgroundColor(Color.TRANSPARENT);
        b19.setBackgroundColor(Color.TRANSPARENT);
        b20.setBackgroundColor(Color.TRANSPARENT);
        b21.setBackgroundColor(Color.TRANSPARENT);
        b22.setBackgroundColor(Color.TRANSPARENT);
        b23.setBackgroundColor(Color.TRANSPARENT);
        b24.setBackgroundColor(Color.TRANSPARENT);
        b25.setBackgroundColor(Color.TRANSPARENT);
        b26.setBackgroundColor(Color.TRANSPARENT);
        b27.setBackgroundColor(Color.TRANSPARENT);
        b28.setBackgroundColor(Color.TRANSPARENT);
        b29.setBackgroundColor(Color.TRANSPARENT);
        b30.setBackgroundColor(Color.TRANSPARENT);
        b31.setBackgroundColor(Color.TRANSPARENT);
        b32.setBackgroundColor(Color.TRANSPARENT);
        b33.setBackgroundColor(Color.TRANSPARENT);
        b34.setBackgroundColor(Color.TRANSPARENT);
        b35.setBackgroundColor(Color.TRANSPARENT);
        b36.setBackgroundColor(Color.TRANSPARENT);
        b37.setBackgroundColor(Color.TRANSPARENT);
        b38.setBackgroundColor(Color.TRANSPARENT);
        b39.setBackgroundColor(Color.TRANSPARENT);
        b40.setBackgroundColor(Color.TRANSPARENT);
        b41.setBackgroundColor(Color.TRANSPARENT);
        b42.setBackgroundColor(Color.TRANSPARENT);
        b43.setBackgroundColor(Color.TRANSPARENT);
        b44.setBackgroundColor(Color.TRANSPARENT);
        b45.setBackgroundColor(Color.TRANSPARENT);
        b46.setBackgroundColor(Color.TRANSPARENT);
        b47.setBackgroundColor(Color.TRANSPARENT);
        b48.setBackgroundColor(Color.TRANSPARENT);
        b49.setBackgroundColor(Color.TRANSPARENT);
        b50.setBackgroundColor(Color.TRANSPARENT);
        b51.setBackgroundColor(Color.TRANSPARENT);
        b52.setBackgroundColor(Color.TRANSPARENT);
        b53.setBackgroundColor(Color.TRANSPARENT);
        b54.setBackgroundColor(Color.TRANSPARENT);
        b55.setBackgroundColor(Color.TRANSPARENT);
        b56.setBackgroundColor(Color.TRANSPARENT);
        b57.setBackgroundColor(Color.TRANSPARENT);
        b58.setBackgroundColor(Color.TRANSPARENT);
        b59.setBackgroundColor(Color.TRANSPARENT);
        b60.setBackgroundColor(Color.TRANSPARENT);
        b61.setBackgroundColor(Color.TRANSPARENT);
        b62.setBackgroundColor(Color.TRANSPARENT);
        b63.setBackgroundColor(Color.TRANSPARENT);
        b64.setBackgroundColor(Color.TRANSPARENT);

        b1.setTag("A");
        b2.setTag("A");
        b3.setTag("A");
        b4.setTag("A");
        b5.setTag("A");
        b6.setTag("A");
        b7.setTag("A");
        b8.setTag("A");
        b9.setTag("A");
        b10.setTag("A");
        b11.setTag("A");
        b12.setTag("A");
        b13.setTag("A");
        b14.setTag("A");
        b15.setTag("A");
        b16.setTag("A");
        b17.setTag("A");
        b18.setTag("A");
        b19.setTag("A");
        b20.setTag("A");
        b21.setTag("A");
        b22.setTag("A");
        b23.setTag("A");
        b24.setTag("A");
        b25.setTag("A");
        b26.setTag("A");
        b27.setTag("A");
        b28.setTag("A");
        b29.setTag("A");
        b30.setTag("A");
        b31.setTag("A");
        b32.setTag("A");
        b33.setTag("A");
        b34.setTag("A");
        b35.setTag("A");
        b36.setTag("A");
        b37.setTag("A");
        b38.setTag("A");
        b39.setTag("A");
        b40.setTag("A");
        b41.setTag("A");
        b42.setTag("A");
        b43.setTag("A");
        b44.setTag("A");
        b45.setTag("A");
        b46.setTag("A");
        b47.setTag("A");
        b48.setTag("A");
        b49.setTag("A");
        b50.setTag("A");
        b51.setTag("A");
        b52.setTag("A");
        b53.setTag("A");
        b54.setTag("A");
        b55.setTag("A");
        b56.setTag("A");
        b57.setTag("A");
        b58.setTag("A");
        b59.setTag("A");
        b60.setTag("A");
        b61.setTag("A");
        b62.setTag("A");
        b63.setTag("A");
        b64.setTag("A");

    }

    public void dibujarPantalla(int pantalla) {

        // cada pantalla son 16 enteros 8 para el rojo y 8 para el verde
        int numeroLed = 0;
        int matriztemporal[] = new int[65];

        // cargar los datos de color rojo
        for (int posicion = pantalla * 8; posicion < ((pantalla * 8) + 8); posicion++) {
            // convertir en binario cada entero y mirar si es un 0 o 1 para poner el color que corresponda

            for (int posicionBinario = 0; posicionBinario < 8; posicionBinario++) {

                String numeroEnBinario = obtenerBinario(dataset[posicion]);
                numeroLed++;


                 if (numeroEnBinario.charAt(posicionBinario) == '1') {
                     // Esta posición tiene el led rojo encendido
                     pintarLed( numeroLed, 1);
                     matriztemporal[numeroLed] = 1;
                 } else {
                     pintarLed( numeroLed, 0);
                     matriztemporal[numeroLed] = 0;
                 }
            }
        }

        // cargar los datos de color verde
        // Si se solapa alguno con el rojo detectarlo para pintar en color amarillo
        numeroLed = 0;
        // En este bucle vamos recuperando los valores
        for (int posicion = ((pantalla * 8) + 96); posicion < ((pantalla * 8) + 104); posicion++) {

        //    Log.d("Miapp" ,  " Numero en Binario: " + obtenerBinario(dataset[posicion]));

            // convertir en binario cada entero y mirar si es un 0 o 1 para poner el color que corresponda
            for (int posicionBinario = 0; posicionBinario < 8; posicionBinario++) {

                String numeroEnBinario = obtenerBinario(dataset[posicion]);
                numeroLed++;

                if (numeroEnBinario.charAt(posicionBinario) == '1') {
                    // Esta posición tiene el led verde encendido
                    // Comprobar si tiene el rojo también encendido,
                    // si es así pintarlo de amarillo
                    if (matriztemporal[numeroLed] == 1) {
                        // Pintar de amarillo
                        pintarLed( numeroLed, 3);
                        matriztemporal[numeroLed] = 3;
                    } else {
                        // Pintar de verde
                        pintarLed( numeroLed, 2);
                        matriztemporal[numeroLed] = 2;
                    }
                }
            }
        }
    }

    private void pintarLed(int posicion, int color) {

        int miColor = Color.TRANSPARENT;
        String etiqueta = "";

        switch (color) {
            case 0:
                miColor = Color.TRANSPARENT;
                etiqueta = "A";
                break;
            case 1:
                miColor = Color.RED;
                etiqueta = "R";
                break;
            case 2:
                miColor = Color.GREEN;
                etiqueta = "G";
                break;
            case 3:
                miColor = Color.YELLOW;
                etiqueta = "Y";
                break;
        }


        switch (posicion -1) {
            case 0:
                b1.setBackgroundColor(miColor);
                b1.setTag(etiqueta);
                break;
            case 1:
                b2.setBackgroundColor(miColor);
                b2.setTag(etiqueta);
                break;
            case 2:
                b3.setBackgroundColor(miColor);
                b3.setTag(etiqueta);
                break;
            case 3:
                b4.setBackgroundColor(miColor);
                b4.setTag(etiqueta);
                break;
            case 4:
                b5.setBackgroundColor(miColor);
                b5.setTag(etiqueta);
                break;
            case 5:
                b6.setBackgroundColor(miColor);
                b6.setTag(etiqueta);
                break;
            case 6:
                b7.setBackgroundColor(miColor);
                b7.setTag(etiqueta);
                break;
            case 7:
                b8.setBackgroundColor(miColor);
                b8.setTag(etiqueta);
                break;
            case 8:
                b9.setBackgroundColor(miColor);
                b9.setTag(etiqueta);
                break;
            case 9:
                b10.setBackgroundColor(miColor);
                b10.setTag(etiqueta);
                break;
            case 10:
                b11.setBackgroundColor(miColor);
                b11.setTag(etiqueta);
                break;
            case 11:
                b12.setBackgroundColor(miColor);
                b12.setTag(etiqueta);
                break;
            case 12:
                b13.setBackgroundColor(miColor);
                b13.setTag(etiqueta);
                break;
            case 13:
                b14.setBackgroundColor(miColor);
                b14.setTag(etiqueta);
                break;
            case 14:
                b15.setBackgroundColor(miColor);
                b15.setTag(etiqueta);
                break;
            case 15:
                b16.setBackgroundColor(miColor);
                b16.setTag(etiqueta);
                break;
            case 16:
                b17.setBackgroundColor(miColor);
                b17.setTag(etiqueta);
                break;
            case 17:
                b18.setBackgroundColor(miColor);
                b18.setTag(etiqueta);
                break;
            case 18:
                b19.setBackgroundColor(miColor);
                b19.setTag(etiqueta);
                break;
            case 19:
                b20.setBackgroundColor(miColor);
                b20.setTag(etiqueta);
                break;
            case 20:
                b21.setBackgroundColor(miColor);
                b21.setTag(etiqueta);
                break;
            case 21:
                b22.setBackgroundColor(miColor);
                b22.setTag(etiqueta);
                break;
            case 22:
                b23.setBackgroundColor(miColor);
                b23.setTag(etiqueta);
                break;
            case 23:
                b24.setBackgroundColor(miColor);
                b24.setTag(etiqueta);
                break;
            case 24:
                b25.setBackgroundColor(miColor);
                b25.setTag(etiqueta);
                break;
            case 25:
                b26.setBackgroundColor(miColor);
                b26.setTag(etiqueta);
                break;
            case 26:
                b27.setBackgroundColor(miColor);
                b27.setTag(etiqueta);
                break;
            case 27:
                b28.setBackgroundColor(miColor);
                b28.setTag(etiqueta);
                break;
            case 28:
                b29.setBackgroundColor(miColor);
                b29.setTag(etiqueta);
                break;
            case 29:
                b30.setBackgroundColor(miColor);
                b30.setTag(etiqueta);
                break;
            case 30:
                b31.setBackgroundColor(miColor);
                b31.setTag(etiqueta);
                break;

            case 31:
                b32.setBackgroundColor(miColor);
                b32.setTag(etiqueta);
                break;
            case 32:
                b33.setBackgroundColor(miColor);
                b33.setTag(etiqueta);
                break;
            case 33:
                b34.setBackgroundColor(miColor);
                b34.setTag(etiqueta);
                break;
            case 34:
                b35.setBackgroundColor(miColor);
                b35.setTag(etiqueta);
                break;
            case 35:
                b36.setBackgroundColor(miColor);
                b36.setTag(etiqueta);
                break;
            case 36:
                b37.setBackgroundColor(miColor);
                b37.setTag(etiqueta);
                break;
            case 37:
                b38.setBackgroundColor(miColor);
                b38.setTag(etiqueta);
                break;
            case 38:
                b39.setBackgroundColor(miColor);
                b39.setTag(etiqueta);
                break;
            case 39:
                b40.setBackgroundColor(miColor);
                b40.setTag(etiqueta);
                break;
            case 40:
                b41.setBackgroundColor(miColor);
                b41.setTag(etiqueta);
                break;

            case 41:
                b42.setBackgroundColor(miColor);
                b42.setTag(etiqueta);
                break;
            case 42:
                b43.setBackgroundColor(miColor);
                b43.setTag(etiqueta);
                break;
            case 43:
                b44.setBackgroundColor(miColor);
                b44.setTag(etiqueta);
                break;
            case 44:
                b45.setBackgroundColor(miColor);
                b45.setTag(etiqueta);
                break;
            case 45:
                b46.setBackgroundColor(miColor);
                b46.setTag(etiqueta);
                break;
            case 46:
                b47.setBackgroundColor(miColor);
                b47.setTag(etiqueta);
                break;
            case 47:
                b48.setBackgroundColor(miColor);
                b48.setTag(etiqueta);
                break;
            case 48:
                b49.setBackgroundColor(miColor);
                b49.setTag(etiqueta);
                break;
            case 49:
                b50.setBackgroundColor(miColor);
                b50.setTag(etiqueta);
                break;
            case 50:
                b51.setBackgroundColor(miColor);
                b51.setTag(etiqueta);
                break;

            case 51:
                b52.setBackgroundColor(miColor);
                b52.setTag(etiqueta);
                break;
            case 52:
                b53.setBackgroundColor(miColor);
                b53.setTag(etiqueta);
                break;
            case 53:
                b54.setBackgroundColor(miColor);
                b54.setTag(etiqueta);
                break;
            case 54:
                b55.setBackgroundColor(miColor);
                b55.setTag(etiqueta);
                break;
            case 55:
                b56.setBackgroundColor(miColor);
                b56.setTag(etiqueta);
                break;
            case 56:
                b57.setBackgroundColor(miColor);
                b57.setTag(etiqueta);
                break;
            case 57:
                b58.setBackgroundColor(miColor);
                b58.setTag(etiqueta);
                break;
            case 58:
                b59.setBackgroundColor(miColor);
                b59.setTag(etiqueta);
                break;
            case 59:
                b60.setBackgroundColor(miColor);
                b60.setTag(etiqueta);
                break;
            case 60:
                b61.setBackgroundColor(miColor);
                b61.setTag(etiqueta);
                break;

            case 61:
                b62.setBackgroundColor(miColor);
                b62.setTag(etiqueta);
                break;
            case 62:
                b63.setBackgroundColor(miColor);
                b63.setTag(etiqueta);
                break;
            case 63:
                b64.setBackgroundColor(miColor);
                b64.setTag(etiqueta);
                break;



        }
    }


    private String obtenerBinario(int numero){
       // Log.d("Miapp", "Numero: " + numero);

        ArrayList<String> binario = new ArrayList<String>();
        int resto;
        String binarioString = "";

        do{
            resto = numero%2;
            numero = numero/2;
            binario.add(0, Integer.toString(resto));
        }while(numero > 2); //Haremos el bucle hasta que el cociente no se pueda dividir mas

        binario.add(0, Integer.toString(numero)); //Cogeremos el ultimo cociente

        //Cogemos cada uno de los elementos del ArrayList y los juntamos en un String
        for(int i = 0; i < binario.size(); i++){
            binarioString += binario.get(i);
        }

        int faltanNceros = 8 - binarioString.length();
        if (faltanNceros > 0) {
            for (int n = 0 ; n < faltanNceros; n++ ) {
                binarioString = "0" + binarioString;
            }
        }

      //  Log.d("Miapp",  " Binario: " + binarioString);
        return binarioString;
    }

    private void guardarPantalla(int numPant) {
        /*
         TODO traducir la pantalla actual a 8 binarios x 2 colores (rojo y verde)
           convertir estos binarios a enteros
           y guardar en el array de datos estos números en su posición
        */

    }



}


