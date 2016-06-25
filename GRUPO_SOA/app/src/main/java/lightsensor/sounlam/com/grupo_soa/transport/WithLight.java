package lightsensor.sounlam.com.grupo_soa.transport;

import com.google.gson.annotations.Expose;

/**
 * Created by Raul on 11/06/16.
 */
public class WithLight {
    @Expose
    private ContentLight content;

    public ContentLight getContent() {
        return content;
    }

    public void setContent(ContentLight content) {
        this.content = content;
    }
}
