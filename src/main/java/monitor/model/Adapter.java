package monitor.model;

import java.util.List;

/**
 * Created by yfzhangbin on 15-5-11.
 */
public class Adapter {
    private List<String> cols;
    private List<Long> values;

    public List<String> getCols() {
        return cols;
    }

    public void setCols(List<String> cols) {
        this.cols = cols;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Adapter{" +
                ", cols=" + cols +
                ", values=" + values +
                '}';
    }
}
