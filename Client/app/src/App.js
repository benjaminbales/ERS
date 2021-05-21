import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  NavLink,
  HashRouter
} from "react-router-dom";
import CreateRR from "./pages/CreateRR"
import UpdateEmployee from "./pages/UpdateEmployee"
import Login from "./pages/Login"
import RegisterEmployee from "./pages/RegisterEmployee"
import ViewEmployee from "./pages/ViewEmployee"
import ViewRR from "./pages/ViewRR"
import ManagerHome from "./pages/ManagerHome"
import EmployeeHome from "./pages/EmployeeHome"
import Home from "./pages/Home"

export default function App() {
  return (
    <HashRouter>
        <div>
          <h1>Revature Expense Reimbursement System (ERS)</h1>
          <ul className="header">
            <li><NavLink exact to="/">Home</NavLink></li>
            <li><NavLink to="/login">Login</NavLink></li>
          </ul>
          <div className="content">
            <Route exact path="/" component={Home}/>
            <Route path="/login" component={Login}/>
            <Route path="/create-rr" component={CreateRR}/> 
            <Route path="/employee-home" component={EmployeeHome}/>
            <Route path="/update-employee" component={UpdateEmployee}/> 
            <Route path="/manager-home" component={ManagerHome}/>  
            <Route path="/register-employee" component={RegisterEmployee}/>
            <Route path="/view-employee" component={ViewEmployee}/>  
            <Route path="/view-rr" component={ViewRR}/>  
          </div>
        </div>
    </HashRouter>
    );
  }
