package com.wy.domain;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
//��ƷС���bean
public class SmallTypeForm
    extends ActionForm {
  private Integer bigId=Integer.valueOf("-1");//��Ʒ����������
  private String creaTime="";//����ʱ��
  private Integer id=Integer.valueOf("-1");//���ݿ���ˮ��
  private String smallName="";//��ƷС�����Ϣ
  public Integer getBigId() {
    return bigId;
  }

  public void setBigId(Integer bigId) {
    this.bigId = bigId;
  }

  public void setSmallName(String smallName) {
    this.smallName = smallName;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setCreaTime(String creaTime) {
    this.creaTime = creaTime;
  }

  public String getCreaTime() {
    return creaTime;
  }

  public Integer getId() {
    return id;
  }

  public String getSmallName() {
    return smallName;
  }

  public ActionErrors validate(ActionMapping actionMapping,
                               HttpServletRequest httpServletRequest) {
      /** @todo: finish this method, this is just the skeleton.*/
    return null;
  }

  public void reset(ActionMapping actionMapping,
                    HttpServletRequest servletRequest) {
  }
}
