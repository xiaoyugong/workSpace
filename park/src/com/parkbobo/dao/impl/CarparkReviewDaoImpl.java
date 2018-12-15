package com.parkbobo.dao.impl;
import org.springframework.stereotype.Component;

import com.parkbobo.dao.CarparkReviewDao;
import com.parkbobo.model.CarparkReview;
@Component("carparkReviewDaoImpl")
public class CarparkReviewDaoImpl extends BaseDaoSupport<CarparkReview> implements CarparkReviewDao {
}