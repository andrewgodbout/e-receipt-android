package cs2130.trojanhorses.e_receipt;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Aleix on 10/20/2017.
 */

public class Receipt {

    private UUID mId;
    private String mDate;
    private String mStore;
    private ArrayList<String> mItems;

    public Receipt (String date, String store){
        mDate = date;
        mStore = store;
        mItems = new ArrayList<>();
        mId = UUID.randomUUID();
    }

    public Receipt() {
        mId = UUID.randomUUID();
    }

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

    public ArrayList<String> getItems() {
        return mItems;
    }

    public void addItems(String items) {
        mItems.add(items);
    }
}
