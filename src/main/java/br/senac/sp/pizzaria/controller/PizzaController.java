package br.senac.sp.pizzaria.controller;

import br.senac.sp.pizzaria.model.Cliente;
import br.senac.sp.pizzaria.model.Pizza;
import br.senac.sp.pizzaria.model.Tamanho;
import br.senac.sp.pizzaria.model.TipoPizza;
import br.senac.sp.pizzaria.service.PizzaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.boot.model.internal.PropertyContainer;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PizzaController implements Initializable {
    @FXML
    private TextField tfId, tfSabor, tfPreco;
    @FXML
    private TextArea taIngredientes;
    @FXML
    private ComboBox<Tamanho> cbTamanho;
    @FXML
    private ComboBox<TipoPizza> cbTipo;
    @FXML
    private TableView<Pizza> tbPizza;
    @FXML
    private TableColumn<Pizza, Long> colId;
    @FXML
    private TableColumn<Pizza, String> colSabor;
    @FXML
    private TableColumn<Pizza, Tamanho> colTamanho;
    private PizzaService service = new PizzaService();
    private Pizza pizzaSelecionada;
    private ObservableList<Pizza> lista;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbTamanho.getItems().setAll(Tamanho.values());
        cbTipo.getItems().setAll(TipoPizza.values());

        // permitir apenas números e vírgula na tfPreco
        tfPreco.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();

            // permite apenas números e ponto
            if (!novoTexto.matches("[0-9.]*?")) {
                return null;//bloqueia
            }

            // conta quantos pontos existem
            long pontos = novoTexto.chars().filter(car -> car == '.').count();
            if (pontos > 1) {
                return null; //bloqueia
            }

            return change;//permite alteração
        }));

        configurarTabela();
        carregarTabela();
        listenerSelecao();
    }

    public void buscar() {
        String ingredientes = taIngredientes.getText().trim();
        carregarTabela(ingredientes);
        limpar();
    }

    private void carregarTabela() {
        lista = FXCollections.observableArrayList(service.listar());
        tbPizza.setItems(lista);
        tbPizza.refresh();
    }

    private void carregarTabela(String ingrediente) {
        lista = FXCollections.observableArrayList(service.listarPorIngrediente(ingrediente));
        tbPizza.setItems(lista);
        tbPizza.refresh();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        colTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanho"));
    }

    private void listenerSelecao() {
        tbPizza.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            pizzaSelecionada = newValue;
            if (pizzaSelecionada != null) {
                tfId.setText(pizzaSelecionada.getId().toString());
                tfSabor.setText(pizzaSelecionada.getSabor());
                taIngredientes.setText(pizzaSelecionada.getIngredientes());
                tfPreco.setText(pizzaSelecionada.getPreco() + "");
                cbTamanho.setValue(pizzaSelecionada.getTamanho());
                cbTipo.setValue(pizzaSelecionada.getTipo());
            }
        });
    }

    public void salvar() {
        try {
            Pizza p;
            if (pizzaSelecionada == null) {
                p = new Pizza();
            } else {
                p = pizzaSelecionada;
            }
            p.setTipo(cbTipo.getValue());
            p.setSabor(tfSabor.getText());
            p.setPreco(Double.parseDouble(tfPreco.getText()));
            p.setTamanho(cbTamanho.getValue());
            p.setIngredientes(taIngredientes.getText());
            service.salvar(p);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("Cliente salvo com sucesso");
            alerta.setHeaderText("Sucesso");
            alerta.show();
            carregarTabela();
            limpar();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(e.getMessage());
            alerta.setHeaderText("Erro");
            alerta.show();
        }
    }

    public void excluir() {
        Alert alerta;
        if (pizzaSelecionada == null) {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Selecione uma pizza para excluí-la");
            alerta.setHeaderText("Pizza não selecionada");
            alerta.show();
        } else {
            alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar exclusão");
            alerta.setHeaderText("Deseja excluir a pizza " + pizzaSelecionada.getSabor() + "?");
            alerta.setContentText("Escolha uma opção");
            alerta.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> botaoClicado = alerta.showAndWait();
            if (botaoClicado.isPresent() && botaoClicado.get() == ButtonType.YES) {
                service.excluir(pizzaSelecionada);
                limpar();
                carregarTabela();
            }
        }
    }

    public void limpar() {
        tfId.clear();
        tfSabor.clear();
        taIngredientes.clear();
        tfPreco.clear();
        cbTipo.setValue(null);
        cbTamanho.setValue(null);
        pizzaSelecionada = null;
        tbPizza.getSelectionModel().clearSelection();
        tfSabor.requestFocus();
    }


}
