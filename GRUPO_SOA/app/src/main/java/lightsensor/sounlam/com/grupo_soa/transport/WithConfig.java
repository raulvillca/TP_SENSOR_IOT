package lightsensor.sounlam.com.grupo_soa.transport;

import com.google.gson.annotations.Expose;

/**
 * Created by Raul on 11/06/16.
 */
public class WithConfig {
    @Expose
    private ContentConfig content;

    public ContentConfig getContent() {
        return content;
    }

    public void setContent(ContentConfig content) {
        this.content = content;
    }
}
