package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Aleix on 10/21/2017.
 */

public class ReceiptLab {

    private static ReceiptLab sReceiptLab;
    private List<Receipt> mReceipts;

    private ReceiptLab(Context context) {
        Log.d("TAG", "receipt lab executed");
        mReceipts = new ArrayList<>();
    }

    public static ReceiptLab get(Context context) {
        if (sReceiptLab == null) {
            sReceiptLab = new ReceiptLab(context);
        }
        return sReceiptLab;
    }

    public List<Receipt> getReceipts() {
        return mReceipts;
    }

    public Receipt getReceipt(UUID id) {
        for (Receipt receipt: mReceipts) {
            if (receipt.getId().equals(id)) {
                return receipt;
            }
        }
        return null;
    }

    public void add(Receipt receipt) {
        mReceipts.add(receipt);
    }

}
