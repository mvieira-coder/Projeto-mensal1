package locadora.core;

public class Pagamento {
    private double valorTotal;
    private String formaPagamento;

    public Pagamento(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void selecionarFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void processarPagamento() {
        System.out.println("\nProcessando pagamento...");
        System.out.println("Valor total: R$ " + valorTotal);
        System.out.println("Forma de pagamento: " + formaPagamento);
        System.out.println("Pagamento realizado com sucesso!");
    }
}