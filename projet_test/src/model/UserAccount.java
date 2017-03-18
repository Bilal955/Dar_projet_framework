package model;

public class UserAccount {
	
	private String mail;
	private String login;
	private String pwd;
	
	
	public UserAccount(String mail, String login, String pwd) {
		super();
		this.mail = mail;
		this.login = login;
		this.pwd = pwd;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Override
	public String toString(){
		return "{\"mail\":\"" + mail + "\"," + "\"login\":\"" + login + "\"," + "\"mdp\":\"" + pwd + "\"}";
	}
	

}
