package org.itas.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class GUID {

	public String valueBeforeMD5 = "";
	public String valueAfterMD5 = "";

	private static String s_id;
	private static final int PAD_BELOW = 0x10;
	private static final int TWO_BYTES = 0xFF;

	static {
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			throw new ItasException(e);
		}

	}
	
	private static class Holder {
		static final SecureRandom numberGenerator = new SecureRandom();
    }
	
	private GUID() {
		getRandomGUID();
	}
	
	public static GUID randomGUID() {
		return new GUID();
	}

	private void getRandomGUID() {
    StringBuilder sbValueBeforeMD5 = new StringBuilder(128);
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			long time = System.currentTimeMillis();
			long rand = Holder.numberGenerator.nextLong();
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());

			byte[] array = md5.digest();
			StringBuilder sb = new StringBuilder(32);
			for (final byte bs : array) {
				int b = bs & TWO_BYTES;
				if (b < PAD_BELOW)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}

			valueAfterMD5 = sb.toString();
		} catch (Exception e) {
			throw new ItasException(e);
		}
	}
	
	public String toString() {
//		String raw = valueAfterMD5.toUpperCase();
//		StringBuffer sb = new StringBuffer(64);
//		sb.append(raw.substring(0, 8));
//		sb.append("-");
//		sb.append(raw.substring(8, 12));
//		sb.append("-");
//		sb.append(raw.substring(12, 16));
//		sb.append("-");
//		sb.append(raw.substring(16, 20));
//		sb.append("-");
//		sb.append(raw.substring(20));

		return valueAfterMD5.toUpperCase();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(randomGUID());
		}
	}


}
