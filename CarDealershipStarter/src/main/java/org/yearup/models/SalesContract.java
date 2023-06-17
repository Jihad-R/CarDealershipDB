package org.yearup.models;

import java.math.BigDecimal;

public class SalesContract
{
    private int salesId;
    private String vin;
    private String customerName;
    private String customerEmail;
    private BigDecimal salesPrice;
    private BigDecimal recordingFee;
    private BigDecimal processingFee;
    private BigDecimal salesTax;

    public int getSalesId()
    {
        return salesId;
    }

    public void setSalesId(int salesId)
    {
        this.salesId = salesId;
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
        this.salesPrice = salesPrice.add(getRecordingFee()).
                            add(getProcessingFee()).add(getSalesTax());

    }

    public BigDecimal getRecordingFee()
    {
        return recordingFee;
    }

    public void setRecordingFee(BigDecimal recordingFee)
    {
        this.recordingFee = BigDecimal.valueOf(100);
    }

    public BigDecimal getProcessingFee()
    {
        return processingFee;
    }

    public void setProcessingFee(BigDecimal processingFee)
    {
        this.processingFee = processingFee;
    }

    public BigDecimal getSalesTax()
    {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax)
    {
        this.salesTax = salesTax;
    }

    public String displayInfo()
    {
        return String.format("Sale_id: %d\n" +
                      "Vin: %s\n" +
                       "Customer Name: %s\n" +
                       "Customer Email: %s\n" +
                       "Sales Price: %.2f\n" +
                       "Recording Fee: %.2f\n" +
                       "Processing Fee: %.2f\n" +
                       "Sales Tax: %.2f",getSalesId(),getVin(),getCustomerName(),
                                        getCustomerEmail(),getSalesPrice(),getRecordingFee(),
                                        getProcessingFee(),getSalesTax());

    }
}
