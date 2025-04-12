package locadora.veiculo;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Carro")
public class Carro extends Veiculo {

    public Carro() {} // Necessário pro Hibernate

    public Carro(String identificador, String modelo, double precoHora) {
        super(identificador, modelo, precoHora);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Carro - ID: " + getIdentificador() +
                ", Modelo: " + getModelo() +
                ", Preço por Hora: R$ " + getPrecoHora());
    }
}
