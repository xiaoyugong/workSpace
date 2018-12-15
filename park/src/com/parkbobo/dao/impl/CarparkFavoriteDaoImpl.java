package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkFavoriteDao;
import com.parkbobo.model.CarparkFavorite;
@Component("carparkFavoriteDaoImpl")
public class CarparkFavoriteDaoImpl extends BaseDaoSupport<CarparkFavorite> implements CarparkFavoriteDao {
}