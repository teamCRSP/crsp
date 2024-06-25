import React from "react";
import "../styles/Login.css";
import { FaUser, FaLock } from "react-icons/fa";
import kakaoImage from '../assets/kakao.png';
import naverImage from '../assets/naver.png';

function Login() {
    return (
        <div className="container">
            <div className="wrapper">
                <form action="">
                    <h1><a href="#">CRSP</a></h1>
                    <h2>Login</h2>
                    <div className="input-box">
                        <input type="text" placeholder="Email" required />
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
                        <a href="#">아이디 찾기</a>
                        <a href="#">비밀번호 찾기</a>
                    </div>
                    <button type="submit">로그인</button>
                    <div className="register-link">
                        <a href="#">회원가입</a>
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
