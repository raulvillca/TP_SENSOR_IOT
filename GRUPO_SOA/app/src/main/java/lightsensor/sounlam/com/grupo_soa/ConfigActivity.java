package lightsensor.sounlam.com.grupo_soa;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lightsensor.sounlam.com.grupo_soa.connection.IComunicationFragment;
import lightsensor.sounlam.com.grupo_soa.connection.SensorRequest;
import lightsensor.sounlam.com.grupo_soa.transport.ContentConfig;
import lightsensor.sounlam.com.grupo_soa.util.ConfigRequestUtil;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raulvillca on 1/7/16.
 */
public class ConfigActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener, IComunicationFragment {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        button = (ImageButton) findViewById(R.id.id_configuration_image_button);
        button.setOnClickListener(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        pSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        gSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        textView_p = (TextView) findViewById(R.id.id_configuration_proximity);
        textView_l = (TextView) findViewById(R.id.id_configuration_light);
        progressBar_max = (ProgressBar) findViewById(R.id.id_config_progressBar_max);
        textViewP_max = (TextView) findViewById(R.id.id_config_txtProgress_max);
        progressBar_max.setProgress(maximo);
        progressBar_min = (ProgressBar) findViewById(R.id.id_config_progressBar_min);
        textViewP_min = (TextView) findViewById(R.id.id_config_txtProgress_min);
        progressBar_min.setProgress(minimo);

        textView_a1 = (TextView) findViewById(R.id.id_configuration_gyroscope1);
    }

    @Override
    public void onClick(View view) {
        //Realizo la consulta al servidor
        //pienso que es buena practica consultar y liberar la conexion
        ConfigRequestUtil requestUtil = new ConfigRequestUtil(ConfigActivity.this);
        requestUtil.sendConfigRequest(minimo, maximo);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Implementar acciones cuando se detecte un cambio
        //sincronizo los eventos para que cada hilo se encargue de un sensor

        synchronized (this) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                textView_p.setText("Sensor Prox: " + sensorEvent.values[0]);
                valorP = sensorEvent.values[0];
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                textView_l.setText("Sensor Luz  : " + sensorEvent.values[0]);
                valorL = sensorEvent.values[0];
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                intensidadgiro = sensorEvent.values[0];
                textView_a1.setText("Sensor Acel: " + sensorEvent.values[0]);
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

    @Override
    public void setLightResponse(List<Number> response) {

    }

    @Override
    public void setConfigResponse(List<Number> maximo, List<Number> minimo) {

    }

    @Override
    public void notifyError(boolean error) {
        Snackbar.make(
                findViewById(R.id.id_activity_configuration),
                getResources().getString(R.string.error_server),
                Snackbar.LENGTH_LONG
        );
    }
}
