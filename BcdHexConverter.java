/**
 * @author Bassama
 * Hexadecimal (0,9 - A,F) to BCD conversion and vice versa
 */
public class BcdHexConverter {
	/**
     * This function converts BCD data to hexadecimal
     * @param bcd - BCD data
     * @return Hexadecimal string corresponding to BCD data
     */
	public static String bcd2hex(byte[] bcd) {
		int size = (bcd == null ? 0 : bcd.length);
		StringBuffer buffer = new StringBuffer(2*size);
		for (int i = 0; i < size; ++i) {
			int octet = bcd[i];
			int firstDigit = (octet >> 4) & 0xF;
			int secondDigit = octet & 0xF;
			buffer.append(dec2hex(firstDigit));
			buffer.append(dec2hex(secondDigit));
		}
		return buffer.toString().toUpperCase();
	}
	
	/**
	 * This method convert hexadecimal (that contains (0..F) chars) to BCD data
	 * @param hexString - hexadecimal string
	 * @return BCD data
	 */
	public static byte[] hex2bcd(String hexString) {
		int length = (hexString == null ? 0:hexString.length());
		int size = (length + 1)/2;
		byte[] buffer = new byte[size];
		for (int i = 0, j = 0, k = 1; i < size; ++i, j += 2, k += 2) {
			int firstDigit = hex2Dec(hexString.charAt(j));
			int secondDigit = 15;
			if (k < length) {
				secondDigit = hex2Dec(hexString.charAt(k));
			}
			int octet = (firstDigit << 4) + secondDigit;
			buffer[i] = (byte)(octet & 0xFF);
		}
		return buffer;
    }
	
	public static int hex2Dec(char c) {
		int base = 16;
		return Integer.parseInt(String.valueOf(c), base);
	}
	
	public static String dec2hex(int value) {
		return Integer.toHexString(value);
	}
}
