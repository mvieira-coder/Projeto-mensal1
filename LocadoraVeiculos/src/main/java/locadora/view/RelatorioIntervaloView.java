package locadora.view;

import locadora.dao.LocacaoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RelatorioIntervaloView extends JFrame {

    private final LocacaoDAO dao = new LocacaoDAO();
    private final DefaultTableModel modelo = new DefaultTableModel();

    public RelatorioIntervaloView() {
        setTitle("Relatório por Intervalo de Datas");
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // painel superior para entrada de datas
        JPanel painel = new JPanel(new FlowLayout());
        painel.add(new JLabel("Data Início (dd/MM/yyyy):"));
        JTextField txtInicio = new JTextField(8);
        painel.add(txtInicio);

        painel.add(new JLabel("Data Fim (dd/MM/yyyy):"));
        JTextField txtFim = new JTextField(8);
        painel.add(txtFim);

        JButton btnOk = new JButton("Filtrar");
        painel.add(btnOk);
        add(painel, BorderLayout.NORTH);

        // tabela no centro
        modelo.setColumnIdentifiers(new Object[]{
                "Locatário", "Modelo do Veículo", "Data", "Valor Total"
        });
        JTable tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // ação do botão
        btnOk.addActionListener(e -> {
            String in = txtInicio.getText().trim();
            String fn = txtFim.getText().trim();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate dataInicio = LocalDate.parse(in, fmt);
                LocalDate dataFim    = LocalDate.parse(fn, fmt);
                carregarDados(dataInicio, dataFim, fmt);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Formato inválido! Use dd/MM/yyyy.",
                        "Erro de Data", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void carregarDados(LocalDate inicio, LocalDate fim, DateTimeFormatter fmt) {
        modelo.setRowCount(0);
        List<Object[]> list = dao.buscarPorIntervalo(inicio, fim);
        for (Object[] row : list) {
            String nome    = (String) row[0];
            String modeloV = (String) row[1];
            String data    = ((LocalDate) row[2]).format(fmt);
            Double valor   = (Double) row[3];
            modelo.addRow(new Object[]{
                    nome,
                    modeloV,
                    data,
                    String.format("R$ %.2f", valor)
            });
        }
    }
}
