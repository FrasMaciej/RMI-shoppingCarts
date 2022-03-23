package gui;

import java.awt.EventQueue;

import javax.swing.*;

import managementElements.CartStation;
import managementElements.Worker;
import shoppingCarts.data.UpdateInfo;
import shoppingCarts.interfaces.IRegistration;
import shoppingCarts.interfaces.IUpdate;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CartStationGui {

    private JFrame frame;
    private CartStation cartStation;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CartStationGui window = new CartStationGui();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public CartStationGui() throws NotBoundException, RemoteException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws NotBoundException, RemoteException {
        Registry reg = LocateRegistry.getRegistry("localhost", 3000);
        Remote central = reg.lookup("controlCenter");

        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.inactiveCaption);
        frame.setBounds(100, 100, 840, 554);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel capacityLabel = new JLabel("Pojemno\u015B\u0107");
        capacityLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        capacityLabel.setBounds(156, 99, 107, 30);
        frame.getContentPane().add(capacityLabel);

        JLabel freePlacesLabel = new JLabel("Wolne miejsca");
        freePlacesLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        freePlacesLabel.setBounds(156, 140, 162, 30);
        frame.getContentPane().add(freePlacesLabel);

        JLabel takenPlacesPercentageLabel = new JLabel("Zaj\u0119ty %");
        takenPlacesPercentageLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        takenPlacesPercentageLabel.setBounds(156, 181, 88, 30);
        frame.getContentPane().add(takenPlacesPercentageLabel);

        JLabel cartStationLabel = new JLabel("PUNKT PARKOWANIA KOSZYK\u00D3W");
        cartStationLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        cartStationLabel.setBounds(156, 11, 332, 51);
        frame.getContentPane().add(cartStationLabel);

        JLabel capacityValueLabel = new JLabel("-");
        capacityValueLabel.setForeground(new Color(50, 205, 50));
        capacityValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        capacityValueLabel.setBounds(357, 99, 83, 30);
        frame.getContentPane().add(capacityValueLabel);

        JTextField capacityTextField = new JTextField();
        capacityTextField.setBounds(560,99,150,30);
        frame.getContentPane().add(capacityTextField);

        JLabel freePlacesValueLabel = new JLabel("-");
        freePlacesValueLabel.setForeground(new Color(255, 69, 0));
        freePlacesValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        freePlacesValueLabel.setBounds(357, 140, 83, 30);
        frame.getContentPane().add(freePlacesValueLabel);

        JLabel takenPlacesPercentageValueLabel = new JLabel("0");
        takenPlacesPercentageValueLabel.setForeground(new Color(0, 0, 0));
        takenPlacesPercentageValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        takenPlacesPercentageValueLabel.setBounds(357, 181, 83, 30);
        frame.getContentPane().add(takenPlacesPercentageValueLabel);

        JButton dropBasketButton = new JButton("Wstaw koszyk");
        dropBasketButton.addActionListener(e -> {
            if(cartStation.getCapacity() > cartStation.getOccupancy()){
                cartStation.setOccupancy(cartStation.getOccupancy() + 1);
                freePlacesValueLabel.setText(String.valueOf(cartStation.getCapacity() - cartStation.getOccupancy()));
                float percentageOccupancy = (((float)cartStation.getOccupancy()/(float)cartStation.getCapacity())*100);
                takenPlacesPercentageValueLabel.setText(String.valueOf(percentageOccupancy));

                try {
                    UpdateInfo updateInfo = new UpdateInfo();
                    updateInfo.capacity = cartStation.getCapacity();
                    updateInfo.occupancy = cartStation.getOccupancy();
                    updateInfo.stationName = cartStation.getName();
                    ((IUpdate)central).update(updateInfo);

                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }

            }
        });
        dropBasketButton.setBackground(new Color(107, 142, 35));
        dropBasketButton.setForeground(SystemColor.text);
        dropBasketButton.setFont(new Font("Consolas", Font.BOLD, 18));
        dropBasketButton.setBounds(156, 237, 284, 102);
        frame.getContentPane().add(dropBasketButton);

        JButton clearPointButton = new JButton("Opr\u00F3\u017Cnij ten punkt");
        clearPointButton.addActionListener(e -> {
            cartStation.setOccupancy(0);
            freePlacesValueLabel.setText(String.valueOf(cartStation.getCapacity()));
            float percentageOccupancy = 0;
            takenPlacesPercentageValueLabel.setText(String.valueOf(percentageOccupancy));

            try {
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.capacity = cartStation.getCapacity();
                updateInfo.occupancy = cartStation.getOccupancy();
                updateInfo.stationName = cartStation.getName();
                ((IUpdate)central).update(updateInfo);

            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

        });
        clearPointButton.setBackground(new Color(139, 69, 19));
        clearPointButton.setForeground(SystemColor.text);
        clearPointButton.setFont(new Font("Consolas", Font.BOLD, 18));
        clearPointButton.setBounds(156, 350, 284, 102);
        frame.getContentPane().add(clearPointButton);

        JLabel identifierLabel = new JLabel("Identyfikator");
        identifierLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        identifierLabel.setBounds(156, 63, 137, 30);
        frame.getContentPane().add(identifierLabel);

        JLabel identifierValueLabel = new JLabel("");
        identifierValueLabel.setForeground(Color.BLACK);
        identifierValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        identifierValueLabel.setBounds(357, 63, 200, 30);
        frame.getContentPane().add(identifierValueLabel);

        JTextField identifierTextField = new JTextField();
        identifierTextField.setBounds(560,63,150,30);
        frame.getContentPane().add(identifierTextField);

        JButton addCartStationButton = new JButton();
        JButton deleteCartStationButton = new JButton();
        deleteCartStationButton.setBounds(560,150,150,80);
        deleteCartStationButton.setText("Usuń");
        deleteCartStationButton.setBackground(new Color(185, 67, 67));
        deleteCartStationButton.setForeground(SystemColor.text);
        deleteCartStationButton.addActionListener(e -> {
            try {
                addCartStationButton.setEnabled(true);
                ((IRegistration)central).unregister(cartStation);

            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        frame.getContentPane().add(deleteCartStationButton);


        addCartStationButton.setBounds(560,240,150,80);
        addCartStationButton.setText("Dodaj");
        addCartStationButton.setBackground(new Color(72, 121, 67));
        addCartStationButton.setForeground(SystemColor.text);
        addCartStationButton.addActionListener(e -> {
            try {
                cartStation = new CartStation(identifierTextField.getText(), Integer.parseInt(capacityTextField.getText()));
                identifierValueLabel.setText(cartStation.getName());
                capacityValueLabel.setText(String.valueOf(cartStation.getCapacity()));
                freePlacesValueLabel.setText(String.valueOf(cartStation.getOccupancy()));
                takenPlacesPercentageValueLabel.setText("0");
                addCartStationButton.setEnabled(false);
            } catch (RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }

        });
        frame.getContentPane().add(addCartStationButton);

    }

}
