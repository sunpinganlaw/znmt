package org.gxz.znrl.controller;

import org.gxz.znrl.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by xieyt on 2015/4/8.
 */

@Controller
@RequestMapping("/business/htmlView")
@SuppressWarnings("unchecked")
public class LocalHtmlViewController extends HtmlViewController {

    //页面跳转到总览图
    @RequestMapping(value="/index", method= RequestMethod.GET)
    public ModelAndView gotoIndex() {
        ModelAndView mav = new ModelAndView("/htmlviews/BB_INDEX/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //页面跳转到采样机（丰城）
    @RequestMapping(value="/bbCY", method= RequestMethod.GET)
    public String gotofcCY() {
        return "/htmlviews/BB_CY/index";
    }

    //页面跳转到采样机（丰城）
    @RequestMapping(value="/bbJXCY", method= RequestMethod.GET)
    public String gotofcJXCY() {
        return "/htmlviews/BB_JXCY/index";
    }

    //页面跳转到制样机（丰城）
    @RequestMapping(value="/bbZY", method= RequestMethod.GET)
    public String gotofcZY() {
        return "/htmlviews/BB_ZY/index";
    }

    //页面跳转到制样机（丰城）
    @RequestMapping(value="/bbZY4car", method= RequestMethod.GET)
    public String gotofcZY4car() {
        return "/htmlviews/BB_ZY/index4car";
    }

    @RequestMapping(value="/bbSYCY", method= RequestMethod.GET)
    public String gotoSyCY() {
        return "/htmlviews/BB_SYCY/index";
    }

    //气动传输
    @RequestMapping(value="/bbQDCS", method= RequestMethod.GET)
    //@RequestMapping(value="/pipetransport", method= RequestMethod.GET)
    public ModelAndView gotoBbQDCS() {
        ModelAndView mav = new ModelAndView("/htmlviews/BB_QDCS/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //蚌埠水运煤的船的详情
    @RequestMapping(value="/shipCargoDetail", method= RequestMethod.GET)
    public String shipCargoDetail() {
        return "/monitor/shipCargoDetail";
    }

    //蚌埠新增水运煤批次
    @RequestMapping(value="/addNewShipBatchWin", method= RequestMethod.GET)
    public String addNewShipBatchWin() {
        return "/monitor/addNewShipBatch";
    }

    //蚌埠修改卸煤吨位
    @RequestMapping(value="/modifyAllNetQtyWin", method= RequestMethod.GET)
    public String modifyAllNetQtyWin() {
        return "/monitor/modifyAllNetQty";
    }

    //蚌埠入炉采样监控
    @RequestMapping(value="/bbRL", method= RequestMethod.GET)
    public ModelAndView gotobbRL() {
        ModelAndView mav = new ModelAndView("/htmlviews/BB_RLCY/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //蚌埠水运煤批次监控
    @RequestMapping(value = "/monitor/{pageName}", method = {RequestMethod.GET, RequestMethod.POST})
    public String showMonitorPage(@PathVariable String pageName) {
        return "/monitor/" + pageName;
    }
}
