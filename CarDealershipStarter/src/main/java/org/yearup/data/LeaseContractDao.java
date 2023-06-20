package org.yearup.data;

import org.yearup.models.LeaseContract;
import org.yearup.models.SalesContract;

import java.util.List;

public interface LeaseContractDao
{
    LeaseContract getContractByID(int id);
    List<LeaseContract> getAllContracts();
    LeaseContract makeLease(LeaseContract contract);
    void remove(int id);
}
