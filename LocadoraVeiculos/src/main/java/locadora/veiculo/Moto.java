package locadora.veiculo;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Moto")
public class Moto extends Veiculo {

    public Moto() {}

    public Moto(String identificador, String modelo, double precoHora) {
        super(identificador, modelo, precoHora);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Moto - ID: " + getIdentificador() +
                ", Modelo: " + getModelo() +
                ", Preço por Hora: R$ " + getPrecoHora());
    }
}
