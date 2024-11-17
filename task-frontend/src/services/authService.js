class AuthService{
	login(userId,user){
		sessionStorage.setItem('userId', userId);
		sessionStorage.setItem('logged', 1);
		sessionStorage.setItem('user', user)
	}
	logout(){
		sessionStorage.removeItem('userId');
		sessionStorage.setItem('logged', 0);
	}
	isLogged(){
		if(sessionStorage.getItem('logged') === 0){
			return false;
		}else{
			return true;
		}
	}
	getUserId(){
		return sessionStorage.getItem('userId')
	}
}

export const auth = new AuthService();