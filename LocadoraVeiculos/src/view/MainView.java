package locadora.view;

import javax.swing.*;
import java.awt.*;

import locadora.view.DevolucaoView;






public class MainView extends JFrame {

    public MainView() {
        setTitle("Sistema de Locadora de Veículos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnVeiculos = new JButton("Gerenciar Veículos");
        JButton btnLocacoes = new JButton("Gerenciar Locações");
        JButton btnPagamentos = new JButton("Gerenciar Pagamentos");
        JButton btnDevolucao = new JButton("Registrar Devolução");
        JButton btnRelatorios = new JButton("Relatórios");
        JButton btnSair = new JButton("Sair");

        btnVeiculos.addActionListener(e -> new VeiculoView());
        btnLocacoes.addActionListener(e -> new LocacaoView());
        btnPagamentos.addActionListener(e -> new PagamentoView());
        btnDevolucao.addActionListener(e -> new DevolucaoView());
        btnRelatorios.addActionListener(e -> new RelatoriosView());
        btnSair.addActionListener(e -> System.exit(0));

        add(btnVeiculos);
        add(btnLocacoes);
        add(btnPagamentos);
        add(btnDevolucao);
        add(btnRelatorios);
        add(btnSair);

        setVisible(true);
    }
}
