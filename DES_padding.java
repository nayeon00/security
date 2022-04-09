/*
 * DES/ECB/NoPadding
 * Zero padding, ANSI X9.23, PKCS#5 한번에 출력
 * 키 : [01 23 45 67 89 AB CD EF] 으로 고정
 * 예시1) 평문 : "Now is the time for"
 * 예시2) 평문 : "Now is the time for jump"
 * */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class DES {

	public static SecretKey key;
	
	// DES 암호화
	public static byte[] DES_encrypt(byte[] plainToByte) throws Exception {
	
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding"); // No padding
		cipher.init(Cipher.ENCRYPT_MODE, key);
	
		byte[] encryptedByte = cipher.doFinal(plainToByte);
		
		return encryptedByte; // byte로 리턴
	}
	
	// Zero padding
	public static void Zero_padding(String data) throws Exception {
		if (data == null || data.length() == 0) return;
		
		byte[] plainToByte = data.getBytes();
		int len = plainToByte.length;
		int r = 8 - (len % 8); // 부족한 공간
		if (r == 8) r = 0;
		
		byte[] p = new byte[len+r]; // 새로 담을 공간
		System.arraycopy(plainToByte, 0, p, 0, len);
		if (r != 0) { // padding 필요한 경우
			byte[] zero = new byte[r]; // 부족한 공간을 채울 byte배열
			java.util.Arrays.fill(zero, (byte)0x00); // 만든 배열을 0x00으로 초기화
			System.arraycopy(zero, 0, p, len, r); // 기존의 배열에 합친다.
		}
		System.out.println("Zero padding : " + ByteToHex(DES_encrypt(p)));
	}
	
	// ANSI X9.23
	public static void ANSI_X9_23(String data) throws Exception{
		if (data == null || data.length() == 0) return;
		
		byte[] plainToByte = data.getBytes();
		int len = plainToByte.length;
		
		int r = 8 - (len % 8); // 부족한 공간, input 크기가 같아도 padding을 항상 추가한다.
		
		byte[] p = new byte[len+r]; // 새로 담을 공간
		byte[] zero = new byte[r]; // 부족한 공간을 채울 byte배열
		System.arraycopy(plainToByte, 0, p, 0, len);
		java.util.Arrays.fill(zero, (byte)0x00); // 만든 배열을 0x00으로 초기화
		zero[zero.length-1] = (byte)(r); // 마지막 바이트는 padding 크기를 나타낸다.
		System.arraycopy(zero, 0, p, len, r); // 기존의 배열에 합친다.
		
		System.out.println("ANSI X9.23 : " + ByteToHex(DES_encrypt(p)));
	}
	
	// PKCS#5
	public static void PKCS5(String data) throws Exception{
		if (data == null || data.length() == 0) return;
		
		byte[] plainToByte = data.getBytes();
		int len = plainToByte.length;
		
		int r = 8 - (len % 8); // 부족한 공간, input 크기가 같아도 padding을 항상 추가한다.
		
		byte[] p = new byte[len+r]; // 새로 담을 공간
		byte[] zero = new byte[r]; // 부족한 공간을 채울 byte배열을 하나 만든다.
		System.arraycopy(plainToByte, 0, p, 0, len);
		java.util.Arrays.fill(zero, (byte)r); // 만든 배열을 padding 크기로 초기화 한다.
		System.arraycopy(zero, 0, p, len, r); // 기존의 배열에 합친다.
		
		System.out.println("PKCS#5 : " + ByteToHex(DES_encrypt(p)));
		
	}
	
	// byte를 16진수로 바꾸는 함수
	private static String ByteToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X ", b)); // "%02X " 2자리 16진수, 영어는 대문자로, 띄어쓰기
		}
		return sb.toString();
	}

	
	public static void main(String[] args) throws Exception{
		byte[] k = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
		key = new SecretKeySpec(k, 0, k.length, "DES");
		
		String plainText1 = "Now is the time for";
		String plainText2 = "Now is the time for jump";
		
		try {
			System.out.println("키 : [01 23 45 67 89 AB CD EF]");
			
			// 예시1) plainText1 = "Now is the time for"
			System.out.println("\n평문1 : " + plainText1);
			Zero_padding(plainText1);
			ANSI_X9_23(plainText1);
			PKCS5(plainText1);
			
			// 예시2) plainText2 = "Now is the time for jump"
			System.out.println("\n평문2 : " + plainText2);
			Zero_padding(plainText2);
			ANSI_X9_23(plainText2);
			PKCS5(plainText2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
