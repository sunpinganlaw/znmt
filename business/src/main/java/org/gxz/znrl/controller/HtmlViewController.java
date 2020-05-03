package org.gxz.znrl.controller;

import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by xieyt on 2015/4/8.
 */

//@Controller
//@RequestMapping("/business/htmlView")
//@SuppressWarnings("unchecked")
public class HtmlViewController extends BaseAction {
    //页面跳转到总蓝图（库车）
    @RequestMapping(value="/kcIndex", method= RequestMethod.GET)
    public String gotoApprove() {
        return "/htmlviews/KC_INDEX/index";
    }

    //页面跳转到采样机（库车）
    @RequestMapping(value="/kcCY", method= RequestMethod.GET)
    public String gotokcCY() {
        return "/htmlviews/KC_CY/index";
    }

    //页面跳转到制样机（库车）
    @RequestMapping(value="/kcZY", method= RequestMethod.GET)
    public String gotokcZY() {
        return "/htmlviews/KC_ZY/index";
    }

    //页面跳转到称重（库车）
    @RequestMapping(value="/kcCZ", method= RequestMethod.GET)
    public String gotokcCZ() {
        return "/htmlviews/KC_CZ/index";
    }


    //页面跳转到称重（丰城）
    @RequestMapping(value="/fcCZ", method= RequestMethod.GET)
    public String gotofcCZ() {
        return "/htmlviews/FC_CZ/index";
    }

    //菜单跳转到称气动传输页面（丰城）
    @RequestMapping(value="/pipetransport", method= RequestMethod.GET)
    public String gotoWeight() {
        return "/monitor/pipetransport";
    }

    //页面跳转到气动传输（丰城）
    @RequestMapping(value="/fcQdcs", method= RequestMethod.GET)
    public String gotofcQdcs() {
        return "/htmlviews/FC_QDCS/index";
    }

    //页面跳转到总览图（衡丰）
    @RequestMapping(value="/hfIndex", method= RequestMethod.GET)
    public String gotohfIndex() {
        return "/htmlviews/HF_INDEX/index";
    }

    //页面跳转到采样机（衡丰）
    @RequestMapping(value="/hfCY", method= RequestMethod.GET)
    public String gotohfCY() {
        return "/htmlviews/HF_CY/index";
    }

    //页面跳转到制样机（衡丰）
//    @RequestMapping(value="/hfZY", method= RequestMethod.GET)
//    public String gotohfZY() {
//        return "/htmlviews/HF_ZY/index";
//    }

