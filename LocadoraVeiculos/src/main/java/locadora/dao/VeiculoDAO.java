package locadora.dao;

import jakarta.persistence.*;
import locadora.veiculo.Veiculo;

import java.util.List;

public class VeiculoDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("locadoraPU");

    public void salvar(Veiculo veiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(veiculo);
        em.getTransaction().commit();
        em.close();
    }

    public List<Veiculo> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Veiculo> lista = em.createQuery("SELECT v FROM Veiculo v", Veiculo.class).getResultList();
        em.close();
        return lista;
    }

    public void atualizar(Veiculo veiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(veiculo);
        em.getTransaction().commit();
        em.close();
    }
}
