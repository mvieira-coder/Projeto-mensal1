import java.util.ArrayList;  // Para manipular listas de veículos
import java.util.List;       // Para a interface List
import java.util.Scanner;    // Para capturar entrada do usuário no console

class Locadora {
    private List<Veiculo> veiculos = new ArrayList<>();
    
    public Locadora() {
        veiculos.add(new Carro("001", "Sedan", 15.0));
        veiculos.add(new Moto("002", "Esportiva", 10.0));
        veiculos.add(new BicicletaEletrica("003", "E-Bike Pro", 5.0));
        veiculos.add(new PatineteEletrico("004", "Scooter X", 3.0));
        veiculos.add(new Barco("005", "Lancha Turbo", 50.0));
    }
    
    public void listarVeiculos() {
        System.out.println("\n=== VEÍCULOS DISPONÍVEIS ===");
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).isDisponivel()) {
                System.out.print((i + 1) + ". ");
                veiculos.get(i).exibirDetalhes();
            }
        }
    }
    
    public Veiculo getVeiculo(int index) {
        if (index >= 0 && index < veiculos.size() && veiculos.get(index).isDisponivel()) {
            return veiculos.get(index);
        }
        return null;
    }
}