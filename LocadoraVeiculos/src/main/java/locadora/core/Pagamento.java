package locadora.core;

public class Pagamento {
    private double valor;
    private String forma;

    public Pagamento(double valor) {
        this.valor = valor;
    }

    public void selecionarFormaPagamento(String forma) {
        this.forma = forma;
    }

    public void processarPagamento() {
        System.out.println("Pagamento de R$ " + valor + " via " + forma + " realizado com sucesso.");
    }
}
