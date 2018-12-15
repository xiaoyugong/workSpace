package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkAuthenticationDao;
import com.parkbobo.model.CarparkAuthentication;
@Component("carparkAuthenticationDaoImpl")
public class CarparkAuthenticationDaoImpl extends BaseDaoSupport<CarparkAuthentication> implements CarparkAuthenticationDao {
}