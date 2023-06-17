package org.yearup.data;

import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

public interface SalesContractDao
{
    SalesContract makePurchase(SalesContract contract);
}
