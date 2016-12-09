package lightsensor.sounlam.com.grupo_soa.connection;

import lightsensor.sounlam.com.grupo_soa.transport.ContentConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ContentLight;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseLight;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Raul on 8/06/16.
 */
public interface SensorRequest {
    //https://dweet.io/get/dweets/for/lightsensor_sounlam_com_grupo_soa_config
    @GET("/get/dweets/for/lightsensor_sounlam_com_grupo_soa_config")
    void getConf(Callback<ResponseConfig> callback);
    ///dweet/for/lightsensor.sounlam.com.grupo_soa.config?minimo=130&maximo=200

    //https://dweet.io/dweet/for/lightsensor_sounlam_com_grupo_soa_config?minimo=yMin&maximo=yMax
    @GET("/dweet/for/lightsensor_sounlam_com_grupo_soa_config")
    void setConf(@Query("minimo") int minimo, @Query("maximo") int maximo, Callback<ContentConfig> callback);

    @GET("/get/dweets/for/lightsensor_sounlam_com_grupo_soa_light")
    void getLight(Callback<ResponseLight> callback);

//    @GET("/dweet/for/lightsensor_sounlam_com_grupo_soa_light?intensidad=100")
//    void setLight(Callback<ContentLight> callback);
}
