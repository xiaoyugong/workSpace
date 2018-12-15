package com.wy.webtier;

import javax.servlet.http.*;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wy.domain.LinkForm;

import com.wy.dao.LinkDao;
import java.util.*;
import java.io.UnsupportedEncodingException;


//��վ���ӵ�Action
public class LinkAction
    extends Action {
  private LinkDao dao = null;
  private int action;


  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws
      UnsupportedEncodingException {
    this.action = Integer.parseInt(request.getParameter("action"));
    dao = new LinkDao();
    switch (action) {
      case 0: {
        return linkSelect(mapping, form, request, response); //��������վ��ַ��Ϣ��ȫ����ѯ
      }   
      case 2: {
       return linkInsert(mapping, form, request, response); //�����վ��Ϣ
     }
       case 4: {
           return linkDelete(mapping, form, request, response); //ɾ����վ��Ϣ
     }
    }
    //  LinkForm linkForm = (LinkForm) form;
    throw new java.lang.UnsupportedOperationException(
        "Method $execute() not yet implemented.");
  }

  //ɾ����վ��Ϣ
  public ActionForward linkDelete(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
    dao.deleteLink(Integer.valueOf(request.getParameter("id")));
    return linkSelect(mapping, form, request, response);
}

    //�����վ��Ϣ
    public ActionForward linkInsert(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
      LinkForm linkForm = (LinkForm) form;
      dao.insertLink(linkForm);
      return linkSelect(mapping, form, request, response);
  }



  //��������վ��ַ��Ϣ��ȫ����ѯ
  public ActionForward linkSelect(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
    List list = dao.selectLink();
    int pageNumber = list.size(); //������ж�������¼
    int maxPage = pageNumber; //�����ж���ҳ��
    String number = request.getParameter("i");
    if (maxPage % 7 == 0) {
      maxPage = maxPage / 7;
    }
    else {
      maxPage = maxPage / 7 + 1;
    }
    if (number == null) {
      number = "0";
    }
    request.setAttribute("number", String.valueOf(number));
    request.setAttribute("maxPage", String.valueOf(maxPage));
    request.setAttribute("pageNumber", String.valueOf(pageNumber));
    request.setAttribute("list", list);
    return mapping.findForward("linkSelect");
  }


}
