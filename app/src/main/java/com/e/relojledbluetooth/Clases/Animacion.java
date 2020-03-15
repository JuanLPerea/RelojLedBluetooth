package com.e.relojledbluetooth.Clases;

public class Animacion {

    /*
        Cada animación está compuesta por 12 x 2 'pantallas' (Una para el rojo y otra para el verde)
        cada pantalla tiene 8 bytes (cada fila de la matriz de leds es un byte (8 filas de 8 leds cada una))
        cada byte se representa por un entero de 0 a 255 que después convertido en binario formará el dibujo en la matriz de leds.

        Aquí guardamos la animación como un array de enteros (192 enteros cada animación)
        96 enteros por color (2 colores)
        8 enteros cada pantalla (12 pantallas)

        Después podemos guardar las animaciones en Shared Preferences
        o enviarlo a través de bluetooth al reloj

     */

    private int led[];
    private boolean esNuevo;
    private String nombre;

    public Animacion(int[] led, String nombre) {
        this.led = led;
        this.esNuevo = false;
        this.nombre = nombre;
    }

    public Animacion() {
        this.esNuevo = true;
        this.led = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        this.nombre = "Nuevo";
    }

    public int[] getLed() {
        return led;
    }

    public void setLed(int[] led) {
        this.led = led;
    }


    public boolean EsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public void modificarLed(int indice, int color) {
        led[indice] = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
