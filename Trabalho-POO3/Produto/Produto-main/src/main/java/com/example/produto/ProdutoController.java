package com.example.produto;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdutoController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtDataFabricacao;
    @FXML private TextField txtDataValidade;
    @FXML private TextField txtPrecoCompra;
    @FXML private TextField txtPrecoVenda;
    @FXML private TextField txtQuantidadeEstoque;
    @FXML private TextField txtCategoria;

    @FXML private TableView<Produto> tableView;
    @FXML private TableColumn<Produto, String> lblCodigo;
    @FXML private TableColumn<Produto, String> lblNome;
    @FXML private TableColumn<Produto, String> lblDescricao;
    @FXML private TableColumn<Produto, String> lblDataFabricacao;
    @FXML private TableColumn<Produto, String> lblDataDeVencimento;
    @FXML private TableColumn<Produto, String> lblPrecoCompra;
    @FXML private TableColumn<Produto, String> lblPrecoVenda;
    @FXML private TableColumn<Produto, String> lblQuantidade;
    @FXML private TableColumn<Produto, String> lblCategoria;
    @FXML private Label lblMediaLucro;

    private final ObservableList<Produto> produtos = FXCollections.observableArrayList();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        lblCodigo.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCodigo())));
        lblNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));
        lblDescricao.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescricao()));
        lblDataFabricacao.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getDataFabricacao().format(formatter))
        );
        lblDataDeVencimento.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getDataValidade().format(formatter))
        );
        lblPrecoCompra.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPrecoCompra())));
        lblPrecoVenda.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPrecoVenda())));
        lblQuantidade.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getQuantidadeEstoque())));
        lblCategoria.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCategoria()));

        carregarProdutosDoArquivo();
        tableView.setItems(produtos);
    }



    @FXML
    public void adicionarProduto(ActionEvent event) {
        try {
            String codigo = txtCodigo.getText();
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            LocalDate dataFabricacao = LocalDate.parse(txtDataFabricacao.getText(), formatter);
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText(), formatter);
            double precoCompra = Double.parseDouble(txtPrecoCompra.getText());
            double precoVenda = Double.parseDouble(txtPrecoVenda.getText());
            int quantidade = Integer.parseInt(txtQuantidadeEstoque.getText());
            String categoria = txtCategoria.getText();

            Produto novo = new Produto(codigo, nome, descricao, dataFabricacao, dataValidade, precoCompra, precoVenda, quantidade, categoria);
            produtos.add(novo);
            salvarProdutosEmArquivo();

            tableView.setItems(produtos);

            limparCampos();
        } catch (Exception e) {
            mostrarAlerta("Erro ao adicionar produto: " + e.getMessage());
        }
    }


    @FXML
    public void excluirProduto(ActionEvent event) {
        Produto selecionado = tableView.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            produtos.remove(selecionado);
        } else {
            mostrarAlerta("Selecione um produto para excluir.");
        }
    }

    @FXML
    public void consultarProduto(ActionEvent event) {
        String codigo = txtCodigo.getText();
        Optional<Produto> encontrado = produtos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
        if (encontrado.isPresent()) {
            Produto p = encontrado.get();
            txtNome.setText(p.getNome());
            txtDescricao.setText(p.getDescricao());
            txtDataFabricacao.setText(String.valueOf(p.getDataFabricacao()));
            txtDataValidade.setText(String.valueOf(p.getDataValidade()));
            txtPrecoCompra.setText(String.valueOf(p.getPrecoCompra()));
            txtPrecoVenda.setText(String.valueOf(p.getPrecoVenda()));
            txtQuantidadeEstoque.setText(String.valueOf(p.getQuantidadeEstoque()));
            txtCategoria.setText(p.getCategoria());
        } else {
            mostrarAlerta("Produto não encontrado.");
        }
    }


    @FXML
    public void listarTodos(ActionEvent event) {
        tableView.setItems(produtos);
    }

    @FXML
    public void filtrarProximosAVencer(ActionEvent event) {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(60);
        tableView.setItems(produtos.filtered(p ->
                p.getDataValidade() != null &&
                        !p.getDataValidade().isBefore(hoje) &&
                        !p.getDataValidade().isAfter(limite)
        ));
    }

    @FXML
    public void filtrarEstoqueMenorQue10(ActionEvent event) {
        tableView.setItems(produtos.filtered(p -> p.getQuantidadeEstoque() < 10));
    }


    @FXML
    public void calcularLucroPorCategoria(ActionEvent event) {
        String categoria = txtCategoria.getText();
        double lucroTotal = produtos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .mapToDouble(p -> p.getPrecoVenda() - p.getPrecoCompra())
                .sum();
        lblMediaLucro.setText(String.format("Lucro: %.2f", lucroTotal));
    }



    @FXML
    public void listarPorCategoria(ActionEvent event) {
        String categoria = txtCategoria.getText();
        tableView.setItems(produtos.filtered(p -> p.getCategoria().equalsIgnoreCase(categoria)));

    }

    private void limparCampos() {
        txtCodigo.clear();
        txtNome.clear();
        txtDescricao.clear();
        txtDataFabricacao.clear();
        txtDataValidade.clear();
        txtPrecoCompra.clear();
        txtPrecoVenda.clear();
        txtQuantidadeEstoque.clear();
        txtCategoria.clear();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void salvarProdutosEmArquivo() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:\\Users\\Unifan\\Documents\\Trabalho-POO3\\Trabalho-POO3\\Produto\\Produto-main\\src\\main\\java\\com\\example\\produto\\csv\\arquivo.csv"))) {
            for (Produto p : produtos) {
                writer.write(String.join(";",
                        p.getCodigo(), p.getNome(), p.getDescricao(),
                        p.getDataFabricacao().format(formatter),
                        p.getDataValidade().format(formatter),
                        String.valueOf(p.getPrecoCompra()),
                        String.valueOf(p.getPrecoVenda()),
                        String.valueOf(p.getQuantidadeEstoque()),
                        p.getCategoria()));
                writer.newLine();
            }
        } catch (IOException e) {
            mostrarAlerta("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    private void carregarProdutosDoArquivo() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\ricardo.vieira\\Documents\\java\\Trabalho-POO3\\Produto\\Produto-main\\src\\main\\java\\com\\example\\produto\\csv\\arquivo.csv"))) {
            produtos.clear();
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                Produto p = new Produto(
                        partes[0], partes[1], partes[2],
                        LocalDate.parse(partes[3], formatter),
                        LocalDate.parse(partes[4], formatter),
                        Double.parseDouble(partes[5]),
                        Double.parseDouble(partes[6]),
                        Integer.parseInt(partes[7]),
                        partes[8]
                );
                produtos.add(p);
            }
            tableView.setItems(produtos);
        } catch (IOException e) {

        }
    }


}
