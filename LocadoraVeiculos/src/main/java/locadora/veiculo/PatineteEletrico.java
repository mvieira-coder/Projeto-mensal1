package locadora.veiculo;
import javax.persistence.*;

@Entity
@DiscriminatorValue("PatineteEletrico")

public class PatineteEletrico extends Veiculo {
    public PatineteEletrico(String identificador, String modelo, double precoHora) {
        super(identificador, modelo, precoHora);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Patinete Elétrico - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}
