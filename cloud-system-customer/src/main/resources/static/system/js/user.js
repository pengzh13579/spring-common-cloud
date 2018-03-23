jQuery.validator.addMethod("isTel", function (value, element) {
    var tel = /^0\d{2,3}-?\d{7,8}$/; //电话号码格式010-12345678
    var regex = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0|1|3]|[5-8]))|(18[0-9]))\d{8}$/
    return this.optional(element) || (tel.test(value)) || (regex.test(value));
}, "请正确填写您的电话号码");
jQuery.validator.addMethod("isEmail", function (value, element) {
    var emial = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    return this.optional(element) || (emial.test(value));
}, "请正确填写您的邮箱");
$(function () {
    loadUser();
    initCombobox_user();

    $("#userForm").validate({
        rules: {
            userCd: {
                required: true,
                maxlength: 10,
                minlength: 3
            },
            userName: {
                required: true,
                maxlength: 10,
                minlength: 2
            },
            age: {
                required: true,
                digits: true,
                maxlength: 2,
                min: 18
            },
            tel: {
                required: true,
                isTel: true
            },
            eml: {
                required: true,
                isEmail: true
            }

        },
        messages: {

            userCd: {
                required: "请输入用户名",
                maxlength: "长度不能超过10",
                minlength: "长度不能少于3"
            },
            userName: {
                required: "请输入真实姓名",
                maxlength: "长度不能超过10",
                minlength: "长度不能少于2"
            },
            age: {
                required: "请输入年龄",
                digits: "请输入正确年龄",
                maxlength: "不能超过三位数字",
                min: "不能小于18岁"
            },
            tel: {
                required: "请输入电话",
                isTel: "请正确填写您的电话号码"
            },
            eml: {
                required: "请输入电子邮箱",
                isEmail: "邮箱格式不正确"
            }
        }
    });

});
var selectData = null;
function initCombobox_user() {
    var url = getWebRootPath() + "/userController/getJob";
    $.getJSON(url, function (json) {
        $('#job').combobox({
            data: json.rows,
            editable: false,
            valueField: 'job',
            textField: 'jobDesc'
        });
    });

    var url_role = getWebRootPath() + "/roleController/getRoles";

    $.getJSON(url_role, function (json) {
        $('#role_combo').combobox({
            data: json.rows,
            editable: false,
            valueField: 'id',
            textField: 'roleName'
        });
    });
}
function loadUser() {
    $('#userDg').datagrid({
        url: getWebRootPath() + '/userController/loadUserList',//加载的URL
        title: "用户管理",
        idField: 'id',
        loadMsg: 'please wait',
        fit: true,
        toolbar: '#user_tool',
        singleSelect: true,
        rownumbers: true,
        pagination: true
    });
}
function lockUser() {
    selectData = $("#userDg").datagrid("getSelected");
    if (selectData == null) {
        $.messager.alert("系统提示", "请选择要锁定/解锁的用户", "info");
        return;
    }
    var id = selectData.id;
    $.post(getWebRootPath() + '/userController/delete', {
        id: id
    }, function (date) {
        if (date.message == "成功锁定") {
            $.messager.alert("系统提示", date.message, "info");
            $('#userDg').datagrid('reload');
            $('#userDg').datagrid('clearSelections');
        } else {
            $.messager.alert("系统提示", date.message, "info");
            $('#userDg').datagrid('reload');
            $('#userDg').datagrid('clearSelections');
        }
    });
}

/**
 * 构建form表单，用于向后台提交url
 * @param strUrl
 * @constructor
 */
function DownLoad(strUrl) {
    var form = $("<form>");   //定义一个form表单
    form.attr('style', 'display:none');   //在form表单中添加查询参数
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', strUrl);
    $('body').append(form);  //将表单放置在web中
    form.submit();
}

function excelFileDownload() {
    DownLoad(getWebRootPath() + '/userController/userExcelDownload')
}
function excelFileUpload() {
    $("#user_upload_dialog").dialog('open').dialog('setTitle', '用户添加');
    $("#user_upload_dialog").form('clear');
}

