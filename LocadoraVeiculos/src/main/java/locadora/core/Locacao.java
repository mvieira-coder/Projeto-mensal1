package locadora.core;

import locadora.veiculo.Veiculo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeLocatario;
    private String cpf;
    private String telefone;
    private int horas;
    private double valorTotal;
    private LocalDate dataHora;

    @ManyToOne
    private Veiculo veiculo;

    // Getters e Setters

    public Long getId() { return id; }

    public String getNomeLocatario() { return nomeLocatario; }
    public void setNomeLocatario(String nomeLocatario) { this.nomeLocatario = nomeLocatario; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public int getHoras() { return horas; }
    public void setHoras(int horas) { this.horas = horas; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public LocalDate getDataHora() { return dataHora; }
    public void setDataHora(LocalDate dataHora) { this.dataHora = dataHora; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
}
