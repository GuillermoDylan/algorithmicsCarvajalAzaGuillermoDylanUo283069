package algstudent.s6.password;

import java.util.List;
import org.junit.Test;

public class PasswordGeneratorTest {
	@Test
	public void test() {
		int numberOfTotalCharacters = 100;
		int numberOfNonLettersEnds = 50;
		int numberOfPasswords = 10;
		String consonantPairsPath = "src/main/java/algstudent/s6/password/consonant_pairs.txt"; 
		
		PasswordGenerator generator = new PasswordGenerator(
				numberOfTotalCharacters,
				numberOfNonLettersEnds,
				numberOfPasswords,
				consonantPairsPath);
	
		generator.generate();
		
		List<String> passwords = generator.getPasswords();	
		
		for (int i = 0; i < numberOfPasswords; i++) {			
			String password = passwords.get(i);
			System.out.println((i+1) + ": " + password);
		}
	}
	
}
