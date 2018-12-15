package com.wy.webtier;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import com.wy.domain.MemberForm;
import org.apache.struts.action.Action;
import com.wy.dao.MemberDao;
import java.io.IOException;
import java.util.*;



//��Ա����Action
public class MemberAction extends Action {
	private int action;
	private MemberDao dao = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		dao = new MemberDao();
		this.action = Integer.parseInt(request.getParameter("action"));
		switch (action) {
		case 0: {
			return insertMember(mapping, form, request, response); // ���Ա����Ϣ
		}
		case 1: {
			return checkMember(mapping, form, request, response); // ��Ա��¼
		}
		case 2: {
			return selectMember(mapping, form, request, response); // �鿴��Ա��Ϣ
		}
        case 3: {
			return selectOneMember(mapping, form, request, response); // �鿴��Ա����ϸ��Ϣ
		}
		case 4: {
			return deleteMember(mapping, form, request, response); // ɾ����Ա��Ϣ
		}
		case 5: {
			return selectOneMemberHead(mapping, form, request, response); // ǰ̨��ѯ��Ա������
		}
		case 6: {
			return updateMemberHead(mapping, form, request, response); // �޸Ļ�Ա����
		}

		}
		// MemberForm memberForm = (MemberForm) form;
		throw new java.lang.UnsupportedOperationException(
				"Method $execute() not yet implemented.");
	}

	// �޸Ļ�Ա����
	public ActionForward updateMemberHead(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MemberForm memberForm = (MemberForm) form;		
		dao.updateMember(memberForm);
		request.setAttribute("success", "�޸ĳɹ�");
		return mapping.findForward("operationMember");
	}

	// ǰ̨��ѯ��Ա������
	public ActionForward selectOneMemberHead(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("form", dao.selectOneMember(Integer
				.valueOf(request.getParameter("id"))));
		return mapping.findForward("selectOneMemberHead");
	}

	// ɾ������
	public ActionForward deleteMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
	   if (!dao.deleteMember(Integer.valueOf(request.getParameter("id")))) {
		   return mapping.findForward("deleteMember");
		}
		return selectMember(mapping,form,request,response);
	}

	// �鿴��Ա��ϸ��Ϣ
	public ActionForward selectOneMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Integer id=Integer.valueOf(request.getParameter("id"));
		request.setAttribute("form", dao.selectOneMember(id));
		return mapping.findForward("selectOneMember");
	}

	// �鿴��Ա��Ϣ
	public ActionForward selectMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List list = dao.selectMember();
		int pageNumber = list.size(); // ������ж�������¼
		int maxPage = pageNumber; // �����ж���ҳ��
		String number = request.getParameter("i");
		if (maxPage % 6 == 0) {
			maxPage = maxPage / 6;
		} else {
			maxPage = maxPage / 6 + 1;
		}
		if (number == null) {
			number = "0";
		}
		request.setAttribute("number", String.valueOf(number));
		request.setAttribute("maxPage", String.valueOf(maxPage));
		request.setAttribute("pageNumber", String.valueOf(pageNumber));
		request.setAttribute("list", list);
		return mapping.findForward("selectMember");
	}

	// ��Ա��¼
	public ActionForward checkMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		MemberForm memberForm = dao.selectMemberForm(name);
		if (memberForm==null||memberForm.equals("")) {
			request.setAttribute("result", "�����ڴ˻�Ա�������µ�¼������");
		} else if (!memberForm.getPassword().equals(request.getParameter("password").trim())) {
			request.setAttribute("result", "������������µ�¼������");
		} else {	
			request.setAttribute("memberForm", memberForm);
		}
		return mapping.findForward("checkMember");
	}

	// ���Ա����Ϣ
	public ActionForward insertMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {	
		MemberForm memberForm=(MemberForm)form;
		MemberForm formSelect=dao.selectMemberForm(memberForm.getName());
		if (formSelect == null || formSelect.equals("")) {
			dao.insertMember(memberForm);
			request.setAttribute("success", "ע��ɹ�");
		} else {
			request.setAttribute("success", "�û�Ա�����Ѿ����ڣ�����");
		}
		return mapping.findForward("operationMember");
	}
}
