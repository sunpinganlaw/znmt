function setCookie(name, value) // cookies设置
{
  var argv = setCookie.arguments;
  var argc = setCookie.arguments.length;
  var expires = (argc > 2) ? argv[2] : null;
  if (expires != null) {
    var LargeExpDate = new Date();
    LargeExpDate.setTime(LargeExpDate.getTime() + (expires * 1000 * 3600 * 24));
  }
  document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + LargeExpDate.toGMTString()));
}

function getCookie(Name) // cookies读取
{
  var search = Name + "="
  if (document.cookie.length > 0) {
    offset = document.cookie.indexOf(search)
    if (offset != -1) {
      offset += search.length
      end = document.cookie.indexOf(";",offset)
      if (end == -1) end = document.cookie.length
      return unescape(document.cookie.substring(offset,end))
    }
    else return ""
  }
}

/*
 * var $jq=jQuery.noConflict(); $jq(document).ready(function(){ $jq(".td_menu").hover( function(){ // 显示二级菜单 // $jq(this).children(".ul_menu").show();
 * if($jq(this).children("div")[0].className=="menu"){ // $jq(this).children("ul").slideDown(); $jq(this).children("ul").show();
 * $jq(this).children("div").removeClass("menu"); $jq(this).children("div").addClass("menu1"); }else{ $jq(this).children("ul").slideUp();
 * $jq(this).children("div").removeClass("menu1"); $jq(this).children("div").addClass("menu"); } }, function(){ // 隐藏二级菜单 //
 * $jq(this).children(".ul_menu").hide(); $jq("div.div_menu").hide(); $jq(this).children("ul").hide(); $jq(this).children("div").removeClass("menu1");
 * $jq(this).children("div").addClass("menu"); } ); $jq("ul.ul_menu>li>span").hover( function(){ // 显示三级菜单 var num = $jq(this).parent().offset().top;
 * num = num - 134; // var num=$jq(this).parent().index(); // num=num*35; var html=$jq(this).parents(".td_menu").html();
 * if(html.indexOf("menu2.gif")!=-1){ $jq(this).next("div.div_menu").css({"width":"632px"}); }
 * $jq(this).next("div.div_menu").children(".menu_jiantou").css({"margin":num+"px 0 0 -27px"}); $jq("div.div_menu").hide();
 * $jq(this).next("div.div_menu").show(); }, function(){ // 隐藏三级菜单 // $jq("div.div_menu").hide(); // $jq("#menu_body").slideUp(); } );
 */

/**
 * 设置消防系统导航菜单
 */
var nav = {};
nav.setNavTitle = function(menu1, _this) {
  if (undefined == $jq(_this).attr("onClick")) return;
  out.print($jq('.area_1'))
  $jq("#menu_3").hide();
  $jq("#menu_jiantou").hide();
  $jq(".area_1").hide();
  //设定导航标题
  var menu2 = $jq(_this).parents("div.sanji").children("label").html();
  var me = $jq(_this).outerHTML();
  var navTille = ">> ";
  if (menu2 != me) {
    navTille = me
//  navTille += menu1 + " >> " + menu2 +" >> " + me;
  }
  else {
    navTille += me;
  }

  $jq("#navTitle").html(navTille);
  G_Pre_NavTitle = $jq("#navTitle").html();

};

/**
 * 扩展JQuery
 * @return {}
 */
jQuery.fn.outerHTML = function() {
  return $jq(this).clone().wrap('<div></div>').parent().html();
};
var $jq = jQuery.noConflict();
$jq(document).ready(function() {
  	$jq("#menu_content>div[class*='menu']").hover(function() {
    $jq(".area_1").hide();
    $jq(this).children(".area_1").show();
  });

//  $jq("#menu_content>div[class*='menu']>div[class='area_1']>div").mouseover(function() {
//    $jq("#menu_3>.clear").css("height","0px");
//    var s_html = $jq("#" + $jq(this).attr("id") + "_content").html();
//    var ids = $jq(this).children(".area_1");
//    var ids = $jq(this);
//    if (s_html) {
//    	
//      var s_left = parseInt($jq(this).offset().left) + 165;
//      var s_top = parseInt($jq(this).offset().top) - 1;
//      $jq("#menu_body").html(s_html);
//
//      var menu1 = $jq(this).html();
//     
//      $jq("#menu_body a").click(function() {
//        nav.setNavTitle(menu1,this);
//      });
//
//      var s_height = 0;
//      var s_top1 = $jq(this).parent().offset().top;
//      $jq("#menu_3").css("display","inline");
//      s_height = parseInt($("menu_3").offsetHeight);
//      if (s_height < s_top) {
//        $jq("#menu_3>.clear").css("height",parseInt(s_top - s_height - s_top1 + 40) + "px");
//      }
//      $jq("#menu_jiantou").css("display","inline");
//      $jq("#menu_jiantou").animate({
//        left:s_left - 31,
//        top:s_top + 3
//      },20);
//      $jq("#menu_3").animate({
//        left:s_left - 10,
//        top:s_top1
//      },20);
//    }
//    else {
//      $jq("#menu_3").css("display","none");
//      $jq("#menu_jiantou").css("display","none");
//    }
//  });
//  $jq("#menu_content>div[class*='menu']>div[class!='area_1']").mouseover(function() {
  //$jq(".area_1").hide();
//    $jq("#menu_3").hide();
//    $jq("#menu_jiantou").hide();
//  });
//  $jq("#menu_3").mouseleave(function() {
//    $jq(this).hide();
//    $jq("#menu_jiantou").hide();
  //$jq(".area_1").hide();
//  });
//  $jq("#menu_3_close").click(function() {
//    $jq("#menu_3").hide();
//    $jq("#menu_jiantou").hide();
//    $jq(".area_1").hide();
//  });
  //关闭所有
  $jq("#area").mouseover(function() {

    $jq(".area_1").hide();
  });
  $jq("div.sanji a").click(function() {

    $jq(".area_1").hide(500);

    var text = $jq(this).text();
    $jq("#navTitle").html(text);

  });
});