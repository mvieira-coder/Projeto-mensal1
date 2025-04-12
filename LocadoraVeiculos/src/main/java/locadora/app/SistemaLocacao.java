package locadora.app;

import locadora.core.Locacao;
import locadora.core.Pagamento;
import locadora.veiculo.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaLocacao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("locadoraPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        long count = em.createQuery("SELECT COUNT(v) FROM Veiculo v", Long.class).getSingleResult();
        if (count == 0) {
            em.persist(new Carro("C001", "Fiat Uno", 15.0));
            em.persist(new Moto("M001", "Honda CG", 10.0));
            em.persist(new Barco("B001", "Lancha X", 25.0));
            System.out.println("Veículos de exemplo inseridos.");
        }
        em.getTransaction().commit();

        while (true) {
            System.out.println("\n==== TOTEM DE LOCAÇÃO ====\n1. Alugar Veículo\n2. Devolver Veículo\n3. Relatórios\n4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1 -> alugarVeiculo(scanner, em);
                case 2 -> devolverVeiculo(scanner, em);
                case 3 -> menuRelatorios(scanner, em);
                case 4 -> {
                    em.close();
                    emf.close();
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void alugarVeiculo(Scanner scanner, EntityManager em) {
        while (true) {
            System.out.println("\nEscolha o tipo de veículo:\n1. Carro\n2. Moto\n3. Barco\n0. Voltar");
            System.out.print("Tipo: ");

            int tipo;
            try {
                tipo = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }

            Class<? extends Veiculo> classe;
            switch (tipo) {
                case 1 -> classe = Carro.class;
                case 2 -> classe = Moto.class;
                case 3 -> classe = Barco.class;
                case 0 -> { return; }
                default -> {
                    System.out.println("Tipo inválido.");
                    continue;
                }
            }

            List<Veiculo> disponiveis = em.createQuery("SELECT v FROM Veiculo v WHERE v.disponivel = true AND TYPE(v) = :tipo", Veiculo.class)
                    .setParameter("tipo", classe)
                    .getResultList();

            if (disponiveis.isEmpty()) {
                System.out.println("Nenhum veículo desse tipo disponível.");
                return;
            }

            System.out.println("\n--- Veículos Disponíveis ---");
            for (int i = 0; i < disponiveis.size(); i++) {
                System.out.print((i + 1) + ". ");
                disponiveis.get(i).exibirDetalhes();
            }
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            int escolha;
            try {
                escolha = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Entrada inválida.");
                continue;
            }

            if (escolha == 0) return;
            if (escolha < 1 || escolha > disponiveis.size()) {
                System.out.println("Escolha inválida.");
                continue;
            }

            Veiculo veiculo = disponiveis.get(escolha - 1);

            System.out.print("Nome do locatário: ");
            String nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("Nome não pode ser vazio.");
                continue;
            }

            System.out.print("Horas de locação: ");
            int horas;
            try {
                horas = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Horas inválidas.");
                continue;
            }

            double total = veiculo.calcularPreco(horas);
            veiculo.locar();

            Locacao locacao = new Locacao(nome, horas, total, veiculo);

            em.getTransaction().begin();
            em.merge(veiculo);
            em.persist(locacao);
            em.getTransaction().commit();

            System.out.println("\nLocação registrada com sucesso! Total: R$ " + total);
            Pagamento pagamento = new Pagamento(total);

            while (true) {
                System.out.println("Forma de pagamento:\n1. Crédito  2. Débito  3. Dinheiro  4. PIX");
                int pgto = scanner.nextInt();
                scanner.nextLine();
                switch (pgto) {
                    case 1 -> pagamento.selecionarFormaPagamento("Crédito");
                    case 2 -> pagamento.selecionarFormaPagamento("Débito");
                    case 3 -> pagamento.selecionarFormaPagamento("Dinheiro");
                    case 4 -> pagamento.selecionarFormaPagamento("PIX");
                    default -> {
                        System.out.println("Opção inválida.");
                        continue;
                    }
                }
                break;
            }

            pagamento.processarPagamento();
            return;
        }
    }

    private static void devolverVeiculo(Scanner scanner, EntityManager em) {
        System.out.print("Digite o ID do veículo: ");
        String id = scanner.nextLine();
        Veiculo veiculo = em.find(Veiculo.class, id);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }
        if (veiculo.isDisponivel()) {
            System.out.println("Esse veículo já está disponível.");
            return;
        }
        veiculo.devolver();
        em.getTransaction().begin();
        em.merge(veiculo);
        em.getTransaction().commit();
        System.out.println("Veículo devolvido com sucesso.");
    }

    private static void menuRelatorios(Scanner scanner, EntityManager em) {
        while (true) {
            System.out.println("\n=== RELATÓRIOS ===\n1. Locações (JOIN)\n2. Filtro por valor total\n3. Busca por nome (LIKE)\n4. Dados agregados\n0. Voltar");
            System.out.print("Escolha: ");

            int opcao;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Entrada inválida.");
                continue;
            }

            switch (opcao) {
                case 1 -> relatorioLocacoes(em);
                case 2 -> relatorioIntervalo(scanner, em);
                case 3 -> relatorioLike(scanner, em);
                case 4 -> relatorioAgregado(em);
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void relatorioLocacoes(EntityManager em) {
        List<Locacao> locacoes = em.createQuery("SELECT l FROM Locacao l JOIN FETCH l.veiculo", Locacao.class).getResultList();
        System.out.println("\n--- Relatório de Locações ---");
        if (locacoes.isEmpty()) {
            System.out.println("Nenhuma locação registrada.");
            return;
        }
        for (Locacao l : locacoes) {
            System.out.printf("%s alugou o %s (%s) por %d horas em %s - Total: R$ %.2f\n",
                    l.getNomeLocatario(), l.getVeiculo().getModelo(),
                    l.getVeiculo().getIdentificador(), l.getHoras(), l.getDataHora(), l.getValorTotal());
        }
    }

    private static void relatorioIntervalo(Scanner scanner, EntityManager em) {
        try {
            System.out.print("Valor mínimo: ");
            double min = scanner.nextDouble();
            System.out.print("Valor máximo: ");
            double max = scanner.nextDouble();
            scanner.nextLine();

            List<Locacao> locacoes = em.createQuery(
                            "SELECT l FROM Locacao l WHERE l.valorTotal BETWEEN :min AND :max", Locacao.class)
                    .setParameter("min", min)
                    .setParameter("max", max)
                    .getResultList();

            System.out.println("\n--- Locações no intervalo ---");
            for (Locacao l : locacoes) {
                System.out.printf("%s - R$ %.2f\n", l.getNomeLocatario(), l.getValorTotal());
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Entrada inválida.");
        }
    }

    private static void relatorioLike(Scanner scanner, EntityManager em) {
        System.out.print("Digite parte do nome: ");
        String parteNome = scanner.nextLine();

        List<Locacao> locacoes = em.createQuery(
                        "SELECT l FROM Locacao l WHERE LOWER(l.nomeLocatario) LIKE :nome", Locacao.class)
                .setParameter("nome", "%" + parteNome.toLowerCase() + "%")
                .getResultList();

        System.out.println("\n--- Resultados ---");
        for (Locacao l : locacoes) {
            System.out.printf("%s - %s - R$ %.2f\n",
                    l.getNomeLocatario(), l.getVeiculo().getModelo(), l.getValorTotal());
        }
    }

    private static void relatorioAgregado(EntityManager em) {
        Object[] resultado = em.createQuery(
                        "SELECT COUNT(l), AVG(l.horas) FROM Locacao l", Object[].class)
                .getSingleResult();

        long totalLocacoes = (long) resultado[0];
        double mediaHoras = (double) resultado[1];

        System.out.println("\n--- Dados Agregados ---");
        System.out.println("Total de locações: " + totalLocacoes);
        System.out.printf("Média de horas por locação: %.2f\n", mediaHoras);
    }
}
