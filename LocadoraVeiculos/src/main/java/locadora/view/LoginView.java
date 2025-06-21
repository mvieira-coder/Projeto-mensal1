package locadora.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Acesso ao Sistema");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton btnAdmin = new JButton("Entrar como Funcionário");
        JButton btnCliente = new JButton("Entrar como Cliente");

        btnAdmin.addActionListener(e -> {
            dispose();
            new MainView(); // A tela completa já pronta que você já tem
        });

        btnCliente.addActionListener(e -> {
            dispose();
            new ClienteView(); // Essa vamos criar no próximo passo
        });

        add(btnAdmin);
        add(btnCliente);
        setVisible(true);
    }
}
