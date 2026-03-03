import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Login() {

  const navigate = useNavigate();

  const [loginData, setLoginData] = useState({
    username: "",
    password: ""
  });

  const handleChange = (e) => {
    setLoginData({...loginData, [e.target.name]: e.target.value});
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/api/login", loginData)
      .then(res => {
        if(res.data){
          localStorage.setItem("user", JSON.stringify(res.data));
          navigate("/profile");
        } else {
          alert("Invalid Credentials");
        }
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Login</h2>
      <input name="username" placeholder="Username" onChange={handleChange} /><br/>
      <input name="password" type="password" placeholder="Password" onChange={handleChange} /><br/>
      <button type="submit">Login</button>
    </form>
  );
}

export default Login;