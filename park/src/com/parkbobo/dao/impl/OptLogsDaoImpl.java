package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.OptLogsDao;
import com.parkbobo.model.OptLogs;
@Component("optLogsDaoImpl")
public class OptLogsDaoImpl extends BaseDaoSupport<OptLogs> implements OptLogsDao {
}