package lightsensor.sounlam.com.grupo_soa.util;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lightsensor.sounlam.com.grupo_soa.R;
import lightsensor.sounlam.com.grupo_soa.connection.IComunicationFragment;
import lightsensor.sounlam.com.grupo_soa.connection.SensorRequest;
import lightsensor.sounlam.com.grupo_soa.transport.ContentConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseConfig;
import lightsensor.sounlam.com.grupo_soa.transport.WithConfig;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raulvillca on 8/12/16.
 */

public class ConfigRequestUtil {

    private RestAdapter restAdapter;
    private SensorRequest sensorRequest;
    private IComunicationFragment iComunication;

    public ConfigRequestUtil(AppCompatActivity activity) {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(
                        activity.getResources().getString(R.string.url_server)
                )
                .build();
        sensorRequest = restAdapter.create(SensorRequest.class);

        iComunication = (IComunicationFragment) activity;
    }

    /***
     * realizamos un post, al servidor para modificar los margenes de tolerancia en la placa
     * @param maximo
     * @param minimo
     */
    public void sendConfigRequest(int maximo, int minimo) {
        sensorRequest.setConf( minimo, maximo, new Callback<ContentConfig>() {
            @Override
            public void success(ContentConfig contentConfig, Response response) {
                //Capturo los datos que me sirven

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("exception","Error, puede que no haya sistema");
                iComunication.notifyError(true);
            }
        });
    }

    /***
     * realizamos una consulta al servidor para obtener el maximo y minimo del margen de tolerancia
     * para la detección de intensidad
     */
    public void sendConfigRequest() {
        sensorRequest.getConf(new Callback<ResponseConfig>() {
            @Override
            public void success(ResponseConfig responseConfig, Response response) {
                if (responseConfig != null) {
                    Log.e("No hay error", "sin error");
                    //Capturo los datos que me sirven
                    List<WithConfig> d = responseConfig.getWith();
                    //solo nos interesa el primero dato
                    WithConfig with = d.get(0);
                    ContentConfig content = with.getContent();

                    List<Number> maxNumbers = new ArrayList<Number>();
                    List<Number> minNumbers = new ArrayList<Number>();

                    // 5 es la cantidad de items que recibimos del servidor de dweet
                    //y debe concordar con la lista de intensidades
                    for (int i = 0; i < 5; i++) {
                        maxNumbers.add(content.getMaximo());
                        minNumbers.add(content.getMinimo());
                    }

                    iComunication.setConfigResponse(maxNumbers, minNumbers);

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
