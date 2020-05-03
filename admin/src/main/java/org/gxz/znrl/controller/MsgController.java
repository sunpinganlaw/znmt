package org.gxz.znrl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.znrl.entity.Msg;
import org.gxz.znrl.service.MsgService;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Alex on 2014/9/9.
 */
@Controller
@RequestMapping(value = "/business/msg")
public class MsgController extends BaseAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MsgService msgService;


    private static final String LIST = "business/msg/msgList";
    private static final String ADD = "business/msg/msgAddEdit";
    private static final String VIEW = "business/msg/msgView";

    @RequestMapping(value="/msgList", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return LIST;
    }

    @RequestMapping(value="/msgAddEdit", method= RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request) {
        Msg msg = new Msg();
        ModelAndView mav = new ModelAndView(ADD);
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(msg);
            mav.addObject("message", "success");
            mav.addObject("doWhat", "add");
            mav.addObject("msg",json);
            mav.addObject("attList",mapper.writeValueAsString(""));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }



    @RequestMapping(value="/listShow")
    @ResponseBody
    public GridModel listMsg() {
        GridModel m = new GridModel();
        Msg msg = form(Msg.class);
        msg.setMsgType("1");
        Page info = msgService.find(page(), msg);
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }




    /**
     * 用日期和随机数格式化文件名避免冲突
     * @param fileName
     * @return
     */
    private String generateFileName(String fileName) {
        System.out.println(fileName);
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
        String formatDate = sf.format(new Date());
        int random = new Random().nextInt(10000);
        //int position = fileName.lastIndexOf(".");
        //String extension = fileName.substring(position);
        return formatDate + random + fileName;
    }



    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value="/delete/{id}", method= RequestMethod.POST)
    @ResponseBody
    public Result msgDelete(@PathVariable Integer id) {
        Result result = new Result();
        Msg msg = new Msg();
        msg.setMsgId((long)id);
        msg.setState("X");
        msgService.update(msg);
        //msgService.delete((long)id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }



    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value="/get/{id}", method= RequestMethod.POST)
    @ResponseBody
    public Msg getInfo(@PathVariable Long id) {
        return  msgService.get(id);
    }

}
