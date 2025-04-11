package locadora.app;

import locadora.core.Pagamento;
import locadora.veiculo.Veiculo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class SistemaLocacao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("locadoraPU");
        EntityManager em = emf.createEntityManager();

        while (true) {
            System.out.println("==== TOTEM DE LOCAÇÃO ====");
            System.out.println("1. Alugar Veículo");
            System.out.println("2. Devolver Veículo");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                List<Veiculo> veiculos = em.createQuery("SELECT v FROM Veiculo v WHERE v.disponivel = true", Veiculo.class).getResultList();

                if (veiculos.isEmpty()) {
                    System.out.println("Nenhum veiculo disponivel.");
                    continue;
                }

                System.out.println("\n=== VEÍCULOS DISPONÍVEIS ===");
                for (int i = 0; i < veiculos.size(); i++) {
                    System.out.print((i + 1) + ". ");
                    veiculos.get(i).exibirDetalhes();


                }

                System.out.print("\nDigite o número do veículo que deseja locar: ");
                int escolha = scanner.nextInt() - 1;
                scanner.nextLine();

                if (escolha < 0 || escolha >= veiculos.size()) {
                    System.out.println("Escolha inválida.");
                    continue;
                }

                Veiculo veiculoSelecionado = veiculos.get(escolha);

                System.out.print("\nDigite seu nome: ");
                String nomeLocatario = scanner.nextLine();

                System.out.print("Digite a quantidade de horas de locação: ");
                int horas = scanner.nextInt();
                scanner.nextLine();

                double total = veiculoSelecionado.calcularPreco(horas);

                System.out.println("\nLocação realizada com sucesso! Valor total: R$ " + total);

                veiculoSelecionado.locar();

                em.getTransaction().begin();
                em.merge(veiculoSelecionado);
                em.getTransaction().commit();

                Pagamento pagamento = new Pagamento(total);

                System.out.println("\nSelecione a forma de pagamento:");
                System.out.println("1. Cartão de Crédito");
                System.out.println("2. Cartão de Débito");
                System.out.println("3. Dinheiro");
                System.out.println("4. PIX");
                System.out.print("Escolha uma opção: ");

                int formaPagamento = scanner.nextInt();
                scanner.nextLine();

                switch (formaPagamento) {
                    case 1:
                        pagamento.selecionarFormaPagamento("Cartão de Crédito");
                        break;
                    case 2:
                        pagamento.selecionarFormaPagamento("Cartão de Débito");
                        break;
                    case 3:
                        pagamento.selecionarFormaPagamento("Dinheiro");
                        break;
                    case 4:
                        pagamento.selecionarFormaPagamento("PIX");
                        break;
                    default:
                        System.out.println("Opção inválida. Pagamento não processado.");
                        continue;
                }

                pagamento.processarPagamento();
            } else if (opcao == 2) {
                System.out.print("\nDigite o ID do veículo que deseja devolver: ");
                String idDevolucao = scanner.nextLine();

                Veiculo veiculo = em.find(Veiculo.class, idDevolucao);

                if (veiculo != null && !veiculo.isDisponivel()) {
                    veiculo.devolver();
                    em.getTransaction().begin();
                    em.merge(veiculo);
                    em.getTransaction().commit();
                    System.out.println("Veículo devolvido com sucesso: " + veiculo.getModelo());
                } else {
                    System.out.println("Veículo não encontrado ou já está disponível.");
                }
            } else if (opcao == 3) {
                break;
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }
}
