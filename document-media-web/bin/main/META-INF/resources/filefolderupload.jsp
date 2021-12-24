<%@ include file="init.jsp"%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/view.jsp"></portlet:param>
</portlet:renderURL>
	
<portlet:actionURL name="/uploadFile" var="uploadFileURL" />


<liferay-ui:success key="fileUploaded" message="File uploaded successfully" />
<liferay-ui:error key="error-key" message="error" />
<aui:form action="<%=uploadFileURL%>" name="<portlet:namespace />fm" enctype="multipart/form-data">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset label="Basic Info">
			<aui:row>
				<aui:col width="33">
					<aui:input label="Folder Name" name="folderName" type="text">
					</aui:input>
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Folder Description" name="folderDesc" type="text">
					</aui:input>
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="33">
					<aui:input label="Choose File" name="uploadedFile" type="file">
					</aui:input>
				</aui:col>
			</aui:row>

		</aui:fieldset>
	</aui:fieldset-group>
	<aui:button-row>
		<aui:button type="submit" value="Upload"></aui:button>
		<aui:button type="cancel" onClick="<%=viewURL.toString()%>"></aui:button>
	</aui:button-row>
</aui:form>