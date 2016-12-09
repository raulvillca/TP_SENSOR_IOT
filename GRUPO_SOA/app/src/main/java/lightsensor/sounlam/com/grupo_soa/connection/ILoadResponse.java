package lightsensor.sounlam.com.grupo_soa.connection;

import java.util.List;

/**
 * Created by raulvillca on 8/12/16.
 */

public interface ILoadResponse {
    public void reload(List<Number> intensity, List<Number> maxList, List<Number> minList);
}
