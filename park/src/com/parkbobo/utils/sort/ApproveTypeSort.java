package com.parkbobo.utils.sort;

import java.util.Comparator;

import org.apache.struts2.components.Else;

import com.parkbobo.model.Users;

public class ApproveTypeSort implements Comparator<Users> {

	@Override
	public int compare(Users o1, Users o2) {
		// TODO Auto-generated method stub
		if(o1.getDriverStatus()==0){
			return 1;
		}
		return -1;
	}

}
