package locadora.dao;

import locadora.core.Locacao;
import locadora.core.Pagamento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class LocacaoDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("locadoraPU");

    public void inserir(Locacao locacao) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(locacao);
        em.getTransaction().commit();
        em.close();
    }

    public List<Locacao> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Locacao> lista = em.createQuery("FROM Locacao", Locacao.class)
                .getResultList();
        em.close();
        return lista;
    }

    public Locacao buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Locacao loc = em.find(Locacao.class, id);
        em.close();
        return loc;
    }

    public void removerComPagamento(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // 1) Deleta em lote todos os Pagamento vinculados
        int removidos = em.createQuery(
                        "DELETE FROM Pagamento p WHERE p.locacao.id = :id"
                )
                .setParameter("id", id)
                .executeUpdate();

        // 2) Agora remove a própria Locacao
        Locacao locacao = em.find(Locacao.class, id);
        if (locacao != null) {
            em.remove(locacao);
        }

        em.getTransaction().commit();
        em.close();
    }


    /**
     * Relatório JOIN: nome do locatário, modelo do veículo, data e valor total.
     */
    public List<Object[]> listarLocacoesComVeiculo() {
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createQuery(
                "SELECT l.nomeLocatario, l.veiculo.modelo, l.dataHora, l.valorTotal " +
                        "FROM Locacao l", Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    /**
     * Relatório LIKE: busca por parte do nome do locatário.
     */
    public List<Object[]> buscarPorNome(String nomeFiltro) {
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createQuery(
                        "SELECT l.nomeLocatario, l.veiculo.modelo, l.dataHora, l.valorTotal " +
                                "FROM Locacao l " +
                                "WHERE LOWER(l.nomeLocatario) LIKE :nome", Object[].class
                )
                .setParameter("nome", "%" + nomeFiltro.toLowerCase() + "%")
                .getResultList();
        em.close();
        return result;
    }

    /**
     * Relatório por intervalo de datas (inclusive).
     */
    public List<Object[]> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createQuery(
                        "SELECT l.nomeLocatario, l.veiculo.modelo, l.dataHora, l.valorTotal " +
                                "FROM Locacao l " +
                                "WHERE l.dataHora BETWEEN :inicio AND :fim " +
                                "ORDER BY l.dataHora", Object[].class
                )
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
        em.close();
        return result;
    }

    /**
     * Quantidade total de locações.
     */
    public long contarLocacoes() {
        EntityManager em = emf.createEntityManager();
        Long total = em.createQuery("SELECT COUNT(l) FROM Locacao l", Long.class)
                .getSingleResult();
        em.close();
        return total != null ? total : 0L;
    }

    /**
     * Média de horas alugadas.
     */
    public double mediaHoras() {
        EntityManager em = emf.createEntityManager();
        Double avg = em.createQuery("SELECT AVG(l.horas) FROM Locacao l", Double.class)
                .getSingleResult();
        em.close();
        return avg != null ? avg : 0.0;
    }

    /**
     * Soma de todos os valores totais.
     */
    public double somaValores() {
        EntityManager em = emf.createEntityManager();
        Double sum = em.createQuery("SELECT SUM(l.valorTotal) FROM Locacao l", Double.class)
                .getSingleResult();
        em.close();
        return sum != null ? sum : 0.0;
    }
}
