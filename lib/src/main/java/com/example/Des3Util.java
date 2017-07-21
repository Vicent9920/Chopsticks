package com.example;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Des3Util {

	private static final String ALGORITHM = "DESede";
	public final static String BASE_TABLE = "0123456789ABCDEF";

	/**
	 * ��16�����ַ���ת�����ֽ�����
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) BASE_TABLE.indexOf(c);
		return b;
	}

	/**
	 * ���ֽ�����ת����16�����ַ���
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static String encryptToBase64(String key, String src, String encoding) {
		try {
			return new BASE64Encoder().encode(encrypt(key.getBytes(), src.getBytes(encoding)));
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public static String decryptFromBase64(String key, String src, String encoding) {
		try {
			return new String(decrypt(key.getBytes(), new BASE64Decoder().decodeBuffer(src)), encoding);
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public static String encryptToBase64UTF8(String key, String src) {
		return encryptToBase64(key, src, "UTF-8");
	}

	public static String decryptFromBase64UTF8(String key, String src) {
		return decryptFromBase64(key, src, "UTF-8");
	}

	public static String encryptToBase64DefaultEncoding(String key, String src) {
		try {
			return new BASE64Encoder().encode(encrypt(key.getBytes(), src.getBytes()));
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public static String decryptFromBase64DefaultEncoding(String key, String src) {
		try {
			return new String(decrypt(key.getBytes(), new BASE64Decoder().decodeBuffer(src)));
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public static String encryptToHex(String key, String src, String encoding) {
		try {
			return bytesToHexString(encrypt(key.getBytes(), src.getBytes(encoding)));
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public static String decryptFromHex(String key, String src, String encoding) {
		try {
			return new String(decrypt(key.getBytes(), hexStringToByte(src)), encoding);
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public static String encryptToHexUTF8(String key, String src) {
		return encryptToHex(key, src, "UTF-8");
	}

	public static String decryptFromHexUTF8(String key, String src) {
		return decryptFromHex(key, src, "UTF-8");
	}

	public static String encryptToHexDefaultEncoding(String key, String src) {
		try {
			return bytesToHexString(encrypt(key.getBytes(), src.getBytes()));
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public static String decryptFromHexDefaultEncoding(String key, String src) {
		try {
			return new String(decrypt(key.getBytes(), hexStringToByte(src)));
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	// ���� �����㷨,���� DES,DESede,Blowfish
	// keybyteΪ������Կ������24�ֽ�
	// srcΪ�����ܵ����ݻ�����
	public static byte[] encrypt(byte[] keybyte, byte[] src) throws Exception {
		// ������Կ
		SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
		// ����
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(src);
	}

	// keybyteΪ������Կ������24�ֽ�
	// srcΪ���ܺ�Ļ�����
	public static byte[] decrypt(byte[] keybyte, byte[] src) throws Exception {
		// ������Կ
		SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
		// ����
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		return c1.doFinal(src);
	}

	public static void main(String[] args) {
		try {
			// ����°�ȫ��,�����JCE��Ҫ������ӽ�ȥ
//			final String keys = "801313824543172280131382";
			final String keys = "l0m8qnucCOw67IaJl0m8qnuc";
			// 24�ֽڵ���
			String szSrc = "���Ĳ���";
			System.out.println("����ǰ���ַ�:1" + szSrc);
			String s = Des3Util.encryptToBase64(keys, szSrc, "UTF-8"); //���ܷ���
			System.out.println("���ܺ���ַ�:2" + s);
			System.out.println("���ܺ���ַ�:3" + Des3Util.decryptFromBase64(keys, s, "UTF-8"));
			
			// 24�ֽڵ���
			szSrc = "123456";
			System.out.println("����ǰ���ַ�:1" + szSrc);
			String s1 = Des3Util.encryptToBase64(keys, szSrc, "UTF-8");
			System.out.println("���ܺ���ַ�:2" + s1);
			System.out.println("���ܺ���ַ�:3" + Des3Util.decryptFromBase64(keys, s1, "UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
