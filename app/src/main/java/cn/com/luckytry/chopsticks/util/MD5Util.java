package cn.com.luckytry.chopsticks.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
		/**
		 * 进行MD5加密
		 * 
		 * @param info
		 *            要加密的信息
		 * @return String 加密后的字符串
		 */
		public static String encryptToMD5(String info) {
			byte[] digesta = null;
			try {
				// 得到一个md5的消息摘要
				MessageDigest alga = MessageDigest.getInstance("MD5");
				// 添加要进行计算摘要的信息
				alga.update(info.getBytes());
				// 得到该摘要
				digesta = alga.digest();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			// 将摘要转为字符串
			if(null != digesta){
				return byte2hex(digesta);
			}else{
				return "";
			}
		}
		
		/**
		 * 将二进制转化为16进制字符串
		 * 
		 * @param b
		 *            二进制字节数组
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
