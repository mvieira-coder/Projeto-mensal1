package locadora.veiculo;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Veiculo implements Alugavel {
    @Id
    private String identificador;
    private String modelo;
    private double precoHora;
    private boolean disponivel;

    public Veiculo() {} // Construtor padrão obrigatório para JPA

    public Veiculo(String identificador, String modelo, double precoHora) {
        this.identificador = identificador;
        this.modelo = modelo;
        this.precoHora = precoHora;
        this.disponivel = true;
    }

    // Getters e Setters...
    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getPrecoHora() { return precoHora; }
    public void setPrecoHora(double precoHora) { this.precoHora = precoHora; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public void locar() { this.disponivel = false; }
    public void devolver() { this.disponivel = true; }

    @Override
    public double calcularPreco(int horas) {
        return horas * precoHora;
    }
}
