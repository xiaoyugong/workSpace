package com.wy.domain;


import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
//����Աbean
public class ManagerForm
    extends ActionForm {
  private String account="";//����Ա�˺�
  private Integer id=Integer.valueOf("-1");//���ݿ���ˮ��
  private String name="";//����Ա����
  private String password="";//����Ա��¼����
  private Integer sigh=Integer.valueOf("-1");//����Ա��ʶ��1���ǡ�0��
 //���췽��
  public ManagerForm(){}


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setSigh(Integer sigh) {
    this.sigh = sigh;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public Integer getSigh() {
    return sigh;
  }

  public ActionErrors validate(ActionMapping actionMapping,
                               HttpServletRequest httpServletRequest) {
     return null;
  }

  public void reset(ActionMapping actionMapping,
                    HttpServletRequest servletRequest) {
  }
}
