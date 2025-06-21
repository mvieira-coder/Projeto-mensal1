package locadora.view;

import locadora.core.Locacao;
import locadora.dao.LocacaoDAO;
import locadora.dao.VeiculoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class LocacaoView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private final LocacaoDAO locacaoDAO;
    private final VeiculoDAO veiculoDAO;

    public LocacaoView() {
        locacaoDAO = new LocacaoDAO();
        veiculoDAO = new VeiculoDAO();

        setTitle("Cadastro de Locações");
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{
                "ID", "Locatário", "CPF", "Telefone",
                "Veículo", "Modelo", "Horas", "Valor Total", "Data"
        }, 0);
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton btnAdicionar  = new JButton("Adicionar");
        JButton btnExcluir    = new JButton("Excluir");
        JButton btnAtualizar  = new JButton("Atualizar Lista");

        // painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        // listeners
        btnAdicionar.addActionListener(e -> adicionarLocacao());
        btnExcluir  .addActionListener(e -> excluirLocacao());
        btnAtualizar.addActionListener(e -> carregarLocacoes());

        carregarLocacoes();
        setVisible(true);
    }

    private void carregarLocacoes() {
        modelo.setRowCount(0);
        List<Locacao> locacoes = locacaoDAO.listarTodos();
        for (Locacao l : locacoes) {
            modelo.addRow(new Object[]{
                    l.getId(),
                    l.getNomeLocatario(),
                    l.getCpf(),
                    l.getTelefone(),
                    l.getVeiculo().getClass().getSimpleName(),
                    l.getVeiculo().getModelo(),
                    l.getHoras(),
                    String.format("R$ %.2f", l.getValorTotal()),
                    l.getDataHora()
            });
        }
    }

    private void adicionarLocacao() {
        try {
            String nome     = JOptionPane.showInputDialog(this, "Nome do Locatário:");
            String cpf      = JOptionPane.showInputDialog(this, "CPF:");
            String telefone = JOptionPane.showInputDialog(this, "Telefone:");

            List<locadora.veiculo.Veiculo> veiculos = veiculoDAO.listarTodos();
            locadora.veiculo.Veiculo veiculoSelecionado = (locadora.veiculo.Veiculo)
                    JOptionPane.showInputDialog(
                            this,
                            "Selecione o Veículo:",
                            "Veículo",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            veiculos.toArray(),
                            veiculos.get(0)
                    );

            String horasStr   = JOptionPane.showInputDialog(this, "Horas de locação:");
            int horas         = Integer.parseInt(horasStr);
            double valorTotal = veiculoSelecionado.getPrecoHora() * horas;

            Locacao loc = new Locacao();
            loc.setNomeLocatario(nome);
            loc.setCpf(cpf);
            loc.setTelefone(telefone);
            loc.setHoras(horas);
            loc.setValorTotal(valorTotal);
            loc.setDataHora(LocalDate.now());
            loc.setVeiculo(veiculoSelecionado);

            locacaoDAO.inserir(loc);
            JOptionPane.showMessageDialog(this, "Locação cadastrada com sucesso!");
            carregarLocacoes();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar locação: " + ex.getMessage());
        }
    }

    private void excluirLocacao() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma locação para excluir.");
            return;
        }

        Long id = (Long) modelo.getValueAt(linha, 0);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir essa locação (e o pagamento, se houver)?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                locacaoDAO.removerComPagamento(id);
                JOptionPane.showMessageDialog(this, "Locação (e pagamento) excluída com sucesso.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
            carregarLocacoes();
        }
    }
}
