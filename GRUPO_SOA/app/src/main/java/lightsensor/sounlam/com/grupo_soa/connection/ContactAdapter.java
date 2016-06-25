package lightsensor.sounlam.com.grupo_soa.connection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import lightsensor.sounlam.com.grupo_soa.R;

/**
 * Created by raulvillca on 25/6/16.
 */
public class ContactAdapter extends BaseAdapter {
    private List<Contact> contacts;
    private LayoutInflater inflate;
    public ContactAdapter(Context context, List<Contact> list) {
        inflate = LayoutInflater.from(context);
        contacts = list;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = null;
        if (view == null) {
            v = inflate.inflate(R.layout.about_contact, viewGroup, false);
        } else {
            v = view;
        }
        populate(v, contacts.get(i));
        return v;
    }

    private void populate (View view, Contact item) {
        TextView title = (TextView) view.findViewById(R.id.id_about_item_text);
        title.setText(item. getFullname());
    }
}
