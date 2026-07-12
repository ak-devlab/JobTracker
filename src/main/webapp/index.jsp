<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>就活管理メニュー</title>
<style>
 :root{ --bg:#f5f7fa; --card:#fff; --line:#e5e7eb; --text:#0f172a; --brand:#6366f1;}
 *{ box-sizing:border-box}
 body{margin:0; background:var(--bg); color:var(--text); font:16px/1.6 system-ui,-apple-system,"Segoe UI",Roboto,sans-serif;}
 .container{ max-width:900px; margin:24px auto; padding:0 16px;}
 h1{ font-size:28px; margin:8px 0 16px; text-align:center}
 .list{ display:grid; gap:12px;}
 @media(min-width:640px){.list{grid-template-columns:1fr 1fr;}}
 .card{
   display:flex; align-items:center; justify-content:space-between;
   padding:16px; border:1px solid var(--line); border-radius:12px;
   background:var(--card); text-decoration:none; color:inherif;
   transition:transform .12s ease, box-shadow .12s ease;
 }
 .card:hover{ transform:translateY(-2px); box-shadow:0 6px 16px rgba(0,0,0,0.7);}
 .card small{color:#64748b}
 .footer{ margin-top:20px; text-align:center}
 .btn{ display:inline-block; padding:10px 14px; border:1px solid var(--line); border-radius:10px; text-decoration:none}
 .btn.primary{background:var(--brand); color:#fff; border-color:var(--brand)}
</style>
</head>
<body>
  <div class="container">
    <h1>就活管理メニュー</h1>
    <% if("1".equals(request.getParameter("deleted"))) { %>
     <p style="text-align:center; color:#c0392b; font-weight:bold;">
        退会処理が完了しました。登録データを削除しました。
     </p>
    <% } %>
    <p style="text-align:center; color:#555; margin-top:10px;">
         Googleアカウントでログインすると、自分専用の応募一覧を管理できます。
    </p>
    <div>
    <a href="<%= request.getContextPath() %>/google-login"
        style="
          display:inline-block;
          padding:12px 24px;
          background:#4285f4;
          color:white;
          text-decoration:none;
          border-radius:8px;
          font-weight:bold;
        ">
         Googleでログイン
    </a>
    </div>
    <div class="list">
     <!-- 好きなだけ増やせます。group=o を渡すのがポイント -->
      <a class="card" href="app?group=it">
        <div>
         <div style="font-weight:700">IT企業応募</div>
         <small>エンジニア/テスター/社内SEなど</small>
        </div>
        <span>→</span>
      </a>
      
      <a class="card" href="app?group=office">
       <div>
        <div style="font-weight:700">事務職応募</div>
        <small>一般事務/経理/総務など</small>
       </div>
       <span>→</span>
      </a>
      
      <a class="card" href="app?group=parttime">
      <div>
       <div style="font-weight:700">営業職応募</div>
       <small>法人営業/個人営業/ルート営業など</small>
      </div>
      <span>→</span>
      </a>
    </div>
    
    <a class="card" href="app?group=sales_service">
  <div>
    <div style="font-weight:700">販売・接客</div>
    <small>アパレル / 飲食 / 小売など</small>
  </div>
  <span>→</span>
</a>

<a class="card" href="app?group=creative">
  <div>
    <div style="font-weight:700">クリエイティブ</div>
    <small>デザイン / 動画 / Web制作など</small>
  </div>
  <span>→</span>
</a>

<a class="card" href="app?group=physical">
  <div>
    <div style="font-weight:700">フィールドワーク</div>
    <small>工場 / 倉庫 / 引越し / 建設など</small>
  </div>
  <span>→</span>
</a>

<a class="card" href="app?group=medical">
  <div>
    <div style="font-weight:700">医療・福祉</div>
    <small>看護 / 介護 / 医療事務など</small>
  </div>
  <span>→</span>
</a>

<a class="card" href="app?group=manufacturing">
  <div>
    <div style="font-weight:700">製造</div>
    <small>工場／組立／検品／ライン作業など</small>
  </div>
  <span>→</span>
</a>
      
      <a class="card" href="app?group=parttime">
      <div>
       <div style="font-weight:700">アルバイト応募</div>
       <small>短期/時短/シフト制など</small>
      </div>
      <span>→</span>
      </a>
    </div>
    
    
    
    <div class="footer" style="margin-top:24px">
     <a class="btn" href="app?group=all">全件一覧へ</a>
    </div>
  </div>
  
  <div style="text-align:center; margin-top:30px;">
    <a href="<%= request.getContextPath() %>/privacy.jsp">
      プライバシーポリシー
    </a>
    
    <a href="<%= request.getContextPath() %>/terms.jsp">
      利用規約
    </a>
   
  </div>

</body>
</html>