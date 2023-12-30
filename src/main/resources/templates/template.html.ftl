<html>
<head>
<title>技术博客 - OpenRSS</title>
<meta name="viewport" content="width=device-width" />
<style>
HTML {
  margin: 0;
  border-top: 8px solid #08f;
}

  a { color: #06c; }
  a:visited { color: #aaa; }

  body {
    font-family: verdana, monaco, menlo, consolas, "courier new", monospace;
    margin: 12px;
  }

  main {
    margin: 2em auto;
    max-width: 90ch;
  }

  .item {
    margin-bottom: 1ch;
    display: grid;
    align-items: start;
    grid-template-columns: minmax(max-content, 24%) 1fr;
  }

  h2 {
    font-weight: 300;
    font-size: 24px;
    margin-top: 36px;
    margin-bottom: 24px;
  }

  h1 {
    font-weight: 300;
    font-size: 42px;
    margin-top: 30px;
  }

  .link { grid-column: 2;   grid-row: 1 }
  .source { grid-column: 1; grid-row: 1 }

  .source {
    text-align: left;
    padding-right: 12px;
    margin-right:12px;
    background: linear-gradient(180deg,
        rgba(0,0,0,0) calc(50% - 1px),
        rgba(220,220,220,1) calc(50%),
        rgba(0,0,0,0) calc(50% + 1px)
    );
  }
  .source span {
    background-color: #fff;
    font-size: 0.8em;
    text-transform: uppercase;
  }
  .link {
    font-family: Verdana, arial, sans-serif;
  }
  .time { color: #999; padding: 0px 6px; }
  .link A { text-decoration: none; }

  @media only screen  and (max-width: 720px) {
    .item {
      grid-template-columns: 100%;
      margin-bottom: 18px;
    }
    .link {
      grid-column: 1;
      grid-row: 2;
    }
    .source {
      background: none;
      margin-bottom: 2px;
    }
  }
</style>
</head>
<body>

<main>

<h1>${BLOGS_COUNT} 技术博客 <sup style="font-size: 14px"><a href="/ffutop.opml">OPML</a></sup></h1>
<p><em>最后更新时间：${.now?string("yyyy-MM-dd HH:mm +08:00")}</em></p>

<div class="items">
<#assign DATE>"9999-99-99"</#assign>
<#list BLOG_ITEMS as ITEM>
  <#if DATE != ITEM.pubDate>
  <h2>${ITEM.pubDate}</h2>
  <#assign DATE>${ITEM.pubDate}</#assign>
  </#if>
  <div class="item">
    <div class="link"><a href="${ITEM.link}">${ITEM.title}</a></div>
    <div class="source"><span>${ITEM.author}</span></div>
  </div>
</#list>
</div>
  <footer>
    <p><a href="https://github.com/ffutop/openrss">GitHub repo.</a></p>
  </footer>
</main>
</body>
</html>