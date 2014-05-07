function dologout(){
	document.logout.submit();
}

function date_time(id){
	date = new Date;
	year = date.getFullYear();
	month = date.getMonth();
	months = new Array('Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic');
	d = date.getDate();
	day = date.getDay();
	days = new Array('Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab');
	h = date.getHours();
	if(h<10){
		h = "0"+h;
	}
	m = date.getMinutes();
	if(m<10){
		m = "0"+m;
	}
	s = date.getSeconds();
	if(s<10){
		s = "0"+s;
	}
	result = ''+days[day]+' '+d+' '+months[month]+' '+year+' '+h+':'+m+':'+s;
	document.getElementById(id).innerHTML = result;
	setTimeout('date_time("'+id+'");','1000');
	return true;
}

function showForm(){
	sendData("menuoption");
}

function sendDataConfirm(formId,confirm){
	jConfirm('&iquest;Esta seguro de realizar el movimiento de '+confirm+'?', 'Confirmaci\xF3n Requerida', function(r) {
		if (r){
			var forms = document.getElementById(formId);
			forms.action="main";
			forms.method="post";
			var options = { 
				target: '#divcontent',
				beforeSubmit: 	showLoadScreen,
				success:      	hiddeLoadScreen,
				error:        	function(xhr,err){
									$('#divcontent').html(xhr.responseText);
									hiddeLoadScreen();
								}
			}; 
			$(forms).ajaxSubmit(options); 
			return false; 
		}
    });
}

function sendData(formId){
	var forms=document.getElementById(formId);
	forms.action="main";
	forms.method="post";
    var options = { 
		target: 		'#divcontent',
		beforeSubmit:  	showLoadScreen,
		success:		hiddeLoadScreen,
		error:          function(xhr,err){
							$('#divcontent').html(xhr.responseText);
							hiddeLoadScreen();
						}
	}; 
	$(forms).ajaxSubmit(options); 
	return false; 
}

function showLoadScreen(){
	$("#submitDialog").show();
	return true;
}

function hiddeLoadScreen(){
	$("#submitDialog").hide();
	         
	try{
		eval($('#divcontent').html());
	}catch(err){if (err.message !== "Unexpected token <") console.log(err);}
}

function showError(message){
	hiddeLoadScreen();
	$('#errorMessage').html(message);
	$('#error').show();
	return true;	
}


function sentDataLoadModal(formId){
	var forms=document.getElementById(formId);
	forms.action="main";
	forms.method="post";
    var options = { 
		target: '#modalDiv',
		beforeSubmit: showLoadScreen,
		success:
			function(){
			hiddeLoadScreen();
			$('#modalDiv').dialog(open);			
		},
		error:
			function(xhr,err){
				$('#modalDiv').html(xhr.responseText);
				hiddeLoadScreen();
			}
	}; 
	$(forms).ajaxSubmit(options); 
	return false; 
}

function loadModal(targetId){
	$('#'+targetId).load();
}

function getInternetExplorerVersion()
// Returns the version of Internet Explorer or a -1
// (indicating the use of another browser).
{
    var rv = -1; // Return value assumes failure.
    if (navigator.appName == 'Microsoft Internet Explorer')
    {
        var ua = navigator.userAgent;
        var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
    if (re.exec(ua) != null)
        rv = parseFloat( RegExp.$1 );
    }
    return rv;
}

$(function(){
	$.validator.setDefaults({ 
	    ignore: ":hidden",
        errorPlacement: function (error, element) {
	    	element.attr('title', error.text());
	        $(".error").tooltip(
	        {   
	            position: 
	            {
	                my: "left+5 center",
	                at: "right+10 center"
	            },
	            tooltipClass: "ttError"
	        }); 
	    },
	    messages:{
	    	required: "Este campo es requerido" 
	    }
	});
});

function hideUpdateDelete(){
	document.getElementById("updateButton").style.visibility = "hidden";
	document.getElementById("deleteButton").style.visibility = "hidden";		
}

function activeAdd(fieldName){
	if (document.getElementById(fieldName).value !== ''){
		document.getElementById("addButton").style.visibility = "visible";
	} else {
		document.getElementById("addButton").style.visibility = "hidden";
	}
}

function formatNumber(n) {
	res = "$"+formatNumberWithoutSign(n);
	if (res === '$NaN'){
		res = "0.00";
	}
	return res;
}

function formatNumberWithoutSign(n) {
	n=flatNumber(n);
	res = n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
	if (res === 'NaN'){
		res = "0.00";
	}
	return res;
}

function flatNumber(n) {
	try{n=n.replace(',','');}catch(e){}
	try{n=n.replace('$','');}catch(e){}
	return n;
}

function fixAccents(val){
	val = val.replace(/\u0026iacute;/g,'\xED');
	val = val.replace(/\u0026aacute;/g,'\xE1');
	val = val.replace(/\u0026eacute;/g,'\xE9');
	val = val.replace(/\u0026oacute;/g,'\xF3');
	val = val.replace(/\u0026uacute;/g,'\xFA');
	val = val.replace(/\u0026Iacute;/g,'\xCD');
	val = val.replace(/\u0026Aacute;/g,'\xC1');
	val = val.replace(/\u0026Eacute;/g,'\xC9');
	val = val.replace(/\u0026Oacute;/g,'\xD3');
	val = val.replace(/\u0026Uacute;/g,'\xDA');
	val = val.replace(/\u0026ntilde;/g,'\xF1');
	val = val.replace(/\u0026Ntilde;/g,'\xD1');
	return val;
}