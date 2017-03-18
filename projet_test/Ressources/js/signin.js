var m;

function validate() {
	var login = document.getElementById("login").value;
	var pwd  = document.getElementById("pwd").value;
	console.log("Vaut " + login + " et : " + pwd);
	document.body.className += "loading";

	console.log("pwd :" + pwd + " login: " + login);
	signin(login, pwd);
	$("#error_holder").text("");
}


function signin(login, pwd) {

	$.ajax({
		url : "/login",
		type : "GET",
		data : "login=" + login + "&pwd=" + pwd+"?",
		dataType : "json",
		success : function(response) {
			responseSignin(response);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			// func_erreur(-1, jqXHR.responseText, errorThrown);
			document.body.className = '';
		}
	});
	console.log("End login.");

}

function responseSignin(response) {
	$("#error_holder").fadeOut('fast');
	if(response != undefined && response !== "false") {
		document.body.className = '';
		window.location.href = "main.html";
	} else {
		$("#error_holder").text("Login or pwd unknown. Try again").fadeIn('fast');
		document.body.className = '';
	}
}