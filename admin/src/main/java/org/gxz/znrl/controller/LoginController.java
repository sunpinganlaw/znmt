package org.gxz.znrl.controller;

import org.gxz.znrl.common.SecurityConstants;
import org.gxz.znrl.shiro.*;
import org.gxz.znrl.viewModel.Json;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class); 
	
	private static final String LOGIN_PAGE = "login";
	private static final String LOGIN_DIALOG = "management/index/loginDialog";
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return LOGIN_PAGE;
	}
	
	@RequestMapping(value="/load") 
	@ResponseBody
	public Json load(String username,String password,String captcha_key,Boolean rememberMe) throws Exception {
        //System.out.println("username:"+username+",password:"+password+",captcha_key:"+captcha_key+",rememberMe:"+rememberMe);
        Subject subject=SecurityUtils.getSubject();
		CaptchaUsernamePasswordToken token=new CaptchaUsernamePasswordToken();
        token.setUsername(username);
        token.setPassword(password.toCharArray());
        token.setCaptcha(captcha_key);
        token.setRememberMe(rememberMe);
        Json json=new Json();
        json.setTitle("登录提示");
        try{
            subject.login(token);
            LOG.debug("sessionTimeout===>"+subject.getSession().getTimeout());
         /*   subject.get
            Cookie cookie = new Cookie("easyuiThemeName",user.getTheme());
            response.addCookie(cookie);*/

            json.setStatus(true);	
        }
        catch (UnknownSessionException use) {
            subject = new Subject.Builder().buildSubject();
            subject.login(token);
            LOG.error(SecurityConstants.UNKNOWN_SESSION_EXCEPTION);
            json.setMessage(SecurityConstants.UNKNOWN_SESSION_EXCEPTION);
        }
        catch(UnknownAccountException ex){
			LOG.error(SecurityConstants.UNKNOWN_ACCOUNT_EXCEPTION);
			json.setMessage(SecurityConstants.UNKNOWN_ACCOUNT_EXCEPTION);
		}
        catch (IncorrectCredentialsException ice) {
            json.setMessage(SecurityConstants.INCORRECT_CREDENTIALS_EXCEPTION);
        } 
        catch (LockedAccountException lae) {
            json.setMessage(SecurityConstants.LOCKED_ACCOUNT_EXCEPTION);
        }
        catch (DisabledAccountException dae) {
            json.setMessage(SecurityConstants.DISABLED_ACCOUNT_EXCEPTION);
        }
        catch (IncorrectCaptchaException e) {
        	 json.setMessage(SecurityConstants.INCORRECT_CAPTCHA_EXCEPTION);
		}catch (RepeatLoginException e) {
            /*RealmSecurityManager securityManager =
            (RealmSecurityManager) SecurityUtils.getSecurityManager();
            ShiroDbRealm userRealm = (ShiroDbRealm) securityManager.getRealms().iterator().next();
            userRealm.clearCachedAuthorizationInfo((String)subject.getPrincipal());*/
        	json.setMessage(SecurityConstants.REPEAT_LOGIN_EXCEPTION);
		}
        catch (AuthenticationException ae) {
            json.setMessage(SecurityConstants.AUTHENTICATION_EXCEPTION);
        } 
        catch(Exception e){
            json.setMessage(SecurityConstants.UNKNOWN_EXCEPTION);
        }
        return json;
	}
	
}
