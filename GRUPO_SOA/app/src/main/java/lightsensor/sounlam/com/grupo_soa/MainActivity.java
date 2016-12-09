package lightsensor.sounlam.com.grupo_soa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lightsensor.sounlam.com.grupo_soa.connection.IComunicationFragment;
import lightsensor.sounlam.com.grupo_soa.fragment.GraphicFragment;
import lightsensor.sounlam.com.grupo_soa.util.ConfigRequestUtil;
import lightsensor.sounlam.com.grupo_soa.util.GraphicRequestUtil;
import lightsensor.sounlam.com.grupo_soa.util.MainUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IComunicationFragment {

    private List<Integer> arrayNotification;
    private int max = 200;
    private int retry = 0;
    private boolean connection = true;
    private List<Number> intensityList;

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

        consulting();
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

        Intent i = null;
        if (item.getItemId() == R.id.id_menu_grap) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.id_activity_main, new GraphicFragment(), GraphicFragment.TAG)
                    .commit();
        } else if (item.getItemId() == R.id.id_menu_config) {
            i = new Intent(MainActivity.this, ConfigActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void consulting() {

        final GraphicRequestUtil requestUtil1 = new GraphicRequestUtil(MainActivity.this);
        final ConfigRequestUtil requestUtil2 = new ConfigRequestUtil(MainActivity.this);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                while (connection) {
                    Log.e("Realizando consulta","primera");
                    requestUtil1.sendLightRequest();
                    SystemClock.sleep(2500);
                    requestUtil2.sendConfigRequest();
                }
            }
        };
        new Timer().schedule(timerTask, 0L);

    }

    @Override
    public void setLightResponse(List<Number> response) {
        intensityList = response;
        List<Integer> intengerList = new ArrayList<>();
        Log.e("Realizando consulta","primera Respuesta");
        for (int i = 0; i < response.size(); i++) {
            intengerList.add(response.get(i).intValue());
        }

        sendNotification(intengerList);
    }

    @Override
    public void setConfigResponse(List<Number> maximo, List<Number> minimo) {
        Log.e("Realizando consulta","segunda Respuesta");
        if (intensityList != null) {
            GraphicFragment fragment = (GraphicFragment) getSupportFragmentManager().findFragmentByTag(GraphicFragment.TAG);
            if (fragment != null && fragment.isVisible()) {
                max = maximo.get(0).intValue();
                fragment.reload(intensityList, maximo, minimo);
            }
        }
    }

    @Override
    public void notifyError(boolean error) {
        //se podria validar el tipo de error, pero si no hay servidor entonces hacemos un aviso general
        if (error) {
            retry++;
            if (retry > 4) {
                Snackbar.make(
                        findViewById(R.id.id_activity_main),
                        getResources().getString(R.string.error_server),
                        Snackbar.LENGTH_LONG);
                connection = false;
            }
        }
    }

    //enviamos notificaciones si todos los valores son mayores al margen de intensidad
    private boolean sendNotification(List<Integer> arrayNotification) {
        int cant = 0;

        for (int i = 0; i < arrayNotification.size(); i++) {
            if (arrayNotification.get(i) > max) {
                cant++;
            }
        }

        if (cant == arrayNotification.size()) {
            arrayNotification.clear();
            MainUtil.showNotification(MainActivity.this,
                    getResources().getString(R.string.title_notification),
                    getResources().getString(R.string.message_notification));
            return true;
        }
        return false;
    }
}
