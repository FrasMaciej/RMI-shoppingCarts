package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import managementElements.ControlCenter;
import shoppingCarts.data.UpdateInfo;

public class ControlCenterGui {

    private JFrame frame;
    private ControlCenter controlCenter;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ControlCenterGui window = new ControlCenterGui();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public ControlCenterGui() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        controlCenter = new ControlCenter();

        JList<UpdateInfo> parkingRegisterJList  = new JList<>();
        parkingRegisterJList.setModel(controlCenter.getCartStations());
        parkingRegisterJList.setBounds(166, 168, 464, 193);

        JList<String> workerRegisterJList = new JList<>();
        workerRegisterJList.setBounds(197, 463, 402, 228);
        workerRegisterJList.setModel(controlCenter.getWorkers());

        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.inactiveCaption);

        JLabel parkingRegisterLabel = new JLabel("REJESTR STACJI PARKOWANIA");
        parkingRegisterLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        parkingRegisterLabel.setBounds(238, 88, 325, 29);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(parkingRegisterJList);
        frame.getContentPane().add(workerRegisterJList);
        frame.getContentPane().add(parkingRegisterLabel);

        JLabel workerRegisterLabel = new JLabel("REJESTR PRACOWNIK\u00D3W");
        workerRegisterLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        workerRegisterLabel.setBounds(275, 387, 256, 34);
        frame.getContentPane().add(workerRegisterLabel);

        JLabel controlCenterLabel = new JLabel("CENTRUM OBS\u0141UGI KOSZYK\u00D3W SKLEPOWYCH");
        controlCenterLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        controlCenterLabel.setBounds(166, 31, 464, 29);
        frame.getContentPane().add(controlCenterLabel);
        frame.setBounds(100, 100, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }


}
