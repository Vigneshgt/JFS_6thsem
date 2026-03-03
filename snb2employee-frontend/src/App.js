import React from "react";
import EmployeeList from "./components/EmployeeList";
import AddEmployee from "./components/AddEmployee";

function App() {
  return (
    <div>
      <AddEmployee />
      <EmployeeList />
    </div>
  );
}

export default App;