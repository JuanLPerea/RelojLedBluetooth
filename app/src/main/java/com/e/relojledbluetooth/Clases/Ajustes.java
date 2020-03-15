package com.e.relojledbluetooth.Clases;

import java.util.List;

public class Ajustes {

    private List<Animacion> animaciones;
    private int brillo;
    private int apagarEnSeg;
    private int modoReloj;
    private String alarma;
    private boolean tocarHoras;

    public Ajustes(List<Animacion> animaciones, int brillo, int apagarEnSeg, int modoReloj, String alarma, boolean tocarHoras) {
        this.animaciones = animaciones;
        this.brillo = brillo;
        this.apagarEnSeg = apagarEnSeg;
        this.modoReloj = modoReloj;
        this.alarma = alarma;
        this.tocarHoras = tocarHoras;
    }

    public Ajustes() {
    }

    public List<Animacion> getAnimaciones() {
        return animaciones;
    }

    public void setAnimaciones(List<Animacion> animaciones) {
        this.animaciones = animaciones;
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
