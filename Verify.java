import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Sam Buck 03/05/2015
 */
public class Verify {
	ArrayList<Integer> generator, transmission;
	boolean valid = true;

	public Verify(String transmission, String generator) {
		this.generator = this.stringToArrayList(generator);
		this.transmission = this.stringToArrayList(transmission);
		this.crcAlgorithm();
	}

	@Override
	public String toString() {
		if (this.valid) {
			return "Correct Transmission\n";
		} else {
			return "Incorrect Transmission\n";
		}
	}

	/**
	 * Division algorithm, sets the transmission to the final transmission
	 */
	private void crcAlgorithm() {
		ArrayList<Integer> zero = new ArrayList<Integer>();
		for (int i = 0; i < this.generator.size(); i++) {
			zero.add(0);
		}
		ArrayList<Integer> a = this.xor(this.transmission, this.generator);
		for (int i = this.generator.size(); i < this.transmission.size(); i++) {
			a.add(this.transmission.get(i));
			switch (a.get(0)) {
			case 0:
				a = this.xor(zero, a);
				break;
			case 1:
				a = this.xor(this.generator, a);
				break;
			}
		}
		for (Integer i : a) {
			if (i != 0) {
				this.valid = false;
				return;
			}

		}
	}

	/**
	 * @param a
	 * @param b
	 * @return a bitwise XOR b
	 */
	private ArrayList<Integer> xor(ArrayList<Integer> a, ArrayList<Integer> b) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 1; i < b.size(); i++) {
			result.add(a.get(i) ^ b.get(i));
		}
		return result;
	}

	/**
	 * @param s
	 * @return s as a ArrayList of Integers
	 */
	private ArrayList<Integer> stringToArrayList(String s) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < s.length(); i++) {
			result.add(Integer.parseInt(s.substring(i, i + 1)));
		}
		return result;
	}

	/**
	 * @param a
	 * @return true if a is a valid sequence of 1s and 0s
	 */
	static public boolean verifiedInput(String a) {
		for (int i = 0; i < a.length(); i++) {
			String sub = a.substring(i, i + 1);
			if (!sub.equals("1") && !sub.equals("0")) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] a) {
		Scanner scan = new Scanner(new InputStreamReader(System.in));
		System.out.println("Verify\nEnter Transmission String: ");
		String transmission = scan.nextLine();
		while (!verifiedInput(transmission)) {
			System.out
					.println("Invalid Transmission\nEnter Transmission String: ");
			transmission = scan.nextLine();
		}
		System.out.println("Enter Generator String: ");
		String generator = scan.nextLine();
		while (!verifiedInput(generator)) {
			System.out.println("Invalid Generator\nEnter Generator String: ");
			transmission = scan.nextLine();
		}
		scan.close();
		Verify verify = new Verify(transmission, generator);
		System.out.println(verify);
	}

}
