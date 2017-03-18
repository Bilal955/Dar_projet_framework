var C_NAME   = "fyf_ident";
var MAPS_KEY = "AIzaSyDZv1TYlMIMxAPIV1ZuspwPD5zZEjylW28";
var myLogin;

/************************ Cookie mnam mnam mnam ***********************/
function setCookie(cname, cvalue, minutes) {

	var d = new Date();
	d.setTime(d.getTime() + (minutes * 60 * 60 * 1000));
	var expires = "expires=" + d.toUTCString();

	document.cookie = cname + "=" + cvalue + "; " + expires;
	console.log("[SetCookie] OK " + cname + ", " + cvalue + ", " + minutes);
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');

	for ( var i = 0; i < ca.length; i++) {
		var c = ca[i];

		while (c.charAt(0) == ' ')
			c = c.substring(1);

		if (c.indexOf(name) == 0) {
			str = c.substring(name.length, c.length);
			if(str == "-1") {
				console.log("Oh oh it has been reinitialised.");
				return null;
			}
			console.log("[GetCookie] OK " + cname + ": " + str);
			return str;
		}
	}

	console.log("[GetCookie] Nothing to show");
	return null;
}


function checkCookie(name) {
	us = getCookie(name);
	if (us != null) {
		alert("Welcome again " + us);
	} else {
		console.log("[CheckCookie] " + name + " FAILED! ");
	}
}


function isConnected(bool) {
	$.ajax({
		url : "/isconnected",
		type : "GET",
		success : function(rep) {
			retConnected(rep, bool);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			// We do nothing if there isn't an active session
			console.log("Error(" + textStatus + ") : " + jqXHR.responseText);
			console.log("Maybe user is not connected.");      
		}
	});
}

function retConnected(rep, bool){
	if(bool === "true"){
		if(rep.indexOf('true') >= 0) {
			document.body.className = '';
			window.location.href = "main.html";
		} else {
			document.body.className = '';
		}
	}
	else{
		if(rep.indexOf('false') >= 0) {
			document.body.className = '';
			window.location.href = "home.html";
		} else {
			document.body.className = '';
		}
	}
}


function changePage() {
	//alert(document.getElementById("distanceSelector").value);
	addr = document.getElementById("searchTextField").value;
	if(addr.length > 0)
		window.location.href = "home.html?adresse=" + addr;
}


function goToHome() {
	window.location.href = "home.html?";
}

function goToChat(loginContact) {
	// Tester if connected
	if(loginContact != undefined)
		window.location.href = "chat.html?friend_login="+loginContact;
	else
		window.location.href = "chat.html?";
}

function toInfos(){
	console.log("ToUserProfile");
	window.location.href = "userprofile.html";
}

function disconnect() {
	console.log("disconnect");
	logout();
}

/* Convetion : Cookie value equals "-1" means there is no active session. */
function logout() {
	var idKey = getCookie(C_NAME);
	setCookie(C_NAME, "-1", 1);
	if(idKey != undefined) {
		$.ajax({
			url : "../logout?",
			type : "GET",
			data : "keyID=" + idKey,
			dataType : "json",
			success : function(rep) {
				console.log("Logout success from serveur")
				okBar("You are disconnected now", 1000);
				setInterval(function(){window.location.href = "index.html";}, 1000);
			}, 
			error : function(resultatXHR, statut, erreur) {
				errorFunction(resultatXHR, statut, erreur, "logout");
			}
		});
	}
}


/* Display banner messages */
function topBar(message) {
	$("<div />", { class: 'erreur_topbar', text: message }).hide().prependTo("body")
	.slideDown('fast').delay(5000).fadeOut(function() { $(this).remove(); });
}


function okBar(message, myDelay) {
	if(myDelay == undefined)
		myDelay = 5000;
	$("<div />", { class: 'ok_topbar', text: message }).hide().prependTo("body")
	.slideDown('fast').delay(myDelay).fadeOut(function() { $(this).remove(); });
}


