import React, { useState } from "react";
import "../styles/Login.css";
import { FaUser, FaLock } from "react-icons/fa";
import kakaoImage from "../assets/kakao.png";
import naverImage from "../assets/naver.png";
import { Link, useNavigate } from "react-router-dom";
import { API_BASE_URL as BASE, USER } from "../constants/host";
import axios from "axios";
import { setLoginUserInfo, getLoginUserInfo } from "../utils/login-util";

function Login() {
    const navigate = useNavigate();
    const API_BASE_URL = BASE + USER;
    const [userValue, setUserValue] = useState({
        email: "",
        password: "",
    });

    const loginSubmit = (e) => {
        e.preventDefault();
        console.log(userValue.email, userValue.password);
        axios
            .post(`${API_BASE_URL}/signIn`, userValue)
            .then((res) => {
                console.log(res.data);
                setLoginUserInfo(res.data);
                navigate("/");
            })
            .catch((error) => {
                console.error("로그인 실패:", error);
                alert("🤔 이메일 또는 비밀번호를 확인해주세요");
            });
    };

    const setEmail = (e) => {
        setUserValue({
            ...userValue,
            email: e.target.value,
        });
    };
    const setPassword = (e) => {
        setUserValue({
            ...userValue,
            password: e.target.value,
        });
    };
    return (
        <div className="container">
            <div className="wrapper">
                <form action="">
                    <h1>
                        <Link to="#" className="home">
                            CRSP
                        </Link>
                    </h1>
                    <h2>Login</h2>
                    <div className="input-box">
                        <input
                            type="email"
                            placeholder="Email"
                            required
                            onChange={setEmail}
                        />
                        <FaUser className="icon" />
                    </div>
                    <div className="input-box">
                        <input
                            type="password"
                            placeholder="Password"
                            required
                            onChange={setPassword}
                        />
                        <FaLock className="icon" />
                    </div>
                    <div className="remember-forget">
                        <Link to="#" className="find">
                            아이디 찾기
                        </Link>
                        <Link to="#" className="find">
                            비밀번호 찾기
                        </Link>
                    </div>
                    <button type="submit" onClick={loginSubmit}>
                        로그인
                    </button>
                    <div className="register-link">
                        <Link to="/register" className="register">
                            회원가입
                        </Link>
                    </div>
                    <div className="social-kakao-login">
                        <img
                            src={naverImage}
                            alt="Naver logo"
                            className="naver"
                        />
                        <img
                            src={kakaoImage}
                            alt="Kakao logo"
                            className="kakao"
                        />
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;
