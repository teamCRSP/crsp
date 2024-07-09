function MyInfo(){

    return (
        <div>

        <header className="header">
  <a href="" className="logo">CRSP</a>
  <input className="menu-btn" type="checkbox" id="menu-btn" />
  <label className="menu-icon" htmlFor="menu-btn"><span className="navicon"></span></label>
  <ul className="menu">
  <li><a href="/register">회원가입</a></li>
        <li><a href="/login">로그인</a></li>
        <li><a href="/#">로그아웃</a></li>
        <li><a href="/myInfo">내정보</a></li>
  </ul>
</header>
        </div>
    );
}

export default MyInfo;