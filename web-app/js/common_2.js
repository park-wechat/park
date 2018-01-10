(function(){
	if(!window.HC){
		window.HC={
			initMenu:function(){
				var menus=$('.menu_inner>li>a');
				var subMenus_1=$('.menu_child_1>li>a');
				//var subMenus_2=$('.menu_child_2>li>a');				
				var allSubMenus=$('.menu_child_1');
				menus.click(function(e){
					allSubMenus.slideUp();
					menus.removeClass('active');
					subMenus_1.removeClass('active');
					var curA=$(this);
					var curObj=$('.menu_child_1',curA.parent());
					if(curObj.is(":hidden")){
						curObj.slideToggle();
						curA.addClass('active');
					}
				});
				subMenus_1.click(function(e){
					subMenus_1.removeClass('active');
					var curA=$(this);
					var curObj=$('.menu_child_2',curA.parent());	
					curObj.slideToggle();
					curObj.click(function(e){
						$("a",this).removeClass('active');
						var curB=$(e.target);
						curB.addClass('active');
					});
					curA.addClass('active');
				});
			},
			initCosp:function(){
				var cosp=$('.cosp');
				var menus=$('.menu_inner>li>a');
				var subMenus=$('.menu_child_1>li>a');
				var allSubMenus=$('.menu_child_1');
				cosp.click(function(e){
					var thisObj=$(this);
					menus.removeClass('active');
					subMenus.removeClass('active');
					if(thisObj.html()=="折叠全部"){
						thisObj.html("展开全部");
						allSubMenus.slideUp();
					}else{
						thisObj.html("折叠全部");
						allSubMenus.slideDown();						
					}
				});	
			},
			logout:function(){
				if(confirm('确定注销?')){
					window.location.href='../index.html';
				}
			}
		};
	}
})();