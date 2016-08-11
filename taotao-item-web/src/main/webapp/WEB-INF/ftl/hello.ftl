<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>${hello!"默认值"}</h1>
<h2>null值处理第二种方式</h2>
<#if hello??>
${hello}
</#if>
</body>
</html>