package managementElements;

import shoppingCarts.data.UpdateInfo;
import shoppingCarts.interfaces.IInfo;
import shoppingCarts.interfaces.IRegistration;
import shoppingCarts.interfaces.IUpdate;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CartStation implements IInfo, Serializable {

    private final String name;
    private final int capacity;
    private int occupancy;

    public CartStation(String name, int capacity) throws RemoteException, NotBoundException {
        this.name = name;
        this.capacity = capacity;
        this.occupancy = 0;
        Registry reg = LocateRegistry.getRegistry("localhost", 3000);
        Remote central = reg.lookup("controlCenter");
        ((IRegistration)central).register(this);
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.stationName = name;
        updateInfo.capacity = capacity;
        updateInfo.occupancy = occupancy;
        ((IUpdate)central).update(updateInfo);

    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupancy() {
        return occupancy;
    }
}
