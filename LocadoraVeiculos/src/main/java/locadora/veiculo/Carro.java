package locadora.veiculo;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Carro")
public class Carro extends Veiculo {

    public Carro() {}

    public Carro(String identificador, String modelo, double precoHora, int quantidade) {
        super(identificador, modelo, precoHora, quantidade);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Carro - ID: " + getIdentificador() +
                ", Modelo: " + getModelo() +
                ", Preço por Hora: R$ " + getPrecoHora() +
                ", Quantidade: " + getQuantidade());
    }
}
