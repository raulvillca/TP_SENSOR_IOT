package lightsensor.sounlam.com.grupo_soa.connection;

import java.util.List;

import lightsensor.sounlam.com.grupo_soa.transport.ContentConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ContentLight;

/**
 * Created by raulvillca on 8/12/16.
 */

public interface ICommunicationFragment {
    public void setLightResponse(List<Number> response);
    public void setConfigResponse(List<Number> maximo, List<Number> minimo);
    public void notifyError(boolean error);
}
