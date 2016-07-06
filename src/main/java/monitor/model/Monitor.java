package monitor.model;

/**
 * Created by chenlu11 on 2016/1/21.
 */
public class Monitor {

    private Integer number;
    private String  timer;

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public String getTimer() {
        return timer;
    }

}
