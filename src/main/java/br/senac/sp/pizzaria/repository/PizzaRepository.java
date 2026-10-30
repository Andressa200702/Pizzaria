package br.senac.sp.pizzaria.repository;

import br.senac.sp.pizzaria.dao.InterfaceDao;
import br.senac.sp.pizzaria.factory.JpaUtil;
import br.senac.sp.pizzaria.model.Pizza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


import java.beans.ExceptionListener;
import java.util.List;

public class PizzaRepository implements InterfaceDao<Pizza> {

    @Override
    public void inserir(Pizza objeto) {
        try (EntityManager manager = JpaUtil.getEntityManager()) {
            manager.getTransaction().begin();
            manager.persist(objeto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(Pizza objeto) {
        try (EntityManager manager = JpaUtil.getEntityManager()) {
            manager.getTransaction().begin();
            manager.merge(objeto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(Long id) {
        try (EntityManager manager = JpaUtil.getEntityManager()) {
            Pizza p = manager.find(Pizza.class, id);
            manager.getTransaction().begin();
            manager.remove(p);
            manager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pizza> listar() {
        try (EntityManager manager = JpaUtil.getEntityManager()) {
            return manager.createQuery("SELECT p FROM Pizza p ORDER BY p.sabor", Pizza.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pizza> listarPorIngredientes(String ingredientes) {
        try (EntityManager manager = JpaUtil.getEntityManager()) {
            TypedQuery<Pizza> query = manager.createQuery
                    ("FROM Pizza p WHERE p.ingredientes LIKE :ingrediente ORDER BY p.sabor", Pizza.class);
            query.setParameter("ingrediente", "%"+ingredientes+"%");
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
