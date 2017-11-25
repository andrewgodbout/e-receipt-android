package cs2130.trojanhorses.e_receipt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity implements Callbackable {

    private ArrayList<BarEntry> mEntries;
    private ArrayList<String> mLabels;
    private BarChart mBarChart;
    private BarDataSet mDataSet;
    private BarData mData;

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

}
