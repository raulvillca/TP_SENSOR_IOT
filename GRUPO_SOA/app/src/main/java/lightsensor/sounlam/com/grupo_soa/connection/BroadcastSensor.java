package lightsensor.sounlam.com.grupo_soa.connection;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

/**
 * Created by Raul on 8/06/16.
 */
public class BroadcastSensor extends BroadcastReceiver {
    public static String TRANS_INTENT = "sounlam.grupo_soa.sendBroadcast";

    public static final int OP_GRAPHIC = 1;
    private ArrayNumber arrayNumber;
    private Number max;
    private Number min;

    public BroadcastSensor(ArrayNumber arrayNumber) {
        this.arrayNumber = arrayNumber;
        this.max = 150;
        this.min = 50;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //si no hay ninguna operacion enviada por intent, entonces devuelvo -1
        int op = intent.getIntExtra("operacion", -1);
        switch (op) {
            case OP_GRAPHIC:
                arrayNumber = (ArrayNumber) intent.getSerializableExtra("datos");
                System.out.println("maximo " + arrayNumber.getMax() + " minimo " + arrayNumber.getMin());
                break;
        }
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public Number getMax() {
        return this.max;
    }

    public Number getMin() {
        return this.min;
    }
}
