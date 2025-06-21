package locadora.dao;

import locadora.core.Pagamento;

import javax.persistence.*;
import java.util.List;

public class PagamentoDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("locadoraPU");

    public void inserir(Pagamento pagamento) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(pagamento);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        Pagamento pagamento = em.find(Pagamento.class, id);
        if (pagamento != null) {
            em.getTransaction().begin();
            em.remove(pagamento);
            em.getTransaction().commit();
        }
        em.close();
    }

    public List<Pagamento> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Pagamento> lista = em.createQuery("FROM Pagamento", Pagamento.class).getResultList();
        em.close();
        return lista;
    }
}
