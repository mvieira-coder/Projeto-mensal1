package locadora.app;

import java.util.Scanner;
import locadora.core.Locadora;
import locadora.core.Pagamento;
import locadora.veiculo.Veiculo;

public class SistemaLocacao {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

  
        Locadora locadora = new Locadora();
        
        // 3. Loop principal do sistema, que mantém o programa em execução até que o usuário escolha sair.
        while (true) {
            // 4. Exibe o menu principal com as opções disponíveis.
            System.out.println("==== TOTEM DE LOCAÇÃO ====");
            System.out.println("1. Alugar Veículo");
            System.out.println("2. Devolver Veículo");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

   
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do Scanner após ler um número.
            
     
            if (opcao == 1) {
             
           
                locadora.listarVeiculos();

            
                System.out.print("\nDigite o número do veículo que deseja locar: ");
                int escolha = scanner.nextInt() - 1;
                scanner.nextLine(); 

             
                Veiculo veiculoSelecionado = locadora.getVeiculo(escolha);

               
                if (veiculoSelecionado != null) {
                 
                    System.out.print("\nDigite seu nome: ");
                    String nomeLocatario = scanner.nextLine();

                  
                    System.out.print("Digite a quantidade de horas de locação: ");
                    int horas = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    double total = veiculoSelecionado.calcularPreco(horas);

                    System.out.println("\nLocação realizada com sucesso! Valor total: R$ " + total);

                   
                    veiculoSelecionado.locar();

                  
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

                    // . Processa o pagamento.
                    pagamento.processarPagamento();
                }
            } else if (opcao == 2) {
            

               
                System.out.print("\nDigite o ID do veículo que deseja devolver: ");
                String idDevolucao = scanner.nextLine();

               
                locadora.devolverVeiculo(idDevolucao);
            } else if (opcao == 3) {
            
                break; // Encerra o loop e finaliza o programa.
            }
        }
    }
}