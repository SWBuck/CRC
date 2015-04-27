import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Sam Buck 03/05/2015
 *
 */
public class Encode {
	ArrayList<Integer> message, generator, transmission;

	public Encode(String message, String generator) {
		this.message = this.stringToArrayList(message);
		this.generator = this.stringToArrayList(generator);
		this.transmission = this.initializeTransmission();
		this.crcAlgorithm();
	}

	@Override
	public String toString() {
		String transmissionString = "";
		String generatorString = "";
		for (int i : this.transmission) {
			transmissionString += i;
		}
		for (int i : this.generator) {
			generatorString += i;
		}
		return "Transmission: " + transmissionString + "\nGenerator: "
				+ generatorString + "\n";
	}

	/**
	 * @return the message with k-1 zeros added
	 */
	private ArrayList<Integer> initializeTransmission() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (Integer i : this.message) {
			result.add(i);
		}
		for (int i = 0; i < this.generator.size() - 1; i++) {
			result.add(0);
		}
		return result;
	}

	/**
	 * 
	 * @param The
	 *            remainder from the crcAlgorithm
	 * @return The final transmission
	 */
	private ArrayList<Integer> setTransmission(ArrayList<Integer> a) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (Integer i : this.message) {
			result.add(i);
		}
		for (int i = 0; i < a.size(); i++) {
			result.add(a.get(i));
		}
		return result;
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
		this.transmission = this.setTransmission(a);
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
		System.out.println("Encode\nEnter Message String: ");
		String message = scan.nextLine();
		while (!verifiedInput(message)) {
			System.out.println("Invalid Message\nEnter Message String: ");
			message = scan.nextLine();
		}
		System.out.println("Enter Generator String: ");
		String generator = scan.nextLine();
		while (!verifiedInput(generator)) {
			System.out.println("Invalid Generator\nEnter Generator String: ");
			generator = scan.nextLine();
		}
		scan.close();
		Encode encode = new Encode(message, generator);
		System.out.println(encode);
	}

}
