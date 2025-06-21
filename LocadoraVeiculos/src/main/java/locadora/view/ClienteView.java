package locadora.view;

import locadora.dao.LocacaoDAO;
import locadora.dao.VeiculoDAO;
import locadora.core.Locacao;
import locadora.veiculo.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import locadora.core.Pagamento;
import locadora.dao.PagamentoDAO;



public class ClienteView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private VeiculoDAO veiculoDAO;
    private LocacaoDAO locacaoDAO;

    public ClienteView() {
        veiculoDAO = new VeiculoDAO();
        locacaoDAO = new LocacaoDAO();

        setTitle("Área do Cliente");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"Identificador", "Tipo", "Modelo", "Preço Hora", "Disponível"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnAlugar = new JButton("Solicitar Locação");
        JButton btnAtualizar = new JButton("Atualizar Lista");
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAlugar);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnAlugar.addActionListener(e -> solicitarLocacao());
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
                    v.isDisponivel() ? "Sim" : "Não"
            });
        }
    }

    private void solicitarLocacao() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para alugar.");
            return;
        }

        String identificador = (String) modelo.getValueAt(linha, 0);
        Veiculo veiculoSelecionado = veiculoDAO.buscarPorId(identificador);

        // VERIFICA SE TEM ESTOQUE:
        if (veiculoSelecionado.getQuantidade() <= 0) {
            JOptionPane.showMessageDialog(this, "Não há unidades disponíveis deste modelo.");
            return;
        }

        String nome = JOptionPane.showInputDialog(this, "Digite o nome do locatário:");
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF:");
        String telefone = JOptionPane.showInputDialog(this, "Digite o telefone:");
        String horasStr = JOptionPane.showInputDialog(this, "Quantas horas deseja alugar?");
        int horas = Integer.parseInt(horasStr);

        double total = veiculoSelecionado.getPrecoHora() * horas;

        Locacao locacao = new Locacao();
        locacao.setNomeLocatario(nome);
        locacao.setCpf(cpf);
        locacao.setTelefone(telefone);
        locacao.setHoras(horas);
        locacao.setValorTotal(total);
        locacao.setDataHora(LocalDate.now());
        locacao.setVeiculo(veiculoSelecionado);

        try {
            locacaoDAO.inserir(locacao);

            // ATUALIZA O ESTOQUE:
            veiculoSelecionado.reduzirEstoque();
            veiculoDAO.atualizar(veiculoSelecionado);

            JOptionPane.showMessageDialog(this, "Locação realizada com sucesso!\nValor total: R$ " + total);

            registrarPagamento(locacao);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar locação: " + e.getMessage());
        }
    }


    private void registrarPagamento(Locacao locacao) {
        try {
            String valorStr = JOptionPane.showInputDialog(this, "Informe o valor a pagar:", locacao.getValorTotal());
            double valor = Double.parseDouble(valorStr);

            String[] formas = {"PIX", "Cartão", "Dinheiro"};
            String forma = (String) JOptionPane.showInputDialog(this, "Forma de pagamento:", "Forma",
                    JOptionPane.PLAIN_MESSAGE, null, formas, formas[0]);

            Pagamento pagamento = new Pagamento();
            pagamento.setLocacao(locacao);
            pagamento.setValorPago(valor);
            pagamento.setFormaPagamento(forma);
            pagamento.setStatus("PAGO"); // aqui o cliente já está pagando
            pagamento.setDataPagamento(LocalDate.now());

            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            pagamentoDAO.inserir(pagamento);

            JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso!");
            carregarVeiculos(); // atualiza a lista de veículos disponíveis

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar pagamento: " + ex.getMessage());
        }
    }


}
