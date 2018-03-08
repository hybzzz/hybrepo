var reqData={
    'currentTime':new Date(),
    'currentPage':'1',
    'keyword':''
};
$(function () {
   getData(reqData);
    $("#searchInput").val(reqData.keyword);
    $("#delBtn").on("click",doDel);
    $("#doneBtn").on("click",doDone);
    $("#search").on("click",doSearcchAnyWay);
})
function doSearcchAnyWay() {
    var keyword=$("#searchInput").val();
    reqData.keyword=keyword;
    reqData.currentPage='1';
    getData(reqData);
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
        $.post("../lnquiry/delBatch",{'ids':ids.join(",")},function (resp) {
            alert(resp);
            location.reload();
        })
    }
}
function doDone() {
    var chks = $("input[name='chk']:checked").parent().next();
    if(chks.length>0){
        var ids=[];
        for (var i =0;i<chks.length;i++){
            ids.push(chks[i].innerHTML);
        }
        $.post("../lnquiry/doneBatch",{'ids':ids.join(",")},function (resp) {
            alert(resp);
            location.reload();
        })
    }else{
        return;
    }
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
        strhtml.push('<td><input type="checkbox" name="chk" /></td> <td>',data[i].recId,'</td><td>',data[i].lnquiryPrd,'</td><td>',data[i].contacter,'</td><td>',data[i].contactPhone,'</td><td>',data[i].contactEmail,'</td><td>',data[i].lnquiryExtra,'</td><td>',data[i].lnquiryStatus,'</td></tr>')
    }
    $("#lnquiryTable tbody").html(strhtml.join(""));
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
    var url='../lnquiry/list';
    $.post(url,reqParams,function (result) {
        var data=result['list'];
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
