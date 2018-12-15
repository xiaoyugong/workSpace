package com.wy.tool;

import java.io.*;
import java.util.Date;
import org.apache.struts.upload.FormFile;

public class UploadFile {
	public String upload(String dir, FormFile formFile) throws Exception {
		Date date = new Date();
		// ȡ���ϴ����ļ������ֺͳ���
		String fname = formFile.getFileName();
		// ���ϴ�ʱ������ļ���
		int i = fname.indexOf(".");
		String name = String.valueOf(date.getTime());
		String type = fname.substring(i + 1);
		fname = name + "." + type;
		InputStream streamIn = formFile.getInputStream(); // ������ȡ�û��ϴ��ļ��Ķ���
		File uploadFile = new File(dir); // �������ϴ�����д��Ŀ���ļ��Ķ���
		if (!uploadFile.exists() || uploadFile == null) { // �ж�ָ��·���Ƿ���ڣ��������򴴽�·��
			uploadFile.mkdirs();
		}
		String path = uploadFile.getPath() + "/" + fname;
		OutputStream streamOut = new FileOutputStream(path);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
			streamOut.write(buffer, 0, bytesRead);
		}
		streamOut.close();
		streamIn.close();
		formFile.destroy();
		return fname;
	}
}
