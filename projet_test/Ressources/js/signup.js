var m;

function validate() {
	var mail = document.getElementById("email").value;
	var login = document.getElementById("login").value;
	var pwd = document.getElementById("pwd").value;
	var repwd = document.getElementById("repwd").value;

	document.body.className += "loading";
	m = mail;
	rep = repwd;
	console.log(mail);
	valMail = validateEmail(mail);
	$("#error_holder").fadeOut('fast');

	if(valMail == true && pwd === repwd && pwd.length >= 6 && login.length >= 4) {		
		signup(mail, login, pwd, repwd);
	} else if(valMail == false) {
		$("#error_holder").text("Please enter a valid email.").fadeIn('fast');
		document.body.className = '';
	} else if(login.length < 4) {
		$("#error_holder").text("Your login is too short (at least 4 chars).").fadeIn('fast');
		document.body.className = '';
	} else if(pwd.length < 6) {
		$("#error_holder").text("Your password is too short (at least 6 chars).").fadeIn('fast');
		document.body.className = '';
	} else if(pwd != repwd) {
		$("#error_holder").text("Passwords doesn't match.").fadeIn('fast');
		document.body.className = '';
	} 
}


function signup(mail, login, pwd, repwd) {

	$.ajax({
		url : "/saveaccount",
		type : "GET",
		data : "mail=" + mail + "&login=" + login + "&pwd=" + pwd,
		success : function(rep) {
			responseSignup(rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#error_holder").text("Unexpected error. Try again").fadeIn('fast');
			console.log(jqXHR.responseText);
		}
	});

}


function responseSignup(response) {
	$("#error_holder").fadeOut('fast');
	if(response != undefined) {
		$("#error_holder").fadeOut('fast');
		window.location.href = "main.html"
	} else {
		$("#error_holder").text(response.erreur).fadeIn('fast');
		console.log("What is this: " + JSON.stringify(response));
	}
}