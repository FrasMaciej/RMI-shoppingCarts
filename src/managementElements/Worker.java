package managementElements;

import shoppingCarts.data.UpdateInfo;
import shoppingCarts.interfaces.IInfo;
import shoppingCarts.interfaces.INotification;
import shoppingCarts.interfaces.IRegistration;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Worker extends UnicastRemoteObject implements INotification, IInfo, Serializable {
    String name;
    String fistName;
    String lastName;
    DefaultListModel<String> stationsToClear = new DefaultListModel<>();
    List<String> list = new ArrayList<>();

    public Worker(String name, String firstName, String lastName) throws RemoteException, NotBoundException {
        this.fistName = firstName;
        this.lastName = lastName;
        this.name = name;

        Registry reg = LocateRegistry.getRegistry("localhost", 3000);
        Remote central = reg.lookup("controlCenter");
        ((IRegistration)central).register(this);
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void notify(UpdateInfo updateInfo) throws RemoteException {
        int capacity = updateInfo.capacity;
        int occupancy = updateInfo.occupancy;
        float percentageOccupancyValue = (float) (((float)occupancy / (float)capacity)*100.0);
        System.out.println("aaa");
        if(updateInfo.occupancy == -1 && updateInfo.capacity == -1){
            for(int i=0; i<stationsToClear.getSize(); i++){
                if(stationsToClear.get(i).equals(updateInfo.stationName)){
                    stationsToClear.remove(i);
                }
            }
        }
        else if(percentageOccupancyValue>= 80){
            for(int i=0; i<stationsToClear.getSize(); i++){
                if(stationsToClear.get(i).equals(updateInfo.stationName)){
                    return;
                }
            }
            String str = updateInfo.stationName;
            stationsToClear.addElement(str);
            list.add(str);
        }
        else if(percentageOccupancyValue < 80){
            for(int i=0; i<stationsToClear.getSize(); i++){
                if(stationsToClear.get(i).equals(updateInfo.stationName)){
                    stationsToClear.remove(i);
                    System.out.println("ccc");
                    break;
                }
            }
        }
    }

    @Override
    public String toString(){
        return name + " " + fistName + " " + lastName;
    }

    public DefaultListModel<String> getStationsToClear() {
        return stationsToClear;
    }

}
