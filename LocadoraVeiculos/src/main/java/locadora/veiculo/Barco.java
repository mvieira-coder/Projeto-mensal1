package locadora.veiculo;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Barco")
public class Barco extends Veiculo {

    public Barco() {}

    public Barco(String identificador, String modelo, double precoHora, int quantidade) {
        super(identificador, modelo, precoHora, quantidade);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Barco - ID: " + getIdentificador() +
                ", Modelo: " + getModelo() +
                ", Preço por Hora: R$ " + getPrecoHora() +
                ", Quantidade: " + getQuantidade());
    }
}
