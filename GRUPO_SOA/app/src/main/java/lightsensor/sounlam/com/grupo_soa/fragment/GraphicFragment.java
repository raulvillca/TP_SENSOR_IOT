package lightsensor.sounlam.com.grupo_soa.fragment;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lightsensor.sounlam.com.grupo_soa.R;

/**
 * Created by Raul on 8/06/16.
 */
public class GraphicFragment extends Fragment {
    private Number yMax;
    private Number yMin;
    private List<Number> yIntensity;
    private XYPlot mySimpleXYPlot;

    public GraphicFragment(Number max, Number min, List<Number> intList) {
        this.yMax = max;
        this.yMin = min;
        this.yIntensity = intList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graphic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textViewMax = (TextView) getActivity().findViewById(R.id.id_max);
        TextView textViewMin = (TextView) getActivity().findViewById(R.id.id_min);
        textViewMax.setText("Maximo: " + yMax);
        textViewMin.setText("Minimo: " + yMin);
        //Preparo el graficador
        // initialize our XYPlot reference:
        mySimpleXYPlot = (XYPlot) getActivity().findViewById(R.id.id_xy_plot);

        // Create a couple arrays of y-values to plot:
        List<Number> listMax = new ArrayList<Number>();
        List<Number> listMin = new ArrayList<Number>();
        for (int i = 0; i < 5; i++) {
            listMax.add(yMax);
            listMin.add(yMin);
        }

        Number[] seriesIntensityNumbers = {130, 210, 150, 150,170};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                listMin,          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Margen Minimo");                             // Set the display title of the series
        // same as above
        XYSeries series2 = new SimpleXYSeries(
                listMax,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Margen Maximo");
        XYSeries seriesIntensity = new SimpleXYSeries(
                //Arrays.asList(seriesIntensityNumbers),
                yIntensity,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Intensidad");

        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                null,                                   // fill color (none)
                new PointLabelFormatter(Color.WHITE));  // text color
        LineAndPointFormatter series2Format = new LineAndPointFormatter(
                Color.rgb(0, 0, 200),
                Color.rgb(0, 0, 100),
                null,
                new PointLabelFormatter(Color.WHITE));
        LineAndPointFormatter seriesIntensityFormat = new LineAndPointFormatter(
                Color.rgb(200, 0, 0),
                Color.rgb(100, 0, 0),
                null,
                new PointLabelFormatter(Color.WHITE));

        // add a new series' to the xyplot:
        mySimpleXYPlot.addSeries(series1, series1Format);
        // same as above:
        mySimpleXYPlot.addSeries(series2, series2Format);
        mySimpleXYPlot.addSeries(seriesIntensity, seriesIntensityFormat);
        // reduce the number of range labels
        mySimpleXYPlot.setTicksPerRangeLabel(3);
    }
}
