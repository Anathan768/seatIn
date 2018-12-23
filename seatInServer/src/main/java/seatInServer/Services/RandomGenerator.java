package seatInServer.Services;

public class RandomGenerator {
	
	private int minRangePassword = 102030;
	private int maxRangePassword = 999999;
	private int minRangeActivationCode = 102030405;
	private int maxRangeActivationCode = 999999999;
	
	public RandomGenerator() {
	}
	
	/**
	 * Genera una password, composta da 6 numeri interi, pseudorandomica appartenente al range[102030,999999]
	 * @return String -- password generata randomicamente.
	 */
	public String getRandomPassword() {
		int number = 0;
		number = this.generateRandomPassword();
		String result = ""+number;
		return result;
	}
	
	/**
	 * Genera un codice attivazione di account, in modo pseudorandomico, composto da 9 cifre appartenente al range[102030405, 99999999]
	 * @return String -- il codice attivazione account
	 */
	public String getRandomActivationCode() {
		int number = 0;
		number = this.generateActivationCode();
		String result = ""+number;
		return result;
	}
	
	
	private int generateRandomPassword() {
		int result = ((int) (Math.random() * this.maxRangePassword));
		while(result == 0)
			result = this.generateRandomPassword();
		result += this.minRangePassword;
		return result;
	}
	
	
	private int generateActivationCode() {
		int result = ((int)(Math.random() * this.maxRangeActivationCode));
		while(result == 0)
			result = this.generateActivationCode();
		result += this.minRangeActivationCode;
		return result;
	}

}
