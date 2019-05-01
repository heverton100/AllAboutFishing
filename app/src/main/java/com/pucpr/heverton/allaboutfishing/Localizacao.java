package com.pucpr.heverton.allaboutfishing;

public class Localizacao {

    Integer idL;
    String cepL;
    String cidadeL;
    String estadoL;
    String latitudeL;
    String longitudeL;


    public Localizacao(Integer idL, String cepL, String cidadeL, String estadoL, String latitudeL, String longitudeL) {
        this.idL = idL;
        this.cepL = cepL;
        this.cidadeL = cidadeL;
        this.estadoL = estadoL;
        this.latitudeL = latitudeL;
        this.longitudeL = longitudeL;
    }

    public Integer getIdL() {
        return idL;
    }

    public void setIdL(Integer idL) {
        this.idL = idL;
    }

    public String getCepL() {
        return cepL;
    }

    public void setCepL(String cepL) {
        this.cepL = cepL;
    }

    public String getCidadeL() {
        return cidadeL;
    }

    public void setCidadeL(String cidadeL) {
        this.cidadeL = cidadeL;
    }

    public String getEstadoL() {
        return estadoL;
    }

    public void setEstadoL(String estadoL) {
        this.estadoL = estadoL;
    }

    public String getLatitudeL() {
        return latitudeL;
    }

    public void setLatitudeL(String latitudeL) {
        this.latitudeL = latitudeL;
    }

    public String getLongitudeL() {
        return longitudeL;
    }

    public void setLongitudeL(String longitudeL) {
        this.longitudeL = longitudeL;
    }
}
