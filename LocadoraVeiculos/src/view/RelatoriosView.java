// src/main/java/locadora/view/RelatoriosView.java
package locadora.view;

import javax.swing.*;
import java.awt.*;

public class RelatoriosView extends JFrame {

    public RelatoriosView() {
        setTitle("Relatórios");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnJoin     = new JButton("1) Locações (JOIN)");
        JButton btnLike     = new JButton("2) Buscar por Nome (LIKE)");
        JButton btnIntervalo= new JButton("3) Intervalo de Datas");
        JButton btnAgreg    = new JButton("4) Estatísticas (Agregação)");

        btnJoin.addActionListener(e -> new RelatorioJoinView());
        btnLike.addActionListener(e -> new RelatorioLikeView());
        btnIntervalo.addActionListener(e -> new RelatorioIntervaloView());
        btnAgreg.addActionListener(e -> new RelatorioAgregView());

        add(btnJoin);
        add(btnLike);
        add(btnIntervalo);
        add(btnAgreg);

        setVisible(true);
    }
}
