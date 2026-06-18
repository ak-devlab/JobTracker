<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import= "java.util.*,com.example.jobtracker.model.ApplicationEntry" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>就活応募トラッカー</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
  rel = "stylesheet" >
 <link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
:root{
 --bg:#f8fafc; --card:#ffffff; --line:#e5e7eb;
 --text:#0f172a; --muted:#647486;
 --brand:#6366f1; --brand-weak:#eef2ff; --danger:#ef4444;
}
*{box-sizing:border-box}
body{margin:0; background:var(--bg); color:var(--text); font:16px/1.6 system-ui,-apple-system,"Segoe UI", Roboto,sans-serif;}
.container{max-width:1100px; margin:24px auto; padding:0 16px;}
h1{font-size:28px; margin:8px 0 16px}
.actions{display:flex; gap:8px; flex-weap:wrap; margin:8px 0 16px}
.btn{display:inline-block; padding:8px 12px; border:1px solid var(--line); border-radius:10px; text-decoration:none}
.btn.primary{background:var(--brand); color:#fff; border-clor:var(--brand)}
.btn.ghost{background:#fff}
.link-danger{color:var(--danger)}

form.toolbar{background:var(--card); border:1 solid var(--line); border-radius:14px; padding:12px; display:grid; gap:10px}
input,select,textarea,button{font:16px/1.3 inherit; padding:10px 12px; border:1px solid var(--line); border-radius:10px; background:#fff}
button[type=submit]{cursor:pointer}

.table-wrap{background:var(--card); border:1px solid var(--line); border-radius:14px; overflow:hidden}
table{width:100px; border-collapse:collapse}
th,td{padding:10px 12px; border-bottom:1px solid var(--line); vertical-align:top}
th{background:#f3f4f6; font-weight:600; text-align:left; position:sticky; top:0}
tr:nth-child(even) td{background:#fbfbfd}
tr:hover td{background:#f9fafb}
.nowrap{white-space:nowrap}
.badge{display:inline-bloc; padding:.2rem .5rem; border-radius:999px; font-size:12px; background:#eef2f7; color:#334155}
.badge.blue{background:#e0e7ff; color:#3730a3}
.badge.green{background:#dcfce7; color:#166534}
.badge.orange{background:#ffedd5; color:#9a3412}
.badge.gray{background:#e5e7eb; color:#374151}
.dueSoon td{background:#fff7cc !important}
.overdue td{background:#ffe0e0 !important}

@media(min-width: 640px){form.toolbar{grid-template-columns:1fr 1fr}}
@media(min-width: 900px){
 form.toolbar{grid-template-columns:repeat(6,1fr)}
 form.toolbar [data-span="2"]{grid-column:span 2}
 form.toolbar [data-span="3"]{grid-column:span3}
 form.toolbar [data-span="6"]{grid-column:span6}
}

@media(max-width: 639px){
 .table-scroll{overflow-x:auto}
 table{min-width:800px}
 input,select,textarea,button{font-size:16px}
}


 body{ font-family:system-ui, -apple-system, "Segoe UI", Roboto, sans-serif; margin:16px;}
 input,select,textarea,button{ font-size:16px; padding:10px;}
 .toolbar{ display:grid; gap:8px; grid-template-columns:1ft;}
 table{ width:100%; border-collapse:collapse; margin-top:12px;}
 th,td{ border:1px solid #ddd; padding:8px;}
 th{ background:#f7f7f7; position:sticky; top:0;}
 .nowrap{ white-space:nowrap;}
 .btn{display:inline-block; padding:10px 12px; border:1px solid #ddd; border-radius:8px; text-decoration:none;}
 @media(min-width: 768px){
 form.toolbar{grid-template-columns: repeat(6, 1ft);}
 .toolbar input[type="hidden"]{
  display:none;
 }
 .toolbar input[name="company"]{ grid-column: span 3;}
 .toolbar input[name="role"]   {grid-column: span 3;}
 .toolbar select[name="status"]{ grid-column: span 1;}
 .toolbar button[type="submit"]{ grid-column: span 1;}
 }
 body{
    background: linear-gradient(180deg,#eef6ff, #ffffff);
 }
 .card{
    border:none;
    border-radius:20px;
    box-shadow:0 4px 12px rgba(0,0,0,0.08);
    background:white;
 }
 .btn-main{
   background:linear-gradient(90deg,#2f7cff,#9b5cff);
   color:white;
   border:none;
   border-radius:12px;
   padding:10px 18px;
 }
 .app-title{
  display:flex;
  align-items:center;
  gap:12px;
  font-size:40px;
  font-weight:bold;
  color:#1e3a8a;
 }
 .app-title i{
  color:#3b82f6;
  font-size:42px;
 }
 body{
  font-family:system-ui;
  background:
   linear-gradient(rgba(135deg,
     #eef4ff 0%,
     #f8f5ff 50%,
     #ffffff 100%);
  
 }
 
  .app-title{
   font-size:32px;
  }
 }
</style>
</head>
<%
 String userName = (String)session.getAttribute("user_name");
%>
<body>
  <h1 class="app-title">
  <i class="bi bi-briefcase-fill"></i>
  就活応募トラッカー
  </h1>
  <div style="margin-right:20px;">
     <%= userName %> さん
     　<a href="<%= request.getContextPath() %>/logout">ログアウト</a>
  </div>
  <%
  String group = (String)request.getAttribute("group");
  if(group == null || group.isBlank()) group = "default";
  String title = "応募一覧";
  if("it".equals(group)) title = "IT企業応募";
  else if("office".equals(group)) title = "事務職応募";
  else if ("sales".equals(group)) title = "営業職応募";
  else if ("medical".equals(group)) title = "医療・福祉応募";
  else if ("creative".equals(group)) title = "クリエイティブ応募";
  else if("physical".equals(group)) title = "フィールドワーク";
  else if("parttime".equals(group)) title = "アルバイト応募";
  else if("manufacturing".equals(group)) title = "製造職応募";
  %>
  <h2 style="margin:4px 0 12px;"><%= title %></h2>
  <form method= "get" action="<%= request.getContextPath() %>/app">
    <input type="hidden" name="group" value="<%= group %>" class="form-control">
    <input type="text"
           name="keyword"
           placeholder="会社名で検索" class="form-control">
    <select name="status" class="form-select">
       <option value="">全ステータス</option>
       <option value="検討中">検討中</option>
     　<option value="応募済み">応募済み</option>
      <option value="面接1">面接1</option>
      <option value="面接2">面接2</option>
      <option value="内定">内定</option>
      <option value = "辞退">辞退</option>
      <option value="不採用">不採用</option>
      <option value = "連絡待ち">連絡待ち</option>
   </select>
    
    <button type="submit" class="btn btn-primary">検索</button>
           
  </form>
  
  
  <div style="margin:12px 0; display:flex; gap:8px; flex-wrap:wrap;" class="card shadow-sm rounded-4 p-4 mb-4">
   <a class="btn" href="https://jp.indeed.com/" target="_blank">Indeed</a>
   <a class="btn" href="https://www.hellowork.mhlw.go.jp/" target="_blank">ハローワーク</a>
   <a class="btn" href="https://tenshoku.mynavi.jp/" target="_blank">マイナビ転職</a>
   <a class="btn" href="https://next.rikunabi.com/" target="_blank">リクナビNEXT</a>
  
  </div>
  <div class="card">
  <form method="post" class="toolbar">
   <input type="hidden" name="group" value="<%= group %>" class="form-control">
   <input name="company" placeholder="会社名"required class="form-control">
 　<input name="role" placeholder="職種" required class="form-control">
   <select name="status" class="form-select">
     <option value = "検討中">検討中</option ><option value = "応募済み">応募済み</option><option　value = "書類選考中">書類選考中</option>
     <option value = "面接1">面接1</option><option value = "面接2">面接2</option><option value = "内定">内定</option>
     <option value = "辞退">辞退</option><option value = "不採用">不採用</option><option value = "連絡待ち">連絡待ち</option>
   </select>
    <input type="datetime-local" name="nextActionAt" class="nowrap" placeholder="次アクション日時">
    <input name="nextAction" placeholder="次アクション(例：お礼メール)"> 
    <input name="note" placeholder="メモ">
    <button type="submit" class="btn-main">追加</button> 
  </form>
  </div>
  <table class="table table-striped table-hover">
   <tr>
   <th>ID</th><th>会社名</th><th>職種</th><th>ステータス</th>
   <th>次アクション</th><th>日時</th><th>残り</th><th>メモ</th><th></th>
   </tr>
   
   <%
    List<ApplicationEntry> items = (List<ApplicationEntry>)request.getAttribute("items");
   if(items != null){
	   for(ApplicationEntry e: items){
		   String when = "";
		   String remain = "";
		   if(e.nextActionAt != null){
			   java.time.Instant ins = java.time.Instant.ofEpochMilli(e.nextActionAt);
			   when = java.time.ZonedDateTime.ofInstant(ins, java.time.ZoneId.systemDefault())
					   .toLocalDateTime().toString().replace('T',' ');
			   long now = System.currentTimeMillis();
			   if(e.nextActionAt != null) {
				   long diff = e.nextActionAt - now;
				   if(diff > 0){
					   long hours = diff / (1000 * 60 * 60);
					   long days = hours / 24;
					   remain = (days > 0) ? days + "日後" : (hours + "時間後");
				   }else{
					   remain = "期限切れ";
				   }
			   }
		   }
		   %>
		   <tr>
		   <td><%= e.id %></td>
		   <td><%= e.company %></td>
		   <td><%= e.role %></td>
		   <td>
		   <%
		    String badge = "bg-secondary";
		   
		   if("応募済み".equals(e.status)) badge = "bg-primary";
		   else if("面接１".equals(e.status)) badge = "bg-warning text-dark";
		   else if("面接２".equals(e.status)) badge = "bg-into text-dark";
		   else if("内定".equals(e.status)) badge = "bg-success";
		   else if("辞退".equals(e.status)) badge = "bg-dark";
		   else if("不採用".equals(e.status)) badge = "bg-danger";
		   else if("連絡待ち".equals(e.status)) badge = "bg-secondary";
		   %>
		   <span class="badge <%= badge %>">
		       <%= e.status %>
		   </span>
		   </td>
		   <td><%= e.nextAction == null ? "" : e.nextAction %></td>
		   <td class="nowrap"><%= when %></td>
		   <td class="nowrap">
		    <%
		     String remainBadge = "bg-secondary";
		    
		    if("期限切れ".equals(remain)){
		    	remainBadge = "bg-danger";
		    } else if(remain.contains("1日後") || remain.contains("2日後") || remain.contains("3日後")){
		    	remainBadge = "bg-warning text-dark";
		    }else if(!remain.isBlank()){
		    	remainBadge = "bg-success";
		    }
		    %>
		    <span class="badge <%= remainBadge %>">
		    <%= remain %>
		    </span>
		   </td>
		  
		   <td>  <%= (e.note == null || e.note.isBlank()) ? "" : e.note  %></td>
		   <td>
		  
		    <a href="app?edit=<%= e.id %>&group=<%= group %>">編集</a> /
          <a style="color:#c00"
           href="app?delete=<%= e.id %>&group=<%= group%>"
           onclick="return confirm('この行を削除します。よろしいですか？');">削除</a>
　　　　　　　　　　</td>
		    </tr>
		      <%
	   }
   }
   %>
    </table>
    <div style="margin: 8px 0 16px 0;">
     <a class="btn" href="<%= request.getContextPath() %>/index.jsp">
     就活管理メニューに戻る
      </a>
    </div>
</body>
</html>     