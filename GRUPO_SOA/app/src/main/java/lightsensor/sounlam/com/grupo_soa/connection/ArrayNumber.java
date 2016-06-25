package lightsensor.sounlam.com.grupo_soa.connection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raul on 12/06/16.
 */
public class ArrayNumber implements Serializable {
    private Number max;
    private Number min;
    private List<Number> intensityNumberList;

    public ArrayNumber() {
        this.intensityNumberList = new ArrayList<Number>();
    }

    public Number getMax() {
        return max;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public List<Number> getIntensityNumberList() {
        return intensityNumberList;
    }

    public void setIntensityNumberList(List<Number> intensityNumberList) {
        this.intensityNumberList = intensityNumberList;
    }
}
