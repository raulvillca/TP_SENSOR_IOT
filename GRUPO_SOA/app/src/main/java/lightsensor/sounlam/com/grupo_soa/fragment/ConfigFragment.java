package lightsensor.sounlam.com.grupo_soa.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lightsensor.sounlam.com.grupo_soa.R;
import lightsensor.sounlam.com.grupo_soa.connection.SensorRequest;
import lightsensor.sounlam.com.grupo_soa.transport.ContentConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseConfig;
import lightsensor.sounlam.com.grupo_soa.transport.WithConfig;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Raul on 8/06/16.
 */
public class ConfigFragment extends Fragment implements View.OnClickListener, SensorEventListener{
    private ImageButton button;
    private SensorManager mSensorManager;
    private Sensor pSensor;
    private Sensor lSensor;
    private Sensor gSensor;
    private TextView textView_p;
    private TextView textView_l;
    float valorP = 0;
    float valorL = 0;
    int maximo = 200;
    int minimo = 100;
    float intensidadgiro = 0;
    ProgressBar progressBar_max;
    ProgressBar progressBar_min;
    TextView textViewP_max;
    TextView textViewP_min;
    TextView textView_a1;
    public ConfigFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = (ImageButton) getActivity().findViewById(R.id.id_image_button);
        button.setOnClickListener(this);

        mSensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        pSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        gSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        textView_p = (TextView) getActivity().findViewById(R.id.id_config_proximity);
        textView_l = (TextView) getActivity().findViewById(R.id.id_config_light);
        progressBar_max = (ProgressBar) getActivity().findViewById(R.id.id_config_progressBar_max);
        textViewP_max = (TextView) getActivity().findViewById(R.id.id_config_txtProgress_max);
        progressBar_max.setProgress(maximo);
        progressBar_min = (ProgressBar) getActivity().findViewById(R.id.id_config_progressBar_min);
        textViewP_min = (TextView) getActivity().findViewById(R.id.id_config_txtProgress_min);
        progressBar_min.setProgress(minimo);

        textView_a1 = (TextView) getActivity().findViewById(R.id.id_config_gyroscope1);
    }

    @Override
    public void onClick(View view) {
        //Realizo la consulta al servidor
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://dweet.io")
                .build();
        SensorRequest sensorRequest = restAdapter.create(SensorRequest.class);

        sensorRequest.setConf( minimo, maximo, new Callback<ContentConfig>() {
            @Override
            public void success(ContentConfig contentConfig, Response response) {
                Toast.makeText(getContext(), "Configuracion enviada", Toast.LENGTH_LONG).show();
                //Capturo los datos que me sirven
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Implementar acciones cuando se detecte un cambio
        //sincronizo los eventos para que cada hilo se encargue de un sensor

        synchronized (this) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                textView_p.setText("Proximidad: " + sensorEvent.values[0]);
                valorP = sensorEvent.values[0];
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                textView_l.setText("Intensidad de Luz: " + sensorEvent.values[0]);
                valorL = sensorEvent.values[0];
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                intensidadgiro = sensorEvent.values[0];
                textView_a1.setText("Acelerometro: " + sensorEvent.values[0]);
            }
        }
        if (valorP == 0 && valorL == 0) {
            //Toast.makeText(getContext(), "Bajar marguen", Toast.LENGTH_SHORT).show();
            maximo--;
            progressBar_max.setProgress(maximo);
            textViewP_max.setText(maximo + " %");
        } else if (valorP != 0 && valorL == 0) {
            //Toast.makeText(getContext(), "Aumentar Marguen", Toast.LENGTH_SHORT).show();
            maximo++;
            progressBar_max.setProgress(maximo);
            textViewP_max.setText(maximo + " %");
        } else if (intensidadgiro > 2.5) {
            minimo--;
            progressBar_min.setProgress(minimo);
            textViewP_min.setText(minimo + " %");
        } else if (intensidadgiro < -2.5) {
            minimo++;
            progressBar_min.setProgress(minimo);
            textViewP_min.setText(minimo + " %");
        } else {
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Implementar acciones cuando se cambia la precision
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, pSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, lSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
}
