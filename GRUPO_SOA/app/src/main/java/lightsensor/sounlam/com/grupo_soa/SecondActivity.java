package lightsensor.sounlam.com.grupo_soa;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by raulvillca on 21/6/16.
 */
public class SecondActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        progressBar = (ProgressBar) findViewById(R.id.id_second_progressBar_init);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0 ; i < progressBar.getMax(); i++) {
                    progressBar.setProgress(i+1);
                    SystemClock.sleep(20);
                }

                Intent i = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };

        timer.schedule(timerTask, 0L);
    }
}
