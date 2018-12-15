package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.FeedbackDao;
import com.parkbobo.model.Feedback;
@Component("feedbackDaoImpl")
public class FeedbackDaoImpl extends BaseDaoSupport<Feedback> implements FeedbackDao {
}