<script type="text/javascript">
function show(){
	document.getElementById("sim_stuff").style.visibility = "visible";
	document.getElementById("show_it").style.visibility = "hidden";
}

function show_not(){
	document.getElementById("sim_stuff").style.visibility = "hidden";
	document.getElementById("show_it").style.visibility = "visible";
}
</script>
<div style="position:absolute; right:10px; top:20px;">
	<div id="sim_stuff" style="visibility:hidden;">
		<a href="SimControllerMi">+ 5 Minuten</a><br>
		<a href="SimControllerH">+ 1 Stunde</a><br>
		<a href="SimControllerD">+ 1 Tag</a><br>
		<a href="SimControllerW">+ 1 Woche</a><br>
		<a href="SimControllerMo">+ 1 Monat</a><br><br>
		<a href="javascript:show_not()">>>></a>
	</div>

	<div id="show_it" style="visibility:visible; float:right;">
		<a href="javascript:show()"> <<< </a>
	</div>
</div>