// src/main/java/locadora/view/RelatorioJoinView.java
package locadora.view;

import locadora.dao.LocacaoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioJoinView extends JFrame {

    private final LocacaoDAO dao = new LocacaoDAO();
    private final DefaultTableModel modelo = new DefaultTableModel();

    public RelatorioJoinView() {
        setTitle("Relatório de Locações (JOIN)");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Colunas: Locatário, Modelo, Data, Valor Total
        modelo.setColumnIdentifiers(new Object[]{
                "Locatário", "Modelo do Veículo", "Data", "Valor Total"
        });
        JTable tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarDados();
        setVisible(true);
    }

    private void carregarDados() {
        modelo.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Object[]> list = dao.listarLocacoesComVeiculo();
        for (Object[] row : list) {
            String nome    = (String) row[0];
            String modeloV = (String) row[1];
            String data    = ((java.time.LocalDate) row[2]).format(fmt);
            Double valor   = (Double) row[3];
            modelo.addRow(new Object[]{ nome, modeloV, data, String.format("R$ %.2f", valor) });
        }
    }
}
