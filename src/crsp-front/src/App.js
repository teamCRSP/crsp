import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";
import AppRouter from "./pages/AppRouter";

function App() {
    return (
        <div className="App">
            <AppRouter />
        </div>
    );
}

export default App;
