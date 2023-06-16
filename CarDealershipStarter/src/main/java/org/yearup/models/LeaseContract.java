package org.yearup.models;

import java.math.BigDecimal;

public class LeaseContract
{
    private int leaseId;
    private String vin;
    private String customerName;
    private String customerEmail;
    private BigDecimal salesPrice;
    private BigDecimal endingValue;
    private BigDecimal leaseFee;
    private BigDecimal salesTax;
    private BigDecimal monthlyPayment;

    public int getLeaseId()
    {
        return leaseId;
    }

    public void setLeaseId(int leaseId)
    {
        this.leaseId = leaseId;
    }

    public String getVin()
    {
        return vin;
    }

    public void setVin(String vin)
    {
        this.vin = vin;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerEmail()
    {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail)
    {
        this.customerEmail = customerEmail;
    }

    public BigDecimal getSalesPrice()
    {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice)
    {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getEndingValue()
    {
        return endingValue;
    }

    public void setEndingValue(BigDecimal endingValue)
    {
        this.endingValue = endingValue;
    }

    public BigDecimal getLeaseFee()
    {
        return leaseFee;
    }

    public void setLeaseFee(BigDecimal leaseFee)
    {
        this.leaseFee = leaseFee;
    }

    public BigDecimal getSalesTax()
    {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax)
    {
        this.salesTax = salesTax;
    }

    public BigDecimal getMonthlyPayment()
    {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment)
    {
        this.monthlyPayment = monthlyPayment;
    }
}
