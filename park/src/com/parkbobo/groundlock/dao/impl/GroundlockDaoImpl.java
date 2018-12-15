package com.parkbobo.groundlock.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.BerthFavoriteDao;
import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.groundlock.dao.GroundlockDao;
import com.parkbobo.groundlock.model.Groundlock;
import com.parkbobo.model.BerthFavorite;
@Component("groundlockDaoImpl")
public class GroundlockDaoImpl extends BaseDaoSupport<Groundlock> implements GroundlockDao {
}