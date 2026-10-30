package br.senac.sp.pizzaria.controller;

import br.senac.sp.pizzaria.model.*;
import br.senac.sp.pizzaria.service.PedidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PedidoController implements Initializable {
    @FXML
    private ComboBox<FormaPagamento> cbFormaPagamento;
    @FXML
    private TextField tfTelefone, tfCliente, tfIngredientes, tfEndereco;
    @FXML
    private ListView<Pizza> lstPizza;
    @FXML
    private ListView<ItemPedido> lstItem;
    @FXML
    private Spinner<Integer> spQtd;
    @FXML
    private Label lbTotal;

    private Cliente cliente;
    private ObservableList<Pizza> listaPizza;
    private ObservableList<ItemPedido> listItens;
    private PedidoService service = new PedidoService();
    private List<ItemPedido> itens = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbFormaPagamento.getItems().setAll(FormaPagamento.values());
        buscarPizza();
        listItens = FXCollections.observableArrayList(itens);
        lstItem.setItems(listItens);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
        spQtd.setValueFactory(valueFactory);

    }

    public void addItem(){
        int qtd = spQtd.getValue();
        Pizza pizza;
        if((pizza = lstPizza.getSelectionModel().getSelectedItem()) != null){
            ItemPedido item = new ItemPedido();
            item.setPizza(pizza);
            item.setQuantidade(qtd);
            item.setPreco(pizza.getPreco());
            listItens.add(item);
            // atualizar o total
        }
    }

    public void atualizaTotal(){
        double total = 0;
        for (ItemPedido item : listItens){
            total += item.getTotal();
        }
        lbTotal.setText(String.format("R$ %5.2f", total));
    }

    public void removeItem(){
        ItemPedido item = lstItem.getSelectionModel().getSelectedItem();
        if (item != null){
            listItens.remove(item);
            atualizaTotal();
        }
    }

    public void gerarPedido(){
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItens(listItens);
        pedido.setFormaPagamento(cbFormaPagamento.getValue());
        service.inserir(pedido);
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText("Pedido gerado com sucesso. Número: " + pedido.getNumero());
        alerta.setHeaderText("Sucesso");
        alerta.show();
        limparCampos();
    }

    public void limparCampos(){
        cliente = null;
        tfCliente.clear();
        tfEndereco.clear();
        tfTelefone.clear();
        cbFormaPagamento.setValue(null);
        listItens.clear();
        lstPizza.getSelectionModel().clearSelection();
        atualizaTotal();
        buscarPizza();
       // lbTotal.setText("R$ 0,00");
    }

    public void buscarPizza(){
        listaPizza = FXCollections.observableArrayList(service.buscarPizzas(tfIngredientes.getText()));
        lstPizza.setItems(listaPizza);
    }

    public void buscarCliente(){
        if(!tfTelefone.getText().isBlank()){
            try {
                cliente = service.buscarCliente(tfTelefone.getText());
                tfCliente.setText(cliente.getNome());
                tfEndereco.setText(cliente.getEndereco());
            }catch (Exception e){
                tfCliente.setText("Cliente não encontrado");
                tfEndereco.setText("");
            }
        }
    }
}
