package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity implements Callbackable {

    private ArrayList<BarEntry> mEntries;
    private ArrayList<String> mLabels;
    private BarChart mBarChart;
    private BarDataSet mDataSet;
    private BarData mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mBarChart = (BarChart) findViewById(R.id.bar_chart);

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
        /*IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mLabels.get((int)value);
            }
        };

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);*/

        //BarData data = new BarData(mDataSet);
        mBarChart.setData(mData);
        mBarChart.invalidate();
        mBarChart.animateY(5000);

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







    /*private class BarEntry {
        private double price;
        private int numBars;


        public BarEntry(double price, int numBars) {
            this.price = price;
            this.numBars = numBars;
        }

        public double getPrice() {
            return price;
        }

        public int getNumBars() {
            return numBars;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setNumBars(int numBars) {
            this.numBars = numBars;
        }
    }

    private class BarDataSet{
        private ArrayList<BarEntry> myarray;
        private String someString;


        public BarDataSet(ArrayList<BarEntry> myarray, String someString) {
            this.myarray = myarray;
            this.someString = someString;
        }

        public ArrayList<BarEntry> getMyarray() {
            return myarray;
        }

        public void setMyarray(ArrayList<BarEntry> myarray) {
            this.myarray = myarray;
        }

        public String getSomeString() {
            return someString;
        }

        public void setSomeString(String someString) {
            this.someString = someString;
        }
    }

    private class BarChar{

    }*/
}
