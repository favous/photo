package com.cloudsea.common.unit.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CommonsMultipartResolverExt extends CommonsMultipartResolver {

	@Override
	protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		
		//把uploadProgressListener对象存入session
		String uploadOrder = request.getParameter("uploadOrder");
		if (uploadOrder == null) {
			String queryString = request.getQueryString();
			if (StringUtils.isNotBlank(queryString)) {
				for (String str : queryString.split("&")){
					if (str.contains("uploadOrder")) {							
						uploadOrder = str.replace("uploadOrder", "").replace("=", "").trim();
					}
				}
			}
		}
		FileUploadListener uploadProgressListener = new FileUploadListener();
		request.getSession().setAttribute("uploadProgressListener_" + uploadOrder, uploadProgressListener);

		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		fileUpload.setProgressListener(uploadProgressListener);
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			MultipartParsingResult parsingResult = parseFileItems(fileItems, encoding);
			
			return parsingResult;
			
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		} catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}
}
