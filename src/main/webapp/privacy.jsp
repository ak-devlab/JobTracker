<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>プライバシーポリシー　｜　就活管理トラッカー</title>
<style>
     body{
       font-family: system-ui, sans-serif;
       line-height: 1.8;
       max-width: 900px;
       margin: 40px auto;
       padding: 0 20px;
       color: #333;
     }
     h1{
      text-align: center;
      margin-bottom: 40px;
     }
     h2{
      margin-top: 32px;
      border-left: 5px solid #4f7cff;
      padding-left: 10px;
     }
     ul{
      padding-left: 24px;
     }
     .back{
       margin-top: 40px;
       text-align: center;
     }
     .back a{
       color: #4f7cff;
       text-decoration: none;
       font-weight: bold;
     }
</style>
</head>
<body>

    <h1>プライバシーポリシー</h1>
    
    <p>
     就活応募トラッカー（以下、「本サービス」といいます。）は、
    利用者の個人情報の保護を重要なものと考え、以下の方針に基づき個人情報を取り扱います。
　　　</p>

　　　<h2>1.取得する情報</h2>

<p>本サービスでは、以下の情報を取得する場合があります。</p>

<ul>
   <li>Googleログインにより取得する氏名</li>
   <li>Googleログインにより取得するメールアドレス</li>
   <li>Googleアカウントの識別情報</li>
   <li>Googleアカウントのプロフィール画像</li>
   <li>利用者が入力する応募企業名</li>
   <li>職種</li>
   <li>選考ステータス</li>
   <li>次アクション</li>
   <li>次アクション日時</li>
   <li>メモ</li>
</ul>

<h2>2. 利用目的</h2>

<p>取得した情報は、以下の目的で利用します。</p>

<ul>
  <li>本サービスへのログイン認証のため</li>
  <li>利用者ごとの応募データを管理するため</li>
  <li>応募企業、選考状況、次アクション、期限を表示・編集・削除するため</li>
  <li>サービスの不具合確認および改善のため</li>
  <li>お問い合わせ対応のため</li>
</ul>

<h2>3. 第三者提供</h2>

<p> 本サービスは、法令に基づく場合を除き、利用者の個人情報を本人の同意なく第三者に提供しません。</p>

<h2>4. 外部サービスの利用</h2>

<p>
  本サービスでは、ログイン認証にGoogle OAuthを利用しています。
    Googleアカウントによる認証時には、Googleのプライバシーポリシーが適用されます。
</p>

<p>  また、本サービスはデプロイ環境としてRender、データベースとしてPostgreSQLを利用しています。</p>

<h2>5. 個人情報の管理</h2>

<p>
 本サービスは、取得した情報について、不正アクセス、漏えい、改ざん、紛失等を防ぐため、
    適切な管理に努めます。
</p>

<h2>6. データの削除</h2>

<p>利用者が登録した応募データは、サービス内の削除機能により削除できます。
    アカウント情報や保存データの削除を希望する場合は、運営者までご連絡ください。</p>
  
  <h2>7. Cookie等の利用</h2>
  
  <p>
   本サービスでは、ログイン状態を維持するためにセッション情報を利用する場合があります。
    現時点では、広告配信やアクセス解析を目的としたCookieの利用は行っていません。
  </p>
  
  <h2>8. 免責事項</h2>
  
  <p>本サービスの利用により発生した損害について、運営者は故意または重大な過失がある場合を除き、
    責任を負わないものとします。</p>
    
    <p>また、本サービスは個人開発によるものであり、予告なく内容の変更、停止、終了を行う場合があります。</p>
    
    <h2>9. プライバシーポリシーの変更</h2>
    
    <p>
      本ポリシーの内容は、必要に応じて変更することがあります。
    変更後の内容は、本サービス上に掲載した時点で効力を生じるものとします。
    </p>
    
    <h2>10. お問い合わせ</h2>
    
    <p>本サービスに関するお問い合わせは、以下までお願いいたします。</p>
    
    <p>
      運営者：AK-Devlab<br>
    お問い合わせ先：メールアドレスまたは問い合わせフォームURLを記載
    </p>
    
    <p>制定日：2026年6月30日</p>
    
　　　　<div class="back">
        <a href="<%= request.getContextPath() %>/">トップページへ戻る</a>
     </div>
</body>
</html>