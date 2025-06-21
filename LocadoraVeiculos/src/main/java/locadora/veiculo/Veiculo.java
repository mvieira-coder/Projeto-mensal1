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
    private int quantidade;

    public Veiculo() {}

    public Veiculo(String identificador, String modelo, double precoHora, int quantidade) {
        this.identificador = identificador;
        this.modelo = modelo;
        this.precoHora = precoHora;
        this.quantidade = quantidade;
    }

    public String getIdentificador() { return identificador; }
    public String getModelo() { return modelo; }
    public double getPrecoHora() { return precoHora; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public void reduzirEstoque() {
        if (quantidade > 0) quantidade--;
    }

    public void aumentarEstoque() {
        quantidade++;
    }

    public boolean isDisponivel() {
        return quantidade > 0;
    }

    @Override
    public double calcularPreco(int horas) {
        return horas * precoHora;
    }

    public abstract void exibirDetalhes();
}
