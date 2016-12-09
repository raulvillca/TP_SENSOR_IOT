package lightsensor.sounlam.com.grupo_soa.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lightsensor.sounlam.com.grupo_soa.MainActivity;
import lightsensor.sounlam.com.grupo_soa.R;
import lightsensor.sounlam.com.grupo_soa.connection.ILoadResponse;
import lightsensor.sounlam.com.grupo_soa.connection.SensorRequest;
import lightsensor.sounlam.com.grupo_soa.transport.ContentConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ContentLight;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseLight;
import lightsensor.sounlam.com.grupo_soa.transport.WithConfig;
import lightsensor.sounlam.com.grupo_soa.transport.WithLight;
import lightsensor.sounlam.com.grupo_soa.util.GraphicUtil;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Raul on 8/06/16.
 */
public class GraphicFragment extends Fragment implements ILoadResponse {
    public static String TAG = "GraphicFragment";
    List<Number> maxNumbers;
    List<Number> minNumbers;
    List<Number> yIntensity;
    List<Integer> lista;
    int MAXIMO = 200;
    int MINIMO = 100;
    XYPlot mySimpleXYPlot;
    TextView textViewMin;
    TextView textViewMax;

    private final static String TITLE_BOARD = "Mensaje de Galileo";
    private final static String MSJ_BOARD = "Se detecto baja intensidad luminica";

    public GraphicFragment() {
        GraphicUtil.preload(yIntensity, maxNumbers, minNumbers, MAXIMO, MINIMO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graphic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textViewMax = (TextView) getActivity().findViewById(R.id.id_graphic_max);
        textViewMin = (TextView) getActivity().findViewById(R.id.id_graphic_min);
        textViewMax.setText("Maximo: " + MAXIMO);
        textViewMin.setText("Minimo: " + MINIMO);
        //Preparo el graficador
        // initialize our XYPlot reference:
        mySimpleXYPlot = (XYPlot) getActivity().findViewById(R.id.id_graphic_xy_plot);

        GraphicUtil.draw(
                mySimpleXYPlot,
                yIntensity,
                maxNumbers,
                minNumbers,
                getResources().getString(R.string.margin_intensity),
                getResources().getString(R.string.margin_max),
                getResources().getString(R.string.margin_min));
    }

    @Override
    public void reload( List<Number> yIntensity, List<Number> maxList, List<Number> minList) {
        mySimpleXYPlot.clear();
        mySimpleXYPlot.redraw();

        GraphicUtil.draw(
                mySimpleXYPlot,
                yIntensity,
                maxList,
                minList,
                getResources().getString(R.string.margin_intensity),
                getResources().getString(R.string.margin_max),
                getResources().getString(R.string.margin_min));
    }
}
