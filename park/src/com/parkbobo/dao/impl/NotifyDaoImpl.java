package com.parkbobo.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;
import com.parkbobo.model.Notify;
import com.parkbobo.dao.NotifyDao;
@Component("notifyDaoImpl")
public class NotifyDaoImpl extends BaseDaoSupport<Notify> implements NotifyDao {
}