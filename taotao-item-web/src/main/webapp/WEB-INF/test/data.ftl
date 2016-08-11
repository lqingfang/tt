<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>The World of Freemarker</h1>
	${hello!"hello的值为null"}
	现在时间：${today?time}
	现在日期：${today?date}
	现在日期时间：${today?datetime}
	格式化时间：${today?string('yyyy/MM/dd HH:mm:ss')}
</body>
</html>