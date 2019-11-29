import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Test1 {

	public static void main(String[] args) throws Exception {
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
		fileEncrypt("12345", cipher);
		fileDecrypt("12345", cipher);
	}

	private static void fileEncrypt(String password, Object cipher) throws Exception {
		FileInputStream inFile = new FileInputStream("\\Eclipse Photon\\Encryption\\bin\\Nelson_12_Physics.pdf");
		FileOutputStream outFile = new FileOutputStream("\\Eclipse Photon\\Encryption\\bin\\Nelson_12_Physics.ec");
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndTripleDES");
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		byte[] salt = new byte[8];
		SecureRandom secRandom = new SecureRandom() ;
		secRandom.nextBytes(salt);

		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
		cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
		((Cipher) cipher).init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
		outFile.write(salt);

		byte[] input = new byte[64];
		int bytesRead;
		while ((bytesRead = inFile.read(input)) != -1) {
			byte[] output = ((Cipher) cipher).update(input, 0, bytesRead);
			if (output != null)
				outFile.write(output);
		}

		byte[] output = ((Cipher) cipher).doFinal();
		if (output != null)
			outFile.write(output);

		inFile.close();
		outFile.flush();
		outFile.close();
	}

	private static void fileDecrypt(String password, Object cipher) throws Exception {
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndTripleDES");
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		FileInputStream fis = new FileInputStream("F:\\Eclipse Photon\\Encryption\\bin\\Nelson_12_Physics.ec");
		byte[] salt = new byte[8];
		fis.read(salt);

		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);

		cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
		((Cipher) cipher).init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);
		FileOutputStream fos = new FileOutputStream("F:\\Eclipse Photon\\Encryption\\bin\\Nelson_12_Physics_dc.pdf");
		byte[] in = new byte[64];
		int read;
		while ((read = fis.read(in)) != -1) {
			byte[] output = ((Cipher) cipher).update(in, 0, read);
			if (output != null)
				fos.write(output);
		}

		byte[] output = ((Cipher) cipher).doFinal();
		if (output != null)
			fos.write(output);

		fis.close();
		fos.flush();
		fos.close();
	}

}
