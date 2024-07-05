export const setLoginUserInfo = ({ token, email, role, id, name, phone }) => {
    // 토큰 객체에 더 추가하면 더 넣을수 있음
localStorage.setItem('ACCESS_TOKEN', token);
localStorage.setItem('LOGIN_USERNAME', name);
localStorage.setItem('LOGIN_USEREMAIL', email);
localStorage.setItem('USER_GRADE', role);
localStorage.setItem('USER_ID', id);
localStorage.setItem('USER_PHONE',phone)
};


// 로그인한 유저의 데이터객체를 반환하는 함수
export const getLoginUserInfo = () => {
return {
token: localStorage.getItem('ACCESS_TOKEN'),
userEmail: localStorage.getItem('LOGIN_USEREMAIL'),
userName: localStorage.getItem('LOGIN_USERNAME'),
userGrade: localStorage.getItem('USER_GRADE'),
userId: localStorage.getItem('USER_ID'),
userPhone : localStorage.getItem('USER_PHONE')
};
};

// 로그인 id 들고다닐 함수
export const getUserInfo = () => {
return {
userId: localStorage.getItem('USER_ID'),
userEmail: localStorage.getItem('LOGIN_USEREMAIL'),
userName: localStorage.getItem('LOGIN_USERNAME'),
userGrade: localStorage.getItem('USER_GRADE'),
userPhone : localStorage.getItem('USER_PHONE')
};
};

export const isLogin = () => localStorage.getItem('ACCESS_TOKEN');