    //页面跳转到制样机（衡丰）flash
    @RequestMapping(value="/hfZY", method= RequestMethod.GET)
    public ModelAndView gotohfZY() {
        ModelAndView mav = new ModelAndView("/htmlviews/HF_ZY/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //页面跳转到总览图（丰城）
    @RequestMapping(value="/fcIndex", method= RequestMethod.GET)
    public String gotofcIndex() {
        return "/htmlviews/FC_INDEX/car_train";
    }

    //页面跳转到采样机（丰城）
    @RequestMapping(value="/fcCY", method= RequestMethod.GET)
    public String gotofcCY() {
        return "/htmlviews/FC_CY/index";
    }

    //页面跳转到采样机（丰城）
    @RequestMapping(value="/fcJXCY", method= RequestMethod.GET)
    public String gotofcJXCY() {
        return "/htmlviews/FC_JXCY/index";
    }

    //页面跳转到制样机（丰城）
    @RequestMapping(value="/fcZY", method= RequestMethod.GET)
    public String gotofcZY() {
        return "/htmlviews/FC_ZY/index";
    }

    //页面跳转到制样机（丰城）
    @RequestMapping(value="/fcZY4car", method= RequestMethod.GET)
    public String gotofcZY4car() {
        return "/htmlviews/FC_ZY/index4car";
    }

    //菜单跳转到全厂总览页面(哈平南)
    @RequestMapping(value="/trainAndCarOverview/list", method= RequestMethod.GET)
    public String gotoTrainAndCarOverview() {
        return "/monitor/trainAndCarOverview";
    }

    //菜单跳转到皮带采样监控页面(哈平南)
    @RequestMapping(value = "/sampleHpn/list", method = RequestMethod.GET)
    public String gotoTrainSampleFc() {
        return "/monitor/sample";
    }

    //菜单跳转到火车采样监控页面(哈平南)
    @RequestMapping(value = "/hcsample/list", method = RequestMethod.GET)
    public String gotoTrainhcSampleFc() {
        return "/monitor/hcSample";
    }

    //菜单跳转到制样监控页面(哈平南)
    @RequestMapping(value="/makesampleHpn/list", method= RequestMethod.GET)
    public String gotoMakeSampleFc() {
        return "/monitor/makesample";
    }

    //页面跳转到总览图（哈平南）
    @RequestMapping(value="/hpnIndex", method= RequestMethod.GET)
    public String gotohpnIndex() {
        return "/htmlviews/HPN_INDEX/index";
    }

    //页面跳转到采样机（哈平南）
    @RequestMapping(value="/hpnCY", method= RequestMethod.GET)
    public String gotohpnCY() {
        return "/htmlviews/HPN_CY/index";
    }

    //页面跳转到采样机（哈平南）
    @RequestMapping(value="/hpnJXCY", method= RequestMethod.GET)
    public String gotohpnJXCY() {
        return "/htmlviews/HPN_JXCY/index";
    }


    //页面跳转到制样机（哈平南）
    @RequestMapping(value="/hpnZY", method= RequestMethod.GET)
    public String gotohpnZY() {
        return "/htmlviews/HPN_ZY/index";
    }

    //页面跳转到采样机（哈平南）
    @RequestMapping(value="/hpnHCCY", method= RequestMethod.GET)
    public String gotohpnHCCY() {
        return "/htmlviews/HPN_HCCY/index";
    }

    //flash版本制样机
    @RequestMapping(value="/baseZY", method= RequestMethod.GET)
    public ModelAndView gotoBaseZY() {
        ModelAndView mav = new ModelAndView("/htmlviews/BASE_ZY/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //flash版本开元制样机
    @RequestMapping(value="/baseKYZY", method= RequestMethod.GET)
    public ModelAndView gotoBaseKYZY() {
        ModelAndView mav = new ModelAndView("/htmlviews/BASE_KYZY/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    @RequestMapping(value="/baseCZ", method= RequestMethod.GET)
    public ModelAndView gotoBaseCZ(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/htmlviews/BASE_CZ/index");
        mav.addObject("heavyNo", request.getParameter("heavyNo")==null?"1":request.getParameter("heavyNo"));
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //flash版本回皮(称轻)
    @RequestMapping(value="/baseCQ", method= RequestMethod.GET)
    public ModelAndView gotoBaseCQ() {
        ModelAndView mav = new ModelAndView("/htmlviews/BASE_CQ/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //flash版本气动传输
    @RequestMapping(value="/baseQDCS", method= RequestMethod.GET)
    public ModelAndView gotoBaseQDCS() {
        ModelAndView mav = new ModelAndView("/htmlviews/BASE_QDCS/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //flash版本远光存查样柜
    @RequestMapping(value="/baseYGCYG", method= RequestMethod.GET)
    public ModelAndView gotoBaseYGCYG() {
        ModelAndView mav = new ModelAndView("/htmlviews/BASE_YGCYG/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //汽车煤批次信息处理
    @RequestMapping(value="/carBatchInfo", method= RequestMethod.GET)
    public String gotoCarBatchInfo(){
        return "/monitor/carBatchInfo";
    }

    //手动新增汽车煤批次信息处理
    @RequestMapping(value="/manualAddCarBatchInfo", method= RequestMethod.GET)
    public String gotoManualAddCarBatchInfo(){
        return "/monitor/addCarBatchInfo";
    }

    //页面跳转到入厂制样机（大开）flash
    @RequestMapping(value="/DK_RCZY", method= RequestMethod.GET)
    public ModelAndView gotoRCZY() {
        ModelAndView mav = new ModelAndView("/htmlviews/DK_RCZY/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }
    //页面跳转到入炉制样机（大开）flash
    @RequestMapping(value="/DK_RLZY", method= RequestMethod.GET)
    public ModelAndView gotoRLZY() {
        ModelAndView mav = new ModelAndView("/htmlviews/DK_RLZY/index");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }
    //菜单跳转到入炉采样监控页面(大开)
    @RequestMapping(value = "/sampleInBoiler", method = RequestMethod.GET)
    public String gotoSampleInBoiler() {
        return "/monitor/sampleInBoiler";
    }

    //页面跳转到采样机（大开）
    @RequestMapping(value="/CYInBoiler", method= RequestMethod.GET)
    public String gotoCYInBoiler() {
        return "/htmlviews/DK_RLCY/index";
    }

    @RequestMapping(value="/desCode4RL", method= RequestMethod.GET)
    public String gotoDesCode4RL() {
        return "/report/desCode4RL";
    }


    /**
     * 前台日志
     * @return
     */
    @RequestMapping(value="/opLogRecord4IP", method= RequestMethod.GET)
    public String gotoOpLogRecord4IP() {
        return "/monitor/opLogRecord4IP";
    }
}
