/**
 * Created by Administrator on 2018/3/14.
 */
if (typeof jQuery !== 'undefined') {
    classifyList()
    menuList()
    function classifyList() {
        $.ajax({
            url: url + '/api/restaurant/classifies',
            type: 'GET', //GET
            timeout: 5000,    //超时时间
            dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend: function (xhr) {

            },
            success: function (data, textStatus, jqXHR) {
                var container1 = $("#lineContainer1")
                var container2 = $("#lineContainer2")
                for (var i = 0; i < data.length; i++) {
                    var classifiesData = data[i];
                    var html = '<a href="javascript:;" class="weui-tabbar__item" onclick="classifyMenu('+classifiesData.id+')">' +
                        '<img src="'+classifiesData.classifyUrl+'" alt="" class="weui-tabbar__icon">' +
                        '<p class="weui-tabbar__label">'+classifiesData.classifyName+'</p>'
                    if(i<4){
                        container1.append(html)
                    }else if(i >4 && i< 9){
                        container2.append(html)
                    }

                }
            },
            error: function (xhr, textStatus) {
                console.log(textStatus)
            },
            complete: function () {
            }
        })
    }

    function menuList() {
        $.ajax({
            url: url + '/api/restaurant/listMenu',
            type: 'GET', //GET
            timeout: 5000,    //超时时间
            dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend: function (xhr) {

            },
            success: function (data, textStatus, jqXHR) {
                var container = $("#menuListContainer")
                container.empty()
                for (var i = 0; i < data.length; i++) {
                    var menuData = data[i];
                    var html = '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">' +
                        '<div class="weui-media-box__hd">' +
                        '<img class="weui-media-box__thumb border-radius5" src="'+menuData.menuPicture+'" alt="">'  +
                        '</div>' +
                        '<div class="weui-media-box__bd">' +
                        '<h4 class="weui-media-box__title">'+menuData.menuName+'</h4>' +
                        '<p class="weui-media-box__desc">'+menuData.menuIntroduce+'</p>' +
                        '</div>' +
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

    function classifyMenu(data) {
        // console.log($(this).attr("id"))
        window.location.href = url + '/api/resource/parkDetail/'+data
    }
}