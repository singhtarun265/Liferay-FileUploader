<%@ include file="/init.jsp" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects />

<portlet:renderURL var="uploadURL">
	<portlet:param name="mvcPath" value="/filefolderupload.jsp"></portlet:param>
</portlet:renderURL>

<b>Want to Upload a Document</b> <br>
<br>
<a>Click on the below button >>> </a> <aui:button value="File Uploader" onClick="<%=uploadURL.toString()%>"></aui:button>
