import React, { useState } from "react";
import axios from "axios";

function AddEmployee() {

  const [employee, setEmployee] = useState({
    name: "",
    email: "",
    salary: ""
  });

  const handleChange = (e) => {
    setEmployee({...employee, [e.target.name]: e.target.value});
  };

  const saveEmployee = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/snb2employees", employee)
      .then(() => alert("Employee Added"));
  };

  return (
    <div>
      <h2>Add Employee</h2>
      <form onSubmit={saveEmployee}>
        <input type="text" name="name" placeholder="Name" onChange={handleChange} />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} />
        <input type="number" name="salary" placeholder="Salary" onChange={handleChange} />
        <button type="submit">Save</button>
      </form>
    </div>
  );
}

export default AddEmployee;