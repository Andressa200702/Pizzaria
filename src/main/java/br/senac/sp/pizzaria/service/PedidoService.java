package br.senac.sp.pizzaria.service;

import br.senac.sp.pizzaria.model.Cliente;
import br.senac.sp.pizzaria.model.ItemPedido;
import br.senac.sp.pizzaria.model.Pedido;
import br.senac.sp.pizzaria.model.Pizza;
import br.senac.sp.pizzaria.repository.ClienteRepository;
import br.senac.sp.pizzaria.repository.PedidoRepository;
import br.senac.sp.pizzaria.repository.PizzaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoService {

    private final ClienteRepository repCliente = new ClienteRepository();
    private final PizzaRepository repPizza = new PizzaRepository();
    private final PedidoRepository repPedido = new PedidoRepository();

    public Cliente buscarCliente(String telefone){
        return repCliente.buscarPorTel(telefone);
    }

    public List<Pizza> buscarPizzas(String ingredientes){
        return repPizza.listarPorIngredientes(ingredientes);
    }

    public void inserir(Pedido p){
        p.setData(LocalDateTime.now());
        int tempo = 30;
        for(ItemPedido item : p.getItens()){
            tempo += item.getQuantidade() * 5;
            item.setPedido(p);
        }
        p.setPrazoEntrega(p.getData().plusMinutes(tempo));
        repPedido.inserir(p);
    }
}
