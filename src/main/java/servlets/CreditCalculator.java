package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc")
public class CreditCalculator extends HttpServlet{

	private float kwota;
	private int liczbaRat;
	private float oprocentowanie;
	private float oplata;
	private RodzajRat rodzajRat;
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		// znaczniki html/polskie znaki
		response.setContentType("text/html;charset=UTF-8");
		
		// bloki try-catch walidują formularz
		try {
			kwota = Float.parseFloat(request.getParameter("kwota"));
		} catch (NullPointerException e)  {
			response.getWriter().println("Nie podano kwoty kredytu");
			kwota = 0;
			response.sendRedirect("/");
		} catch(NumberFormatException e) {
			response.getWriter().println("Podana kwota kredytu jest niepoprawna");
			kwota = 0;
			response.sendRedirect("/");
		}
		
		try{
			liczbaRat = Integer.parseInt(request.getParameter("liczbaRat"));
		} catch (NullPointerException e) {
			response.getWriter().println("Nie podano liczby rat kredytu");
			liczbaRat = 0;
			response.sendRedirect("/");
		} catch (NumberFormatException e) {
			response.getWriter().println("Podana liczba rat kredytu jest niepoprawna");
			liczbaRat = 0;
			response.sendRedirect("/");
		}
		
		try {
			oprocentowanie = Float.parseFloat(request.getParameter("oprocentowanie"));
		} catch (NullPointerException e) {
			response.getWriter().println("Nie podano oprocentowania kredytu");
			oprocentowanie = 0;
			response.sendRedirect("/");
		} catch (NumberFormatException e) {
			response.getWriter().println("Podane oprocentowanie kredytu jest niepoprawne");
			oprocentowanie = 0;
			response.sendRedirect("/");
		}
		
		try {
			oplata = Float.parseFloat(request.getParameter("oplata"));
		} catch (NullPointerException e) {
			response.getWriter().println("Nie podano wartości opłaty stałej kredytu");
			oplata = 0;
			response.sendRedirect("/");
		} catch (NumberFormatException e) {
			response.getWriter().println("Podana wartości opłaty stałej kredytu jest niepoprawna");
			oplata = 0;
			response.sendRedirect("/");
		}
		
		// pole wyboru rat - stale/malejące
		if(request.getParameter("rodzajRat").equals("ratyStale")) {
			rodzajRat = RodzajRat.stale;
		} else if(request.getParameter("rodzajRat").equals("ratyMalejace")) {
			rodzajRat = RodzajRat.malejace;
		}
		
		//raty malejące
		if(rodzajRat.equals(RodzajRat.malejace)) {
			
			//kalkulacja
			float rataKapitalowa = kwota/liczbaRat;
			float oplatawRatach = oplata/liczbaRat;
			
			//przycisk generujący pdf
			response.getWriter().println("<br /><form action=\"pdfGenerator\" method=\"post\"><input type=\"submit\" value=\"Generuj plik PDF z harmonogramem\"></form><br />");
			
			//rysuje tabelkę
			response.getWriter().println("<table cellpadding=\"0\" cellspacing=\"3\">"
					+ "<tr> <td style=\"line-height: 1px\"><img src=\"tabelaCSS/lg.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td>  <td style=\"line-height: 1px\"><img src=\"tabelaCSS/pg.gif\" alt=\"\" /></td> </tr>"
					+ "<tr> <td bgcolor=\"#426B9C\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Nr raty</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Kwota kapitału</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Kwota odsetek</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Opłaty stałe</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Całkowita kwota raty</b></font></td> <td bgcolor=\"#426B9C\"><img src=\"0.gif\" alt=\"\" /></td> </tr>");
			
			float k = kwota;
			for (int i=0; i<liczbaRat; i++) {
				
				//kalkulacja
				float rataOdsetkowa = k*oprocentowanie/100/12;
				float rataMiesieczna = rataKapitalowa+rataOdsetkowa;
				
				//zaokrąglanie wyników
				rataKapitalowa*=100;
				rataKapitalowa = Math.round(rataKapitalowa);
				rataKapitalowa/=100;
				
				rataOdsetkowa*=100;
				rataOdsetkowa = Math.round(rataOdsetkowa);
				rataOdsetkowa/=100;
				
				oplatawRatach*=100;
				oplatawRatach = Math.round(oplatawRatach);
				oplatawRatach/=100;
				
				rataMiesieczna*=100;
				rataMiesieczna = Math.round(rataMiesieczna);
				rataMiesieczna/=100;
				
				//rysuję tabelkę
				response.getWriter().println(
						 "<tr> <td bgcolor=\"#426B9C\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+(i+1)+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+rataKapitalowa+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+rataOdsetkowa+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+oplatawRatach+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+rataMiesieczna+"</b></font></td> <td bgcolor=\"#426B9C\"><img src=\"0.gif\" alt=\"\" /></td> </tr>"
						);
				
				//kalkulacja
				k-=rataKapitalowa;
			}
			
			//rysuję tabelkę
			response.getWriter().println(
					 "<tr> <td style=\"line-height: 1px\"><img src=\"tabelaCSS/ld.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td>  <td style=\"line-height: 1px\"><img src=\"tabelaCSS/pd.gif\" alt=\"\" /></td> </tr>"
					+"</table>"); 
		}
		
