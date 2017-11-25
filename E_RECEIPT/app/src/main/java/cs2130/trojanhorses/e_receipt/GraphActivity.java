package cs2130.trojanhorses.e_receipt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements Callbackable {

    private ArrayList<BarEntry> mEntries;
    private ArrayList<String> mLabels;
    private BarChart mBarChart;
    private BarDataSet mDataSet;
    private BarData mData;
    private ReceiptLab mReceiptLab;
    private ArrayList<Double> prices;
    private List<Receipt> receipts;

    private final String DATABASE_NAME = "receiptList.db";

    // Todo 1: Get the correct Application Context - URGENT fix */
    //private ReceiptHelper mReceiptHelper = new ReceiptHelper(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mBarChart = (BarChart) findViewById(R.id.bar_chart);

        //Todo 2: This code should work with Todo 1 done, Query is not good. */
        /*SQLiteDatabase db = mReceiptHelper.getReadableDatabase();
        String[] queryColumns = {"date","items"};

        Cursor cs = db.query(DATABASE_NAME, queryColumns, null, null, null, null, null);*/


        mReceiptLab = ReceiptLab.get(GraphActivity.this, this, false);

        receipts = mReceiptLab.getReceipts();
        prices = new ArrayList<>();

        /*for(int i=0; i<receipts.size(); i++){
            Log.d("CHECK","Date: "+receipts.get(i).getDate()+ "Price: "+receipts.get(i).getTotal());
        }*/


        /*for (int i =5; i > 0; i--){
            String date = "2017"+Integer.toString(i)+"30";
            Log.d("TAG", date);
            prices.add(getTotal(receipts, date));
        }
        //Log.d("TAG", prices.toString());*/

        Log.d("TAG", Double.toString(getTotal(receipts, "2015530")));
        Log.d("TAG", Double.toString(getTotal(receipts, "2015430")));
        Log.d("TAG", Double.toString(getTotal(receipts, "2015330")));
        Log.d("TAG", Double.toString(getTotal(receipts, "20155230")));
        Log.d("TAG", Double.toString(getTotal(receipts, "2015130")));



        mLabels = new ArrayList<>();
        mLabels.add("Jan");
        mLabels.add("Feb");
        mLabels.add("Mar");
        mLabels.add("Apr");
        mLabels.add("May");

        mEntries = new ArrayList<>();
        mEntries.add(new BarEntry(1f,700));
        mEntries.add(new BarEntry(2f,650));
        mEntries.add(new BarEntry(3f,675));
        mEntries.add(new BarEntry(4f,500));
        mEntries.add(new BarEntry(5f,480));

        mDataSet = new BarDataSet(mEntries, "Month");

        mData = new BarData(mDataSet);

        mDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        mDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        mBarChart.setData(mData);
        mBarChart.invalidate();
        mBarChart.animateY(3000);

        // Todo 3: LimitLine not working yet - not urgent */
        LimitLine line = new LimitLine(10f);
        //mData.addLimitLine(line);
        Description description = new Description();
        description.setText("My Budget");
        mBarChart.setDescription(description);
    }

    @Override
    public void update() {
        System.out.print("Hello Graph!");
    }

    public double getTotal(List<Receipt> list, String date){
        //int convertedDate = Integer.parseInt(date);
        //int endingDate = date.charAt((4));
        int count = 0;
        double value = 0;

        do{

            prices.add(Double.parseDouble(list.get(count).getTotal().substring(1)));
            value += Double.parseDouble(list.get(count).getTotal().substring(1));

            //Log.d("VALUE",  "Value: "+value);
            count++;
        }
        while (list.get(count).getDate().charAt(4) == date.charAt(4));
        return value;
    }

}
