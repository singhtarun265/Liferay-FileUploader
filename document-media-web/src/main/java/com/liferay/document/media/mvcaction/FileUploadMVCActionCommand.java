package com.liferay.document.media.mvcaction;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.media.api.api.DocumentMediaApi;
import com.liferay.document.media.constants.DocumentMediaPortletKeys;
import com.liferay.document.media.portlet.DocumentMediaPortlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(immediate = true, 
		property = { 
			"javax.portlet.name=" + DocumentMediaPortletKeys.DOCUMENTMEDIA,
			"mvc.command.name=/uploadFile"
			}, 
			service = MVCActionCommand.class)

public class FileUploadMVCActionCommand extends BaseMVCActionCommand {
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	private static Log log = LogFactoryUtil.getLog(FileUploadMVCActionCommand.class);
	
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,PortletException, PortalException, SystemException {

		String root_Folder_Name = ParamUtil.getString(actionRequest, "folderName");
		String root_Folder_Description = ParamUtil.getString(actionRequest, "folderDesc");
		
		if(root_Folder_Name.isEmpty())
		{
			root_Folder_Name="Temporary";
		}
		if(root_Folder_Description.isEmpty())
		{
			root_Folder_Description="Temporary Files";
		}
		try {

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			log.info("ThemeDisplay object created...");
			documentMediaApi.createFolder(PARENT_FOLDER_ID,root_Folder_Name,root_Folder_Description,actionRequest, themeDisplay);
			
			
			documentMediaApi.fileUpload(PARENT_FOLDER_ID, root_Folder_Name, root_Folder_Description, themeDisplay, actionRequest);
			log.info("File uploaded successfully");
			
			SessionMessages.add(actionRequest, "fileUploaded");

			

		} catch (Exception e) {
			SessionErrors.add(actionRequest, "error-key");
			log.info("The error " + e);
			log.error(e);

		}

	}
	
	@Reference
	DocumentMediaApi documentMediaApi;
}