function addUser() {
    initCombobox_user();
    $("#userForm").removeClass("error");
    $("#userForm").data('validator').resetForm();
    $("#user_dialog").dialog('open').dialog('setTitle', '用户添加');
    $('#userCd').attr("disabled", false);
    $("#user_dialog").form('clear');
    $("#role_combo").combobox('reload');
}
function updateUser() {
    initCombobox_user();
    $("#role_combo").combobox('reload');
    $('#userForm table tr td').each(function () {
        $(this).children('input').removeClass("error");
    });
    $("#userForm").data('validator').resetForm();
    selectData = $("#userDg").datagrid("getSelected");
    if (selectData == null) {
        $.messager.alert("系统提示", "请选择要修改的用户", "info");
        return;
    }
    $("#user_dialog").dialog('open').dialog('setTitle', '用户编辑');
    $("#user_dialog").form('clear');
    $("#id").val(selectData.id);
    $('#id').attr("disabled", "disabled");
    $("#userCd").val(selectData.userCd);
    $('#userCd').attr("disabled", "disabled");
    $("#userName").val(selectData.userName);
    $('#age').val(selectData.age);
    $('#user_tel').val(selectData.tel);
    $("#user_address").val(selectData.address);
    $("#eml").val(selectData.eml);
    $('#job').combobox('setValue', selectData.job);
    $('#role_combo').combobox('setValue', selectData.roleId);
//    $("#job").val(selectData.job);
}
function saveUser() {
    if ($("#userForm").valid()) {


        var urlPath = "";
        var userCD = $("#userCd").val();
        var userName = $("#userName").val();
        var age = $("#age").val();
        if (age == undefined || age == null || age == "") {
            age = undefined;
        }
        var tel = $("#user_tel").val();
        var address = $("#user_address").val();
        if (address == undefined || address == null || address == "") {
            address = undefined;
        }
        var email = $("#eml").val();
        if (email == undefined || email == null || email == "") {
            email = undefined;
        }
        var job = $("#job").val();
        if (job == null || job == '') {
            $.messager.alert("系统提示", "职位不能为空", "info");
            return;
        }
        var roleId = $("#role_combo").val();
        if (roleId == null || roleId == '') {
            $.messager.alert("系统提示", "角色不能为空", "info");
            return;
        }
        $("#btn-saveUser").linkbutton("disable");
        var userId = $("#id").val();
        if (userId == undefined || userId == null || userId == "") {
            urlPath = getWebRootPath() + '/userController/save';
            userId = undefined;
        } else {
            urlPath = getWebRootPath() + '/userController/edit';
            $("#id").val(undefined);
        }

        var formData = {
            id: userId,
            userCd: userCD,
            age: age,
            userName: userName,
            address: address,
            tel: tel,
            eml: email,
            job: job,
            roleId: roleId
        };
        $.ajax({
            type: "POST",
            url: urlPath,
            data: formData,
            dataType: "json",
            success: function (obj) {
                debugger;
                if (obj.flag == "0") {
                    $("#btn-saveUser").linkbutton("enable");
                    $('#user_dialog').dialog('close');
                    $.messager.alert("系统提示", obj.message, "info");
                    $('#userDg').datagrid('reload');
                    $('#userDg').datagrid('clearSelections');
                } else {
                    $("#btn-saveUser").linkbutton("enable");
                    $.messager.alert("系统提示", obj.message, "info");
                }
            }
        });
    }
}
function restUserPwd() {
    selectData = $("#userDg").datagrid("getSelected");
    if (selectData == null) {
        $.messager.alert("系统提示", "请选择要重置密码的用户", "info");
        return;
    }
    var id = selectData.id;
    $.post(getWebRootPath() + '/userController/restUserPwd', {
        id: id
    }, function (date) {
        $.messager.alert("系统提示", date.message, "info");
        $('#userDg').datagrid('reload');
        $('#userDg').datagrid('clearSelections');
    });
}
function userReset(){
    $("#search").searchbox('reset');
}
function selectUser() {
    var searchName = $("#search").val();
    if (searchName == null || searchName == '') {
        $('#userDg').datagrid({
            url: getWebRootPath() + '/userController/loadUserList',//加载的URL
            title: "用户管理",
            idField: 'id',
            loadMsg: 'please wait',
            fit: true,
            toolbar: '#user_tool',
            singleSelect: true,
            rownumbers: true,
            pagination: true
        });
    } else {
        $('#userDg').datagrid({
            url: getWebRootPath() + '/userController/selectUser?userName=' + encodeURI(encodeURI(searchName)),//加载的URL
            title: "用户管理",
            idField: 'id',
            loadMsg: 'please wait',
            fit: true,
            toolbar: '#user_tool',
            singleSelect: true,
            rownumbers: true,
            pagination: true
        });
    }
}

function uploadFile(){
    var formData = new FormData(document.getElementById("userUploadForm"));//表单id
    $.ajax({
        type: "POST",
        url:getWebRootPath() +'/userController/userExcelUpload',
        data: formData,
        contentType: false,
        processData: false,
        success: function (obj) {
            if(obj.flag=="0"){
                $.messager.alert("系统提示", obj.message, "info");
                $('#user_upload_dialog').dialog('close');
                $('#user_dialog').datagrid('reload');
            }else{
                $.messager.alert("系统提示", obj.message, "info");
            }
        }
    });
}