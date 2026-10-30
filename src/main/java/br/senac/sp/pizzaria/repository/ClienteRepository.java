package br.senac.sp.pizzaria.repository;

import br.senac.sp.pizzaria.factory.JpaUtil;
import br.senac.sp.pizzaria.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteRepository {

    public Cliente buscarPorTel(String telefone){
        try(EntityManager manager = JpaUtil.getEntityManager()){
            TypedQuery<Cliente> query =
                    manager.createQuery("FROM Cliente c WHERE c.telefone = :tel", Cliente.class);
            query.setParameter("tel", telefone);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
