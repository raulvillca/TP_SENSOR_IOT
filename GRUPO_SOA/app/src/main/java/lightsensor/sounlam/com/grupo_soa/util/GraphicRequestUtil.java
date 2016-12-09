package lightsensor.sounlam.com.grupo_soa.util;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lightsensor.sounlam.com.grupo_soa.R;
import lightsensor.sounlam.com.grupo_soa.connection.IComunicationFragment;
import lightsensor.sounlam.com.grupo_soa.connection.SensorRequest;
import lightsensor.sounlam.com.grupo_soa.transport.ContentLight;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseLight;
import lightsensor.sounlam.com.grupo_soa.transport.WithLight;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raulvillca on 8/12/16.
 */

public class GraphicRequestUtil {
    private RestAdapter restAdapter;
    private SensorRequest sensorRequest;
    private IComunicationFragment iComunication;

    public GraphicRequestUtil(AppCompatActivity activity) {
        this.restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(activity.getResources()
                .getString(R.string.url_server))
                .build();
        this.sensorRequest = restAdapter.create(SensorRequest.class);
        iComunication = (IComunicationFragment) activity;
    }

    /***
     * realizamos una consulta al servidor para obtener los valores de intensidades de la placa
     */
    public void sendLightRequest() {
        sensorRequest.getLight(new Callback<ResponseLight>() {
            @Override
            public void success(ResponseLight responseLight, Response response) {
                if (responseLight != null) {
                    Log.e("No hay error", "sin error");

                    List<WithLight> d = responseLight.getWith();
                    List<Number> yIntensity = new ArrayList<Number>();

                    WithLight with = null;
                    for (int i = 0; i < d.size(); i++) {
                        with = d.get(i);
                        ContentLight content = with.getContent();
                        yIntensity.add(content.getIntensidad());
                    }

                    iComunication.setLightResponse(yIntensity);

                } else {
                    Log.e("hay error", "con error");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("exception","Error, puede que no haya sistema");
                iComunication.notifyError(true);
            }
        });
    }
}
