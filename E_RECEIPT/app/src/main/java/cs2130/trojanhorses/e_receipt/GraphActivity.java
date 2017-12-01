package cs2130.trojanhorses.e_receipt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
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
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mBarChart = (BarChart) findViewById(R.id.bar_chart);

        mReceiptLab = ReceiptLab.get(GraphActivity.this, this, false);

        receipts = mReceiptLab.getReceipts();
        prices = new ArrayList<>();

        /** Raw Coded Last 5 months Labels to show on the top of the BarChart */
        mLabels = new ArrayList<>();
        mLabels.add("Dec");
        mLabels.add("Nov");
        mLabels.add("Oct");
        mLabels.add("Sept");
        mLabels.add("Aug");

        /**
         *  Sets the values to be display on the mBarChart
         *  For each line you got an extra Bar to be displayed
         **/
        mEntries = new ArrayList<>();
        mEntries.add(new BarEntry(1f,getTotalByMonth(receipts, barEntryDates(4)), mLabels.get(4)));
        mEntries.add(new BarEntry(2f,getTotalByMonth(receipts, barEntryDates(3)), mLabels.get(3)));
        mEntries.add(new BarEntry(3f,getTotalByMonth(receipts, barEntryDates(2)), mLabels.get(2)));
        mEntries.add(new BarEntry(4f,getTotalByMonth(receipts, barEntryDates(1)), mLabels.get(1)));
        mEntries.add(new BarEntry(5f,getTotalByMonth(receipts, barEntryDates(0)), mLabels.get(0)));

        /** Adds mBarChart values to the mDataSet and customize the aspect of the mBarChart */
        mDataSet = new BarDataSet(mEntries, "Month");
        mDataSet.setValueTextSize(16);
        mDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        mDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        mDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return entry.getData().toString()+ "(" + entry.getY() + ")";
            }
        } );

        /**
         *  Steps to make it into a mBarChart,
         *  adding mDataSet to the mData and the mData to the mBarChart
         **/
        mData = new BarData(mDataSet);
        mBarChart.setData(mData);
        mBarChart.invalidate();
        mBarChart.animateY(3000);

        /** Set a LimitLine */
        LimitLine line = new LimitLine(800f);//, "$ LimitPerMonth");
        line.setLineColor(Color.RED);
        line.setLineWidth(4f);
        line.setTextColor(Color.BLACK);
        line.setTextSize(14);
        YAxis yAxis = mBarChart.getAxisLeft();
        yAxis.addLimitLine(line);

        /** Set a Description */
        Description description = new Description();
        description.setText("");
        description.setTextSize(13);
        mBarChart.setDescription(description);
    }

    @Override
    public void update() {
    }

    /**
     *  Returns the total expenses of all the receipts on given month
     *  TODO: Further Implementation: Just checks for month, does not care about years,
     *  TODO: so on different years will add up repeated months.
     * */
    public int getTotalByMonth(List<Receipt> list, String date){
        int count = 0;
        double value = 0;

        while ( count < list.size()) {
            if (list.get(count).getDate().substring(5,7).equals(date.substring(5,7))) {
                prices.add(Double.parseDouble(list.get(count).getTotal().substring(1)));
                value += Double.parseDouble(list.get(count).getTotal().substring(1));
            }

            count ++;
        }
        return (int)value;
    }

    /**
     *  Return exact month based on when you use the App ( if int value = 0 )
     *  Or the previous months based on the number
     * */
    public String barEntryDates (int i) {
        String date;
        mCalendar = Calendar.getInstance();

        if ((mCalendar.get(Calendar.MONTH)+1-i)%12 < 10 || (mCalendar.get(Calendar.MONTH)+1-i)%12 == 0) {
            if ((mCalendar.get(Calendar.MONTH) + 1 - i) % 12 == 0)
                date = "" + mCalendar.get(Calendar.YEAR) + ".12";
            else
                date = "" + mCalendar.get(Calendar.YEAR) + ".0" + ((mCalendar.get(Calendar.MONTH) + 1 - i) % 12);
        }else
            date = ""+mCalendar.get(Calendar.YEAR) + "." + (mCalendar.get(Calendar.MONTH)+1-i)%12;
        return date+".00";
    }

}
