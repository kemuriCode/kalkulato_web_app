package servlets;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

@WebServlet("/pdfGenerator")
public class PdfGenerator extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		//response.setContentType("application/pdf");
		
		Document document = new Document();
		try {
			
			ServletOutputStream os = response.getOutputStream();
			PdfWriter.getInstance(document, os);
			document.open();
			document.add(new Paragraph("Test"));
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		document.close();
	}

}
