package com.example.produto;

import javafx.beans.property.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Produto {
    private final StringProperty codigo;
    private final StringProperty nome;
    private final ObjectProperty<LocalDate> dataFabricacao;
    private final ObjectProperty<LocalDate> dataValidade;
    private final ObjectProperty<BigDecimal> precoCompra;
    private final ObjectProperty<BigDecimal> precoVenda;
    private final IntegerProperty quantidadeEstoque;

    public Produto(String codigo, String nome, LocalDate dataFabricacao, LocalDate dataValidade,
                   BigDecimal precoCompra, BigDecimal precoVenda, int quantidadeEstoque) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.dataFabricacao = new SimpleObjectProperty<>(dataFabricacao);
        this.dataValidade = new SimpleObjectProperty<>(dataValidade);
        this.precoCompra = new SimpleObjectProperty<>(precoCompra);
        this.precoVenda = new SimpleObjectProperty<>(precoVenda);
        this.quantidadeEstoque = new SimpleIntegerProperty(quantidadeEstoque);
    }

    public StringProperty codigoProperty() { return codigo; }
    public StringProperty nomeProperty() { return nome; }
    public ObjectProperty<LocalDate> dataFabricacaoProperty() { return dataFabricacao; }
    public ObjectProperty<LocalDate> dataValidadeProperty() { return dataValidade; }
    public ObjectProperty<BigDecimal> precoCompraProperty() { return precoCompra; }
    public ObjectProperty<BigDecimal> precoVendaProperty() { return precoVenda; }
    public IntegerProperty quantidadeEstoqueProperty() { return quantidadeEstoque; }

    @Override
    public String toString() {
        return String.format("%s - %s (Estoque: %d, Preço: R$ %.2f)", codigo.get(), nome.get(), quantidadeEstoque.get(), precoVenda.get().doubleValue());
    }
}
