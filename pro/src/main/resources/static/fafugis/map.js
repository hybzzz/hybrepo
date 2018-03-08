var map ;
var point ;
var overlays = [];
var overlaycomplete;
var marker;
var marker_;
var opts = {
    width : 200,     // 信息窗口宽度
    height: 100,     // 信息窗口高度
    title : "详细信息:" , // 信息窗口标题
    content:""//
}
var sContent ="<table border='1' cellspacing='0' style='width: 200px' >" +
    "<tr ><td colspan='2' style='text-align: center;'>详细信息</td></tr> <tr><td colspan='2'><span style='width:100%;'>地址:fafu_add</span><br/><span  style='width:100%;'>经度:fafu_x</span><br/><span style='width:100%;'>纬度:fafu_y</span><br/><span style='width:100%;'>高程:fafu_z</span></td></tr>" +
    "<tr ><td style='text-align: center;width:50%'>查询</td><td style='text-align: center;width:50%'>附近</td></tr> <tr ><td style='text-align: center;width:50%'>路线规划</td><td style='text-align: center;width:50%'>更多</td></tr></table>";

$(function () {
    $("#topbar").html('这是一个标题');
    map = new BMap.Map("allmap");
    point = new BMap.Point(116.331398,39.897445);
    map.centerAndZoom(point,15);
    var optx =  {
        offset:new BMap.Size(10,120)
    }
    map.addControl(new BMap.GeolocationControl(optx));


    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            point = r.point;
            map.panTo(point);
            map.centerAndZoom(point);
            var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,157));
            marker_ = new BMap.Marker(point,{icon:myIcon});  // 创建标注
            map.addOverlay(marker_);               // 将标注添加到地图中
            marker_.setAnimation(BMAP_ANIMATION_BOUNCE);
            var gc = new BMap.Geocoder();
            gc.getLocation(point, function(rs){
                var addComp = rs.addressComponents;
                var addstr = addComp.province + addComp.city +  addComp.district +  addComp.street +  addComp.streetNumber;
                opts.content=addstr;
                // var infoWindow = new BMap.InfoWindow(opts.content, opts);  // 创建信息窗口对象
                var infoWindow = new BMap.InfoWindow(sContent.replace('fafu_x',point.lng).replace('fafu_y',point.lat).replace('fafu_add',addstr).replace("fafu_z",'10米'));  // 创建信息窗口对象
                marker_.addEventListener("click", function(){
                    map.openInfoWindow(infoWindow,point);//开启信息窗口
                });
                map.openInfoWindow(infoWindow,point);
            });
        }
        else {
            $("#modal-container .modal-body").html('定位失败,请检查网络设置');
            $("#btnalert").click();
            // var myCity = new BMap.LocalCity();
            // myCity.get(myFun);
            // alert('failed'+this.getStatus());
        }
    });
    map.addEventListener("click", showInfo);

});
function showInfo(e) {
    x=e.point.lng;   //获取鼠标当前点击的经纬度
    y=e.point.lat;

    if(x != "" && y != ""){
        clearAll();  //清除地图上存在的标注
        var point = new BMap.Point(x,y);
        map.centerAndZoom(point);
        marker = new BMap.Marker(point);  // 创建新的标注
        map.addOverlay(marker);    //将标注添加到地图上
    }else{
        map.centerAndZoom("北京", 12);
    }
    var point_ = new BMap.Point(x,y);  //获取当前地理名称
    var gc = new BMap.Geocoder();
    gc.getLocation(point, function(rs){
        var addComp = rs.addressComponents;
        opts.content=addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
        var infoWindow = new BMap.InfoWindow(opts.content, opts);  // 创建信息窗口对象
        marker.addEventListener("click", function(){
            map.openInfoWindow(infoWindow,point_); //开启信息窗口
        });
    });
}
function clearAll(e){
    map.removeOverlay(marker);
}
function myFun(result){
    var cityName = result.name;
    map.setCenter(cityName);
    // alert("当前定位城市:"+cityName);
}
function clearAll() {
    for(var i = 0; i < overlays.length; i++){
        map.removeOverlay(overlays[i]);
    }
    overlays.length = 0
}
function getmore() {
    $(".BMapLib_Drawing_panel").show();
}
function getNum() {
    $("#modal-container .modal-body").html(overlays.length);
    $("#btnalert").click();
}