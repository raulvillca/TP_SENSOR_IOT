package lightsensor.sounlam.com.grupo_soa.util;

import android.graphics.Color;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raulvillca on 8/12/16.
 */

public class GraphicUtil {

    public static void preload(List<Number> yIntensity, List<Number> maxNumbers, List<Number> minNumbers, int MAXIMO, int MINIMO) {
        maxNumbers = new ArrayList<Number>();
        minNumbers = new ArrayList<Number>();
        yIntensity = new ArrayList<Number>();
        minNumbers.add(MINIMO);
        minNumbers.add(MINIMO);
        minNumbers.add(MINIMO);
        minNumbers.add(MINIMO);
        minNumbers.add(MINIMO);

        maxNumbers.add(MAXIMO);
        maxNumbers.add(MAXIMO);
        maxNumbers.add(MAXIMO);
        maxNumbers.add(MAXIMO);
        maxNumbers.add(MAXIMO);

        for (int i = 0; i < 5 ; i++)
            yIntensity.add(MINIMO);
    }

    //Creamos el grafico
    public static void draw(XYPlot mySimpleXYPlot, List<Number> numberList, List<Number> listMax, List<Number> listMin, String m_max, String m_min, String m_intensity) {

        XYSeries series1 = new SimpleXYSeries(
                listMin,          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                m_min);                             // Set the display title of the series

        XYSeries series2 = new SimpleXYSeries(
                listMax,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                m_max);
        XYSeries seriesIntensity = new SimpleXYSeries(
                numberList,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                m_intensity);

        // Creamos el lienso para dibujar la serie, con el color de la linea
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

        // agregamos las series de cada Lineamiento
        mySimpleXYPlot.addSeries(series1, series1Format);
        mySimpleXYPlot.addSeries(series2, series2Format);
        mySimpleXYPlot.addSeries(seriesIntensity, seriesIntensityFormat);

        //definimos cuantos trazos vamos a mostrar
        mySimpleXYPlot.setTicksPerRangeLabel(3);
    }

}
