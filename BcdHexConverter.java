/**
 * @author Bassama
 * Hexadecimal (0,9 - A,F) to BCD convertion and vice versa
 */
public class BcdHexConverter {
	  private static String cTBCDSymbolString = "0123456789ABCDEF";
    private static char[] cTBCDSymbols = cTBCDSymbolString.toCharArray();
    
    /**
     * This function converts BCD data to hexadecimal
     * @param tbcd - BCD data
     * @return Hexadecimal string corresponding to BCD data
     */
	public static String bcd2hex(byte[] tbcd) {
      int size = (tbcd == null ? 0 : tbcd.length);
      StringBuffer buffer = new StringBuffer(2*size);
      for (int i = 0; i < size; ++i) {
          int octet = tbcd[i];
          int n2 = (octet >> 4) & 0xF;
          int n1 = octet & 0xF;
          if (n1 >= 16) {
              throw new NumberFormatException("Illegal filler in octet n=" + i);
          }
			    if (n2 >= 16) {
              if (i != size - 1) {
                  throw new NumberFormatException("Illegal filler in octet n=" + i);
              }
          } else {
              buffer.append(cTBCDSymbols[n2]);
          }
			    buffer.append(cTBCDSymbols[n1]);
      }
      return buffer.toString();
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
      for (int i = 0, i1 = 0, i2 = 1; i < size; ++i, i1 += 2, i2 += 2) {
          char c = hexString.charAt(i1);
          int n2 = getTBCDNibble(c, i1);
          int octet = 0;
          int n1 = 15;
          if (i2 < length) {
              c = hexString.charAt(i2);
              n1 = getTBCDNibble(c, i2);
          }
          octet = (n2 << 4) + n1;
          buffer[i] = (byte)(octet & 0xFF);
      }
      return buffer;
  }
	
	private static int getTBCDNibble(char c, int position) {
      int n = Character.digit(c, 10);
      if (n < 0 || n > 9) {
          switch (c) {
              case 'A':
                  n = 10;
                  break;
              case 'B':
                  n = 11;
                  break;
              case 'C':
                  n = 12;
                  break;
              case 'D':
                  n = 13;
                  break;
              case 'E':
                  n = 14;
                  break;
              case 'F':
                  n = 15;
                  break;
              default:
                  throw new NumberFormatException("Bad character '" + c
                          + "' at position " + position);
            }
        }
        return n;
    }
}
