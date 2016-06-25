package lightsensor.sounlam.com.grupo_soa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lightsensor.sounlam.com.grupo_soa.connection.ArrayNumber;
import lightsensor.sounlam.com.grupo_soa.connection.SensorRequest;
import lightsensor.sounlam.com.grupo_soa.fragment.ConfigFragment;
import lightsensor.sounlam.com.grupo_soa.fragment.GraphicFragment;
import lightsensor.sounlam.com.grupo_soa.fragment.SessionFragment;
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
        consultar();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.id_menu_session) {
            fragment = new SessionFragment();
        } else if (id == R.id.id_menu_config) {
            fragment = new ConfigFragment();
        } else if (id == R.id.id_menu_grap) {
            consultar();
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
                arrayNumber.setMax(content.getMaximo());
                arrayNumber.setMin(content.getMinimo());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Error en la conexion", Toast.LENGTH_LONG).show();
            }
        });
        sensorRequest.getLight(new Callback<ResponseLight>() {
            @Override
            public void success(ResponseLight responseLight, Response response) {
                //Capturo los datos que me sirven
                List<WithLight> d = responseLight.getWith();
                List<Number> intensidades = new ArrayList<Number>();
                WithLight with = null;
                if (intensidades.size() != 0) {
                    for (int i = d.size() - 1; i >= 0; i--) {
                        with = d.get(i);
                        ContentLight content = with.getContent();
                        intensidades.add(content.getIntensidad());
                    }
                    arrayNumber.setIntensityNumberList(intensidades);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Error en la conexion", Toast.LENGTH_LONG).show();
            }
        });
    }
}
