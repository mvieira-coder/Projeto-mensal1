package com.locadora.core;

// Importa as classes de veículos (Carro, Moto, etc.) e a classe Veiculo.
import com.locadora.veiculo.Carro;
import com.locadora.veiculo.Moto;
import com.locadora.veiculo.BicicletaEletrica;
import com.locadora.veiculo.PatineteEletrico;
import com.locadora.veiculo.Barco;
import java.util.ArrayList;
import java.util.List;
import com.locadora.veiculo.Veiculo;

// Classe Locadora, que gerencia os veículos disponíveis para locação.
public class Locadora {
    // Lista de veículos disponíveis na locadora.
    private List<Veiculo> veiculos = new ArrayList<>();

    // Quando uma Locadora é criada, ela já começa com alguns veículos cadastrados.
    public Locadora() {
        // Adiciona veículos à lista de veículos.
        veiculos.add(new Carro("001", "Sedan", 15.0));       // Carro Sedan
        veiculos.add(new Moto("002", "Esportiva", 10.0));    // Moto Esportiva
        veiculos.add(new BicicletaEletrica("003", "E-Bike Pro", 5.0)); // Bicicleta Elétrica
        veiculos.add(new PatineteEletrico("004", "Scooter X", 3.0));   // Patinete Elétrico
        veiculos.add(new Barco("005", "Lancha Turbo", 50.0));          // Barco Lancha Turbo
    }

    // Método para listar os veículos disponíveis.
    public void listarVeiculos() {
        System.out.println("\n=== VEÍCULOS DISPONÍVEIS ===");
        // Percorre a lista de veículos.
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).isDisponivel()) {
                System.out.print((i + 1) + ". ");
                veiculos.get(i).exibirDetalhes();
            }
        }
    }

    // Método para obter um veículo pelo índice.
    public Veiculo getVeiculo(int index) {
        if (index >= 0 && index < veiculos.size() && veiculos.get(index).isDisponivel()) {
            return veiculos.get(index); 
        }
        return null; 
    }

    public void devolverVeiculo(String identificador) {
        // Percorre a lista de veículos.
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getIdentificador().equals(identificador) && !veiculo.isDisponivel()) {
                // Marca o veículo como disponível novamente.
                veiculo.devolver();
                System.out.println("Veículo devolvido com sucesso: " + veiculo.getModelo());
                return; 
            }
        }
        System.out.println("Veículo não encontrado ou já está disponível.");
    }
}