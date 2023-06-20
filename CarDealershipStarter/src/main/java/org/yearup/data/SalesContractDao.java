package org.yearup.data;

import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

import java.util.List;

public interface SalesContractDao
{
    SalesContract getContractByID(int id);
    List<SalesContract> getAllContracts();
    SalesContract makePurchase(SalesContract contract);
    void remove(int id);
}
