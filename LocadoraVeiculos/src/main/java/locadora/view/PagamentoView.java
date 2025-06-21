package locadora.view;

import locadora.core.Pagamento;
import locadora.core.Locacao;
import locadora.dao.LocacaoDAO;
import locadora.dao.PagamentoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PagamentoView extends JFrame {

    private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
    private final LocacaoDAO locacaoDAO = new LocacaoDAO();
    private final DefaultTableModel modelo = new DefaultTableModel();

    public PagamentoView() {
        setTitle("Gerenciar Pagamentos");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo.setColumnIdentifiers(new Object[]{"ID", "Locação", "Valor Pago", "Forma", "Data", "Status"});
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        JButton btnAdicionar = new JButton("Registrar Pagamento");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarPagamento());
        btnAtualizar.addActionListener(e -> carregarPagamentos());

        carregarPagamentos();
        setVisible(true);
    }

    private void carregarPagamentos() {
        modelo.setRowCount(0);
        List<Pagamento> pagamentos = pagamentoDAO.listarTodos();
        for (Pagamento p : pagamentos) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    "Locação ID: " + p.getLocacao().getId(),
                    p.getValorPago(),
                    p.getFormaPagamento(),
                    p.getDataPagamento(),
                    p.getStatus()
            });
        }
    }

    private void adicionarPagamento() {
        try {
            List<Locacao> locacoes = locacaoDAO.listarTodos();
            Locacao locacaoSelecionada = (Locacao) JOptionPane.showInputDialog(
                    this, "Selecione a Locação:", "Locação",
                    JOptionPane.PLAIN_MESSAGE, null, locacoes.toArray(), locacoes.get(0)
            );

            String valorStr = JOptionPane.showInputDialog(this, "Valor pago:");
            double valor = Double.parseDouble(valorStr);

            String[] formas = {"PIX", "Cartão", "Dinheiro"};
            String forma = (String) JOptionPane.showInputDialog(this, "Forma de pagamento:", "Forma",
                    JOptionPane.PLAIN_MESSAGE, null, formas, formas[0]);

            String[] statusOptions = {"PAGO", "PENDENTE"};
            String status = (String) JOptionPane.showInputDialog(this, "Status do pagamento:", "Status",
                    JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);

            Pagamento pagamento = new Pagamento();
            pagamento.setLocacao(locacaoSelecionada);
            pagamento.setValorPago(valor);
            pagamento.setFormaPagamento(forma);
            pagamento.setStatus(status);
            pagamento.setDataPagamento(LocalDate.now());

            pagamentoDAO.inserir(pagamento);
            carregarPagamentos();

            JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar pagamento: " + ex.getMessage());
        }
    }
}
