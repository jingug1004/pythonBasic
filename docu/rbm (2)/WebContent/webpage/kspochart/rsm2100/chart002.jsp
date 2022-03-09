<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true" contentType="text/html;charset=euc-kr" %>
<%@ include file="../util.jsp" %>
<%
   String sMeetCd  = nullToStr(request.getParameter("MEET_CD"),"");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link href="./css/examples.css" rel="stylesheet" type="text/css">
	<script language="javascript" type="text/javascript" src="../js/jquery.js"></script>
	<script language="javascript" type="text/javascript" src="../js/jquery.flot.js"></script>
	<script language="javascript" type="text/javascript" src="../js/jquery.flot.selection.js"></script>
	<script type="text/javascript">


	var data = parent.data002;
	$(function() {

		// Shim allowing us to get the state of the check-box on jQuery versions
		// prior to 1.6, when prop was added.  The reason we don't just use attr
		// is because it doesn't work in jQuery versions 1.9.x and later.

		// TODO: Remove this once Flot's minimum supported jQuery reaches 1.6.
		if (typeof $.fn.prop != 'function') {
		    $.fn.prop = $.fn.attr;
		}

		var options = {
			series: {
				lines: {
					show: true
				},
				points: {
					show: true
				},
				grid: {
					hoverable: true,
					clickable: true
				}
			},
			legend: {
				noColumns: 2
			},
			grid: {
				hoverable: true,
				clickable: true
			},
			xaxis: {
				ticks: 100,
				tickDecimals: 0
			},
			yaxis: {
				min: <%if("003".equals(sMeetCd)) out.print("200"); else out.print("500");%>,
				max: <%if("003".equals(sMeetCd)) out.print("1600"); else out.print("6000");%>
			},
			selection: {
				mode: "x"
			}
		};

		var placeholder = $("#placeholder");
		
		$("<div id='tooltip'></div>").css({
			position: "absolute",
			display: "none",
			border: "1px solid #fdd",
			padding: "2px",
			"background-color": "#fee",
			opacity: 0.80
		}).appendTo("body");

		//set title
		$("<div id='meet_nm'></div>").css({
			position: "absolute",
			display: "inline",
			border: "1px solid #fdd",
			padding: "2px",
			"background-color": "#FFF",
			opacity: 0.60
		}).appendTo("body");
		$("#meet_nm").html("&nbsp;&nbsp;창&nbsp;&nbsp;원(판매금액)&nbsp;&nbsp;").css({top: 15, left:$(window).width()/2});
		
		placeholder.bind("plotselected", function (event, ranges) {

			$("#selection").text(ranges.xaxis.from.toFixed(1) + " to " + ranges.xaxis.to.toFixed(1));

			var zoom = $("#zoom").prop("checked");

			if (zoom) {
				$.each(plot.getXAxes(), function(_, axis) {
					var opts = axis.options;
					opts.min = ranges.xaxis.from;
					opts.max = ranges.xaxis.to;
				});
				plot.setupGrid();
				plot.draw();
				plot.clearSelection();
			}
			
			var str = "(" + pos.x.toFixed(2) + ", " + pos.y.toFixed(2) + ")";
			
			if (item) {
				var x = item.datapoint[0].toFixed(2),
					y = item.datapoint[1].toFixed(2);

				$("#tooltip").html(item.series.caption + " of " + x + " = " + y)
					.css({top: item.pageY+5, left: item.pageX+5})
					.fadeIn(200);
			} else {
				$("#tooltip").hide();
			}
			
		});

		$("#placeholder").bind("plotclick", function (event, pos, item) {
			if (item) {
				plot.highlight(item.series, item.datapoint);
			}
		});
		
		$("#placeholder").bind("plothover", function (event, pos, item) {

			var str = "(" + pos.x.toFixed(0) + ", " + pos.y.toFixed(0) + ")";

			if (item) {
				var x = item.datapoint[0].toFixed(0),
					y = item.datapoint[1].toFixed(0);

				$("#tooltip").html(item.series.caption + " " + x + "회차 : " + y + " 백만원")
					.css({top: item.pageY+5, left: item.pageX+5})
					.fadeIn(200);
			} else {
				$("#tooltip").hide();
			}
		});

		var plot = $.plot(placeholder, data, options);

		$("#clearSelection").click(function () {
			plot.clearSelection();
		});

		$("#setSelection").click(function () {
			plot.setSelection({
				xaxis: {
					from: 1994,
					to: 1995
				}
			});
		});

		// Add the Flot version string to the footer

		$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");
	});

	</script>
</head>
<body width="100%">
	<div width="100%" id="content">
		<div width="100%" class="ccrc-container">
			<div id="placeholder" class="ccrc-placeholder"></div>
		</div>
	</div>
</body>
</html>
