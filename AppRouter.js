import React from "react";
import { Route, Routes } from "react-router-dom";
import Login from "../components/Login";
import Register from "../components/Register";
import Home from "../components/Home";
import MyInfo from "../components/MyInfo";
import Booking from './Booking';


function AppRouter() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/" element={<Home />} />
            <Route path="/myInfo" element={<MyInfo />} />
            <Route path="/booking" component={Booking} />
        </Routes>
    );
}

export default AppRouter;
