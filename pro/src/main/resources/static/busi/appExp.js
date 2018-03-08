var reqData={
    'currentTime':new Date(),
    'currentPage':'1',
    'queryData':{
        'news_content':'',
        'news_title':''
    }
};
var data;
$(function () {
    getData(reqData);
    $("#formsubmit").on("click",update);
    $("#queryDateStr").val(reqData.queryData.dateStr);
    $("#delBtn").on("click",doDel);
    $("#insertBtn").on("click",goInsert);
    $("#search").on("click",doSearch);
})
function doSearch() {
    reqData.queryData.dateStr=$("#queryDateStr").val();
    reqData.currentPage='1';
    console.log(reqData);
    getData(reqData);
}

function update() {
    var rec_id = $("#recId").val();
    var params={
        "news_title":$("#title").val(),
        "news_desc":$("#description").val(),
        "news_content":$("#content").val(),
        "news_agree":Number($("#agree").val()),
        "news_disagree":Number($("#disagree").val())
    }
    if(rec_id!=""){
        params.rec_id=rec_id;
        $.post('update',params,function (data) {
            alert(data);
            window.history.back(-1);
            location.reload();
        });
    }else{
        $.post('insert',params,function (data) {
            alert(data);
            window.history.back(-1);
            location.reload();
        });
    }

}
function doDel() {
    var chks = $("input[name='chk']:checked").parent().next();
    if(chks.length<=0){
        return;
    }
    if(confirm("确认删除"+chks.length+"条数据吗?删除之后不可恢复.")){
        var ids=[];
        for (var i =0;i<chks.length;i++){
            ids.push(chks[i].innerHTML);
        }
        $.post("../hzl/delBatch",{'ids':ids.join(",")},function (resp) {
            alert(resp);
            location.reload();
        })
    }
}
function goInsert() {
    location="insertNews";
}

function initTable(data) {
    var strhtml=[];
    for(var i =0; i<data.length;i++){
        var j=i%5;
        switch (j) {
            case 0:
                strhtml.push('<tr>');
                break;
            case 1:
                strhtml.push('<tr class="active">');
                break;
            case 2:
                strhtml.push('<tr class="success">');
                break;
            case 3:
                strhtml.push('<tr class="warning">');
                break;
            case 4:
                strhtml.push('<tr class="danger">');
                break;
        }
        var deviceInfo=JSON.parse(data[i].deviceinfo);
        var contentStr='model:'+deviceInfo.model+'\n androidVersionCode:'+deviceInfo.androidVersionCode+'\n cpuAbi:'+deviceInfo.cpuAbi+'\n  versionCode:'+deviceInfo.versionCode+'\n versionName:'+deviceInfo.versionName+'\n androidSDKInt:'+deviceInfo.androidSDKInt;
        var btnStr1='<button type="button" class="btn btn-default" title="设备信息"  data-container="body" data-toggle="popover" data-placement="top" data-content="'+contentStr+'">详细</button>';
        var btnStr2='<button type="button" class="btn btn-default" title="异常信息"  onclick="geterror('+i+')">详细</button>';
        strhtml.push('<td><input type="checkbox" name="chk" /></td> <td>',data[i].recId,'</td><td>',btnStr1,'</td><td>',btnStr2,'</td><td>'+data[i].dateStr+'</td></tr>')
}
    $("#newsTable tbody").html(strhtml.join(""));
    $("[data-toggle='popover']").popover();

}
function geterror(i){
    $("#errorcontent").text(data[i].errorcontent);
}
function initPage(page) {
    var pageHtml=[];
    pageHtml.push('<li><a href="javascript:changePage(1)">首页</a></li>');
    if(page.pageIndex!=1){
        pageHtml.push('<li><a href="javascript:changePage(',page.pageIndex-1,')">上一页</a></li>');
    }
    if(page.totalPage-page.pageIndex<5) {
        var fromIndex=page.pageIndex;
        if(page.totalPage>4){
            fromIndex= page.totalPage-4;
        }
        for (var i = fromIndex; i <= page.totalPage; i++) {
            if(i == page.pageIndex){
                pageHtml.push('<li class="active">');
            }else{
                pageHtml.push('<li >');
            }
            pageHtml.push('<a href="javascript:changePage(', i, ')">', i, '</a></li>');
        }
    }else{
        for (var i = page.pageIndex; i < page.pageIndex+5; i++) {
            if(i == page.pageIndex){
                pageHtml.push('<li class="active">');
            }else{
                pageHtml.push('<li >');
            }
            pageHtml.push('<a href="javascript:changePage(', i, ')">', i, '</a></li>');
        }
    }
    if(page.pageIndex!=page.totalPage){

        pageHtml.push('<li><a href="javascript:changePage(',page.pageIndex+1,')">下一页</a></li>');
    }
    pageHtml.push('<li><a href="javascript:changePage(',page.totalPage,')">尾页</a></li>');
    $("#pageContent").html(pageHtml.join(""));
}
function getData(reqParams) {
    var url='../hzl/list';
    $.post(url,reqParams,function (result) {
        data=result['list'];
        var page = result['page'];
        initPage(page);
        console.log(result['page']);
        initTable(data);
    })
}
function changePage(index) {
    reqData.currentPage=index;
    console.log(reqData);
    getData(reqData);
}
function docheck() {
    var chks = $("input[name='chk']");
    var flag;
    if ($("input[name='chkAll']")[0].checked==true){
        flag=true;
    }else{
        flag=false;
    }
    for(var i=0;i<chks.length;i++){
        chks[i].checked=flag;
    }
}
