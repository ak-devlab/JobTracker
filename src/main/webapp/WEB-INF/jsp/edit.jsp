   <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
   <%@ page import="com.example.jobtracker.model.ApplicationEntry, java.time.*, java.time.format.DateTimeFormatter" %>
   <%
   ApplicationEntry e = (ApplicationEntry) request.getAttribute("entry");
   String dtValue = "";
   if(e != null && e.nextActionAt != null){
	   Instant ins = Instant.ofEpochMilli(e.nextActionAt);
	   LocalDateTime ldt = LocalDateTime.ofInstant(ins, ZoneId.systemDefault());
	   dtValue = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
   }
   %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>編集　- 就活応募トラッカー</title>
<style>
 body{font-family:system-ui,-apple-system,"Segoe UI", Roboto,sans-serif; margin:16px;}
 input,select,textarea,button{ font-size:16px; padding:10px; width:100%; box-sizing:border-box;}
 .grid{display:grid; gap:10px; grid-template-columns:1fr; max-width:900px;}
 .row{display:grid; gap:10px; grid-template-columns:1ft 1ft;}
 .actions{display:flex; gap:10px;}
 @media(min-width:768px){.grid{grid-template-columns:1ft;}}
</style>
</head>
<body>
<h2>応募データの編集</h2>

<form method="post" action="<%= request.getContextPath() %>/app">
<input type="hidden" name="group" value="<%= request.getAttribute("group") %>">
 <input type="hidden" name="action" value="update">
 <input type="hidden" name="id" value="<%= e.id %>">
 
 <label>メモを入力</label><br>
 <textarea name="note" rjows="6" clos="40">
 <%= e.note == null ? "" : e.note %>
 </textarea>
 
 <br><br>
 
 
 <div class="grid">
  <input name="company" value="<%= e.company %>" placeholder="会社名"　required>
  <input name="role" value="<%= e.role %>" placeholder="職種"　required>
  
  <div class="row">
   <select name="status">
    <%
     String[] st = {"検討中","応募済み","書類選考中","面接１","面接２","内定","辞退","不採用","連絡待ち"};
    for(String s : st){
    %>
    <option value="<%= s %>"<%= s.equals(e.status) ? "selected" : "" %>><%= s %></option>
    <% }%>
   </select>
   <input type="datetime-local" name="nextActionAt" value="<%= dtValue %>" placeholder="次アクション日時">
  
  </div>
  
  <input name="nextAction" value="<%= e.nextAction == null ? "" : e.nextAction %>" placeholder="次アクション（例：お礼メール）">
  <input name="jobUrl" value="<%= e.jobUrl == null ? "" : e.jobUrl %>" placeholder="求人URL">
  <textarea name="note" rows="4" placeholder="メモ"><%= e.note == null ?  "" : e.note %></textarea>
  
  <div class="actions">
    <button type="submit">保存</button>
    <a href="<%= request.getContextPath() %>/app" style="display:inline-block;padding:10px 12px;border:1px solid #ccc;bord-radius:8px;text-decoration:none;">キャンセル</a>
  </div>
 
 </div>

</form>

</body>
</html>