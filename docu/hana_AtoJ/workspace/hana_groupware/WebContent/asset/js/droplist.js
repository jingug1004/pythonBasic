/**
 *
 */

function Droplist(scope, upCnt) {
    var self = this;

    self.scope = scope;
    self.uiType = scope.data('type');
    self.btnUp = scope.find('*[data-role=ui-droplist-btn-up]');
    self.btnDown = scope.find('*[data-role=ui-droplist-btn-down]');
    self.btnRemove = scope.find('*[data-role=ui-droplist-btn-remove]');
    self.list = scope.find('*[data-role=ui-droplist-list]');
    self.currObj = null;

    self.init = function () {
        self.eventBind();
    };

    self.eventBind = function () {
        self.list.on('click', '*[data-role=ui-droplist-item]', function (e) {
            self.currObj = $(this);

            self.selectItem();
        });

        self.btnUp.on('click', function (e) {
            e.preventDefault();

            if (self.currObj == null) return false;

            var prevItem = self.currObj.prev();

            if (prevItem.length) {
                //self.currObj = prevItem;    // modify : 수정
            }
            
            if(self.currObj.index(0) != upCnt){ // modify : 수정
                self.currObj.prev().before(self.currObj);
                self.selectItem();
            }
        });

        self.btnDown.on('click', function (e) {
            e.preventDefault();

            if (self.currObj == null) return false;

            var nextItem = self.currObj.next();

            if (nextItem.length) {
                //self.currObj = nextItem;   //modify : 수정
            }
            
            //if(self.currObj.index(0) != 0){ // modify : 수정
                self.currObj.next().after(self.currObj);
                self.selectItem();
            //}

        });

        self.btnRemove.on('click', function (e) {
            e.preventDefault();

            if (self.currObj == null) return false;
            
            //if(self.currObj.index(0) != 0){ // modify : 수정
                self.currObj.remove();
                self.currObj = null;
            //}
        });
    };

    self.selectItem = function () {
        if (self.uiType == 'basic') {
            self.list.find('*[data-role=ui-droplist-item]').each(function (idx) {
                $(this).removeClass('selected');
            });

            self.currObj.addClass('selected');
        } else if (self.uiType == 'radio') {
            self.list.find('*[data-role=ui-droplist-item]').each(function (idx) {
                $(this).removeClass('selected');
                $(this).find('input[type=radio]').prop('checked', false);
            });

            self.currObj.addClass('selected');
            self.currObj.find('input[type=radio]').prop('checked', true);
        }

    };

    self.init();
}


/* // �������������� script 141224 �߰� */

// 참조 결제 이름 추가 스크립트
function checkName() {
    var _name = $(".check_name").text(),
        _btnAppr = $(".btn_appr"),
        _btnRefe = $(".btn_refe"),
        _btnCoop = $(".btn_coop"),
        _btnExe = $(".btn_exe"),
        _btnAppr2 = $(".btn_appr2"),
        _btnExe2 = $(".btn_exe2"),
        _apprHtml = "";

    _btnAppr.on("click", function () {
        var _checked = $("input[name=checkName]").is(":checked"),
            _apprName = $("input[name=checkName]:checked").parent().siblings("td:last").text();

        if (_checked == true) {
            _apprHtml = "<li data-role='ui-droplist-item'><label><input type='radio' name='appr' />" + _apprName + "</label></li>";
            $("#apprFrame", parent.document).contents().find(".apprList").append(_apprHtml);
        }
    });

    _btnRefe.on("click", function () {
        var _checked = $("input[name=checkName]").is(":checked"),
            _apprName = $("input[name=checkName]:checked").parent().siblings("td:last").text();

        if (_checked == true) {
            _apprHtml = "<li data-role='ui-droplist-item'><a href='#'>" + _apprName + "</a></li>";
            $("#apprFrame", parent.document).contents().find(".refeList").append(_apprHtml);
        }
    });
    
    _btnCoop.on("click", function () {
        var _checked = $("input[name=checkName]").is(":checked"),
            _apprName = $("input[name=checkName]:checked").parent().siblings("td:last").text();
        
        if (_checked == true) {
            _apprHtml = "<li data-role='ui-droplist-item'><a href='#'>" + _apprName + "</a></li>";
            $("#coopFrame", parent.document).contents().find(".coopList").append(_apprHtml);
        }
    });

    _btnExe.on("click", function () {
        var _checked = $("input[name=checkName]").is(":checked"),
            _apprName = $("input[name=checkName]:checked").parent().siblings("td:last").text();
        
        if (_checked == true) {
            _apprHtml = "<li data-role='ui-droplist-item'><a href='#'>" + _apprName + "</a></li>";
            $("#coopFrame", parent.document).contents().find(".exeList").append(_apprHtml);
        }
    });

    _btnAppr2.on("click", function () {
        var _checked = $("input[name=checkName]").is(":checked"),
            _apprName = $("input[name=checkName]:checked").parent().siblings("td:last").text();
        
        if (_checked == true) {
            _apprHtml = "<li data-role='ui-droplist-item'><input type='radio' name='appr' id=''><label for=''>" + _apprName + "</label></li>";
            $(".apprList2").append(_apprHtml);
        }
    });

    _btnExe2.on("click", function () {
        var _checked = $("input[name=checkName]").is(":checked"),
            _apprName = $("input[name=checkName]:checked").parent().siblings("td:last").text();
        
        if (_checked == true) {
            _apprHtml = "<li data-role='ui-droplist-item'><a href='#'>" + _apprName + "</a></li>";
            $(".exeList2").append(_apprHtml);
        }
    });
};
checkName();