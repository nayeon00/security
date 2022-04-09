package caesar;

/* 
 * 1. 암호화는 영문 대/소문자만 지원할 것 (그 외의 값은 무시, 3번 참고)
 * 2. 출력값은 모두 대문자로 변환할 것
 * ex) key가 3일 경우, a -> D, A -> D, z -> C, Z -> C
 * 3. 한글 등 영문 이외의 문자거나 특수문자의 경우, 변환하지 않고 그대로 출력할 것(ignore)
 * ex) key가 3일 경우, "Hello World!" -> "KHOOR ZROUG!"
 * ex) key가 3일 경우, "안녕하세요!, Hello!" -> "안녕하세요!, KHOOR!"
 * */


import java.util.Scanner;

public class Caesar {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("암호화할 문장: ");
		String str = sc.nextLine();
		System.out.print("key(숫자만 입력): ");
		int key = sc.nextInt();
		sc.close();
		
		int i;
		char c;
		String enc = ""; // 암호화 결과 저장
		for (i=0;i<str.length();i++) {
			c = str.charAt(i);
			if (Character.isLowerCase(c)){ // 소문자일 경우
				enc += (char)('A' + (Character.toUpperCase(c) - 'A' + key) % 26);
			}
			else if (Character.isUpperCase(c)) { // 대문자일 경우
				enc += (char)('A' + (c - 'A' + key) % 26);
			}
			else enc += c; // 영문이 아닐 경우
		}
		System.out.printf("%s -> %s", str, enc);
		
	}
}
