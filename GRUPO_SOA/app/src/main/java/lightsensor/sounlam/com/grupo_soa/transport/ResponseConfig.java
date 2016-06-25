package lightsensor.sounlam.com.grupo_soa.transport;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Raul on 11/06/16.
 */
public class ResponseConfig {
    @Expose
    private List<WithConfig> with;

    public List<WithConfig> getWith() {
        return with;
    }

    public void setWith(List<WithConfig> with) {
        this.with = with;
    }
}
