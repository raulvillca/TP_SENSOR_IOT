package lightsensor.sounlam.com.grupo_soa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lightsensor.sounlam.com.grupo_soa.connection.ArrayNumber;
import lightsensor.sounlam.com.grupo_soa.connection.SensorRequest;
import lightsensor.sounlam.com.grupo_soa.fragment.ConfigFragment;
import lightsensor.sounlam.com.grupo_soa.fragment.GraphicFragment;
import lightsensor.sounlam.com.grupo_soa.transport.ContentConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ContentLight;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseConfig;
import lightsensor.sounlam.com.grupo_soa.transport.ResponseLight;
import lightsensor.sounlam.com.grupo_soa.transport.WithConfig;
import lightsensor.sounlam.com.grupo_soa.transport.WithLight;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayNumber arrayNumber;
    private RestAdapter restAdapter;
    private SensorRequest sensorRequest;

    private List<Integer> arrayNotification;
    private int min;
    private final static String TITLE_BOARD = "Mensaje de Galileo";
    private final static String MSJ_BOARD = "Se detecto un objeto muy cerca";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        arrayNumber = new ArrayNumber();
        //Realizo la consulta al servidor
        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://dweet.io")
                .build();
        sensorRequest = restAdapter.create(SensorRequest.class);
        notificationTask();

    }

    private void notificationTask() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    consultar();
                    SystemClock.sleep(15000);
                }
            }
        };
        timer.schedule(timerTask, 0L);
    }

    private void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(TITLE_BOARD)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(TITLE_BOARD)
                .setContentText(MSJ_BOARD)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private boolean sendNotification() {
        int cant = 0;

        for (int i = 0; i < arrayNotification.size(); i++) {
            if (arrayNotification.get(i) < min) {
                cant++;
            }
        }

        if (cant == arrayNotification.size()) {
            arrayNotification.clear();
            showNotification();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.id_menu_config) {
            fragment = new ConfigFragment();
        } else if (id == R.id.id_menu_grap) {
            Toast.makeText(MainActivity.this, "Descargando ...", Toast.LENGTH_LONG).show();
            //instancio el fragmento con los datos de la anterior descarga
            fragment = new GraphicFragment(
                    arrayNumber.getMax(),
                    arrayNumber.getMin(),
                    arrayNumber.getIntensityNumberList());
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.id_activity_main, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void consultar () {
        sensorRequest.getConf(new Callback<ResponseConfig>() {
            @Override
            public void success(ResponseConfig responseConfig, Response response) {
                //Capturo los datos que me sirven
                List<WithConfig> d = responseConfig.getWith();
                WithConfig with = d.get(0);
                ContentConfig content = with.getContent();
                min = content.getMinimo();
                arrayNumber.setMax(content.getMaximo());
                arrayNumber.setMin(content.getMinimo());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        sensorRequest.getLight(new Callback<ResponseLight>() {
            @Override
            public void success(ResponseLight responseLight, Response response) {
                //Capturo los datos que me sirven
                List<WithLight> d = responseLight.getWith();
                List<Number> intensidades = new ArrayList<Number>();
                WithLight with = null;
                arrayNotification = new ArrayList<Integer>();
                for (int i = 0; i < d.size(); i++) {
                    with = d.get(i);
                    ContentLight content = with.getContent();
                    intensidades.add(content.getIntensidad());
                    arrayNotification.add(content.getIntensidad());
                }
                sendNotification();
                arrayNumber.setIntensityNumberList(intensidades);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
