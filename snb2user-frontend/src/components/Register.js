import React, { useState } from "react";
import axios from "axios";

function Register() {

  const [user, setUser] = useState({
    username: "",
    password: "",
    email: "",
    phone: "",
    about: ""
  });

  const handleChange = (e) => {
    setUser({...user, [e.target.name]: e.target.value});
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/api/register", user)
      .then(res => alert("Registered Successfully"))
      .catch(err => console.log(err));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Register</h2>
      <input name="username" placeholder="Username" onChange={handleChange} /><br/>
      <input name="password" type="password" placeholder="Password" onChange={handleChange} /><br/>
      <input name="email" placeholder="Email" onChange={handleChange} /><br/>
      <input name="phone" placeholder="Phone" onChange={handleChange} /><br/>
      <input name="about" placeholder="About" onChange={handleChange} /><br/>
      <button type="submit">Register</button>
    </form>
  );
}

export default Register;