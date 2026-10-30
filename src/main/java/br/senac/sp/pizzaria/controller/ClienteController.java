package br.senac.sp.pizzaria.controller;

import br.senac.sp.pizzaria.model.Cliente;
import br.senac.sp.pizzaria.service.ClienteService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfCpf;
    @FXML
    private TextArea taEndereco;
    @FXML
    private DatePicker dpNascimento;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TableView<Cliente> tbCliente;
    @FXML
    private TableColumn<Cliente, Long> colId;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colTelefone;
    private ObservableList<Cliente> lista;
    private ClienteService service = new ClienteService();
    private Cliente clienteSelecionado;

    private void configurarTabela(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    private void carregarTabela(){
        lista = FXCollections.observableArrayList(service.listar());
        tbCliente.setItems(lista);
        tbCliente.refresh();
    }

    public void salvar(){
        try {
            Cliente c;
            if(clienteSelecionado == null) {
                c = new Cliente();
            }else{
                c = clienteSelecionado;
            }
            c.setTelefone(tfTelefone.getText());
            c.setCpf(tfCpf.getText());
            c.setNome(tfNome.getText());
            c.setEndereco(taEndereco.getText());
            c.setNascimento(dpNascimento.getValue());
            service.salvar(c);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("Cliente salvo com sucesso");
            alerta.setHeaderText("Sucesso");
            alerta.show();
            carregarTabela();
            limpar();
        }catch (Exception e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(e.getMessage());
            alerta.setHeaderText("Erro");
            alerta.show();
        }
    }

    public void limpar(){
        tfId.clear();
        tfNome.clear();
        tfCpf.clear();
        tfTelefone.clear();
        dpNascimento.setValue(null);
        taEndereco.clear();
        clienteSelecionado = null;
        tbCliente.getSelectionModel().clearSelection();
    }

    private void carregarTabela(String parametro){
        lista = FXCollections.observableArrayList(service.listar(parametro));
        tbCliente.setItems(lista);
        tbCliente.refresh();
    }

    public void excluir(){
        Alert alerta;
        if(clienteSelecionado == null){
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Selecione um cliente para excluí-lo");
            alerta.setHeaderText("Cliente não selecionado");
            alerta.show();
        }else{
            alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar exclusão");
            alerta.setHeaderText("Deseja excluir o cliente "+clienteSelecionado.getNome()+"?");
            alerta.setContentText("Escolha uma opção");
            alerta.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> botaoClicado = alerta.showAndWait();
            if(botaoClicado.isPresent() && botaoClicado.get() == ButtonType.YES){
                service.excluir(clienteSelecionado);
                limpar();
                carregarTabela();
            }
        }

    }

    public void buscar(){
        String parametro = tfTelefone.getText();
        carregarTabela(parametro);
        limpar();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabela();
        carregarTabela();
        listenerSelecao();
    }


    private void listenerSelecao(){
        tbCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            clienteSelecionado = newValue;
            if(clienteSelecionado != null){
                tfId.setText(clienteSelecionado.getId().toString());
                tfTelefone.setText(clienteSelecionado.getTelefone());
                tfNome.setText(clienteSelecionado.getNome());
                taEndereco.setText(clienteSelecionado.getEndereco());
                tfCpf.setText(clienteSelecionado.getCpf());
                dpNascimento.setValue(clienteSelecionado.getNascimento());
            }
        });
    }

}
