package com.cloudsea.photo.module.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudsea.common.entity.User;

@Service
public class LoginServiceImpl implements LoginService {

	@Value("${LOGINNAME}")
	private String LOGINNAME;

	@Value("${PASSWORD}")
	private String PASSWORD;

	@Override
	public boolean verify(User user) {
		if (LOGINNAME != null && PASSWORD != null && user != null && LOGINNAME.equals(user.getLoginName())
				&& PASSWORD.equals(user.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

}
