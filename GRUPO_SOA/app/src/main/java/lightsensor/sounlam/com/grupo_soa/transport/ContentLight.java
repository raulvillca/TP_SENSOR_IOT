package lightsensor.sounlam.com.grupo_soa.transport;

import com.google.gson.annotations.Expose;

/**
 * Created by Raul on 11/06/16.
 */
public class ContentLight {
    @Expose
    private int intensidad;

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }
}
