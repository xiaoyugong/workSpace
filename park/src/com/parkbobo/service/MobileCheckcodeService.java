package com.parkbobo.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.parkbobo.dao.MobileCheckcodeDao;
import com.parkbobo.model.MobileCheckcode;
import com.parkbobo.utils.SMSSend;
@Component("mobileCheckcodeService")
public class MobileCheckcodeService {
	@Resource(name="mobileCheckcodeDaoImpl")
	private MobileCheckcodeDao mobileCheckcodeDao;

	public List<MobileCheckcode> getByHql(String hql){
		return this.mobileCheckcodeDao.getByHQL(hql);
	}
	/**
	 * 通过手机号码查询最近一条短语验证码记录
	 * 短信类型type: 0-注册  1-找回密码
	 * @param telephone
	 * @return
	 */
	public MobileCheckcode getByTelephone(String telephone ,Short type)
	{
		List<MobileCheckcode> mobileCheckcodes = mobileCheckcodeDao.getByHQL("from MobileCheckcode as m where m.telephone = '" + telephone + "'" +
				" and type = " + type + " order by m.posttime desc");
		if(mobileCheckcodes.size() > 0)
		{
			return mobileCheckcodes.get(0);
		}
		else
		{
			return null;
		}
	}
	/**
	 * 通过手机号码判断是否一分钟内已发送过验证码
	 * 短信类型type: 0-注册  1-找回密码
	 * @param telephone
	 * @return
	 */
	public boolean existCheckcode(String telephone, Short type)
	{
		List<MobileCheckcode> mobileCheckcodes = mobileCheckcodeDao.getByHQL("from MobileCheckcode as m where m.telephone = '" + telephone + "'" +
				" and type = " + type + " order by m.posttime desc");
		if(mobileCheckcodes.size() > 0)
		{
			long now = new Date().getTime();
			if( mobileCheckcodes.get(0) != null && (now -  mobileCheckcodes.get(0).getPosttime())/1000l < 60)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	/**
	 * 发送短信验证码并保存
	 * @param phone
	 * @param randomCode
	 * @return
	 */
	public String save(String phone, int randomCode ,Short type) {
//		MobileCheckcode mobileCheckcode = new MobileCheckcode(null, phone, randomCode + "", new Date().getTime(), type, null);
//		mobileCheckcodeDao.save(mobileCheckcode);
//		return true;
		String code = SMSSend.getDefaultInstance().iuyiSendSms("您的验证码是：" + randomCode + "。请不要把验证码泄露给其他人。", phone);
		if(code.equals("2"))
		{
			MobileCheckcode mobileCheckcode = new MobileCheckcode(null, phone, randomCode + "", new Date().getTime(), type, null);
			mobileCheckcodeDao.save(mobileCheckcode);
		}
		return code;
	}
}