package com.pucpr.heverton.allaboutfishing;

public class Clima {

    private String cidade;
    private String estado;
    private String pais;
    private Integer temperatura;
    private Integer umidade;
    private String descricao;

    public Clima(String cidade, String estado, String pais, Integer temperatura, Integer umidade, String descricao) {
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.descricao = descricao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getUmidade() {
        return umidade;
    }

    public void setUmidade(Integer umidade) {
        this.umidade = umidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
