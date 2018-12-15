package com.parkbobo.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.parkbobo.dao.InvitecodeDetailDao;
import com.parkbobo.model.InvitecodeDetail;
import com.parkbobo.utils.PageBean;

@Service("invitecodeDetailService")
public class InvitecodeDetailService {

	@Resource(name="invitecodeDetailDaoImpl")
	private InvitecodeDetailDao invitecodeDetailDao;

	public PageBean<InvitecodeDetail> page(String hqlString, int page, int pageSize) {
		// TODO Auto-generated method stub
		return invitecodeDetailDao.pageQuery(hqlString, pageSize,page);
	}

	public void save(InvitecodeDetail invitecode) {
		// TODO Auto-generated method stub
		invitecodeDetailDao.add(invitecode);
	}

	public InvitecodeDetail getInvitecodeById(String invitecode) {
		// TODO Auto-generated method stub
		return invitecodeDetailDao.getById(invitecode);
	}

	public void update(InvitecodeDetail codeDetail) {
		// TODO Auto-generated method stub
		invitecodeDetailDao.update(codeDetail);
	}
	/**
	 * 删除
	 * @param ids
	 */
	public void bulkDelete(String ids) {
		if(ids.length() > 0){
			String[] strs = ids.split(",");
			this.invitecodeDetailDao.bulkDelete(strs);
		}
	}
}
