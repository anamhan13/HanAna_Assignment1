package ro.utcn.sd.validators;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authentication {

	public String encryptPassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());

			byte byteData[] = md.digest();
			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			System.out.println("Hex format : " + sb.toString());

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private boolean containsSpecialCharacter(String s) {
		if (s == null || s.trim().isEmpty()) {
			return false;
		}
		Pattern p = Pattern.compile("[^A-Za-z0-9]");
		Matcher m = p.matcher(s);
		return m.find();
	}

	public boolean isPasswordStrong(String password) {
		boolean hasUpperLetter = false;
		boolean hasLowerLetter = false;
		boolean hasDigit = false;
		boolean hasSpecialChar = false;

		if (password.length() >= 8) {
			for (int i = 0; i < password.length(); i++) {
				char x = password.charAt(i);
				if (Character.isUpperCase(x))
					hasUpperLetter = true;
				else if (Character.isLowerCase(x))
					hasLowerLetter = true;
				else if (Character.isDigit(x))
					hasDigit = true;

				// no need to check further, break the loop
				if (hasUpperLetter && hasLowerLetter && hasDigit)
					break;
			}

			hasSpecialChar = this.containsSpecialCharacter(password);
			if (hasUpperLetter && hasLowerLetter && hasDigit && hasSpecialChar) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
