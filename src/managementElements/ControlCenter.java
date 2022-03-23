package managementElements;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


import shoppingCarts.data.UpdateInfo;
import shoppingCarts.interfaces.IInfo;
import shoppingCarts.interfaces.INotification;
import shoppingCarts.interfaces.IRegistration;
import shoppingCarts.interfaces.IUpdate;

import javax.swing.*;

public class ControlCenter implements IUpdate, IRegistration, Serializable {
    public static final float maximalPercentageCapacity = 80;

    private final String name = "controlCenter";

    private final DefaultListModel<UpdateInfo> cartStations = new DefaultListModel<>();
    private final DefaultListModel<String> workers = new DefaultListModel<>();

    private Registry registry;

    public ControlCenter() {
        try {
            registry = LocateRegistry.createRegistry(3000);
            registry.rebind(name, UnicastRemoteObject.exportObject(this, 0));
            System.out.println("Server is ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean register(IInfo info) throws RemoteException {
        if(info instanceof INotification){
            workers.addElement(info.getName());
            registry.rebind(info.getName(), UnicastRemoteObject.exportObject(info, 0));
            return true;
        }
        else {
            registry.rebind(info.getName(), UnicastRemoteObject.exportObject(info, 0));
            return true;
        }
    }

    @Override
    public boolean unregister(IInfo info) throws RemoteException {
        if(info instanceof INotification){
            try {
                registry.unbind(info.getName());
                workers.removeElement(info.getName());
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            try {
                for(int i=0; i<cartStations.getSize(); i++){
                    if(cartStations.get(i).stationName.equals(info.getName())){
                        UpdateInfo updateInfo = new UpdateInfo();
                        updateInfo.stationName = info.getName();
                        updateInfo.capacity = -1;
                        updateInfo.occupancy = -1;
                        cartStations.remove(i);

                        for(int j=0; j<workers.getSize(); j++){
                            Remote worker = registry.lookup(workers.get(j));
                            ((INotification)worker).notify(updateInfo);
                        }

                    }
                }
                registry.unbind(info.getName());
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public void update(UpdateInfo updateInfo) throws RemoteException {
        for(int i=0; i < cartStations.getSize(); i++){
            if(cartStations.get(i).stationName.equals(updateInfo.stationName)){
                cartStations.set(i, updateInfo);

                float percentageOccupancy = (float) (((float)updateInfo.occupancy/(float)updateInfo.capacity)*100.0);
                if(percentageOccupancy >= maximalPercentageCapacity  || percentageOccupancy == 0){
                    for(int j=0; j< workers.getSize(); j++){
                        try {
                            Remote worker = registry.lookup(workers.get(j));
                            ((INotification)worker).notify(updateInfo);
                        } catch (NotBoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
                return;
            }
        }
        cartStations.addElement(updateInfo);
    }

    public DefaultListModel<String> getWorkers() {
        return workers;
    }

    public DefaultListModel<UpdateInfo> getCartStations() {
        return cartStations;
    }
}
