/*
 * DES/ECB/NoPadding
 * Zero padding, ANSI X9.23, PKCS#5 �ѹ��� ���
 * Ű : [01 23 45 67 89 AB CD EF] ���� ����
 * ����1) �� : "Now is the time for"
 * ����2) �� : "Now is the time for jump"
 * */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class DES {

	public static SecretKey key;
	
	// DES ��ȣȭ
	public static byte[] DES_encrypt(byte[] plainToByte) throws Exception {
	
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding"); // No padding
		cipher.init(Cipher.ENCRYPT_MODE, key);
	
		byte[] encryptedByte = cipher.doFinal(plainToByte);
		
		return encryptedByte; // byte�� ����
	}
	
	// Zero padding
	public static void Zero_padding(String data) throws Exception {
		if (data == null || data.length() == 0) return;
		
		byte[] plainToByte = data.getBytes();
		int len = plainToByte.length;
		int r = 8 - (len % 8); // ������ ����
		if (r == 8) r = 0;
		
		byte[] p = new byte[len+r]; // ���� ���� ����
		System.arraycopy(plainToByte, 0, p, 0, len);
		if (r != 0) { // padding �ʿ��� ���
			byte[] zero = new byte[r]; // ������ ������ ä�� byte�迭
			java.util.Arrays.fill(zero, (byte)0x00); // ���� �迭�� 0x00���� �ʱ�ȭ
			System.arraycopy(zero, 0, p, len, r); // ������ �迭�� ��ģ��.
		}
		System.out.println("Zero padding : " + ByteToHex(DES_encrypt(p)));
	}
	
	// ANSI X9.23
	public static void ANSI_X9_23(String data) throws Exception{
		if (data == null || data.length() == 0) return;
		
		byte[] plainToByte = data.getBytes();
		int len = plainToByte.length;
		
		int r = 8 - (len % 8); // ������ ����, input ũ�Ⱑ ���Ƶ� padding�� �׻� �߰��Ѵ�.
		
		byte[] p = new byte[len+r]; // ���� ���� ����
		byte[] zero = new byte[r]; // ������ ������ ä�� byte�迭
		System.arraycopy(plainToByte, 0, p, 0, len);
		java.util.Arrays.fill(zero, (byte)0x00); // ���� �迭�� 0x00���� �ʱ�ȭ
		zero[zero.length-1] = (byte)(r); // ������ ����Ʈ�� padding ũ�⸦ ��Ÿ����.
		System.arraycopy(zero, 0, p, len, r); // ������ �迭�� ��ģ��.
		
		System.out.println("ANSI X9.23 : " + ByteToHex(DES_encrypt(p)));
	}
	
	// PKCS#5
	public static void PKCS5(String data) throws Exception{
		if (data == null || data.length() == 0) return;
		
		byte[] plainToByte = data.getBytes();
		int len = plainToByte.length;
		
		int r = 8 - (len % 8); // ������ ����, input ũ�Ⱑ ���Ƶ� padding�� �׻� �߰��Ѵ�.
		
		byte[] p = new byte[len+r]; // ���� ���� ����
		byte[] zero = new byte[r]; // ������ ������ ä�� byte�迭�� �ϳ� �����.
		System.arraycopy(plainToByte, 0, p, 0, len);
		java.util.Arrays.fill(zero, (byte)r); // ���� �迭�� padding ũ��� �ʱ�ȭ �Ѵ�.
		System.arraycopy(zero, 0, p, len, r); // ������ �迭�� ��ģ��.
		
		System.out.println("PKCS#5 : " + ByteToHex(DES_encrypt(p)));
		
	}
	
	// byte�� 16������ �ٲٴ� �Լ�
	private static String ByteToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X ", b)); // "%02X " 2�ڸ� 16����, ����� �빮�ڷ�, ����
		}
		return sb.toString();
	}

	
	public static void main(String[] args) throws Exception{
		byte[] k = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
		key = new SecretKeySpec(k, 0, k.length, "DES");
		
		String plainText1 = "Now is the time for";
		String plainText2 = "Now is the time for jump";
		
		try {
			System.out.println("Ű : [01 23 45 67 89 AB CD EF]");
			
			// ����1) plainText1 = "Now is the time for"
			System.out.println("\n��1 : " + plainText1);
			Zero_padding(plainText1);
			ANSI_X9_23(plainText1);
			PKCS5(plainText1);
			
			// ����2) plainText2 = "Now is the time for jump"
			System.out.println("\n��2 : " + plainText2);
			Zero_padding(plainText2);
			ANSI_X9_23(plainText2);
			PKCS5(plainText2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}