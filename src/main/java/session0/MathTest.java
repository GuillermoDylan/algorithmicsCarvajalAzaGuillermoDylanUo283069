package session0;


import org.junit.BeforeClass;
import org.junit.Test;

public class MathTest {
	Math math;
	   
    @BeforeClass
    public void initialize() {
        math = new Math();
    }

	@Test
	public void test2Plus3Equals5() {
		assertEquals(5, math.sum(2, 3));
	}

}
