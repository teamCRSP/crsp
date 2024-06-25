import React from "react";
import "../styles/Login.css";
import { FaUser, FaLock } from "react-icons/fa";
import kakaoImage from '../assets/kakao.png';
import naverImage from '../assets/naver.png';
import { Link } from "react-router-dom";

function Login() {
    return (
        <div className="container">
            <div className="wrapper">
                <form action="">
                    <h1><Link to="#" className="home">CRSP</Link></h1>
                    <h2>Login</h2>
                    <div className="input-box">
                        <input type="email" placeholder="Email" required />
                        <FaUser className="icon" />
                    </div>
                    <div className="input-box">
                        <input
                            type="password"
                            placeholder="Password"
                            required
                        />
                        <FaLock className="icon" />
                    </div>
                    <div className="remember-forget">
                        <label>
                            <input type="checkbox" />
                            로그인 상태 유지
                        </label>
                        <Link to='#' className="find">아이디 찾기</Link>
                        <Link to='#' className="find">비밀번호 찾기</Link>
                    </div>
                    <button type="submit">로그인</button>
                    <div className="register-link">
                        <Link to="/register" className="register">회원가입</Link>
                    </div>
                    <div className="social-kakao-login">
                        <img src={naverImage} alt="Naver logo" className="naver" />
                        <img src={kakaoImage} alt="Kakao logo" className="kakao" />
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;
