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
	 * 把16进制字符串转换成字节数组
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
	 * 把字节数组转换成16进制字符串
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

	// 定义 加密算法,可用 DES,DESede,Blowfish
	// keybyte为加密密钥，长度24字节
	// src为被加密的数据缓冲区
	public static byte[] encrypt(byte[] keybyte, byte[] src) throws Exception {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
		// 加密
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(src);
	}

	// keybyte为加密密钥，长度24字节
	// src为加密后的缓冲区
	public static byte[] decrypt(byte[] keybyte, byte[] src) throws Exception {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
		// 解密
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		return c1.doFinal(src);
	}

	public static void main(String[] args) {
		try {
			// 添加新安全算,如果用JCE就要把它添加进去
//			final String keys = "801313824543172280131382";
			final String keys = "l0m8qnucCOw67IaJl0m8qnuc";
			// 24字节的密
			String szSrc = "中文测试";
			System.out.println("加密前的字符:1" + szSrc);
			String s = Des3Util.encryptToBase64(keys, szSrc, "UTF-8"); //加密方法
			System.out.println("加密后的字符:2" + s);
			System.out.println("解密后的字符:3" + Des3Util.decryptFromBase64(keys, s, "UTF-8"));
			
			// 24字节的密
			szSrc = "123456";
			System.out.println("加密前的字符:1" + szSrc);
			String s1 = Des3Util.encryptToBase64(keys, szSrc, "UTF-8");
			System.out.println("加密后的字符:2" + s1);
			System.out.println("解密后的字符:3" + Des3Util.decryptFromBase64(keys, s1, "UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
