package com.cloudsea.photo.dto;

import java.io.Serializable;

import com.cloudsea.photo.entity.Gallery;
import com.cloudsea.photo.entity.MenuItem;

public class MenuDto extends MenuItem implements Serializable {
	
	private static final long serialVersionUID = -7413386830092518409L;
	
	private Gallery gallery;

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
	
}