		//raty stałe
		if(rodzajRat.equals(RodzajRat.stale)) {
			
			//kalkulacja
			float oprocentowanieMiesieczne = 1+oprocentowanie/100/12;
			double rataMiesieczna = (kwota*(Math.pow(oprocentowanieMiesieczne, liczbaRat))*(oprocentowanieMiesieczne-1))/(Math.pow(oprocentowanieMiesieczne, liczbaRat)-1);
			float oplatawRatach = oplata/liczbaRat;
			
			//rysuję tabelkę
			response.getWriter().println("<table cellpadding=\"0\" cellspacing=\"3\">"
					+ "<tr> <td style=\"line-height: 1px\"><img src=\"tabelaCSS/lg.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td>  <td style=\"line-height: 1px\"><img src=\"tabelaCSS/pg.gif\" alt=\"\" /></td> </tr>"
					+ "<tr> <td bgcolor=\"#426B9C\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Nr raty</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Kwota kapitału</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Kwota odsetek</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Opłaty stałe</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>Całkowita kwota raty</b></font></td> <td bgcolor=\"#426B9C\"><img src=\"0.gif\" alt=\"\" /></td> </tr>");
			
			//kalkulacja
			float rataKapitalowa = kwota;
			float rataOdsetkowa = kwota*(oprocentowanieMiesieczne-1);
			rataMiesieczna+=oplatawRatach;
			
			for (int i=0; i<liczbaRat; i++) {
				
				//zaokrąglanie wyników
				rataKapitalowa*=100;
				rataKapitalowa = Math.round(rataKapitalowa);
				rataKapitalowa/=100;
				
				rataOdsetkowa*=100;
				rataOdsetkowa = Math.round(rataOdsetkowa);
				rataOdsetkowa/=100;
				
				oplatawRatach*=100;
				oplatawRatach = Math.round(oplatawRatach);
				oplatawRatach/=100;
				
				rataMiesieczna*=100;
				rataMiesieczna = Math.round(rataMiesieczna);
				rataMiesieczna/=100;
				
				//rysuję tabelkę
				response.getWriter().println(
						 "<tr> <td bgcolor=\"#426B9C\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+(i+1)+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+rataKapitalowa+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+rataOdsetkowa+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+oplatawRatach+"</b></font></td> <td bgcolor=\"#426B9C\"><font color=\"#C0FFFF\"><b>"+rataMiesieczna+"</b></font></td> <td bgcolor=\"#426B9C\"><img src=\"0.gif\" alt=\"\" /></td> </tr>"
						);
				
				//kalkulacja
				rataKapitalowa-=rataMiesieczna;
				rataKapitalowa+=rataOdsetkowa;
				rataOdsetkowa=rataKapitalowa*(oprocentowanieMiesieczne-1);

			}
			
			//rysuję tabelkę
			response.getWriter().println(
					 "<tr> <td style=\"line-height: 1px\"><img src=\"tabelaCSS/ld.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td> <td bgcolor=\"#426B9C\" style=\"line-height: 1px\"><img src=\"tabelaCSS/0.gif\" alt=\"\" /></td>  <td style=\"line-height: 1px\"><img src=\"tabelaCSS/pd.gif\" alt=\"\" /></td> </tr>"
					+"</table>");
		}

	}
	
}
