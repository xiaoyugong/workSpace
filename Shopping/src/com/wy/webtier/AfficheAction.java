package com.wy.webtier;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import com.wy.domain.AfficheForm;
import com.wy.dao.AfficheDao;
import java.util.List;


//������Ϣ��Action
public class AfficheAction
    extends Action {
  private AfficheDao dao = null;
  private int action;
  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) {
    dao = new AfficheDao();
    this.action = Integer.parseInt(request.getParameter("action"));
    switch (action) {
      case 0: {
        return afficheSelect(mapping, form, request, response); //�Թ�����Ϣȫ����ѯ����
      }
      case 2: {
        return afficheInsert(mapping, form, request, response); //��ӹ�����Ϣ
      }
      case 3: {
        return afficheDelete(mapping, form, request, response); //�����ݿ���ˮ��Ϊ����ɾ��������Ϣ
      }
      case 4: {
        return afficheSelectOne(mapping, form, request, response); //�����ݿ���ˮ��Ϊ������ѯ������Ϣ
      }
      case 5: {
        return afficheUpdate(mapping, form, request, response); //�����ݿ���ˮ��Ϊ�����޸Ĺ�����Ϣ
      }
      case 6: {
      return afficheContent(mapping, form, request, response);   //�����ݿ���ˮ��Ϊ������ѯ������Ϣ������
    }
    }
    throw new java.lang.UnsupportedOperationException(
        "Method $execute() not yet implemented.");
  }

  //�����ݿ���ˮ��Ϊ������ѯ������Ϣ������
  public ActionForward afficheContent(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
   request.setAttribute("affiche",dao.selectOneAffiche(Integer.valueOf(request.getParameter("id"))));
   return mapping.findForward("afficheContent");
  }
      //�����ݿ���ˮ��Ϊ������ѯ������Ϣ
      public ActionForward afficheSelectOne(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
    request.setAttribute("affiche",
                         dao.selectOneAffiche(Integer.valueOf(request.
        getParameter("id"))));

    return mapping.findForward("afficheSelectOne");
  }

  //�����ݿ���ˮ��Ϊ�����޸Ĺ�����Ϣ
  public ActionForward afficheUpdate(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
    AfficheForm afficheForm = (AfficheForm) form;
    dao.updateAffiche(afficheForm);
    return afficheSelect(mapping, form, request, response);
  }

  //�Թ�����Ϣ��ȫ����ѯ����
  public ActionForward afficheSelect(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
    List list = dao.selectAffiche();
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
    return mapping.findForward("afficheSelect");
  }


  //��ӹ�����Ϣ
  public ActionForward afficheInsert(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
    AfficheForm afficheForm = (AfficheForm) form;
    dao.insertAffiche(afficheForm);
    return afficheSelect(mapping, form, request, response);
  }

  //�����ݿ���ˮ��Ϊ����ɾ��������Ϣ
  public ActionForward afficheDelete(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
    dao.deleteAffiche(Integer.valueOf(request.getParameter("id")));
    return afficheSelect(mapping, form, request, response);
  }

}
