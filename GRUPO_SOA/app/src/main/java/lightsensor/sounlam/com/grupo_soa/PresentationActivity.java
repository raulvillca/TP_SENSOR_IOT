package lightsensor.sounlam.com.grupo_soa;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by raulvillca on 21/6/16.
 */
public class PresentationActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView textView;
    private int progress = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        progressBar = (ProgressBar) findViewById(R.id.id_second_progressbar);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (progress = 1 ; progress <= progressBar.getMax(); progress++) {
                    progressBar.setProgress(progress);

                    SystemClock.sleep(20);
                }
                Intent i = new Intent(PresentationActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };
        timer.schedule(timerTask, 0L);
    }
}
