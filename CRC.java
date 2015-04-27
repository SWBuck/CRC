import java.io.InputStreamReader;
import java.util.Scanner;

public class CRC {

	public static void main(String[] a) {
		boolean run = true;
		Scanner scan = new Scanner(new InputStreamReader(System.in));
		while (run) {
			System.out.println("(V)erify, (E)ncode, or (Q)uit");
			String task = scan.nextLine().toLowerCase();
			switch (task) {
			case "e":
				System.out.println("Encode\nEnter Message String: ");
				String message = scan.nextLine();
				while (!verifiedInput(message)) {
					System.out
							.println("Invalid Message\nEnter Message String: ");
					message = scan.nextLine();
				}
				System.out.println("Enter Generator String: ");
				String generatorE = scan.nextLine();
				while (!verifiedInput(generatorE)) {
					System.out
							.println("Invalid Generator\nEnter Generator String: ");
					generatorE = scan.nextLine();
				}
				Encode encode = new Encode(message, generatorE);
				System.out.println(encode);
				break;
			case "v":
				System.out.println("Verify\nEnter Transmission String: ");
				String transmission = scan.nextLine();
				while (!verifiedInput(transmission)) {
					System.out
							.println("Invalid Transmission\nEnter Transmission String: ");
					transmission = scan.nextLine();
				}
				System.out.println("Enter Generator String: ");
				String generatorV = scan.nextLine();
				while (!verifiedInput(generatorV)) {
					System.out
							.println("Invalid Generator\nEnter Generator String: ");
					transmission = scan.nextLine();
				}
				Verify verify = new Verify(transmission, generatorV);
				System.out.println(verify);
				break;
			case "q":
				run = false;
				break;
			}
		}
		scan.close();

	}

	static public boolean verifiedInput(String a) {
		for (int i = 0; i < a.length(); i++) {
			String sub = a.substring(i, i + 1);
			if (!sub.equals("1") && !sub.equals("0")) {
				return false;
			}
		}
		return true;
	}

}
