package lightsensor.sounlam.com.grupo_soa.connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raulvillca on 25/6/16.
 */
public class Contacts {
    public static List<Contact> ContactList() {
        List<Contact> list = new ArrayList<Contact>();
        list.add(new Contact("Acevedo Zain Gaspar"));
        list.add(new Contact("..... Federico"));
        list.add(new Contact("Villca Raul"));
        return list;
    }
}
