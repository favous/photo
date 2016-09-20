package com.cloudsea.common.unit.upload;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.cloudsea.common.dto.Result;
import com.cloudsea.photo.utils.commonutils.Json;

@WebServlet(urlPatterns = { "/uploadFileProgress" })
public class ProgressServlet extends HttpServlet {

	private static final long serialVersionUID = -3606377225255957324L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(true);
		if (session == null) {
			out.print(Json.toJson(Result.fail("未查到进度")));
			return;
		}

		String uploadOrder = request.getParameter("uploadOrder");
		uploadOrder = StringUtils.isBlank(uploadOrder) ? "" : uploadOrder;

		FileUploadListener listener = (FileUploadListener) session.getAttribute("uploadProgressListener_" + uploadOrder);
		if (listener == null) {
			out.print(Json.toJson(Result.fail("未查到进度")));
			return;
		}
		
		out.print(Json.toJson(Result.succ(listener)));
	}
}
