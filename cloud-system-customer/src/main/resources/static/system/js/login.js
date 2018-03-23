$(document).ready(function() {
	//getCookie();
});
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

//设置cookie
function setCookie() {
    if ($('#on_off').val() == '1') {
        $("input[iscookie='true']").each(function() {
            $.cookie(this.name, $("#"+this.name).val(), "/",24);
            $.cookie("COOKIE_NAME","true", "/",24);
        });
    } else {
        $("input[iscookie='true']").each(function() {
            $.cookie(this.name,null);
            $.cookie("COOKIE_NAME",null);
        });
    }
}

//读取cookie
function getCookie() {
    var COOKIE_NAME=$.cookie("COOKIE_NAME");
    if (COOKIE_NAME !=null) {
        $("input[iscookie='true']").each(function() {
            $($("#"+this.name).val( $.cookie(this.name)));

            if("admin" == $.cookie(this.name)) {
                $("#randCode").focus();
            } else {
                $("#password").val("");
                $("#password").focus();
            }

        });
        $("#on_off").attr("checked", true);
        $("#on_off").val("1");
    }
    else
    {
        $("#on_off").attr("checked", false);
        $("#on_off").val("0");

        $("#randCode").focus();

    }
}

// 重置
$('.resertBtn').click(function() {
    $(":input").each(function() {
        $('#'+this.name).val("");
    });
});

// 点击注册
$('#registrationBtn').click(function() {
    submitForm(1);
});

// 点击注册
$('#loginBtn').click(function() {
    submitForm(0);
});

//注册处理函数
function submitForm(flag) {
    //setCookie();
    var actionUrl;
    var formData = new Object();
    if(flag==1){
        actionUrl=$('#registForm').attr('action');//提交路径
        formData['password'] =$("#registration_pass" ).val();
        formData['userName'] =$("#registration_userName").val();
        formData['email'] =$("#registration_email" ).val();
    }else if(flag==0){
        actionUrl=$('#loginForm').attr('action');//提交路径
        formData['userName'] =$("#userName").val();
        formData['password'] =$("#password" ).val();
        formData['rememberFlag'] =$("#on_off").val();
    }
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        url : actionUrl,// 请求的action路径
        data : formData,
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
            if (data.success) {
               setTimeout("window.location.href='/index'", 1000);
            } else {
               showError(data.msg);
            }
        }
    });
}
$('.userload').click(function() {
	$('.formLogin').animate({
		opacity : 1,
		left : '0'
	}, 300);
	$('.userbox').animate({
		opacity : 0
	}, 200, function() {
		$('.userbox').hide();
	});
});

$('#randCodeImage').click(function(){
    reloadRandCodeImage();
});


//回车登录
$(document).keydown(function(e){
	if(e.keyCode == 13) {
		submit();
	}
});
//表单提交
function submit()
{
	var submit = true;
	$("input[nullmsg]").each(function() {
		if ($("#" + this.name).val() == "") {
			showError($("#" + this.name).attr("nullmsg"), 500);
			jrumble();
			setTimeout('hideTop()', 1000);
			submit = false;
			return false;
		}
	});
	if (submit) {
		hideTop();
		try {
			loading(checking, 1);
		} catch (e) {
			// TODO: handle exception
		}
		setTimeout("unloading()", 1000);
		setTimeout("Login()", 1000);
	}

}
function zhanggm(orgId) {

    alert("zhanggm test in login.js: orgId= " +orgId);
}
//登录处理函数
function Login(orgId) {
	setCookie();
	var actionurl=$('form').attr('action');//提交路径
	var checkurl=$('form').attr('check');//验证路径
	 var formData = new Object();
	var data=$(":input").each(function() {
		 formData[this.name] =$("#"+this.name ).val();
	});
    formData['orgId'] = orgId ? orgId : "";

	formData['langCode']=$("#langCode").val();

	formData['langCode'] = $("#langCode option:selected").val();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : checkurl,// 请求的action路径
		data : formData,
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
        window.location = "/index";
			} else {
					showError(d.msg);
			}
		}
	});
}
//显示错误提示
function showError(str) {
	$('#alertMessage').addClass('error').html(str).stop(true, true).show().animate({
		opacity : 1,
		right : '0'
	}, 500);

}

function showSuccess(str) {
	$('#alertMessage').removeClass('error').html(str).stop(true, true).show().animate({
		opacity : 1,
		right : '0'
	}, 500);
}

function hideTop() {
	$('#alertMessage').animate({
		opacity : 0,
		right : '-20'
	}, 500, function() {
		$(this).hide();
	});
}
//加载信息
function loading(name, overlay) {
	$('body').append('<div id="overlay"></div><div id="preloader">' + name + '..</div>');
	if (overlay == 1) {
		$('#overlay').css('opacity', 0.1).fadeIn(function() {
			$('#preloader').fadeIn();
		});
		return false;
	}
	$('#preloader').fadeIn();
}

function unloading() {
	$('#preloader').fadeOut('fast', function() {
		$('#overlay').fadeOut();
	});
}
// 表单晃动
function jrumble() {
	$('.inner').jrumble({
		x : 4,
		y : 0,
		rotation : 0
	});
	$('.inner').trigger('startRumble');
	setTimeout('$(".inner").trigger("stopRumble")', 500);
}

function setCookie(name,value)
{
var Days = 30;
var exp = new Date();
exp.setTime(exp.getTime() + Days*24*60*60*1000);
document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
