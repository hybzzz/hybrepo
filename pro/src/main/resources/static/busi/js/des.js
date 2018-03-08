
    $("#jiami").click(function () {
        var miyao = $("#miyao").val();
        var mingwen = encodeURI($("#mingwen").val());
        var charset = $("#charset").val();
        var data = {'key':miyao,'data':mingwen,'charset':charset};
        console.log(data);
        $.post("encryptToDES",data,function (resp) {
            $("#miwen").val(resp);
            // alert(2);
        })
    });
    $("#jiemi").click(function () {
        var miyao = $("#miyao").val();
        var miwen = $("#miwen").val();
        $.post("decrypt",{'key':miyao,'data':miwen},function (resp) {
            $("#mingwen").val(resp);
            // alert(2);
        })
    });