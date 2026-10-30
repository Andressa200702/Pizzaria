package br.senac.sp.pizzaria.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("pizzaria");

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
