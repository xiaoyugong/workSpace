package com.wy.domain;

import org.apache.struts.action.ActionForm;

//��Ա���bean
public class MemberForm extends ActionForm {
	private Integer age = 1; // ����

	private String email = ""; // �����ʼ�

	private Integer id = 1; // ���ݿ���ˮ��

	private String name = ""; // ��Ա����

	private String password = ""; // ��Ա����

	private String profession = ""; // ��Աְҵ

	private String question = ""; // �һ����������

	private String reallyName = ""; // ��ʵ����

	private String result = ""; // �һ�����Ĵ�

	public MemberForm() {
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setReallyName(String reallyName) {
		this.reallyName = reallyName;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setProfession(String profession) {
		this.profession = profession;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
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

	public String getProfession() {
		return profession;
	}

	public String getQuestion() {
		return question;
	}

	public String getReallyName() {
		return reallyName;
	}

	public String getResult() {
		return result;
	}
}
