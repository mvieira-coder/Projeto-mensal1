package locadora.dao;

import locadora.veiculo.Veiculo;

import javax.persistence.*;
import java.util.List;

public class VeiculoDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("locadoraPU");
    private EntityManager em = emf.createEntityManager();

    public void inserir(Veiculo v) {
        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();
    }

    public void atualizar(Veiculo v) {
        em.getTransaction().begin();
        em.merge(v);
        em.getTransaction().commit();
    }

    public void remover(String id) {
        Veiculo v = buscarPorId(id);
        if (v != null) {
            em.getTransaction().begin();
            em.remove(v);
            em.getTransaction().commit();
        }
    }

    public Veiculo buscarPorId(String id) {
        return em.find(Veiculo.class, id);
    }

    public List<Veiculo> listarTodos() {
        return em.createQuery("FROM Veiculo", Veiculo.class).getResultList();
    }
}
