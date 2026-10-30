package br.senac.sp.pizzaria.service;

import br.senac.sp.pizzaria.dao.ClienteDao;
import br.senac.sp.pizzaria.dao.InterfaceDao;
import br.senac.sp.pizzaria.model.Cliente;
import br.senac.sp.pizzaria.model.Pizza;
import br.senac.sp.pizzaria.repository.PizzaRepository;

import java.util.List;

public class PizzaService {
    private final InterfaceDao<Pizza> rep = new PizzaRepository();

    public void salvar(Pizza pizza) {
        if (pizza.getSabor().isBlank()) {
            throw new RuntimeException("O sabor da pizza é obrigatório");
        } else if (pizza.getTipo() == null) {
            throw new RuntimeException("O tipo da pizza é obrigatório");
        } else if (pizza.getTamanho() == null) {
            throw new RuntimeException("O tamamho da pizza é obrigatório");
        } else if (pizza.getPreco() < 0) {
            throw new RuntimeException("O preço da pizza é obrigatório");
        } else {
            if (pizza.getId() == null) {
                rep.inserir(pizza);
            } else {
                rep.alterar(pizza);
            }
        }
    }

    public List<Pizza> listar() {
        return rep.listar();
    }

    public List<Pizza> listarPorIngrediente(String ingrediente) {
        PizzaRepository repo = (PizzaRepository) rep;
        return ((PizzaRepository) rep).listarPorIngredientes(ingrediente);
    }

    public void excluir(Pizza pizza) {
        rep.excluir(pizza.getId());
    }


}
