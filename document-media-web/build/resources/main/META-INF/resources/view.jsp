<%@ include file="init.jsp"%>


<!-- Defining the resourceURL -->
<!--<portlet:resourceURL id="/uploadFile" var="submitForm" />
-->
 
 

<aui:script>
AUI().ready(
	    'aui-base', 'io', 'aui-io-request',
	    function(A){
	    	var btnUploadFile = A.one("#<portlet:namespace />btnUploadFile");

	    	btnUploadFile.on("click", uploadFile);
	function uploadFile(event){
    event.preventDefault();
    debugger;
    var fileUploads = A.one("#<portlet:namespace/>fileUpload");
    console.log(fileUploads);
    var ajaxURL = "<portlet:resourceURL id='/uploadFile'></portlet:resourceURL>";
    console.log(ajaxURL);
    var configs = {
        method: 'POST',
        form: {
            id: fileUploads, 
            upload: true
        },
        sync: true,
        on: {
            complete: function(){
                alert("File Upload Complete!");
            }
        }
    };
    
    A.io.request(ajaxURL, configs); }   
});
</aui:script>	

	
	<%-- function submitFormToServer(){
	
		AUI().use('aui-io-request',function(A) {
				A.io.request(
					'<%=submitForm.toString()%>',
					{
						method : 'post',
		
						// Sending the form to the server.
						form : {
							id: 'fileUpload'
						},
						
						// Receiving data from the server. Data is contained in this.get('responseData').
						on: {
							success: function() {
								var response = this.get('responseData');
								document.getElementById('result').innerHTML = response.result;
							}
						}
					}
				);
			}
		);
	} --%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/view.jsp"></portlet:param>
</portlet:renderURL>
	
<portlet:actionURL name="/uploadFile" var="uploadFileURL" />


<liferay-ui:success key="fileUploaded" message="File uploaded successfully" />
<liferay-ui:error key="error-key" message="error" />
<aui:form name="fileUpload" method="post" enctype="multipart/form-data" id="fileUpload">
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
					<aui:input type="file"  name="uploadedFile" multiple="true" label="Choose File">
					</aui:input>
				</aui:col>
			</aui:row>

		</aui:fieldset>
	</aui:fieldset-group>
	<aui:button-row>
		<aui:button type="submit" name="btnUploadFile" value="Upload" ></aui:button>
		<aui:button type="cancel" onClick="<%=viewURL.toString()%>"></aui:button>
	</aui:button-row>
</aui:form>
<div id="result"></div>