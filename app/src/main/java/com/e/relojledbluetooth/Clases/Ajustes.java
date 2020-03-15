package com.e.relojledbluetooth.Clases;

public class Ajustes {

    private Animacion animacion;
    private int brillo;
    private int apagarEnSeg;
    private int modoReloj;
    private String alarma;
    private boolean tocarHoras;

    public Ajustes(Animacion animacion, int brillo, int apagarEnSeg, int modoReloj, String alarma, boolean tocarHoras) {
        this.animacion = animacion;
        this.brillo = brillo;
        this.apagarEnSeg = apagarEnSeg;
        this.modoReloj = modoReloj;
        this.alarma = alarma;
        this.tocarHoras = tocarHoras;
    }

    public Ajustes() {
    }

    public Animacion getAnimacion() {
        return animacion;
    }

    public void setAnimacion(Animacion animacion) {
        this.animacion = animacion;
    }

    public int getBrillo() {
        return brillo;
    }

    public void setBrillo(int brillo) {
        this.brillo = brillo;
    }

    public int getApagarEnSeg() {
        return apagarEnSeg;
    }

    public void setApagarEnSeg(int apagarEnSeg) {
        this.apagarEnSeg = apagarEnSeg;
    }

    public int getModoReloj() {
        return modoReloj;
    }

    public void setModoReloj(int modoReloj) {
        this.modoReloj = modoReloj;
    }

    public String getAlarma() {
        return alarma;
    }

    public void setAlarma(String alarma) {
        this.alarma = alarma;
    }

    public boolean isTocarHoras() {
        return tocarHoras;
    }

    public void setTocarHoras(boolean tocarHoras) {
        this.tocarHoras = tocarHoras;
    }
}
