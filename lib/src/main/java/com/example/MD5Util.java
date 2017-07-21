package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/**
	 * ����MD5����
	 *
	 * @param info
	 *            Ҫ���ܵ���Ϣ
	 * @return String ���ܺ���ַ���
	 */
	public static String encryptToMD5(String info) {
		byte[] digesta = null;
		try {
			// �õ�һ��md5����ϢժҪ
			MessageDigest alga = MessageDigest.getInstance("MD5");
			// ���Ҫ���м���ժҪ����Ϣ
			alga.update(info.getBytes());
			// �õ���ժҪ
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// ��ժҪתΪ�ַ���
		if(null != digesta){
			return byte2hex(digesta);
		}else{
			return "";
		}
	}

	/**
	 * ��������ת��Ϊ16�����ַ���
	 *
	 * @param b
	 *            �������ֽ�����
	 * @return String
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}
