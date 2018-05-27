import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import servlets.CreditCalculator;


public class TestCreditCalculator extends Mockito{
	
	@Test
	public void servlet_should_not_greet_the_user_if_the_credit_price_is_null() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		CreditCalculator calc = new CreditCalculator();
		
		when(request.getParameter("kwota")).thenReturn("");
		
		calc.doPost(request, response);
		
		verify(response).sendRedirect("/");
		
	}
}
