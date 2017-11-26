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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mBarChart = (BarChart) findViewById(R.id.bar_chart);

        mReceiptLab = ReceiptLab.get(GraphActivity.this, this, false);

        receipts = mReceiptLab.getReceipts();
        prices = new ArrayList<>();

        for(int i=0; i<receipts.size(); i++){
            Log.d("CHECK", "Date: "+receipts.get(i).getDate()+ "Price: "+receipts.get(i).getTotal());
        }

        Log.d("TAG", Double.toString(getTotalByMonth(receipts, "2015530")));
        Log.d("TAG", Double.toString(getTotalByMonth(receipts, "2015430")));
        Log.d("TAG", Double.toString(getTotalByMonth(receipts, "2015330")));
        Log.d("TAG", Double.toString(getTotalByMonth(receipts, "2015230")));
        Log.d("TAG", Double.toString(getTotalByMonth(receipts, "2015130")));

        mLabels = new ArrayList<>();
        mLabels.add("Jan");
        mLabels.add("Feb");
        mLabels.add("Mar");
        mLabels.add("Apr");
        mLabels.add("May");

        mEntries = new ArrayList<>();
        mEntries.add(new BarEntry(1f,getTotalByMonth(receipts, "20150530")));
        mEntries.add(new BarEntry(2f,getTotalByMonth(receipts, "20150430")));
        mEntries.add(new BarEntry(3f,getTotalByMonth(receipts, "20150330")));
        mEntries.add(new BarEntry(4f,getTotalByMonth(receipts, "20150230")));
        mEntries.add(new BarEntry(5f,getTotalByMonth(receipts, "20150130")));


        mDataSet = new BarDataSet(mEntries, "Month");
        mDataSet.setValueTextSize(16);
        mDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        mDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        mData = new BarData(mDataSet);

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

    public int getTotalByMonth(List<Receipt> list, String date){
        int count = 0;
        double value = 0;

        while ( count < list.size()) {
            if (list.get(count).getDate().substring(4,6).equals(date.substring(4,6))) {
                prices.add(Double.parseDouble(list.get(count).getTotal().substring(1)));
                value += Double.parseDouble(list.get(count).getTotal().substring(1));
            }

            count ++;
        }
        return (int)value;
    }

}
