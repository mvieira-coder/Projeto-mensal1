
abstract class Veiculo implements Alugavel {
    // Atributos privados da classe Veiculo:
    private String identificador; 
    private String modelo;       
    private double precoHora;    
    private boolean disponivel;  

    // Construtor da classe Veiculo:
    // É chamado quando um objeto de uma subclasse (como Carro ou Moto) é criado.
    public Veiculo(String identificador, String modelo, double precoHora) {
        this.identificador = identificador; 
        this.modelo = modelo;               
        this.precoHora = precoHora;        
        this.disponivel = true;             
    }
    
 
    public String getIdentificador() { return identificador; } 
    public String getModelo() { return modelo; }               
    public double getPrecoHora() { return precoHora; }         
    public boolean isDisponivel() { return disponivel; }      
    

    public void locar() { this.disponivel = false; } // 
    public void devolver() { this.disponivel = true; } 
    
 
    @Override
    public double calcularPreco(int horas) {
        return horas * precoHora; 
    }
}