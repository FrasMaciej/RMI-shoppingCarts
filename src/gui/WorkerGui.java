package gui;

import java.awt.*;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import managementElements.Worker;
import shoppingCarts.interfaces.IRegistration;

public class WorkerGui {

    private JFrame frame;
    private Worker worker;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                WorkerGui window = new WorkerGui();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public WorkerGui() throws NotBoundException, RemoteException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws RemoteException, NotBoundException {

        Registry reg;
        reg = LocateRegistry.getRegistry("localhost", 3000);
        Remote central = reg.lookup("controlCenter");

        JList<String> stationsToClearJList = new JList<>();
        stationsToClearJList.setBounds(137, 283, 376, 144);

        JLabel stationToClearIdLabel = new JLabel("Do opróżnienia");
        stationToClearIdLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        stationToClearIdLabel.setBounds(266, 250, 170, 22);

        frame = new JFrame();

        frame.getContentPane().add(stationsToClearJList);
        frame.getContentPane().add(stationToClearIdLabel);

        frame.getContentPane().setBackground(SystemColor.inactiveCaption);
        frame.setBackground(SystemColor.inactiveCaption);
        frame.setBounds(100, 100, 820, 525);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel workerLabel = new JLabel("APLIKACJA PRACOWNIKA SKLEPU");
        workerLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        workerLabel.setBounds(149, 11, 364, 54);
        frame.getContentPane().add(workerLabel);

        JLabel workerNameLabel = new JLabel("Imi\u0119:");
        workerNameLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        workerNameLabel.setBounds(72, 68, 66, 22);
        frame.getContentPane().add(workerNameLabel);

        JTextField workerNameTextField = new JTextField();
        workerNameTextField.setBounds(430,68,100,22);
        frame.getContentPane().add(workerNameTextField);

        JTextField workerSurnameTextField = new JTextField();
        workerSurnameTextField.setBounds(430,101,100,22);
        frame.getContentPane().add(workerSurnameTextField);

        JTextField workerIdTextField = new JTextField();
        workerIdTextField.setBounds(430,134,100,22);
        frame.getContentPane().add(workerIdTextField);

        JLabel workerSurnameLabel = new JLabel("Nazwisko:");
        workerSurnameLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        workerSurnameLabel.setBounds(72, 101, 112, 22);
        frame.getContentPane().add(workerSurnameLabel);

        JLabel workerIdLabel = new JLabel("ID:");
        workerIdLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        workerIdLabel.setBounds(72, 134, 200, 22);
        frame.getContentPane().add(workerIdLabel);

        JLabel workerNameValueLabel = new JLabel("-");
        workerNameValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        workerNameValueLabel.setBounds(202, 68, 200, 22);
        frame.getContentPane().add(workerNameValueLabel);

        JLabel workerSurnameValueLabel = new JLabel("-");
        workerSurnameValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        workerSurnameValueLabel.setBounds(202, 101, 200, 22);
        frame.getContentPane().add(workerSurnameValueLabel);

        JLabel workerIdValueLabel = new JLabel("-");
        workerIdValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        workerIdValueLabel.setBounds(202, 134, 200, 22);
        frame.getContentPane().add(workerIdValueLabel);

        JButton addWorkerButton = new JButton();
        JButton deleteWorkerButton = new JButton();
        deleteWorkerButton.setBounds(600,50,200,50);
        deleteWorkerButton.setText("Usuń");
        deleteWorkerButton.setBackground(new Color(185, 67, 67));
        deleteWorkerButton.setForeground(SystemColor.text);
        deleteWorkerButton.addActionListener(e -> {
            try {
                addWorkerButton.setEnabled(true);
                ((IRegistration)central).unregister(worker);

            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        frame.getContentPane().add(deleteWorkerButton);


        addWorkerButton.setBounds(600,110,200,50);
        addWorkerButton.setText("Dodaj");
        addWorkerButton.setBackground(new Color(72, 121, 67));
        deleteWorkerButton.setForeground(SystemColor.text);
        addWorkerButton.addActionListener(e -> {
            try {
                worker = new Worker(workerIdTextField.getText(), workerNameTextField.getText(),workerSurnameTextField.getText());
                workerIdValueLabel.setText(workerIdTextField.getText());
                workerNameValueLabel.setText(workerNameTextField.getText());
                workerSurnameValueLabel.setText(workerSurnameTextField.getText());
                stationsToClearJList.setModel(worker.getStationsToClear());
                addWorkerButton.setEnabled(false);

            } catch (RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }
        });
        frame.getContentPane().add(addWorkerButton);

    }
}
