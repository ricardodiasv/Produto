package com.example.produto;

import java.time.LocalDate;

public class Produto {
    private String codigo;
    private String nome;
    private String descricao;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private double precoCompra;
    private double precoVenda;
    private int quantidadeEstoque;
    private String categoria;

    public Produto(String codigo, String nome, String descricao,
                   LocalDate dataFabricacao, LocalDate dataValidade,
                   double precoCompra, double precoVenda,
                   int quantidadeEstoque, String categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}
