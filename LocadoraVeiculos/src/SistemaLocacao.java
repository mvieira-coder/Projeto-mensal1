import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner; 


public class SistemaLocacao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locadora locadora = new Locadora();
        
        while (true) {
            System.out.println("==== TOTEM DE LOCAÇÃO ====");
            System.out.println("1. Alugar Veículo");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
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
                    
                    System.out.print("\nEscolha a forma de pagamento (Cartão/Dinheiro/Pix): ");
                    String pagamento = scanner.nextLine();
                    
                    veiculoSelecionado.locar();
                    System.out.println("==== CONFIRMAÇÃO DE LOCAÇÃO ====");
                    System.out.println("Locatário: " + nomeLocatario);
                    veiculoSelecionado.exibirDetalhes();
                    System.out.println("Horas locadas: " + horas);
                    System.out.println("Valor total: R$ " + total);
                    System.out.println("Forma de pagamento: " + pagamento);
                    System.out.println("\nLocação realizada com sucesso!");
                }
            } else if (opcao == 2) {
                break;
            }
        }
    }
}
