/**
 * Created by QIJUNYU on 17-3-30.
 */
function editPassword() {
    $("#pass_manager").dialog('open').dialog('setTitle', '密码修改');
    $("#news_manager").form('clear');
}
function savePwd(){
    var oldPwd = $("#old_password").val();
    var newPwd = $("#new_password").val();
    var newConfirm = $("#new_password_confirm").val();
    if (oldPwd==undefined || oldPwd=='') {
        $.messager.alert("系统提示","请输入旧密码", "info");
        return;
    }
    if (newPwd==undefined || newPwd=='') {
        $.messager.alert("系统提示","请输入新密码", "info");
        return;
    }
    if (newConfirm==undefined || newConfirm=='') {
        $.messager.alert("系统提示","请再次输入新密码", "info");
        return;
    }
    if (newPwd == oldPwd) {
        $.messager.alert("系统提示","新密码与旧密码相同，请重新输入", "info");
        return;
    }
    if (newPwd != newConfirm) {
        $.messager.alert("系统提示","两次密码不同，请重新输入", "info");
        return;
    }
    $.ajax({
        url: getWebRootPath() + "/loginController/editPassword",
        method: "post",
        dataType: "json",
        data: {
            "old_password": oldPwd,
            "new_password": newPwd
        },
        success: function (data) {
            debugger;
            if(data.flag =="1"){
//                $("#btn-saveNews").linkbutton("enable");
                $('#pass_manager').dialog('close');
                $.messager.alert("系统提示",data.message,"info");
                $('#mainPanle').datagrid('reload');
//                $('#newsTab').datagrid('clearSelections');
            }else{
                $.messager.alert("系统提示",data.message,"info");
            }
        }
    });
}

//function logout(){
//    window.location.href= getWebRootPath()+"/loginController/logout";
//}