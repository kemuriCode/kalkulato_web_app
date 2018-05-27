<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1 style="color:blue"><b>Kalkulator kredytowy</b></h1>
<br />
<form action="calc" method="post">
<label><i>Wnioskowana kwota kredytu: &nbsp</i><input type="text" id="kwota" name="kwota" /></label>
<br />
<label><i>Liczba rat miesięcznych kredytu: &nbsp</i><input type="text" id="liczbaRat" name="liczbaRat" /></label>
<br />
<label><i>Oprocentowanie kredytu w skali roku: &nbsp</i><input type="text" id="oprocentowanie" name="oprocentowanie" /></label>
<br />
<label><i>Opłata stała kredytu: &nbsp</i><input type="text" id="oplata" name="oplata" /></label>
<br />
<label><i>Rodzaj rat: &nbsp</i>
<select name="rodzajRat">
<option value="ratyStale">Raty stałe</option>
<option value="ratyMalejace">Raty malejące</option>
</select>
</label>
<br />
<br />
<br />
<input type="submit" value="Oblicz harmonogram kredytu">
</form>

</body>
</html>