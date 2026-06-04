package com.example.jobtracker.web;
import java.util.List;

import com.example.jobtracker.model.ApplicationEntry;
import com.example.jobtracker.repo.ApplicationRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;



@WebServlet(urlPatterns = {"/app","/export"})
public class ApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ApplicationRepo repo = new  ApplicationRepo();
	private final DateTimeFormatter INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	
	 @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		 
		
		 
		 //group パラメータを取得
		 String group = req.getParameter("group");
		 if(group == null || group.isBlank()) group = "default";
		 String keyword = req.getParameter("keyword");
		 String status = req.getParameter("status");
		 String deleteId = req.getParameter("delete");
		 if(deleteId != null) {
			 try {
				 long id = Long.parseLong(deleteId);
				 repo.delete(group, id);
			 }catch(NumberFormatException ignored) {}
			 resp.sendRedirect(req.getContextPath()+"/app?group=" + group);
			 return;
		 }
		 //編集
		 String editId = req.getParameter("edit");
		 if(editId != null) {
			 try {
				 long id = Long.parseLong(editId);
				 var entry = repo.find(group, id);
				 req.setAttribute("group", group);
				 req.setAttribute("entry", entry);
				 req.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(req, resp);
				 return;
			}catch(NumberFormatException ignored) {}
		 }
		 List<ApplicationEntry> items;
		 
		 if(keyword != null && !keyword.isBlank()) {
			  items = repo.search(group,keyword);
		 }else if(status != null && !status.isBlank()) {
			 items = repo.filterByStatus(group,status);
		 }else if("all".equals(group)){
			 items = repo.listAll();
		 }else {
			 items = repo.list(group);
		 }
		 
		 req.setAttribute("group", group);
		 req.setAttribute("items", items);
		 req.getRequestDispatcher("/WEB-INF/jsp/list.jsp")
		 .forward(req,resp);
		 return;
		 
	 }
		 
	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		 
		 //groupを取得
		 String group = req.getParameter("group");
		 if(group == null || group.isBlank()) group = "default";
		 
		 req.setCharacterEncoding("UTF-8");
		 String idStr = req.getParameter("id");
		 String company = req.getParameter("company");
		 String role = req.getParameter("role");
		 String status = req.getParameter("status");
		 String nextActionAtStr = req.getParameter("nextActionAt");
		 String nextAction = req.getParameter("nextAction");
		 String jobUrl = req.getParameter("jobUrl");
		 String note = req.getParameter("note");
		 
		 Long nextActionAt = null;
		 if(nextActionAtStr != null && !nextActionAtStr.isBlank()) {
			 nextActionAt = LocalDateTime.parse(nextActionAtStr,INPUT)
					 .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		 }
		 
		 ApplicationEntry entry = new ApplicationEntry();
		 entry.company = company;
		 entry.role = role;
		 entry.status = status;
		 entry.nextActionAt = nextActionAt;
		 entry.nextAction = nextAction;
		 entry.jobUrl = jobUrl;
		 entry.note = note;
		 entry.group = group;
		 
		 if(idStr == null || idStr.isBlank()) {
			 repo.add(group, entry);
		 }else {
			 try {
				 entry.id = Long.parseLong(idStr);
				 repo.update(group, entry);
			 }catch(NumberFormatException ignored) {}
		 }
		 resp.sendRedirect(req.getContextPath() + "/app?group=" + group);
		 
		
}
	 
	
	 }
