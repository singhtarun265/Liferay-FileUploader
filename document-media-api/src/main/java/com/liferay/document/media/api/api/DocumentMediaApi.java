package com.liferay.document.media.api.api;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.ActionRequest;

/**
 * @author Argil DX
 */
public interface DocumentMediaApi {
	
	
	public void fileUpload(long parent_Folder_Id,String root_Folder_Name,String root_Folder_Description,ThemeDisplay themeDisplay,ActionRequest actionRequest);
	
	public Folder getFolder(long parent_Folder_Id,String root_Folder_Name,ThemeDisplay themeDisplay);
	
	public boolean isFolderExist(long parent_Folder_Id,String root_Folder_Name,ThemeDisplay themeDisplay);
	
	public Folder createFolder(long parent_Folder_Id,String root_Folder_Name,String root_Folder_Description,ActionRequest actionRequest,ThemeDisplay themeDisplay);
}