package br.senac.sp.pizzaria.repository;

import br.senac.sp.pizzaria.dao.InterfaceDao;
import br.senac.sp.pizzaria.factory.JpaUtil;
import br.senac.sp.pizzaria.model.Pedido;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PedidoRepository implements InterfaceDao<Pedido> {

    @Override
    public void inserir(Pedido objeto) {
        try (EntityManager manager = JpaUtil.getEntityManager()) {
            manager.getTransaction().begin();
            manager.persist(objeto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(Pedido objeto) {

    }

    @Override
    public void excluir(Long id) {

    }

    @Override
    public List<Pedido> listar() {
        return List.of();
    }
}
