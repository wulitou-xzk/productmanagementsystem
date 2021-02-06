// 创建cookie，添加了expires属性，则在关闭浏览器后，cookie依旧存在
function setCookie(uId,detailId,userLevel){
    var d = new Date();
    d.setTime(d.getTime() + (24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = uId + "=" + detailId + "=" + userLevel + ";" + expires + ";path=/";
}

// 获取cookie
var loginUid;
var detailId;
var userLevel;
function getCookie() {
    var cook = document.cookie;
    if(cook == null || cook == '' || cook == undefined){
        window.open('/','_top');
    }
    var data = cook.split(";");
    loginUid = data[1].split("=")[0];
    detailId = data[1].split("=")[1];
    userLevel = data[1].split("=")[2];
}

// 防止用户不小心关闭浏览器后需要重新登录
function checkCookie(){
    var cook = document.cookie;
    if(cook != null && cook != '' && cook != undefined){
        return 1;
    }
}

// 删除cookie
function delCookie(){
    var d = new Date();
    d.setTime(d.getTime() + (-1*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = loginUid + "=" + detailId + "=" + userLevel + ";" + expires + ";path=/";
}

// 使用ajax校验用户是否依旧处于登录状态
function isLogin(){
    $.ajax({
        url: '/logindetail/selectExitLoginById',
        data:{ detailId: detailId },
        cache: false,
        success: function(text){
            if(text === 'logout'){
                mini.alert('你已被强制下线','提示',function(){
                    delCookie();
                    window.open("/","_top");
                });
            }
        }
    });
}

// 防止员工登录后出现越权问题
function turnPage(loginUid, selfStr, otherOnetStr, otherTwoStr){
    if(loginUid.indexOf(selfStr) < 0){
        if (loginUid.indexOf(otherOnetStr) >= 0) {
            window.location.href = "/pages/" + otherOnetStr + ".html";
        }
        if (loginUid.indexOf(otherTwoStr) >= 0) {
            window.location.href = "/pages/" + otherTwoStr + ".html";
        }
    }
}

// 针对系统中的日期格式化
function dateFormat(date, type){
    // 解决不同浏览器的兼容性问题
    //var formatDate = date.replace(/-/g, '/').replace('T', ' ').replace('.000+0000', '');
    var newDate = new Date(date);
    var result = '';
    var year = newDate.getFullYear();
    var month = (newDate.getMonth()) < 9 ? '0' + (newDate.getMonth() + 1) : newDate.getMonth() + 1;
    var day = newDate.getDate() < 10 ? '0' + newDate.getDate() : newDate.getDate();
    var hour = newDate.getHours() < 10 ? '0' + newDate.getHours() : newDate.getHours();
    var minute = newDate.getMinutes() < 10 ? '0' + newDate.getMinutes() : newDate.getMinutes();
    var second = newDate.getSeconds() < 10 ? '0' + newDate.getSeconds() : newDate.getSeconds();
    // 精确到秒
    if(type === 1) {
        result = year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
    }
    // 精确到日
    if(type === 0){
        result = year + '-' + month + '-' + day;
    }
    return result;
}
