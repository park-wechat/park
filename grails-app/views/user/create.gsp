<!DOCTYPE html>
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'jquery-ui.css')}" />
        <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.4.4.js' +
                '')}"></script>
        <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.core.js')}"></script>
        <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.widget.js')}"></script>
        <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.position.js')}"></script>
        <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.autocomplete.js')}"></script>

        <script>
            //缓存
            var cache = {};
            $(function() {

                $( "#store1" ).autocomplete({
                    source: function(request, response ) {
                        var term = request.term;
                        if ( term in cache ) {
                            response( $.map( cache[ term ], function( item ) {
                                return {
                                    label:item.partner
                                }
                            }));
                            return;
                        }else{
                            $.ajax({
                                url: "/zdsauth/user/autoUsers",
                                dataType: "json",
                                data:{
                                    searchItem: term
                                },
                                success: function( data ) {
                                    cache[term] = data;
                                    response( $.map( data, function( item ) {
                                        return {
                                            label:item.partner
                                        }
                                    }));
                                }
                            });
                        }
                    },
                    minLength: 1,
                    select: function( event, ui ) {
                        $("#store1").val(ui.item.partner);
                    }
                });
            });
        </script>
	</head>
	<body>
            <div class="title">用户新增界面</div>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
			<g:form url="[resource:userInstance, action:'save']" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="创建" />
                    <g:link action="index" class="button">返回</g:link>
				</fieldset>
			</g:form>
	</body>
</html>
