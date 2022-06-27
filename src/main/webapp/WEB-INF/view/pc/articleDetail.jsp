<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${beanData.title}-详情</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/static/layui/style/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/static/layui/style/template.css" media="all">
    <script src="${ctx}/static/layui/layui.js"></script>
    <!--引用百度地图API-->
    <style type="text/css">
        html, body {
            margin: 0;
            padding: 0;
        }

        .iw_poi_title {
            color: #CC5522;
            font-size: 14px;
            font-weight: bold;
            overflow: hidden;
            padding-right: 13px;
            white-space: nowrap
        }

        .iw_poi_content {
            font: 12px arial, sans-serif;
            overflow: visible;
            padding-top: 4px;
            white-space: -moz-pre-wrap;
            word-wrap: break-word
        }
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
</head>
<body>
<!--导航条 start-->
<jsp:include page="head.jsp"/>

<!--导航条 end-->

<!--轮播图 start-->
<jsp:include page="banner.jsp"/>
<!--轮播图 end-->



<div class="layui-container layadmin-cmdlist-fluid">
    <div>
        <h1 style="margin: 20px; text-align: center;">${beanData.title}</h1>
    </div>
    <div class="layui-elem-quote" style="margin: 20px;padding-left: 200px;">
        <h3>所属分类：${beanData.type}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作者： ${beanData.author}
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间:<fmt:formatDate value="${beanData.createtime}"
                                                               pattern="yyyy-MM-dd HH:mm:ss"/></h3>
    </div>
    <div id="showContent">
        ${beanData.content}
    </div>
    <style>
        #showContent img {
            max-width: 100%;
        }
    </style>


    <div class="layui-elem-quote" style="margin: 20px;padding-left: 200px;text-align: center;">
        <h3>帖子评论显示</h3>
    </div>
    <div class="layui-container layadmin-cmdlist-fluid">
        <div class="layui-row layui-col-space30">

            <div class="layui-collapse">

                <%--内容区域--%>
                <div class="layui-card layadmin-header">
                    <div class="layui-breadcrumb" lay-filter="breadcrumb">
                        <a lay-href="">主页</a>
                        <a><cite>页面</cite></a>
                        <a><cite>留言板</cite></a>
                    </div>
                </div>

                <div class="layui-fluid layadmin-message-fluid">
                    <form action="${ctx}/pc/liuyanSubmit" method="post">
                        <div class="layui-row">
                            <div class="layui-col-md12">
                                <div class="layui-form">
                                    <div class="layui-form-item layui-form-text">
                                        <div class="layui-input-block">
                                            <input name="intro" type="hidden" value="${beanData.id}" >
                                            <input name="fid" type="hidden" value="0" id="fidInput">
                                            <input name="level" type="hidden" value="0" id="levelInput">
                                            <input name="score" type="hidden" value="0" id="scoreInput">
                                            <input name="picurl" type="hidden"
                                                   value="${sessionScope.loginUserinfo.picurl}">
                                            <input name="username" type="hidden"
                                                   value="${sessionScope.loginUserinfo.name}">
                                            <div id="replyDiv"
                                                 style="display:none;font-size: 16px;margin-bottom: 10px;">
                                                回复内容:<span
                                                    id="showReplyInfo"
                                                    style="font-weight: 700;color: red;">41564564654</span></div>
                                            <textarea name="content" id="contentInput" placeholder="请输入内容"
                                                      class="layui-textarea"></textarea>
                                        </div>
                                    </div>

                                    <div class="layui-form-item" style="overflow: hidden;">
                                        <div class="layui-input-block layui-input-right">
                                            <c:if test="${sessionScope.loginUserinfo==null}">
                                                <a class="layui-btn" href="${ctx}/pc/login">登录以后才能发表留言</a>
                                            </c:if>
                                            <c:if test="${sessionScope.loginUserinfo!=null}">
                                                <button type="submit" class="layui-btn">发表留言</button>
                                            </c:if>
                                        </div>
                                        <div class="layadmin-messag-icon">
                                            <a href="javascript:;"><i
                                                    class="layui-icon layui-icon-face-smile-b"></i></a>
                                            <a href="javascript:;"><i class="layui-icon layui-icon-picture"></i></a>
                                            <a href="javascript:;"><i class="layui-icon layui-icon-link"></i></a>
                                            <a href="javascript:;"><span id="xingxingScore"></span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-md12 layadmin-homepage-list-imgtxt message-content">
                                <c:forEach items="${liuyanList}" var="item" varStatus="ss">
                                    <c:set var="leftPaddingVar" value="${item.level*20}"/>
                                    <div class="media-body"
                                         style="margin-left: ${leftPaddingVar}px;border-left: 8px gainsboro solid;padding-left: 20px;border-radius: 10px;">
                                        <a href="javascript:;" class="media-left" style="float: left;">
                                                <%--<img src="${ctx}/static/img/headicon.jpg" height="46px" width="46px">--%>
                                            <img src="${item.picurl}" height="46px" width="46px">
                                        </a>
                                        <div class="pad-btm">
                                            <p class="fontColor"><a href="javascript:;">${item.username}</a></p>
                                            <p class="min-font">
                                          <span class="layui-breadcrumb" lay-separator="-">
                                            <a href="javascript:;" class="layui-icon layui-icon-cellphone"></a>
                                            <a href="javascript:;"> <span id="xingxing${item.id}"></span></a>
                                            <a href="javascript:;">
                                                <span class="layui-icon layui-icon-date"></span>
                                                <fmt:formatDate value="${item.createtime}"
                                                                pattern="yyyy-MM-dd HH:mm:ss"/></a>
                                              <script>
                                                  //只读
                                                  layui.use(['rate'], function () {
                                                      var rate = layui.rate;
                                                      rate.render({
                                                          elem: '#xingxing${item.id}'
                                                          , value: ${item.score}
                                                          , readonly: true
                                                      });
                                                  });
                                              </script>
                                          </span>
                                            </p>
                                        </div>
                                        <p class="message-text">${item.content}
                                            <c:set var="testStr" value="${item.content}"/>
                                            <c:choose>
                                                <c:when test="${fn:length(testStr) > 10}">
                                                    <c:set var="showStr" value="${fn:substring(testStr, 0, 10)}......"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="showStr" value="${testStr}"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <span onclick='replyMsg("${item.id}","${item.level+1}","${showStr}")'
                                                  class="layui-btn"
                                                  style=" height: 22px; line-height: 22px; padding: 0 8px; font-size: 12px;">回复</span>
                                        </p>
                                    </div>
                                </c:forEach>
                                <div class="layui-row message-content-btn">
                                    <a href="javascript:;" class="layui-btn">没有更多了</a>
                                </div>
                            </div>

                        </div>
                    </form>
                </div>
            </div>

            <script>

                var $
                layui.use(['layer', 'form', 'rate'], function () {
                    var rate = layui.rate;
                    $ = layui.$
                        , admin = layui.admin
                        , form = layui.form;
                    //显示文字
                    rate.render({
                        elem: '#xingxingScore'
                        , value: 2 //初始值
                        , text: true //开启文本
                        , setText: function (value) {
                            var arrs = {
                                '1': '1星'
                                , '2': '2星'
                                , '3': '3星'
                                , '4': '4星'
                                , '5': '5星'
                            };
                            this.span.text(arrs[value] || (value + "星"));
                            $("#scoreInput").val(value);
                        }
                    });

                });


                function replyMsg(fid, level, content) {
                    $("#fidInput").val(fid);
                    $("#levelInput").val(level);
                    $("#showReplyInfo").html(content);
                    $("#contentInput").focus();
                    $("#replyDiv").show();
                }
            </script>


        </div>

        <div class="layui-col-md12 layui-col-sm12">
            <div id="demo0"></div>
        </div>
    </div>

</div>


<jsp:include page="foot.jsp"/>

<script>
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function () {
        var element = layui.element;
    });
</script>

</body>
</html>