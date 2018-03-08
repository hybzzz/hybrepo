$(function () {
    $.post("getinfo",{'currentTime':new Date()},function (resp) {
        console.log(resp);
        $('#cpyaddr').val(resp.addr);
        $('#cpytel').val(resp.tel);
        $('#cpyemail').val(resp.email);
    });
    $("#formsubmit").on("click",function () {
        var cpyaddr=$('#cpyaddr').val();
        var cpytel=$('#cpytel').val();
        var cpyemail=$('#cpyemail').val();
        var email=document.getElementById('cpyemail');
        var tel=document.getElementById('cpytel');
        if(email.checkValidity()&&tel.checkValidity()){
            $.post("updateinfo",{
                'cpyaddr':cpyaddr,
                'cpytel':cpytel,
                'cpyemail':cpyemail
            },function (resp) {
                if(resp>0){
                    alert('infomation has been saved');
                }
            });
        }else{

        }

    })
})