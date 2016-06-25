package lightsensor.sounlam.com.grupo_soa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import lightsensor.sounlam.com.grupo_soa.connection.ContactAdapter;
import lightsensor.sounlam.com.grupo_soa.connection.Contacts;

/**
 * Created by raulvillca on 24/6/16.
 */
public class AboutActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        listView = (ListView) findViewById(R.id.id_about_list);
        listView.setAdapter(new ContactAdapter(getApplicationContext(), Contacts.ContactList()));
    }
}
