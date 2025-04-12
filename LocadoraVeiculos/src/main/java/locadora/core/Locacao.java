package locadora.core;

import locadora.veiculo.Veiculo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeLocatario;
    private int horas;
    private double valorTotal;
    private LocalDateTime dataHora;

    @ManyToOne
    private Veiculo veiculo;

    public Locacao() {}

    public Locacao(String nomeLocatario, int horas, double valorTotal, Veiculo veiculo) {
        this.nomeLocatario = nomeLocatario;
        this.horas = horas;
        this.valorTotal = valorTotal;
        this.veiculo = veiculo;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNomeLocatario() { return nomeLocatario; }
    public int getHoras() { return horas; }
    public double getValorTotal() { return valorTotal; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Veiculo getVeiculo() { return veiculo; }

    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
}
