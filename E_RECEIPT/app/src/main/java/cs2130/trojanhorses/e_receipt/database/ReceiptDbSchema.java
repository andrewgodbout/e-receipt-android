package cs2130.trojanhorses.e_receipt.database;

/**
 * Created by davidadams on 2017-11-12.
 */

public class ReceiptDbSchema {
    public static final class ReceiptTable{
        public static final String NAME = "receipts";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String STORE = "store";
            public static final String DATE = "date";
            public static final String ITEMS = "items";
        }

    }
}
