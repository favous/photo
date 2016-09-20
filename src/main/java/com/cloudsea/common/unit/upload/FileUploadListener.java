package com.cloudsea.common.unit.upload;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadListener implements ProgressListener {

	private String uploadOrder;
	private long theBytesRead = 0;
	private long contentLength = -1;
	private int whichItem = 0;
	//private int percentDone = 0;
	
	FileUploadListener(){}

	public FileUploadListener(String uploadOrder) {
		this.uploadOrder = uploadOrder;
	}

	public void update(long bytesRead, long contentLength, int items) {

		if (contentLength > 0) {			
			this.theBytesRead = bytesRead;
			this.contentLength = contentLength;
			this.whichItem = items;
			//this.percentDone = (int) Math.round(100.00 * bytesRead / contentLength);
		}
	}

	public String getUploadOrder() {
		return uploadOrder;
	}

	public long getTheBytesRead() {
		return theBytesRead;
	}

	public long getContentLength() {
		return contentLength;
	}

	public int getWhichItem() {
		return whichItem;
	}

//	public int getPercentDone() {
//		return percentDone;
//	}
	
}
