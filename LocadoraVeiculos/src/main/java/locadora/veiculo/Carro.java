package locadora.veiculo;
import javax.persistence.*;

@Entity
@DiscriminatorValue("Carro")


public class Carro extends Veiculo {
  
    public Carro(String identificador, String modelo, double precoHora) {
     
        super(identificador, modelo, precoHora);
    }

    @Override
    public void exibirDetalhes() {
        // Exibe os detalhes específicos do carro.
        System.out.println("Carro - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}