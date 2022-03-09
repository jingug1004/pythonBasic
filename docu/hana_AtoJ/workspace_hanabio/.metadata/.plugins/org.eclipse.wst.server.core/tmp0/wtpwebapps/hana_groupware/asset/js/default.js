$(document).ready(function() {
	$(".btn_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
    datepicker();
    tableScroll();
	showHide();
});

/**
 * 달력
 */
function datepicker() {
    $(".btn_date").click(function() {
        $(this).prev().datepicker({"dateFormat":"yy-mm-dd"}); 
		$(this).prev().focus();
	});
};

/**
 * 테이블 스크롤
 */
function tableScroll(){
    var _box = $(".cont_table07"),
        _tr = $(".cont_table07 tbody").find(">tr"),
        _trlen = _tr.length;

    if(_trlen > 9){
        _box.css({"overflow-y":"auto","height":"274px"});
    }

};

//row
function addrow() {
  var _table = $(".wrap_statement_overtime .inner_box > table");
		_row = _table.insertRow(mytable.rows.length);

		  cell1 = row.insertCell(0);
		  cell2 = row.insertCell(1);
		  cell1.innerHTML = '항목';
		  cell2.innerHTML = '<input type="text" name="strs[]"/>';
};

/**
 * 연말정상 펼치기, 접기
 */
function showHide() {
    var _allBtn = $(".btn_show"),
        _oneBtn = $(".btn_ipt"),
        _cont = $(".list_add").find(".tableA"),
		_chk = $(".ipt_chk"),
        flag = true;
    
    _allBtn.on("click",function(e){
        e.preventDefault();
        var el = $(this);
        if(flag){
            flag = false;
            if(el.text() == el.data("text-swap")){
                el.text(el.data("text-original"));
                _allBtn.removeClass("close");
                _oneBtn.text("입력하기");
                _oneBtn.removeClass("close");
				_chk.prop("checked", false);
                _cont.slideUp("slow", function() {
                    flag = true;
                });
            }else {
                el.data("text-original", el.text());
                el.text(el.data("text-swap"));
                _allBtn.addClass("close");
                _oneBtn.text(_oneBtn.data("text-swap"));
                _oneBtn.addClass("close");
				_chk.prop("checked", true);
                _cont.slideDown("slow", function() {
                    flag = true;    
                });
            }
        }
    });
    
    _oneBtn.on("click",function(e){
        e.preventDefault();
        var el = $(this);
        if(flag){
            flag = false;
            if(el.text() == el.data("text-swap")){
                el.text("입력하기");
                el.removeClass("close");
				el.prev().prev().prop("checked", false);
                el.parent().next().slideUp("slow", function() {
                    flag = true;    
                });
            }else {
                el.data("text-original", el.text());
                el.text(el.data("text-swap"));
                el.addClass("close");
				el.prev().prev().prop("checked", true);
                el.parent().next().slideDown("slow", function() {
                    flag = true;    
                });
            }
        }
    });
};

/**
 * 연말정산 tab
 */
$(function () {

    $(".wrap_receipt_midbox").hide();
    $(".wrap_receipt_midbox:first").show();

    $("ul.tab li").click(function () {
        $("ul.tab li").removeClass("on").css("color", "#333");
        //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
        $(this).addClass("on").css("color", "#f9f9f9");
        $(".wrap_receipt_midbox").hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).show()
    });
});