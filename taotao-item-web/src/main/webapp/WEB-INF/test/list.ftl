<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>The World of freemarker</h1>
	<#list list as person>
		<tr>
			<td>${person.id}</td>
			<td>${person.username}</td>
			<td>${person.address}</td>
		</tr>
	</#list>
</body>
</html>