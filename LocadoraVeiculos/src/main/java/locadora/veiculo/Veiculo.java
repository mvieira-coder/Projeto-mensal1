package locadora.veiculo;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Veiculo implements Alugavel {

    @Id
    private String identificador;
    private String modelo;
    private double precoHora;
    private boolean disponivel;

    public Veiculo() {} // Obrigatório pro JPA

    public Veiculo(String identificador, String modelo, double precoHora) {
        this.identificador = identificador;
        this.modelo = modelo;
        this.precoHora = precoHora;
        this.disponivel = true;
    }

    // Getters e setters
    public String getIdentificador() { return identificador; }
    public String getModelo() { return modelo; }
    public double getPrecoHora() { return precoHora; }
    public boolean isDisponivel() { return disponivel; }

    public void locar() { this.disponivel = false; }
    public void devolver() { this.disponivel = true; }

    @Override
    public double calcularPreco(int horas) {
        return horas * precoHora;
    }

    // Método abstrato que precisa ser implementado nas subclasses
    public abstract void exibirDetalhes();
}
