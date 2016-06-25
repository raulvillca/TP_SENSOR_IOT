package lightsensor.sounlam.com.grupo_soa.transport;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Raul on 11/06/16.
 */
public class ResponseLight {
    @Expose
    private List<WithLight> with;

    public List<WithLight> getWith() {
        return with;
    }

    public void setWith(List<WithLight> with) {
        this.with = with;
    }
}
