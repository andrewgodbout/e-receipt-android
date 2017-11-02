package cs2130.trojanhorses.e_receipt;

/**
 * Created by Aleix on 11/1/2017.
 */

public class Item {

    private String mitemName;
    private double mitemPrice;

    public Item(String itemName, double itemPrice) {
        mitemName = itemName;
        mitemPrice = itemPrice;
    }

    public String getMitemName() {
        return mitemName;
    }

    public void setMitemName(String mitemName) {
        this.mitemName = mitemName;
    }

    public double getMitemPrice() {
        return mitemPrice;
    }

    public void setMitemPrice(double mitemPrice) {
        this.mitemPrice = mitemPrice;
    }
}
