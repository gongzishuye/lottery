package monitor.utils;


import monitor.model.Adapter;
import monitor.model.Monitor;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by yfzhangbin on 15-5-11.
 */
public class AdapterUtil {
    public static Adapter transfor(List<Monitor> list) {
        String ip = "";
        List<String> cols = new LinkedList<String>();
        List<Long> values = new LinkedList<Long>();
        for (Monitor li : list) {
            cols.add(li.getTimer());
            values.add(Long.valueOf(li.getNumber()));
        }
        Adapter adapter = new Adapter();
        adapter.setCols(cols);
        adapter.setValues(values);
        return adapter;
    }
}
