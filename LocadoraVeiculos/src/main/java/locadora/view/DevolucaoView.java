package locadora.view;

import locadora.core.Locacao;
import locadora.dao.LocacaoDAO;
import locadora.dao.VeiculoDAO;
import locadora.veiculo.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DevolucaoView extends JFrame {
    private final LocacaoDAO locacaoDAO = new LocacaoDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final DefaultTableModel modelo = new DefaultTableModel();

    public DevolucaoView() {
        setTitle("Registrar Devolução");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Lista de locações ativas
        modelo.setColumnIdentifiers(new Object[]{
                "Locação ID", "Locatário", "Veículo ID", "Modelo", "Valor Total", "Data"
        });
        JTable tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painel = new JPanel();
        JButton btnDevolver  = new JButton("Devolver Selecionada");
        JButton btnAtualizar = new JButton("Atualizar Lista");
        painel.add(btnDevolver);
        painel.add(btnAtualizar);
        add(painel, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarLocacoes());
        btnDevolver.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma locação para devolver.");
                return;
            }
            Long locId = (Long) modelo.getValueAt(linha, 0);
            devolverLocacao(locId);
            carregarLocacoes();
        });

        carregarLocacoes();
        setVisible(true);
    }

    private void carregarLocacoes() {
        modelo.setRowCount(0);
        List<Locacao> lista = locacaoDAO.listarTodos();
        for (Locacao l : lista) {
            modelo.addRow(new Object[]{
                    l.getId(),
                    l.getNomeLocatario(),
                    l.getVeiculo().getIdentificador(),
                    l.getVeiculo().getModelo(),
                    String.format("R$ %.2f", l.getValorTotal()),
                    l.getDataHora()
            });
        }
    }

    private void devolverLocacao(Long locId) {
        Locacao loc = locacaoDAO.buscarPorId(locId);
        if (loc == null) {
            JOptionPane.showMessageDialog(this, "Locação não encontrada.");
            return;
        }
        // 1) Aumenta estoque do veículo
        Veiculo v = loc.getVeiculo();
        v.aumentarEstoque();
        veiculoDAO.atualizar(v);
        // 2) Remove locação + pagamento
        locacaoDAO.removerComPagamento(locId);
        JOptionPane.showMessageDialog(this, "Devolução efetuada com sucesso.");
    }
}
