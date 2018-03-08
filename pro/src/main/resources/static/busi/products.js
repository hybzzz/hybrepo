var reqData={
    'currentTime':new Date(),
    'currentPage':'1',
    'queryData':{
        'prd_name':''
    }
};
$(function () {
    getData(reqData);
    $("#formsubmit").on("click",update);
    $("#queryName").val(reqData.queryData.prd_name);
    $("#delBtn").on("click",doDel);
    $("#insertBtn").on("click",goInsert);
    $("#search").on("click",doSearch);

})
function doSearch() {
    reqData.queryData.prd_name=$("#queryName").val();
    reqData.currentPage='1';
    console.log(reqData);
    getData(reqData);
}
function uploadPic() {
    var fileName = $("#pic").val();
    if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(fileName)){
        alert("its not a picture ! please check it");
        $("#pic").val("");
        $("#uploadImg").attr("src","");
        return;
    }
    var options = {
        url: "../comm/upload",
        // 请求方式
        type: "post",
        dataType: "json",
        success: function(data, status, xhr) {
            // 图片显示地址
            $("#imgpath").attr('src',data.path);
        }
    };

    $("#jvForm").ajaxSubmit(options);
}
function update() {
    var imgPath = $("#uploadImg").attr("src");
    if(imgPath==""){
        alert("please upload picture of your product");
        return ;
    }
    var rec_id = $("#recId").val();
    var params={
        "prd_desc":$("#description").val(),
        "prd_name":$("#prdname").val(),
        "prd_imgs":imgPath
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
        $.post("../product/delBatch",{'ids':ids.join(",")},function (resp) {
            alert(resp);
            location.reload();
        })
    }
}
function goInsert() {
    location="insertProduct";
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
        strhtml.push('<td><input type="checkbox" name="chk" /></td> <td>',data[i].recId,'</td><td>',data[i].name,'</td><td>',data[i].description,'</td><td><img width="100" height="100"  src="',data[i].imgs,'"/></td><td><a href="toUpdate?id='+data[i].recId+'">update</a></td></tr>')
}
    $("#newsTable tbody").html(strhtml.join(""));
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
    var url='../product/list';
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
