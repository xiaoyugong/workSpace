package com.parkbobo.groundlock.dao.impl;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.AppActivityDao;
import com.parkbobo.dao.impl.BaseDaoSupport;
import com.parkbobo.groundlock.dao.RunningRecordsDao;
import com.parkbobo.groundlock.model.RunningRecords;
import com.parkbobo.model.AppActivity;
@Component("runningRecordsDaoImpl")
public class RunningRecordsDaoImpl extends BaseDaoSupport<RunningRecords> implements
RunningRecordsDao {

}
