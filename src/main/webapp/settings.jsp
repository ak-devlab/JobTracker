<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    if(session == null || session.getAttribute("user_id") == null){
    	response.sendRedirect(request.getContextPath() + "/");
    	return;
    }

    String userName = (String) session.getAttribute("user_name");
    String email = (String) session.getAttribute("email");
    String picture = (String) session.getAttribute("picture");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>設定　｜　就活応募トラッカー</title>
 <style>
    body{
      margin: 0;
      background: #f5f7fa;
      font-family: system-ui, sans-serif;
      color: #222;
    }
    
    .container{
      max-width: 800px;
      margin: 40px auto;
      padding: 0 20px;
    }
    
    .card{
      background: #fff;
      border: 1px solid #e5e7eb;
      boreder-radius: 14px;
      padding: 24px;
      margin-bottom: 20px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.06);
    }
     h1{
      margin-bottom: 24px
     }
     
     h2{
       margin-top: 0;
       font-size: 20px;
     }
     
     .profile{
       display: flex;
       align-items: center;
       gap: 16px;
     }
     
     .profile img {
       width: 56px;
       height: 56px;
       border-radius: 50%;
     }
     
     .name{
       font-weight: bold;
       font-size: 18px;
     }
     
     .email{
       color: #555;
       font-size: 14px;
     }
     
     .link-btn{
      display: inline-block;
      padding: 10px 16px;
      border-radius: 8px;
      background: #2563eb;
      color: white;
      text-decoration: none;
      font-weight: bold;
      margin-right: 8px;
     }
     
     .logout-btn {
       background: #6b7280;
     }
     
     .danger{
       border-color: #f3caca;
       background: #fffafa;
     }
     
     .danger h2{
       color: #c0392b;
     }
     
     .danger button{
       background: #c0392b;
       color: white;
       border: none;
       padding: 10px 18px;
       border-radius: 8px;
       cursor: pointer;
       font-weight: bold;
     }
     
     .small{
       color: #666;
       line-height: 1.7;
     }
     
 </style>
</head>
<body>
   <div class="container">
      <h1>設定</h1>
      
      <div class="card">
         <h2>アカウント情報</h2>
      <div class="profile">
         <% if(picture != null && !picture.isBlank()) { %>
            <img src="<%= picture %>" alt="プロフィール画像">
         <% } %>
      <div>
         <div class="name"><%= userName != null ? userName : "ユーザー" %></div>
         <div class="email"><%= email != null ? email : "" %></div>
      </div>
      </div>
      </div>
      
      <div class="card">
        <h2>メニュー</h2>
        
        <a class="link-btn" href="<%= request.getContextPath() %>/app?group=all">
          応募一覧へ戻る
        </a>
        
        <a class="link-btn logout-btn" href="<%= request.getContextPath() %>/logout">
           ログアウト
        </a>
      </div>
      
      <div class="card danger">
        <h2>退会　・　データ削除</h2>
        
        <p class="small">
           退会すると、登録した応募データとアカウント情報が削除されます。
            この操作は取り消せません。
        </p>
        
        <form method="post" action="<%= request.getContextPath() %>/delete-account"
              onsubmit="return confirm('本当に退会しますか？登録した応募データもすべて削除されます。');">
              <button type="submit">
                 退会してデータを削除する
              </button>
        
        </form>
      </div>
   </div>
</body>
</html>