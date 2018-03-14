/**
 * Created by Administrator on 2018/3/14.
 */
if (typeof jQuery !== 'undefined') {
    parkList()
    function parkList() {
        $.ajax({
            url: url + '/api/park/parkList',
            type: 'GET', //GET
            timeout: 5000,    //超时时间
            dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend: function (xhr) {

            },
            success: function (data, textStatus, jqXHR) {
                var container = $("#listContainer")
                for (var i = 0; i < data.length; i++) {
                    var parkData = data[i];
                    var html = '<a href="javascript:void(0);" id="' + parkData.id + '" class="weui-media-box weui-media-box_appmsg" onclick="parkDetail()"> ' +
                        '<div class="weui-media-box__hd">' +
                        '<img class="weui-media-box__thumb border-radius5"  id="park" src="51lvcn1.jpg" alt=""> ' +
                        '</div>' +
                        '<div class="weui-media-box__bd" id="' + parkData.id + '">' +
                        '<h4 class="weui-media-box__title" id="parkName">' + parkData.parkName + '</h4>' +
                        '</input>' +
                        '</a>'
                    container.append(html)
                }
            },
            error: function (xhr, textStatus) {
                console.log(textStatus)
            },
            complete: function () {
            }
        })
    }

    function parkDetail(event) {
        console.log($(this).attr("id"))
        window.location.reload("jingDianDetail.html")
    }
}