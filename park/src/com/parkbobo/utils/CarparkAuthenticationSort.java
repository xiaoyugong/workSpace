package com.parkbobo.utils;

import java.util.Comparator;

import com.parkbobo.model.CarparkAuthentication;

public class CarparkAuthenticationSort implements Comparator<CarparkAuthentication> {

	@Override
	public int compare(CarparkAuthentication o1, CarparkAuthentication o2) {
		// TODO Auto-generated method stub
		if(o1.getStatus()==0){
			
			return -1;
		}
		return 1;
	}

}
