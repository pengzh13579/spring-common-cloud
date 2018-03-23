/**
 * Created by Administrator on 17-3-21.
 */

var getWebRootPath = function(){
    var a =window.document.location.href;
    var b= window.document.location.pathname;
    var pos = a.indexOf(b);
    var path = a.substring(0,pos);
    a = a.substring(a.indexOf("/")+2,a.length);
    a = a.substring(a.indexOf("/")+1,a.length);
    var pathName = a.substring(0,a.indexOf("/"));
    if(""==pathName)
        return path;
    else
        return path+"/"+pathName;
}
/**
 * 格式化日期函数
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function layout_center_addTab(title,url){
    var t=$('#layout_center_tabs');
    if(t.tabs('exists',title)){
        t.tabs('select',title);
    }else{
        t.tabs('add',{
            title:title,
            href:getWebRootPath()+url,
            closable:true
        });
    }
};

function layout_center_refreshTab(title){
    $('#layout_center_tabs').tabs('getTab',title).panel('refresh');
}
