package com.wy.webtier;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import com.wy.domain.OrderForm;
import com.wy.domain.OrderDetailForm;
import org.apache.struts.action.Action;
import com.wy.dao.OrderDao;
import com.wy.dao.OrderDetailDao;
import java.util.List;

//��̨���������Action
public class OrderAction
    extends Action {
  private int action;
  private OrderDao order = null;
  private OrderDetailDao orderDetail = null;
  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) {
    action = Integer.parseInt(request.getParameter("action"));
    order = new OrderDao();
    orderDetail = new OrderDetailDao();
    switch (action) {
      case 0: {
        return selectOrder(mapping, form, request, response); //��ѯ���еĶ���
      }
      case 1: {
        return selectOrderSend(mapping, form, request, response); //����
      }
      case 2: {
      return deleteOrder(mapping, form, request, response); //ɾ������
    }
    case 3: {
        return selectOneOrder(mapping, form, request, response); //��ѯ��������ϸ��Ϣ
      }

    }
    throw new java.lang.UnsupportedOperationException(
        "Method $execute() not yet implemented.");
  }
//��ѯ��������ϸ��Ϣ
  public ActionForward selectOneOrder(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
 String number=request.getParameter("number");
   request.setAttribute("orderForm",order.selectOrderNumber(number));
   request.setAttribute("orderDetailList",orderDetail.selectOrderDetailNumber(number));
   return mapping.findForward("selectOneOrder");
  }
//ɾ��������Ϣ
  public ActionForward deleteOrder(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
    String number = (String) request.getParameter("number");
    orderDetail.deleteOrderDetail(number);
    order.deleteOrder(number);
    return selectOrder(mapping, form, request, response);
   }

//����
  public ActionForward selectOrderSend(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
    order.updateSignOrder(request.getParameter("number"));
    return selectOrder(mapping, form, request, response);
  }

  //��ѯ���еĶ���
  public ActionForward selectOrder(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
    Integer sign = null;
    if (request.getParameter("sign") != null) {
      sign = Integer.valueOf(request.getParameter("sign"));
    }
    List list = order.selectOrderSign(sign);
    int pageNumber = list.size(); //������ж�������¼
    int maxPage = pageNumber; //�����ж���ҳ��
    String number = request.getParameter("i");
    if (maxPage % 6 == 0) {
      maxPage = maxPage / 6;
    }
    else {
      maxPage = maxPage / 6 + 1;
    }
    if (number == null) {
      number = "0";
    }
    request.setAttribute("number", String.valueOf(number));
    request.setAttribute("maxPage", String.valueOf(maxPage));
    request.setAttribute("pageNumber", String.valueOf(pageNumber));
    request.setAttribute("list", list);
    return mapping.findForward("selectOrder");
  }
}
