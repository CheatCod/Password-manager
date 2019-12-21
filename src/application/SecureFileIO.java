package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureFileIO {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Encrypt/Decrypt?");
		String input = br.readLine();
		String password;
		String fileAddress;
		if (input.equalsIgnoreCase("Encrypt")) {
			System.out.println("file address:");
			fileAddress = br.readLine();
			File file = new File(fileAddress);
			double fileSizeMb = file.length()/1048576;
			System.out.println("password:");
			password = br.readLine();
			double startTime = System.nanoTime();
			fileEncrypt(password, fileAddress);
			double endTime = System.nanoTime();
			double timeElapsed = endTime - startTime;
			System.out.println("Execution time in milliseconds : " + 
					timeElapsed / 1000000 + " for file " + fileSizeMb + " mb");
		}
		if (input.equalsIgnoreCase("Decrypt")) {
			System.out.println("file address:");
			fileAddress = br.readLine();
			File file = new File(fileAddress);
			System.out.println("password:");
			password = br.readLine();
			double startTime = System.nanoTime();
			fileDecrypt(password, fileAddress);
			double endTime = System.nanoTime();
			double timeElapsed = endTime - startTime;
			System.out.println("Execution time in milliseconds : " + 
					timeElapsed / 1000000 + " for file " + file.length()/1048576 + " mb");
		}
	}

	protected static void fileEncrypt(String password, String fileAddress) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		File file = new File(fileAddress);
		FileInputStream inFile = new FileInputStream(fileAddress);
		FileOutputStream outFile = new FileOutputStream(file.getParent() + "\\" + file.getName() + ".aes");
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
		SecretKey secretKey = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		((Cipher) cipher).init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = ((Cipher) cipher).getParameters();
		
		outFile.write(salt);
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		outFile.write(iv);
		
		
		
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
		file.delete();
		System.out.println("File Encrypted.");
	}

	protected static void fileDecrypt(String password, String fileAddress) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		FileInputStream fis = new FileInputStream(fileAddress);

		FileOutputStream fos = new FileOutputStream(fileAddress.replace(".aes", ""));
		// reading the salt
		// user should have secure mechanism to transfer the
		// salt, iv and password to the recipient
		byte[] salt = new byte[8];
		fis.read(salt);
		// reading the iv
		byte[] iv = new byte[16];
		fis.read(iv);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
		SecretKey tmp = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

		// file decryption
		((Cipher) cipher).init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

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
		System.out.println("File Decrypted.");
	}

}