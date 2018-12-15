package com.lqkj.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.model.ManagerResources;
import com.parkbobo.manager.model.ManagerRole;
import com.parkbobo.manager.model.Resources;
import com.parkbobo.manager.model.Role;
import com.parkbobo.manager.model.RoleResources;
import com.parkbobo.manager.service.ManagerService;



/**
 * 在这个类中，你就可以从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等。建议通过我们封装的平台级持久层管理类获取和管理。
 * @author LH
 *
 */
@SuppressWarnings("deprecation")
public class MyUserDetailService implements UserDetailsService {
	private static final Logger logger = Logger.getLogger(MyUserDetailService.class);
	@Resource(name="managerService")
	private ManagerService managerService;
	//登录验证
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if (logger.isDebugEnabled()) {
			logger.debug("loadUserByUsername(String) - start"); //$NON-NLS-1$
		}
		Manager manager = managerService.getUniqueByProperty("username", username);
		Collection<GrantedAuthority> auths = obtionGrantedAuthorities(manager);
		boolean enables = true;//用户失效
		boolean accountNonExpired = true;//用户账号过期
		boolean credentialsNonExpired = true;//用户凭证过期
		boolean accountNonLocked =true;//账号锁定
		if(manager.getActivation() == 1){
			accountNonLocked = true;
		}else{
			accountNonLocked = false;
		}
		//封装成spring security的user
		User user = new User(manager.getUsername(),manager.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, auths);
		return user;
	}
	//取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(Manager manager) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		Set<ManagerResources> managerResourcees = manager.getManagerResourceses();
		Set<ManagerRole> managerRoles = manager.getManagerRoles();
		//用户权限
		for (ManagerResources managerResources : managerResourcees) {
			Resources resources = managerResources.getResources();
			if(resources.getEnable() == 1){
				authSet.add(new GrantedAuthorityImpl(resources.getEnname()));
			}
		}
		//用户角色权限
		for (ManagerRole managerRole : managerRoles) {
			Role role = managerRole.getRole();
			if(role.getEnable() == 1){
				Set<RoleResources> roleResourcees  = role.getRoleResourceses();
				for (RoleResources roleResources : roleResourcees) {
					Resources resources = roleResources.getResources();
					if(resources.getEnable() == 1){
						authSet.add(new GrantedAuthorityImpl(resources.getEnname()));
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("真蛋疼,靠"); //$NON-NLS-1$
		}
		authSet.add(new GrantedAuthorityImpl("main_index"));
		return authSet;
	}
}
