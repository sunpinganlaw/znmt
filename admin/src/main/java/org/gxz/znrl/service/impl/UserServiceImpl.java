package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.common.util.SimpleDate;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.exception.ExistedException;
import org.gxz.znrl.exception.ServiceException;
import org.gxz.znrl.mapper.OrganizationMapper;
import org.gxz.znrl.mapper.ProfessionMapper;
import org.gxz.znrl.mapper.UserMapper;
import org.gxz.znrl.service.*;
import org.gxz.znrl.shiro.ShiroDbRealm;
import org.gxz.znrl.shiro.ShiroDbRealm.HashPassword;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
@SuppressWarnings("unchecked")
public class UserServiceImpl extends BaseService implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ShiroDbRealm shiroRealm;
	
	@Autowired
	private UserRoleService userRoleService;


    @Autowired
    private UserPlaintextService userPlaintextService;

    @Autowired
    private ExtMsgService extMsgService;

    @Autowired
    private CommonService commonService;
	
	@Override
	public void delete(Long id) throws ServiceException {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new ServiceException("不能删除超级管理员用户。");
		}
		
		User user = baseDao.getMapper(UserMapper.class).selectByPrimaryKey(id);
		baseDao.getMapper(UserMapper.class).deleteByPrimaryKey(user.getId());
		
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());

	}

    @Override
    public void delete(User user) throws ServiceException {
//        baseDao.getMapper(UserMapper.class).updateByPrimaryKeySelective(user);
        User userTmp =  baseDao.getMapper(UserMapper.class).selectByPrimaryKey(user.getId());
        baseDao.getMapper(UserMapper.class).deleteById(user);
        shiroRealm.clearCachedAuthorizationInfo(userTmp.getUsername());
    }

	@Override
	public List<User> find(User user) {
		UserCriteria criteria = new UserCriteria();
		UserCriteria.Criteria cri = criteria.createCriteria();
		
		if(user != null){
			
			if(StringUtils.isNotBlank(user.getUsername())){
				cri.andUsernameEqualTo(user.getUsername());
			}
			
			if(StringUtils.isNotBlank(user.getEmail())){
				cri.andEmailEqualTo(user.getEmail());
			}
			
			if(StringUtils.isNotBlank(user.getRealname())){
				cri.andRealnameEqualTo(user.getRealname());
			}
			
			if(StringUtils.isNotBlank(user.getStatus())){
				cri.andStatusEqualTo(user.getStatus());
			}
            else
            {
                cri.andStatusEqualTo("enabled");
            }
			
			if(user.getOrgId() != null){
				cri.andOrgIdEqualTo(user.getOrgId());
			}
			
			if(StringUtils.isNotBlank(user.getPhone())){
				cri.andPhoneEqualTo(user.getPhone());
			}
		}
        List<User> list =baseDao.getMapper(UserMapper.class).selectByExample(criteria);
        if(list != null && list.size() > 0){
            for(User u:list){
                u.setUserRoles(userRoleService.find(u.getId()));
            }
        }
		
		return list;
	}

	@Override
	public Page findByPage(Page page,User user) {
		
		UserCriteria criteria = new UserCriteria();
		UserCriteria.Criteria cri = criteria.createCriteria();
		
		if(user != null){


            if (StringUtils.isNotBlank(user.getUsername())) {
//                cri.addCriterion(" username LIKE CONCAT('%','"+user.getUsername()+"','%') ");
                cri.addCriterion(" username LIKE '%"+user.getUsername()+"%' ");
            }
            if (StringUtils.isNotBlank(user.getRealname())) {
                cri.addCriterion(" realname LIKE '%"+user.getRealname()+"%'");
            }
            if (StringUtils.isNotBlank(user.getEmail())) {
                cri.addCriterion(" email LIKE '%"+user.getEmail()+"%'");
            }

			if(StringUtils.isNotBlank(user.getStatus())){
				cri.andStatusEqualTo(user.getStatus());
			}
			
			if(user.getOrgId() != null){
				cri.andOrgIdEqualTo(user.getOrgId());
			}
			
			if(StringUtils.isNotBlank(user.getPhone())){
				cri.andPhoneEqualTo(user.getPhone());
			}
            cri.addCriterion(" status = 'enabled'");
		}
		
		if(page != null && page.getSort() != null && page.getOrder() != null){
			criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
		}
		
		List<User> list = new ArrayList<User>();
		if(page == null){
			list = baseDao.getMapper(UserMapper.class).selectByExample(criteria);
		}
		
		list = baseDao.selectByPage("org.gxz.znrl.mapper.UserMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria, page);
		if(list != null && list.size() > 0){
			for(User u:list){
				setUserOrganization(u);
                setUserProfession(u);
				u.setUserRoles(userRoleService.find(u.getId()));
			}
		}
		page.setCount(baseDao.getMapper(UserMapper.class).countByExample(criteria));
		return page.setRows(list);
	}

	@Override
	public User get(String username) {
		UserCriteria criteria = new UserCriteria();
		UserCriteria.Criteria cri = criteria.createCriteria();
		if(StringUtils.isNotBlank(username)){
			cri.andUsernameEqualTo(username);
		}
		
		List<User> list = baseDao.getMapper(UserMapper.class).selectByExample(criteria);
		if(list != null && list.size() > 0){
			User user = list.get(0);
			setUserOrganization(user);
            user.setUserRoles(userRoleService.find(user.getId()));
			return user;
		}
		return null;
	}
	
	public void setUserOrganization(User user){
		if(user != null && user.getOrgId() != null){
			user.setOrganization(baseDao.getMapper(OrganizationMapper.class).selectByPrimaryKey(user.getOrgId()));
		}
        setTheme(user);
	}

    public void setUserProfession(User user){
        if(user != null && user.getProfessionId() != 0){
            user.setProfession(baseDao.getMapper(ProfessionMapper.class).selectByPrimaryKey(user.getProfessionId()));
        }
        //setTheme(user);
    }


    public  void setTheme(User user)
    {
        if(user !=null)
        {
            ExtMsg extMsg = new ExtMsg();
            extMsg.setType("theme");
            extMsg.setName(user.getUsername());
            String theme_this="";
            List<ExtMsg> list  = extMsgService.findList(null,extMsg);

            if (list.size() > 0) {
                for (ExtMsg extMsg1 : list) {
                    theme_this = extMsg1.getValue();
                }
            }
            user.setTheme(theme_this);
        }
    }
	

	
	/**
	 * 按用户名
	 * @param username
	 * @return
	 */
	public User getByUsername(String username) {
		UserCriteria criteria = new UserCriteria();
		UserCriteria.Criteria cri = criteria.createCriteria();
		if(StringUtils.isNotBlank(username)){
			cri.andUsernameEqualTo(username);
		}
		User user =  baseDao.selectOne("org.gxz.znrl.mapper.UserMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        if(user!=null) {
            setUserOrganization(user);
            if(user.getId()!=null)
            user.setUserRoles(userRoleService.find(user.getId()));
        }
        return user;
	}
	
	/**
	 * 按邮箱
	 * @param email
	 * @return
	 */
	public User getByEmail(String email) {
		UserCriteria criteria = new UserCriteria();
		UserCriteria.Criteria cri = criteria.createCriteria();
		if(StringUtils.isNotBlank(email)){
			cri.andEmailEqualTo(email);
		}
        User user =  baseDao.selectOne("org.gxz.znrl.mapper.UserMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        if(user!=null) {
            setUserOrganization(user);
            if(user.getId()!=null)
                user.setUserRoles(userRoleService.find(user.getId()));
        }
        return user;
	}

	@Override
	public User get(Long id) {
		User user = baseDao.getMapper(UserMapper.class).selectByPrimaryKey(id);
        if(user!=null) {
            setUserOrganization(user);
            if(user.getId()!=null)
                user.setUserRoles(userRoleService.find(user.getId()));
        }
		return user;
	}

	@Override
	public void resetPwd(User user, String newPwd) {
		if (newPwd == null) {
			newPwd = "123456";
		}
		HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
		user.setSalt(hashPassword.salt);
		user.setPassword(hashPassword.password);
		
		baseDao.getMapper(UserMapper.class).updateByPrimaryKeySelective(user);
	}

	@Override
    @Transactional
	public void save(User user) throws ExistedException {
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}

		user.setCreateTime(SimpleDate.getDateTime());
		baseDao.getMapper(UserMapper.class).insertSelective(user);

        //加入明文密码表 begin
        UserPlaintext userPlaintext = new UserPlaintext();
        userPlaintext.setId(user.getId());
        userPlaintext.setPassword(user.getPlainPassword());
        userPlaintext.setUsername(user.getUsername());
        userPlaintextService.save(userPlaintext);
        //加入明文密码表 end

        String roles = user.getRoles();
        Long id = getByUsername(user.getUsername()).getId();
        if(StringUtils.isNotBlank(roles)){
            if(roles.indexOf(",")>0) {
                for (String roleId : StringUtils.split(roles,",")) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(Long.parseLong(roleId));
                    userRole.setUserId(id);
                    userRole.setPriority(99);
                    userRole.setId(commonService.getNextval("SEQ_USER_ROLE_ID"));
                    userRoleService.save(userRole);
                }
            }
            else{
                UserRole userRole = new UserRole();
                userRole.setRoleId(Long.parseLong(roles));
                userRole.setUserId(id);
                userRole.setPriority(99);
                userRole.setId(commonService.getNextval("SEQ_USER_ROLE_ID"));
                userRoleService.save(userRole);
            }
        }
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	@Override
	public void update(User user) {
        String roles = user.getRoles();
        Long id = getByUsername(user.getUsername()).getId();
        if(StringUtils.isNotBlank(roles)){
            if(StringUtils.indexOfAny(roles,",")>0) {
                //比较新入参与原始记录，如原始记录在新入参中已经存在，则无任何操作，如无，则做删除操作
                int existTag = 0;
                List <UserRole>userRoleList = userRoleService.find(id);
                for (UserRole tempUserRole : userRoleList) {
                    existTag = 0;
                    for (String roleId : StringUtils.split(roles,",")) {
                        if (tempUserRole.getRoleId() == Long.parseLong(roleId)) {
                            existTag = 1;
                            break;
                        }
                    }
                    if (existTag ==0) {
//                        userRoleService.delete(tempUserRole.getRoleId());
                        userRoleService.delete(tempUserRole.getId());
                    }
                }
               //比较新入参与原始记录，如新入参在原始记录中已经存在，则无任何操作，如无，则做增加操作
                for (String roleId : StringUtils.split(roles,",")) {
                    existTag = 0;
                    for (UserRole tempUserRole : userRoleList) {
                        if (tempUserRole.getRoleId() == Long.parseLong(roleId)) {
                            existTag =1;
                            break;
                        }
                    }
                    if (existTag == 0) {
                        UserRole userRole = new UserRole();
                        userRole.setRoleId(Long.parseLong(roleId));
                        userRole.setUserId(id);
                        userRole.setPriority(99);
                        if (userRoleService.find(userRole).size()==0) {
                            try{
                                userRole.setId(commonService.getNextval("SEQ_USER_ROLE_ID"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            userRoleService.save(userRole);
                        }
                    }
                }
            }
            else{
                List <UserRole>userRoleList = userRoleService.find(id);
                for (UserRole tempUserRole : userRoleList) {
                     if (tempUserRole.getRoleId() != Long.parseLong(roles)) {
                         //userRoleService.delete(tempUserRole.getRoleId());
                         userRoleService.delete(tempUserRole.getId());
                         UserRole userRole = new UserRole();
                         userRole.setRoleId(Long.parseLong(roles));
                         userRole.setUserId(id);
                         userRole.setPriority(99);
                         if (userRoleService.find(userRole).size()==0) {
                             try{
                                 userRole.setId(commonService.getNextval("SEQ_USER_ROLE_ID"));
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                             userRoleService.save(userRole);
                         }
                     }
                }
            }
        }
        if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
            HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);
            updatePwd(user,user.getPlainPassword());
        }
		baseDao.getMapper(UserMapper.class).updateByPrimaryKeySelective(user);
        //加入明文密码表 begin
        UserPlaintext userPlaintext = userPlaintextService.get(user.getId());
        if (userPlaintext != null) {
            userPlaintext.setPassword(user.getPlainPassword());
            userPlaintextService.update(userPlaintext);
        } else {
            userPlaintext = new UserPlaintext();
            userPlaintext.setId(user.getId());
            userPlaintext.setPassword(user.getPlainPassword());
            userPlaintext.setUsername(user.getUsername());
            userPlaintextService.save(userPlaintext);
        } 
        //加入明文密码表 end
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}



	@Override
	public void updatePwd(User user, String newPwd) throws ServiceException {
		//if (isSupervisor(user.getId())) {
		//	logger.warn("操作员{},尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
		//	throw new ServiceException("不能修改超级管理员用户");
		//}
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		boolean isMatch = ShiroDbRealm.validatePassword(user.getPlainPassword(), user.getPassword(), user.getSalt());
		if (isMatch) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
			
			baseDao.getMapper(UserMapper.class).updateByPrimaryKeySelective(user);
			shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
			
			return; 
		}
		
		throw new ServiceException("用户密码错误！");

	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
	
}
