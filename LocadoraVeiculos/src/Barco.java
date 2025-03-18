// Define a classe Barco, que herda da classe Veiculo.
// "extends Veiculo" significa que Barco é uma subclasse de Veiculo.
class Barco extends Veiculo {
   
    public Barco(String identificador, String modelo, double precoHora) {
        // Chama o construtor da classe Veiculo (a classe pai) usando "super".
        // Isso inicializa os atributos da classe Veiculo com os valores passados.
        super(identificador, modelo, precoHora);
    }
    
   
    @Override
    public void exibirDetalhes() {
        // Exibe os detalhes específicos do barco.
        System.out.println("Barco - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}