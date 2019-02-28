<!DOCTYPE html>
<html lang="zh-CN">
<head class="v_scrollbar">
    <meta charset="UTF-8"/>
    <title>Simple Form</title>
    <link href="../css/temp.css" />
</head>
<body>
<div class="container">
    <div class="main">
        <div class="form-table">
            <form class="simple-form">
                <#if mode == "pdf"><#global lineStyle="underline"/></#if>
                <div class="form-group">
                    <span class="content-line ${lineStyle?default("")}" class="project" style="font-size: 12pt">${project}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <br/>
                <br/>
                <div class="form-group">
                    <label for="exampleInputTime">时间: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${time}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group">
                    <label for="exampleInputAddress">地点: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${address}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group">
                    <label for="exampleInputParticipant">参与者: </label>
                </div>
                <div class="form-group suojin">
                    <label for="exampleInputParticipantA">公司: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${participantA}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group suojin">
                    <label for="exampleInputParticipantA">华兴: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${participantB}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group">
                    <label for="exampleInputParticipantA">项目来源: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${projectResource}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group">
                    <label for="exampleInputParticipantA">项目评分:(5 分制)</label>
                </div>
                <div class="form-group suojin">
                    <label for="exampleInputParticipantA">团队: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${scoreTeam}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group suojin">
                    <label for="exampleInputParticipantA">市场: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${scoreMarket}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group suojin">
                    <label for="exampleInputParticipantA">产品: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${scoreProduct}&nbsp;&nbsp;&nbsp;</span>
                </div>
                <div class="form-group suojin">
                    <label for="exampleInputParticipantA">估值: </label>
                    <span class="content-line ${lineStyle?default("")}">&nbsp;&nbsp;&nbsp;${scoreValuation}&nbsp;&nbsp;&nbsp;</span>
                </div>
            </form>
        </div>
        <br/>
        <br/>
    </div>
</div>
<ul class="ul1">
    <!-- 递归  宏定义 -->
    <#macro bpTree children>
        <#if children?? && children?size gt 0>
            <#list children as child>
                <#if child.children?? && child.children?size gt 0>
                    <li class="li1">
                        <span class="span1">${child.mCname}</span>
                        <ul class="ul2">
                            <@bpTree children=child.children />
                        </ul>
                    </li>
                <#else>
                    <li class="li2">
                        ${child.mCname}
                    </li>
                </#if>
            </#list>
        </#if>
    </#macro>
    <!-- 调用宏 生成递归树 -->
    <@bpTree children=treeMenu />
</ul>

</body>
</html>