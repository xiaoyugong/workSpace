package com.parkbobo.utils.sort;

import java.util.Comparator;

import com.parkbobo.model.WithdrawLog;

public class WithdrawSort implements Comparator<WithdrawLog>{

	@Override
	public int compare(WithdrawLog o1, WithdrawLog o2) {
		// TODO Auto-generated method stub
		if(o1.getStatus()==0){
			return -1;
		}else if(o1.getStatus()==0&&o2.getStatus()==1){
			return -1;
		}
		
		return 1;
	}

}
