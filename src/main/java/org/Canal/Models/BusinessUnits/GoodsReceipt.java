package org.Canal.Models.BusinessUnits;

import org.Canal.Models.Objex;
import org.Canal.Utils.LockeStatus;

import java.util.ArrayList;

public class GoodsReceipt extends Objex {

    public String purchaseOrder; //PO this is tied to
    public String received; //Timestamp of when received
    public String receiver; //User ID
    public String location; //Location receiving in at
    public ArrayList<OrderLineItem> items; //Items received and qty received
    private LockeStatus status; //Status of this receival

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<OrderLineItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderLineItem> items) {
        this.items = items;
    }

    public double getTotalItems(){
        double total = 0;
        for(OrderLineItem item : items){
            total += item.getQuantity();
        }
        return total;
    }

    public LockeStatus getStatus() {
        return status;
    }

    public void setStatus(LockeStatus status) {
        this.status = status;
    }
}