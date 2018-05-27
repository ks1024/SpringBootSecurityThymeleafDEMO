$(document).ready(function() {
    clickSendButton();
});

function clickSendButton() {
    $('#sendValidate').on('click', function (e) {
        //send ajax here then count down
        var phone = $('#username').val();
        $.ajax({
            method: "POST",
            url: "/send_code?phone=" + phone,
        }).done(function (r) {
            if (r.code == '000100') {
                alert(r.msg);
            } else {
                alert(r.data);
                //disable the click button
                $('#sendValidate').prop('disabled', true);
                countDown(60);
            }
        })
    })

}
function countDown(waittime){
    if (waittime == 0) {
        $('#sendValidate').text("重新获取").prop('disabled',false);
        waittime = 60;
    } else {
        $('#sendValidate').text(waittime+"s");
        waittime--;
        setTimeout(function() {
            countDown(waittime);
        },1000);
    }
}