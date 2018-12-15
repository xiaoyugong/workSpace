package com.parkbobo.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class MacUtils {
	private static MacUtils macUtils;
	private MacUtils(){}
	public synchronized static MacUtils getInstance(){
		if(macUtils==null){
			macUtils = new MacUtils();
		}
		return macUtils;
	}
	public String longToMac(long mac)
	{
		StringBuffer macBuf = new StringBuffer();
		byte macs[] = new byte[6];
		macs = macToBytes(mac);
		for (byte b : macs) {
			int s = b & 0xff;
			macBuf.append(String.format("%02X", s) + ":");
		}
		return macBuf.substring(0, macBuf.length() -1);
	}
	/**
	 * 不带冒号的MAC地址
	 * @return
	 */
	public long noColonMacToLong(String macStr)
	{
		String macStrs[] = new String[6] ;
		for(int i = 0 ; i < 6; i++)
		{
			macStrs[i] = macStr.substring(i * 2, (i * 2) + 2 );
		}
		byte macs[] = new byte[6];
		for(int i=0;i<6;i++)
		{
			int m = Integer.parseInt(macStrs[i], 16);
			macs[i]=(byte) (m&0xff);
		}
		ByteBuffer bb=ByteBuffer.allocate(8);
		bb.order(ByteOrder.nativeOrder());
		bb.position(0);
		bb.put(macs, 0, 6);
		bb.put((byte) 0);
		bb.put((byte) 0);
		bb.position(0);
		return bb.getLong();
	}
	/**
	 * 带冒号的MAC地址
	 * @param macStr
	 * @return
	 */
	public long macToLong(String macStr)
	{
		String macStrs[] = macStr.split(":");
		byte macs[] = new byte[6];
		for(int i=0;i<6;i++)
		{
			int m = Integer.parseInt(macStrs[i], 16);
			macs[i]=(byte) (m&0xff);
		}
		ByteBuffer bb=ByteBuffer.allocate(8);
		bb.order(ByteOrder.nativeOrder());
		bb.position(0);
		bb.put(macs, 0, 6);
		bb.put((byte) 0);
		bb.put((byte) 0);
		bb.position(0);
		return bb.getLong();
	}
	private static byte[] macToBytes(long mac)
	{
		ByteBuffer bb=ByteBuffer.allocate(8);
		bb.order(ByteOrder.nativeOrder());
		bb.position(0);
		bb.putLong(mac);
		byte ret[]=new byte[6];
		bb.position(0);
		bb.get(ret);
		return ret;
	}
	public static void main(String[] args) {
//		System.out.println(MacUtils.getInstance().macToLong("00:1B:35:0E:43:FF"));
//		System.out.println(MacUtils.getInstance().macToLong("00:1B:35:0E:63:24"));
//		184823946156
//		System.out.println(MacUtils.getInstance().macToLong("00:1B:35:0E:63:29"));
		System.out.println(MacUtils.getInstance().macToLong("7E:BF:FF:3E:C1:3D"));
		
	}
}
