package cs2130.trojanhorses.e_receipt;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Aleix on 10/21/2017.
 */

public class ReceiptLab {

    private static ReceiptLab sReceiptLab;

    private static List<Receipt> sReceipts;

    public ReceiptLab(Context context) {
        sReceipts = new ArrayList<>();
    }

    public static ReceiptLab get(Context context) {
        if (sReceiptLab == null) {
            sReceiptLab = new ReceiptLab(context);
        }
        return sReceiptLab;
    }

    private List<Receipt> getReceipts() {
        return sReceipts;
    }

    public Receipt getReceipt(UUID id) {
        for (Receipt receipt: sReceipts) {
            if (receipt.getId().equals(id)) {
                return receipt;
            }
        }
        return null;
    }

    public void add(Receipt receipt) {
        sReceipts.add(receipt);
    }
}
