package com.e.relojledbluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.e.relojledbluetooth.Fragments.PageFragment1;
import com.e.relojledbluetooth.Fragments.PageFragment2;
import com.e.relojledbluetooth.Fragments.PageFragment3;
import com.e.relojledbluetooth.Fragments.PageFragment4;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ListView dispositivos;
    private BluetoothUtils bluetooth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment1());
        list.add(new PageFragment2());
        list.add(new PageFragment3());
        list.add(new PageFragment4());

        pager = findViewById(R.id.pager);
        pagerAdapter = new SlidePageAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(pagerAdapter);

        //Creamos un objeto BluetoothUtils
        //para simplificar us uso
        bluetooth = new BluetoothUtils();

        //Obtenemos la lista de elementos
        dispositivos = findViewById(R.id.dispositivos);

        //Obtenemos los nombres de los dispositivos
        //bluetooth vinculados
        String[] nombres = bluetooth.getNames();

        //Asignamos los nombres a la lista
        dispositivos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombres));

        //Leemos los "clicks" sobre los elementos de
        //la lista
        dispositivos.setOnItemClickListener(this);

    }

    /**
     * Cuando cerramos la app desconectamos
     */
    protected void onPause() {
        super.onPause();
        bluetooth.disconnect();
    }

    /**
     * Método que se ejecutará cuando se pulse sobre un elemento de la
     * lista. Index indicará el número del elemento pulsado.
     */
    public void onItemClick(AdapterView<?> ag, View v, int index, long id) {
        //Conectamos con el elemento pulsado
        if (bluetooth.connect(index)){
            Toast.makeText(this, R.string.conectado, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void enviarComandoBluetooth (String comando) {
        //Si no estamos conectados, terminamos
        if (bluetooth.isConnected() == false) {
            Toast.makeText(this, R.string.primero_conectar, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("Miapp" , "Dato enviado " + comando);

        bluetooth.sendString(comando);
    }


}
