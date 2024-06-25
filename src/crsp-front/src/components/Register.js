import React, { useState } from "react";
import "../styles/Register.css";
import { FaUser, FaLock } from "react-icons/fa";
import kakaoImage from "../assets/kakao.png";
import naverImage from "../assets/naver.png";
import { Link } from "react-router-dom";
import DaumPost from "./DaumPost";

import {API_BASE_URL as BASE, USER} from '../config/host-config';
import axios from "axios";

function Register() {

    const API_BASE_URL = BASE + USER;

    const [userValue, setUserValue] = useState({
        email: "",
        password: "",
        name: "",
        phone: "",
        age: "",
        address: "",
        detailAddress: "",
        role: "",
    });

    const [message, setMessage] = useState({
        email: "",
        password: "",
        name: "",
        phone: "",
        age: "",
        address: "",
        detailAddress: "", 
    });

    const [correct, setCorrect] = useState({
        email: false,
        password: false,
        name: false,
        phone: false,
        age: false,
        address: false,
        detailAddress: false, 
        role: false,
    });

    const [selectedRole, setSelectedRole] = useState("");

    const registerButtonClickHandler = (e) => {
        e.preventDefault();
        console.log(userValue);
        console.log(correct);
    };

    const emailHandler = (e) => {
        const regExp =
            /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        let msg;
        let flag;
        const inputVal = e.target.value;

        if (!inputVal) {
            msg = "이메일을 작성해주세요.";
        } else if (!regExp.test(inputVal)) {
            msg = "이메일 형식으로 작성해주세요.";
        } else {
            msg = "이메일 사용 가능합니다."
            flag = true;
            // 이메일 중복체크
            // fetchDuplicationCheck(inputVal);

        }
        // 이메일 중복체크 
        const fetchDuplicationCheck = (eamil) => {

            // 나중에 다시 작성 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            
        }

        saveInputState({
            key: "email",
            inputVal,
            msg,
            flag,
        });
    };

    const nameHandler = (e) => {
        const regExp = /^[가-힣a-zA-Z0-9]{4,}$/;
        let msg, flag;
        const inputVal = e.target.value;

        if (!inputVal) {
            msg = "이름을 입력하세요.";
        } else if (!regExp.test(inputVal)) {
            msg = "4글자 이상의 영문, 숫자, 한글만 입력 가능합니다.";
        } else {
            msg = "사용 가능한 이름입니다.";
            flag = true;
        }
        saveInputState({
            key: "name",
            inputVal,
            msg,
            flag,
        });
    };

    const passwordHandler = (e) => {
        const regExp =
            /^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()-]).{8,25}$/;
        let msg, flag;
        const inputVal = e.target.value;

        if (!inputVal) {
            msg = "비밀번호를 작성해주세요.";
        } else if (!regExp.test(inputVal)) {
            msg = "8글자 이상의 영문, 숫자, 특수문자를 포함해주세요.";
        } else {
            msg = "사용 가능한 비밀번호 입니다.";
            flag = true;
        }

        saveInputState({
            key: "password",
            inputVal,
            msg,
            flag,
        });
    };

    const phoneHandler = (e) => {
        const regExp = /^\d{3}-\d{3,4}-\d{4}$/;
        let msg, flag;
        const inputVal = e.target.value;

        if (!inputVal) {
            msg = "휴대폰 번호를 입력하세요.";
        } else if (!regExp.test(inputVal)) {
            msg = "올바른 휴대폰 번호 형식이 아닙니다. (010-1234-5678)";
        } else {
            msg = "사용 가능한 휴대폰 번호입니다.";
            flag = true;
        }

        saveInputState({
            key: "phone",
            inputVal,
            msg,
            flag,
        });
    };

    const ageHandler = (e) => {
        const regExp = /^(0|[1-9][0-9]?|1[0-9]{1,2}|200)$/;
        let msg, flag;
        const inputVal = e.target.value;

        if (!inputVal) {
            msg = "나이를 입력하세요.";
        } else if (!regExp.test(inputVal)) {
            msg = "0 이상 200 이하의 숫자만 입력 가능합니다.";
        } else {
            msg = "사용 가능한 나이입니다.";
            flag = true;
        }

        saveInputState({
            key: "age",
            inputVal,
            msg,
            flag,
        });
    };

    const handleRoleClick = (role) => {
        let flag;
        setUserValue({ ...userValue, role });
        setSelectedRole(role);
        setCorrect({
            ...correct,
            role: true,
        });
    };

    const saveInputState = ({ key, inputVal, flag, msg }) => {
        setMessage({
            ...message,
            [key]: msg,
        });

        setUserValue({
            ...userValue,
            [key]: inputVal,
        });

        setCorrect({
            ...correct,
            [key]: flag,
        });
    };

    const setAddress = (address) => {
        setUserValue({
            ...userValue,
            address,
        });
        setCorrect({
            ...correct,
            address: true,
        });
    };

    const setDetailAddress = (detailAddress) => {
        setUserValue({
            ...userValue,
            detailAddress,
        });
        setCorrect({
            ...correct,
            detailAddress: true,
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
                    <h2>Register</h2>
                    <div className="input-box">
                        <input
                            type="email"
                            placeholder="Email"
                            required
                            onChange={emailHandler}
                        />
                        <FaUser className="icon" />
                        <span
                            style={
                                correct.email
                                    ? { color: "green" }
                                    : { color: "red" }
                            }
                        >
                            {message.email}
                        </span>
                    </div>

                    <div className="input-box">
                        <input
                            type="password"
                            placeholder="Password"
                            required
                            onChange={passwordHandler}
                            maxLength={25}
                            minLength={8}
                        />
                        <FaLock className="icon" />
                        <span
                            style={
                                correct.password
                                    ? { color: "green" }
                                    : { color: "red" }
                            }
                        >
                            {message.password}
                        </span>
                    </div>
                    <div className="input-box">
                        <input
                            type="text"
                            placeholder="Name"
                            required
                            onChange={nameHandler}
                            minLength={4}
                            maxLength={12}
                        />
                        <FaUser className="icon" />
                        <span
                            style={
                                correct.name
                                    ? { color: "green" }
                                    : { color: "red" }
                            }
                        >
                            {message.name}
                        </span>
                    </div>

                    <div className="input-box">
                        <input
                            type="text"
                            placeholder="Address"
                            required
                            value={userValue.address}
                            readOnly
                        />
                        <FaUser className="icon" />
                        <DaumPost setAddress={setAddress} />
                    </div>
                    <div className="input-box">
                        <input
                            type="text"
                            placeholder="Detailed Address"
                            required
                            onChange={(e) => setDetailAddress(e.target.value)}
                            value={userValue.detailAddress}
                        />
                        <FaUser className="icon" />
                    </div>

                    <div className="input-box">
                        <input
                            type="text"
                            placeholder="Phone"
                            required
                            onChange={phoneHandler}
                            maxLength={13}
                        />
                        <FaLock className="icon" />
                        <span
                            style={
                                correct.phone
                                    ? { color: "green" }
                                    : { color: "red" }
                            }
                        >
                            {message.phone}
                        </span>
                    </div>
                    <div className="input-box">
                        <input
                            type="text"
                            placeholder="Age"
                            required
                            min={0}
                            max={200}
                            maxLength={3}
                            onChange={ageHandler}
                        />
                        <FaLock className="icon" />
                        <span
                            style={
                                correct.age
                                    ? { color: "green" }
                                    : { color: "red" }
                            }
                        >
                            {message.age}
                        </span>
                    </div>
                    <div className="role">
                        <div
                            className={`role-button ${
                                selectedRole === "ORGANIZER" ? "selected" : ""
                            }`}
                            onClick={() => handleRoleClick("ORGANIZER")}
                        >
                            주최자
                        </div>
                        <div
                            className={`role-button ${
                                selectedRole === "CUSTOMER" ? "selected" : ""
                            }`}
                            onClick={() => handleRoleClick("CUSTOMER")}
                        >
                            고객
                        </div>
                    </div>
                    <button type="submit" onClick={registerButtonClickHandler}>
                        회원가입
                    </button>
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

export default Register;
