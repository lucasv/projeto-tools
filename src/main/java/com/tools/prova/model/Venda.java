package com.tools.prova.model;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

public class Venda implements Serializable {
    private static final String ID = "003";

    private Integer idVenda;
    private Integer idItem;
    private Integer quantidadeItem;
    private BigDecimal valorItem;
    private String nomeVendedor;
    private BigDecimal valorTotal;

    public Venda(@NotEmpty String[] line) {
        this.idVenda = Integer.valueOf(line[1]);
        this.idItem = Integer.valueOf(line[2]);
        this.quantidadeItem = Integer.valueOf(line[3]);
        this.valorItem = new BigDecimal(line[4]);
        this.nomeVendedor = String.valueOf(line[5]);
        this.valorTotal = this.valorItem.multiply(BigDecimal.valueOf(this.quantidadeItem));
    }

    public static String getID() {
        return ID;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(Integer quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public BigDecimal getValorItem() {
        return valorItem;
    }

    public void setValorItem(BigDecimal valorItem) {
        this.valorItem = valorItem;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
