// Define a classe Carro, que herda da classe Veiculo.
// "extends Veiculo" significa que Carro é uma subclasse de Veiculo.
class Carro extends Veiculo {
   
    public Carro(String identificador, String modelo, double precoHora) {
       
        super(identificador, modelo, precoHora);
    }
    
    
    @Override
    public void exibirDetalhes() {
       
        System.out.println("Carro - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}