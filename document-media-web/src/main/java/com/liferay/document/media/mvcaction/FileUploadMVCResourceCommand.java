package com.liferay.document.media.mvcaction;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.media.api.api.DocumentMediaApi;
import com.liferay.document.media.constants.DocumentMediaPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(immediate = true, 
		property = { 
			"javax.portlet.name=" + DocumentMediaPortletKeys.DOCUMENTMEDIA,
			"mvc.command.name=/uploadFile"
			}, 
			service = MVCResourceCommand.class)

public class FileUploadMVCResourceCommand implements MVCResourceCommand {
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	private static Log log = LogFactoryUtil.getLog(FileUploadMVCResourceCommand.class);
	
	/*@Override
	 *
	 * protected void doProcessAction(ActionRequest actionRequest, ActionResponse
	 * actionResponse) throws IOException,PortletException, PortalException,
	 * SystemException {
	 * 
	 * String root_Folder_Name = ParamUtil.getString(actionRequest, "folderName");
	 * String root_Folder_Description = ParamUtil.getString(actionRequest,
	 * "folderDesc");
	 * 
	 * if(root_Folder_Name.isEmpty()) {
	 * log.info("You have not entered Folder Name"); root_Folder_Name="ID-Proof";
	 * log.info("We are creating folder "+root_Folder_Name); }
	 * if(root_Folder_Description.isEmpty()) {
	 * root_Folder_Description="Temporary Files";
	 * log.info("You have not entered Folder Description");
	 * log.info("we are setting folder description "+root_Folder_Description); } try
	 * { ThemeDisplay themeDisplay = (ThemeDisplay)
	 * actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	 * 
	 * log.info("ThemeDisplay object created...");
	 * documentMediaApi.createFolder(PARENT_FOLDER_ID,root_Folder_Name,
	 * root_Folder_Description,actionRequest, themeDisplay);
	 * 
	 * 
	 * documentMediaApi.multipleFileUpload(PARENT_FOLDER_ID, root_Folder_Name,
	 * root_Folder_Description, themeDisplay, actionRequest);
	 * log.info("File uploaded successfully");
	 * 
	 * SessionMessages.add(actionRequest, "fileUploaded");
	 * 
	 * 
	 * 
	 * 
	 * } catch (Exception e) { SessionErrors.add(actionRequest, "error-key");
	 * log.info("The error " + e); log.error(e);
	 * 
	 * }
	 * 
	 * }
	 */
	

	@Override
	public boolean serveResource(ResourceRequest actionRequest, ResourceResponse resourceResponse) {
		// TODO Auto-generated method stub
		String root_Folder_Name = ParamUtil.getString(actionRequest, "folderName");
		String root_Folder_Description = ParamUtil.getString(actionRequest, "folderDesc");
		
		if(root_Folder_Name.isEmpty())
		{
			log.info("You have not entered Folder Name");
			root_Folder_Name="ID-Proof";
			log.info("We are creating folder "+root_Folder_Name);
		}
		if(root_Folder_Description.isEmpty())
		{
			root_Folder_Description="Temporary Files";
			log.info("You have not entered Folder Description");
			log.info("we are setting folder description "+root_Folder_Description);
		}
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			log.info("ThemeDisplay object created...");
			documentMediaApi.createFolder(PARENT_FOLDER_ID,root_Folder_Name,root_Folder_Description,actionRequest, themeDisplay);
			
			
			documentMediaApi.multipleFileUpload(PARENT_FOLDER_ID, root_Folder_Name, root_Folder_Description, themeDisplay, actionRequest);
			log.info("File uploaded successfully");
			
			SessionMessages.add(actionRequest, "fileUploaded");

			JSONObject jsonResponse = JSONFactoryUtil.createJSONObject();
			jsonResponse.put("result", "uploaded successfully");
	 
			// Writing the result in resourceResponse writer.
			PrintWriter writer = resourceResponse.getWriter();
			writer.println(jsonResponse);
			return true;

		} catch (Exception e) {
			SessionErrors.add(actionRequest, "error-key");
			log.info("The error " + e);
			log.error(e);
			return false;
		}
		

	}
	
	@Reference
	DocumentMediaApi documentMediaApi;
}
