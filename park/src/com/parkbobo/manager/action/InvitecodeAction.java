package com.parkbobo.manager.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.InvitecodeDetail;
import com.parkbobo.service.InvitecodeDetailService;
import com.parkbobo.utils.PageBean;

import antlr.collections.List;

@Controller("invitecodeAction")
@Scope("prototype")
public class InvitecodeAction extends BaseAction {

	private InvitecodeDetail invitecode;
	@Resource(name = "invitecodeDetailService")
	private InvitecodeDetailService codeService;
	private PageBean<InvitecodeDetail> invitecodePage;
	
	public String list(){
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String code = "";
			String hqlString = "";
			if(invitecode!=null){
				code = invitecode.getInvitecode();
			}
			hqlString= "from InvitecodeDetail where invitecode like '%"+code+"%'";
			invitecodePage = codeService.page(hqlString,getPage(),getPageSize());
			
			//System.out.println("邀请码："+invitecodePage.getList().get(0).getDescription());
			return "list";
			
		}else if(area!=null && !"".equals(area)){
			String code = "";
			String hqlString = "";
			if(invitecode!=null){
				code = invitecode.getInvitecode();
			}
			hqlString= "from InvitecodeDetail where invitecode like '%"+code+"%' and users.area like '%" + area +"%'";
			invitecodePage = codeService.page(hqlString,getPage(),getPageSize());
			
			//System.out.println("邀请码："+invitecodePage.getList().get(0).getDescription());
			return "list";

		}else{
			return "list";
		}
	}
	public String add(){
		System.out.println(invitecode.getUsers().getUserid());
		invitecode.setUserid(invitecode.getUsers().getUserid());
		codeService.save(invitecode);
			return forward("/invitecode_list");
		}
	public String update(){
		InvitecodeDetail codeDetail = codeService.getInvitecodeById(invitecode.getUserid());
		codeDetail.setDescription(invitecode.getDescription().trim());
		codeDetail.setMemo(invitecode.getMemo().trim());
//		codeDetail.setInvitecode(invitecode.getInvitecode());
		
		codeService.update(codeDetail);
			return forward("/invitecode_list");
		}
	public String toAdd(){
		return "toAdd";
	}
	
	public String checkcode() throws IOException {
		InvitecodeDetail codeDetail = codeService.getInvitecodeById(invitecode.getInvitecode().trim());
		String respStr = "{\"error\":\"邀请码不可用\"}";
		if(codeDetail==null){
			respStr =  "{\"ok\":\"邀请码可用\"}";
		}
			response.getWriter().print(respStr);
		return null;
	}
	public String toUpdate(){
		if(invitecode!=null)
		invitecode = codeService.getInvitecodeById(invitecode.getUserid());
		return "toUpdate";
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		codeService.bulkDelete(getIds());
		return forward("/invitecode_list"); 
	}
	
	public InvitecodeDetail getInvitecode() {
		return invitecode;
	}
	public void setInvitecode(InvitecodeDetail invitecode) {
		this.invitecode = invitecode;
	}
	public PageBean<InvitecodeDetail> getInvitecodePage() {
		return invitecodePage;
	}
	public void setInvitecodePage(PageBean<InvitecodeDetail> invitecodePage) {
		this.invitecodePage = invitecodePage;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
