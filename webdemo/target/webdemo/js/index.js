$(function () {
    d = new dTree('d');

    // $.getJSON(url,args, function (data) {
    $.post("test/test.do",{'user':'Tonny'}, function (data) {
        console.log(data);
        for(var i = 0; i<data.length;i++){
            console.log(data[i]);
            d.add(data[i].menu_id,data[i].menu_pid,data[i].menu_desc,data[i].menu_url);
        }
        document.write(d);
    });


})