function validateEmail(email) {
	var regexp = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return regexp.test(email);
}

function get_ParamGET(param) {
	var vars = {};
	window.location.href.replace( location.hash, '' ).replace( 
			/[?&]+([^=&]+)=?([^&]*)?/gi, // regexp
			function( m, key, value ) { // callback
				vars[key] = value !== undefined ? value : '';
			});
	if ( param )
		return vars[param] ? vars[param] : null;	
	return vars;
}

function savePresence(){
	$.ajax({
		url : "/modifypresence",
		type : "GET",
		data : "login=" + myLogin,
		success : function(rep) {
			savePresenceRet(rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			// We do nothing if there isn't an active session
			console.log("Error(" + textStatus + ") : " + jqXHR.responseText);
			console.log("Maybe user is not connected.");      
		}
	});
}

function savePresenceRet(resp) {
	if(resp.indexOf('false') >= 0){
		document.getElementById("me").className="btn btn-secondary btn-lg btn-block";
	}
	else{
		document.getElementById("me").className="btn btn-primary btn-lg btn-block";
	}
}

function fillPresence(){
	var liste = document.getElementById("liste");
	$.ajax({
		url : "/fillpresence",
		type : "GET",
		success : function(rep, liste) {
			fillPresenceRet(rep, liste);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			// We do nothing if there isn't an active session
			console.log("Error(" + textStatus + ") : " + jqXHR.responseText);
			console.log("Maybe user is not connected.");      
		}
	});
}

function fillPresenceRet(rep){
	myLogin = getCookie("login");
	if(rep != undefined) {
		var name = "";
		var isPresent = "";
		var autreParam = false;
		var pres = "primary";
		for(i = 0; i < rep.length; i++){
			if(rep[i] == ":")
				autreParam = true;
			else{
				if(rep[i] != "/"){
					if(autreParam == false)
						name += rep[i];
					else
						isPresent += rep[i];
				}
				else{
					if(isPresent == "false")
						pres = "secondary";
					if(myLogin.indexOf(name) >= 0){
						liste.innerHTML += '<button type="button" class="btn btn-'+pres+' btn-lg btn-block" id="me" onclick="savePresence()">' + name + "</button>";
					}
					else
						liste.innerHTML += '<button type="button" class="btn btn-'+pres+' btn-lg btn-block" disabled>' + name + "</button>";
					autreParam = false;
					name = "";
					isPresent = "";
					pres = "primary";
				}
			}
		}
		
	} else {
		document.body.className = '';
	}
}


function myHTMLspecialhars(ch) {
	// console.log("Chaine entre = "+ch);
	ch = ch.replace(/&/g,"&amp;");
	ch = ch.replace(/\"/g, "&quot;");
	ch = ch.replace(/\'/g,"&#039;");
	ch = ch.replace(/</g,"&lt;");
	ch = ch.replace(/>/g,"&gt;");
	ch = ch.replace(/é/g,"&eacute;");
	ch = ch.replace(/è/g,"&egrave;");
	ch = ch.replace(/à/g,"&agrave;");
	ch = ch.replace(/ù/g,"&ugrave;");
	//console.log("Chaine sortie = "+ch);
	return ch;
}

function myDecodeHTMLspecialhars(ch) {
	// console.log("Decode Chaine entre = "+ch);
	ch = ch.replace(/&amp;/g,"&");
	ch = ch.replace(/&quot;/g, "\"");
	ch = ch.replace(/&#039;/g,"\'");
	ch = ch.replace(/&lt;/g,"<");
	ch = ch.replace(/&gt;/g,">");
	ch = ch.replace(/&eacute;/g,"é");
	ch = ch.replace(/&egrave;/g,"è");
	ch = ch.replace(/&agrave;/g,"à");
	ch = ch.replace(/&ugrave;/g,"ù");
	//console.log("Decode Chaine sortie = "+ch);
	return ch;
}