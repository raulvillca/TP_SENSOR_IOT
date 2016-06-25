package lightsensor.sounlam.com.grupo_soa.transport;

import com.google.gson.annotations.Expose;

/**
 * Created by Raul on 11/06/16.
 */
public class ContentConfig {
    @Expose
    private int minimo;
    @Expose
    private int maximo;

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }
}
