package com.tools.prova.model;

import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

public class Vendedor implements Serializable {
    private static final String ID = "001";
    private String cpf;
    private String nome;
    private BigDecimal salario;

    public Vendedor(@NotEmpty String[] line) {
        this.cpf = String.valueOf(line[1].trim());
        this.nome = String.valueOf(line[2].trim());
        this.salario = new BigDecimal(line[3].trim());
    }

    public static String getID() {
        return ID;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
