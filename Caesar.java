package caesar;

/* 
 * 1. ��ȣȭ�� ���� ��/�ҹ��ڸ� ������ �� (�� ���� ���� ����, 3�� ����)
 * 2. ��°��� ��� �빮�ڷ� ��ȯ�� ��
 * ex) key�� 3�� ���, a -> D, A -> D, z -> C, Z -> C
 * 3. �ѱ� �� ���� �̿��� ���ڰų� Ư�������� ���, ��ȯ���� �ʰ� �״�� ����� ��(ignore)
 * ex) key�� 3�� ���, "Hello World!" -> "KHOOR ZROUG!"
 * ex) key�� 3�� ���, "�ȳ��ϼ���!, Hello!" -> "�ȳ��ϼ���!, KHOOR!"
 * */


import java.util.Scanner;

public class Caesar {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("��ȣȭ�� ����: ");
		String str = sc.nextLine();
		System.out.print("key(���ڸ� �Է�): ");
		int key = sc.nextInt();
		sc.close();
		
		int i;
		char c;
		String enc = ""; // ��ȣȭ ��� ����
		for (i=0;i<str.length();i++) {
			c = str.charAt(i);
			if (Character.isLowerCase(c)){ // �ҹ����� ���
				enc += (char)('A' + (Character.toUpperCase(c) - 'A' + key) % 26);
			}
			else if (Character.isUpperCase(c)) { // �빮���� ���
				enc += (char)('A' + (c - 'A' + key) % 26);
			}
			else enc += c; // ������ �ƴ� ���
		}
		System.out.printf("%s -> %s", str, enc);
		
	}
}
