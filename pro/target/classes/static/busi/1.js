WeixinJSBridge.invoke('getBrandWCPayRequest', {
    "appId": ("#appid").val(), "timeStamp": ("#timestamp").val(),
    "nonceStr": ("#nonceStr").val(), "package": ("#packageValue").val(),
    "signType": "MD5","paySign": $("#paySign").val()
}, function (res) {
    WeixinJSBridge.log(res.err_msg);
    if (res.err_msg == "get_brand_wcpay_request:ok") {
        alert("支付成功");
    } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
        alert("取消支付！")

    } else {
        alert("支付失败！")
    }
});
