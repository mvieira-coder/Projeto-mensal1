package locadora.view;

import locadora.dao.LocacaoDAO;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class RelatorioAgregView extends JFrame {

    private final LocacaoDAO dao = new LocacaoDAO();
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public RelatorioAgregView() {
        setTitle("Estatísticas de Locações");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        long totalLocacoes   = dao.contarLocacoes();
        double mediaHoras    = dao.mediaHoras();
        double somaValorTotal= dao.somaValores();

        JLabel lblTotal = new JLabel("Total de Locações: " + totalLocacoes);
        JLabel lblMedia = new JLabel("Média de Horas: " + df.format(mediaHoras));
        JLabel lblSoma  = new JLabel("Valor Total Arrecadado: R$ " + df.format(somaValorTotal));

        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        lblMedia.setHorizontalAlignment(SwingConstants.CENTER);
        lblSoma.setHorizontalAlignment(SwingConstants.CENTER);

        add(lblTotal);
        add(lblMedia);
        add(lblSoma);

        setVisible(true);
    }
}
