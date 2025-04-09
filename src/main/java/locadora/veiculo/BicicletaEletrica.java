package locadora.veiculo;

public class BicicletaEletrica extends Veiculo {
    
    public BicicletaEletrica(String identificador, String modelo, double precoHora) {
        
        
        super(identificador, modelo, precoHora);
    }
  
    @Override
    public void exibirDetalhes() {
        // Exibe os detalhes específicos da bicicleta elétrica.
        System.out.println("Bicicleta Elétrica - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}