package com.tools.prova.model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final String ID = "002";

    private String cnpj;
    private String nome;
    private String ramoAtividade;

    public Cliente(String[] line) {
        this.cnpj = String.valueOf(line[1].trim());
        this.nome = String.valueOf(line[2].trim());
        this.ramoAtividade = String.valueOf(line[3].trim());
    }

    public static String getID() {
        return ID;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRamoAtividade() {
        return ramoAtividade;
    }

    public void setRamoAtividade(String ramoAtividade) {
        this.ramoAtividade = ramoAtividade;
    }
}

