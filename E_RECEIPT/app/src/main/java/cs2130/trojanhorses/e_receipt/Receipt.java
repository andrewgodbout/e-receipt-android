package cs2130.trojanhorses.e_receipt;

import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Created by Aleix on 10/20/2017.
 */

public class Receipt {

    private UUID mId;
    private String mDate;
    private String mStore;
    private Item[] mItems;

    public Receipt (String date, String store, Item[] itemList){
        mDate = date;
        mStore = store;
        mItems = itemList;
        mId = UUID.randomUUID();
    }


    public Receipt(UUID id) {
        mId = UUID.randomUUID();
    } /** I don't think that we should create empty Receipts */

    public UUID getId() {
        return mId;
    }
    
    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getStore() {
        return mStore;
    }

    public void setStore(String store) {
        mStore = store;
    }

    public Item[] getItems() {
        return mItems;
    }

    /** Receive a JSONObject to parse and create an Item */
    public void setItems(String items) {

    }

    public String getTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        double total = 0;
        for (Item i: mItems) {
            total += i.getItemPrice();
        }
        return "$"+df.format(total);
    }

    /** You don't need to add Items to a receipt, it comes with items already. */
    /*public void addItems(String items) {
        mItems.add(items);
    }*/
}
