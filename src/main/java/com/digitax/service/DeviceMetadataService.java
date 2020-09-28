package com.digitax.service;

import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;

public interface DeviceMetadataService {

	void saveUserActivity(HttpServletRequest request, long l) throws SocketException;

}
