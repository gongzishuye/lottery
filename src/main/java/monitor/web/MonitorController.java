package monitor.web;

import monitor.dao.MonitorDao;
import monitor.model.Adapter;
import monitor.model.Monitor;
import monitor.utils.AdapterUtil;
import monitor.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 172.17.34.142
 * Created by yfzhangbin on 15-5-11.
 */
@Controller
@RequestMapping("/")
public class MonitorController {


    private MonitorDao monitorDao = new MonitorDao();

    @RequestMapping
    public ModelAndView execute(@RequestParam(required = false, defaultValue = "mercury") String ext) {
        ModelAndView mv = new ModelAndView("empty-chart");
        List<String> recPlacement = new ArrayList<String>();
        recPlacement.add("rec.100000");
        recPlacement.add("rec.100001");
        mv.addObject("rec_placement", recPlacement);
        return mv;
    }

    @RequestMapping("monitor")
    public ModelAndView monitor(@RequestParam String placement) {
        ModelAndView mv = new ModelAndView("offline-chart");
        List<Monitor> list = monitorDao.queryDates();
        Adapter adapter = AdapterUtil.transfor(list);
        mv.addObject("datas", JsonUtil.toJson(adapter));
        mv.addObject("test", "---------------");
//        mv.addObject("test",list.get(0).getNumber());
//        mv.addObject("groups", groups);
//        mv.addObject("ext", ext);
//        mv.addObject("gtm", groupTargetMap);
        return mv;
    }

    public List<Monitor> readFromMysql(){
        return monitorDao.queryDates();
    }
}
