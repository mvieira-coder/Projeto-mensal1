package locadora.core;

import locadora.veiculo.Veiculo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Locadora {
    private EntityManager em;

    public Locadora(EntityManager em) {
        this.em = em;
    }

    public void listarVeiculos() {
        System.out.println("\n=== VEÍCULOS DISPONÍVEIS ===");
        TypedQuery<Veiculo> query = em.createQuery("SELECT v FROM Veiculo v WHERE v.disponivel = true", Veiculo.class);
        List<Veiculo> veiculos = query.getResultList();

        for (int i = 0; i < veiculos.size(); i++) {
            System.out.print((i + 1) + ". ");
            veiculos.get(i).exibirDetalhes();
        }
    }

    public Veiculo getVeiculo(int index) {
        TypedQuery<Veiculo> query = em.createQuery("SELECT v FROM Veiculo v WHERE v.disponivel = true", Veiculo.class);
        List<Veiculo> veiculos = query.getResultList();

        if (index >= 0 && index < veiculos.size()) {
            return veiculos.get(index);
        }
        return null;
    }

    public void devolverVeiculo(String identificador) {
        Veiculo veiculo = em.find(Veiculo.class, identificador);

        if (veiculo != null && !veiculo.isDisponivel()) {
            veiculo.devolver();
            em.getTransaction().begin();
            em.merge(veiculo);
            em.getTransaction().commit();
            System.out.println("Veículo devolvido com sucesso: " + veiculo.getModelo());
        } else {
            System.out.println("Veículo não encontrado ou já está disponível.");
        }
    }
}
