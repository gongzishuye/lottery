package monitor.utils;

import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final Logger log = Logger.getLogger(StringUtil.class);

    private static final Pattern IP_PATTERN = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
	private static final String defaultKey = "cei3UI3509RgbdKL";
	private static Cipher encryptCipher = null;
	private static Cipher decryptCipher = null;
	private static SecretKey key = null;
	private static IvParameterSpec iv = null;

	static{
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] digestOfPassword = md.digest(defaultKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			key = new SecretKeySpec(keyBytes, "DESede");
			iv = new IvParameterSpec(new byte[8]);
			
			encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key, iv);
			
			decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, key, iv);
		} catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
            log.error(e.getMessage(), e);
		}
	}


    public static boolean isIP(String ip) {
        if (isEmpty(ip)) return false;
        Matcher m = IP_PATTERN.matcher(ip);
        return m.matches();
    }
	
	/**
	 * 加密
	 * @param textBytes
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] textBytes) {
		byte[] cipherText = null;
		try {
			cipherText = encryptCipher.doFinal(textBytes);
		} catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
		}
		return cipherText;
	}
	
	public static String encrypt(String textBytes) {
		String s = null;
		try {
			s = byteArr2HexStr(encrypt(textBytes.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
		} catch (Exception e) {
            log.error(e.getMessage(), e);
		}
		return s;
	}

	/**
	 * 解密
	 * @param textBytes
	 * @return
	 */
	public static byte[] decrypt(byte[] textBytes) {
		byte[] plainText = null;
		try {
			plainText = decryptCipher.doFinal(textBytes);
		} catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
		}
		return plainText;
	}
	
	public static String decrypt(String textBytes) throws Exception {
		String s = null;
		try {
			s = new String(decrypt(hexStr2ByteArr(textBytes)), "UTF-8");
		} catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
            log.error(e.getMessage(), e);
			throw e;
		}
		return s;
	}
	
	public static String byteArr2HexStr(byte[] arrByte) throws Exception {
		int iLen = arrByte.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrByte[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	/**
     * 判断是否为空串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() <= 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    /**
     * 检查字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return str != null && Pattern.compile("[0-9]+").matcher(str).matches();
    }

    public static boolean isNotNumber(String str) {
        return !isNumber(str);
    }
	public static void main(String[] args) {
		System.out.println(StringUtil.encrypt("123456"));
	}
	/**
     * This code is needed so that the UTF-16 won't be malformed
     *
     * @param escaped
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String unescape(String escaped) {
        try {
            if (!isEmpty(escaped)) {
                String str = escaped.replaceAll("%([0-9A-F]){1}", "%u00$1");
                // Here we split the 4 byte to 2 byte, so that decode won't fail
                String[] arr = str.split("%u");
                Vector<String> vec = new Vector<String>();
                if (!arr[0].isEmpty()) {
                    vec.add(arr[0]);
                }
                for (int i = 1; i < arr.length; i++) {
                    if (!arr[i].isEmpty()) {
                        vec.add("%" + arr[i].substring(0, 2));
                        vec.add("%" + arr[i].substring(2));
                    }
                }
                str = "";
                for (String string : vec) {
                    str += string;
                }
                // Here we return the decoded string
                return URLDecoder.decode(str, "UTF-16");
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return escaped;
    }
}
