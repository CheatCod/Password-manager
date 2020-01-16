package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseEntry {
	public String dateAndTime;
	private String typeOfEntry;
	// for entry type website

	// for entry type securedText

	// for entry type bank accounts

	/**
	 * @param typeOfEntry
	 * @return Correctly initialized object for json
	 */
	public Object createEntry(int typeOfEntry) {
		switch (typeOfEntry) {
		case 1:
			WebsiteEntry websiteEntry = new WebsiteEntry();
			return websiteEntry;
		case 2:
			SecuredTexEntry securedTextEntry = new SecuredTexEntry();
			return securedTextEntry;
		case 3:
			BankAcctEntry bankAcctEntry = new BankAcctEntry();
			return bankAcctEntry;

		}
		return null;

	}
	class WebsiteEntry {
		private String entryDate;
		private String nameOfWebsite;
		private String webURL;
		private String password;
		private String account;
		private int typeOfEntry;
		
		public WebsiteEntry() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			entryDate = dtf.format(now);
			typeOfEntry = 1;
		}
		
		public String getNameofWebsite() {
			return nameOfWebsite;
		}
		
		public void setNameOfWebsite(String name) {
			nameOfWebsite = name;
		}
		
		public String getWebURL() {
			return webURL;
		}
		public void setWebURL(String url) {
			webURL = url;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String psw) {
			password = psw;
		}
		
		public String getAccount() {
			return account;
		}
		
		public void setAccount(String acnt) {
			account = acnt;
		}
		
	}
	class SecuredTexEntry {
		private String securedText;
	}
	class BankAcctEntry {
		private String cardNumber;
		private String expDate;
		private String cardHolder;
		private String cvv;
	}
}
