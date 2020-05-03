package org.gxz.znrl.controller;
/**
 * Created by admin on 2014/7/8.
 */

import org.gxz.znrl.common.SecurityConstants;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.viewModel.GridModel;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/management/security/sessions")
@SuppressWarnings("unchecked")
public class SessionController extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(SessionController.class);

    private static final String LIST = "management/security/sessions/list";
    private static final String LIST2 = "management/security/sessions/list2";


    @Autowired
    private SessionDAO sessionDAO;

    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return LIST;
    }

    @RequestMapping(value="/list2", method= RequestMethod.GET)
    public String list2(HttpServletRequest request) {
        return LIST2;
    }


    @RequestMapping(value="/listShow")
    @ResponseBody
    public GridModel list() {
        Collection<Session> sessions =  sessionDAO.getActiveSessions();
        GridModel m = new GridModel();
        List<Session> list = new ArrayList();
        for(Session s:sessions){
            log.debug(s.toString());
            list.add(s);
        }
        m.setRows(list);
        m.setTotal(sessions.size());
        return m;
    }

    @RequestMapping("/forceLogout/{sessionId}")
    @ResponseBody
    public Result forceLogout(@PathVariable String sessionId) {
        Result result = new Result();
        try {
            Session session = sessionDAO.readSession(sessionId);
            if (session != null) {
                session.setAttribute(SecurityConstants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
                session.setTimeout(1);
                session.stop();
            }
            result.setMsg("强制退出成功！");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("强制退出失败！");
            result.setSuccessful(false);
        }
        return result;
    }
}
