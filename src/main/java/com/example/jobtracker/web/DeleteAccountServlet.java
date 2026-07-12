package com.example.jobtracker.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.jobtracker.repo.UserRepo;

@WebServlet("/delete-account")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException{
		
		HttpSession session = req.getSession(false);
		
		if(session == null || session.getAttribute("user_id") == null) {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}
		
		Long userId = (Long) session.getAttribute("user_id");
		
		try {
			UserRepo repo = new UserRepo();
			repo.deleteUserAndApplications(userId);
			
			session.invalidate();
			
			resp.sendRedirect(req.getContextPath() + "/?deleted=1");
		}catch(Exception e) {
			e.printStackTrace();
			resp.setContentType("text/plain; charset=UTF-8");
			resp.getWriter().println("退会処理中にエラーが発生しました。");
		}
	}

}
