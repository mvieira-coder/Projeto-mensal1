package locadora.view;

import locadora.dao.VeiculoDAO;
import locadora.veiculo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VeiculoView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private VeiculoDAO veiculoDAO;

    public VeiculoView() {
        veiculoDAO = new VeiculoDAO();

        setTitle("Cadastro de Veículos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"Identificador", "Tipo", "Modelo", "Preço Hora", "Quantidade", "Disponível"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarVeiculo());
        btnExcluir.addActionListener(e -> excluirVeiculo());
        btnAtualizar.addActionListener(e -> carregarVeiculos());

        carregarVeiculos();
        setVisible(true);
    }

    private void carregarVeiculos() {
        modelo.setRowCount(0);
        List<Veiculo> veiculos = veiculoDAO.listarTodos();
        for (Veiculo v : veiculos) {
            modelo.addRow(new Object[]{
                    v.getIdentificador(),
                    v.getClass().getSimpleName(),
                    v.getModelo(),
                    v.getPrecoHora(),
                    v.getQuantidade(),
                    v.isDisponivel() ? "Sim" : "Não"
            });
        }
    }

    private void adicionarVeiculo() {
        String[] tipos = {"Carro", "Moto", "Barco"};
        String tipoSelecionado = (String) JOptionPane.showInputDialog(this, "Selecione o tipo:", "Tipo de Veículo",
                JOptionPane.PLAIN_MESSAGE, null, tipos, tipos[0]);

        if (tipoSelecionado == null) return;

        String identificador = JOptionPane.showInputDialog(this, "Identificador:");
        String modeloVeiculo = JOptionPane.showInputDialog(this, "Modelo:");
        double precoHora = Double.parseDouble(JOptionPane.showInputDialog(this, "Preço por hora:"));
        int quantidade = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade disponível:"));

        Veiculo v;
        if (tipoSelecionado.equals("Carro")) {
            v = new Carro(identificador, modeloVeiculo, precoHora, quantidade);
        } else if (tipoSelecionado.equals("Moto")) {
            v = new Moto(identificador, modeloVeiculo, precoHora, quantidade);
        } else {
            v = new Barco(identificador, modeloVeiculo, precoHora, quantidade);
        }

        try {
            veiculoDAO.inserir(v);
            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
            carregarVeiculos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void excluirVeiculo() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para excluir.");
            return;
        }
        String identificador = (String) modelo.getValueAt(linha, 0);
        try {
            veiculoDAO.remover(identificador);
            JOptionPane.showMessageDialog(this, "Veículo removido com sucesso!");
            carregarVeiculos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }
}
