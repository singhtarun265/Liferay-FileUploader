package com.liferay.document.media.service;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.media.api.api.DocumentMediaApi;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Argil DX
 */
@Component(immediate = true, property = {
		// TODO enter required service properties
})
public class DocumentMediaService implements DocumentMediaApi {
	private static Log log = LogFactoryUtil.getLog(DocumentMediaService.class);
	
	@Override
	public Folder createFolder(long parent_Folder_Id, String root_Folder_Name, String root_Folder_Description,
			ActionRequest actionRequest, ThemeDisplay themeDisplay) {
		
		boolean folderExist = isFolderExist(parent_Folder_Id, root_Folder_Name, themeDisplay);

		Folder folder = null;
		if (!folderExist) {
			long repositoryId = themeDisplay.getScopeGroupId();
			try {
				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFolder.class.getName(),
						actionRequest);

				folder = DLAppServiceUtil.addFolder(repositoryId, parent_Folder_Id, root_Folder_Name,
						root_Folder_Description, serviceContext);
				log.info(root_Folder_Name+" name folder created successfully");
			} catch (PortalException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
		}
		return folder;
	}

	@Override
	public boolean isFolderExist(long parent_Folder_Id, String root_Folder_Name, ThemeDisplay themeDisplay) {
		boolean folderExist = false;
		try {
			DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), parent_Folder_Id, root_Folder_Name);
			folderExist = true;
			log.info("Folder is already Exist");

		} catch (Exception e) {
			
			log.error(e.getMessage());
		}
		return folderExist;
	}

	@Override
	public Folder getFolder(long parent_Folder_Id, String root_Folder_Name, ThemeDisplay themeDisplay) {
		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), parent_Folder_Id, root_Folder_Name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return folder;
	}

	@Override
	public void fileUpload(long parent_Folder_Id, String root_Folder_Name, String root_Folder_Description,ThemeDisplay themeDisplay,
			ActionRequest actionRequest) {
		
		long temp = 0;
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		String fileName = uploadPortletRequest.getFileName("uploadedFile");
		File file = uploadPortletRequest.getFile("uploadedFile");
		String mimeType = uploadPortletRequest.getContentType("uploadedFile");
		long repositoryId = themeDisplay.getScopeGroupId();
		
		String title = fileName;
		
		
		log.info("Title=>" + title);
		try {
			Folder folder = getFolder(parent_Folder_Id, root_Folder_Name, themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					actionRequest);
			InputStream is = new FileInputStream(file);

			List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
			
			for (FileEntry duplicateFile : fileEntries) {
				
				if (duplicateFile.getFileName().equalsIgnoreCase(fileName)) {
					temp = duplicateFile.getFileEntryId();

				}
			}

			if (temp > 0) {
				DLAppServiceUtil.deleteFileEntryByTitle(repositoryId, folder.getFolderId(), fileName);
			}

			DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, root_Folder_Description,
					"", is, file.length(), serviceContext);

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

}