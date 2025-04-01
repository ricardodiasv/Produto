package com.example.produto;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoController {
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNome;
    @FXML private TextField txtPrecoCompra;
    @FXML private TextField txtPrecoVenda;
    @FXML private TextField txtQuantidade;
    @FXML private DatePicker dpFabricacao;
    @FXML private DatePicker dpValidade;
    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, String> colunaCodigo;
    @FXML private TableColumn<Produto, String> colunaNome;
    @FXML private TableColumn<Produto, String> colunaPrecoVenda;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

   // @FXML
    //public void initialize() {
       // colunaCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        //colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
       // colunaPrecoVenda.setCellValueFactory(cellData -> cellData.getValue().precoVendaProperty().asString());
        //tabelaProdutos.setItems(listaProdutos);
    //}

    @FXML
    private void onCadastrarClick() {
        Produto produto = new Produto(txtCodigo.getText(), txtNome.getText(), dpFabricacao.getValue(),
                dpValidade.getValue(), new BigDecimal(txtPrecoCompra.getText()),
                new BigDecimal(txtPrecoVenda.getText()), Integer.parseInt(txtQuantidade.getText()));
        listaProdutos.add(produto);
    }

    @FXML
    private void onExcluirClick() {
        Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            listaProdutos.remove(selecionado);
        }
    }

    @FXML
    private void onListarClick() {
        List<String> listaFormatada = listaProdutos.stream()
                .map(Produto::toString)
                .collect(Collectors.toList());
        System.out.println(String.join("\n", listaFormatada));
    }
}
