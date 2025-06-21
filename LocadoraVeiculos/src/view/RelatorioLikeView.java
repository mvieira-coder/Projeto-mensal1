package locadora.view;

import locadora.dao.LocacaoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioLikeView extends JFrame {

    private final LocacaoDAO dao = new LocacaoDAO();
    private final DefaultTableModel modelo = new DefaultTableModel();

    public RelatorioLikeView() {
        setTitle("Buscar por Nome (LIKE)");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // pedir o nome ao usuário
        String filtro = JOptionPane.showInputDialog(this, "Digite parte do nome do locatário:");
        if (filtro == null || filtro.trim().isEmpty()) {
            dispose();
            return;
        }

        // configurar colunas
        modelo.setColumnIdentifiers(new Object[]{
                "Locatário", "Modelo do Veículo", "Data", "Valor Total"
        });
        JTable tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarDados(filtro.trim());
        setVisible(true);
    }

    private void carregarDados(String filtro) {
        modelo.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Object[]> list = dao.buscarPorNome(filtro);
        for (Object[] row : list) {
            String nome    = (String) row[0];
            String modeloV = (String) row[1];
            String data    = ((java.time.LocalDate) row[2]).format(fmt);
            Double valor   = (Double) row[3];
            modelo.addRow(new Object[]{ nome, modeloV, data, String.format("R$ %.2f", valor) });
        }
    }
}
