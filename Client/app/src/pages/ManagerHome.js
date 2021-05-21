import {React, useState, useEffect} from 'react'
import { useHistory, useLocation} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';
import AllEmployees from './AllEmployees'
import AllPendingRRs from './AllPendingRRs'
import AllResolvedRRs from './AllResolvedRRs'

export default function ManagerHome() {
	const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [filterPending, setFilterPending] = useState("");
	const [filterResolved, setFilterResolved] = useState("");
	const [respData, setRespData] = useState(null);
	const [employees, setEmployees] = useState(undefined);
	let [pendingRRs, setPendingRRs] = useState(undefined);
	let [resolvedRRs, setResolvedRRs] =  useState(undefined);
	const [resetEmployees, setResetEmployees] = useState(false);
	const [resetPendingRRs, setResetPendingRRs] = useState(false);
	const [resetResolvedRRs, setResetResolvedRRs] = useState(false);
	const [loggedIn, setLoggedIn] = useState(true);
	const location = useLocation();
	const history = useHistory();
	const state = useState();

    useEffect( () => {
		if(respData === null){
			console.log("starting ManagerHome");
			if (location.state === undefined){
				alert("Access Denied: redirecting to login");
				history.replace("/login");
			}else{
				console.log(location.state.userData);
				setFirstName(location.state.userData.firstName);
				setLastName(location.state.userData.lastName);
				setUsername(location.state.userData.username);
				setPassword(location.state.userData.password);
			}
			return;
		}

		console.log("respData is ", respData);

	}, [respData, location, history]);


    const getEmployees = (e) =>{
		e.preventDefault();
		setResetEmployees(false);
        axios.post('http://localhost:7777/all-employees')
        .then( res => {
			setRespData(res.data);
			if(res.data !== null) setEmployees(res.data);
        })
	}

	const getPendingRRs = (e) =>{
		e.preventDefault();
		setResetPendingRRs(false);
        axios.post('http://localhost:7777/all-pending-rrs')
        .then( res => {
            setRespData(res.data);
			if(res.data !== null) setPendingRRs(res.data);
			console.log("pendingRRs ", pendingRRs);
        })
	}

	const getResolvedRRs = (e) =>{
		e.preventDefault();
		setResetResolvedRRs(false);
        axios.post('http://localhost:7777/all-resolved-rrs')
        .then( res => {
            setRespData(res.data);
			if(res.data !== null) setResolvedRRs(res.data);
        })
	}

	const onResetEmployees = (e) =>{
		e.preventDefault();
		setResetEmployees(true);
		setEmployees(undefined);
	}

	const onResetPendingRRs = (e) =>{
		e.preventDefault();
		setResetPendingRRs(true);
		setPendingRRs(undefined);
	}

	const onResetResolvedRRs = (e) =>{
		e.preventDefault();
		setResetResolvedRRs(true);
		setResolvedRRs(undefined);
	}

	const handleLogout = (e) =>{
		e.preventDefault();
		setLoggedIn(false);
		history.replace("/login");
	}

	const handleRegister = (e) =>{
		e.preventDefault();
		history.push({
			pathname : "/register-employee",
			state : {
				loggedIn: loggedIn,
				userData: location.state.userData
			}
		});
	}

	const doFilterPending = (e) =>{
		e.preventDefault();
		if(pendingRRs !== undefined){
			setFilterPending(e.target.employee.value);
		}
	}

	const doFilterResolved = (e) =>{
		e.preventDefault();
		if(resolvedRRs !== undefined){
			setFilterResolved(e.target.employee.value);
		}
	}

    return(
    <Page header="Manager Home">
		<h1>Welcome {firstName} {lastName}!</h1>
		<button onClick={handleRegister}>Register Employee</button>
		<button onClick={handleLogout}>Logout</button>
		<table>
			<tbody>
				<tr>
					<td>Employees</td>
					<td>Pending Reimbursment Requests</td>
					<td>Resolved Reimbursment Requests</td>
				</tr>
				<tr>
					<td><button onClick={getEmployees}>Get</button><button onClick={onResetEmployees}>Reset</button></td>
					<td><button onClick={getPendingRRs}>Get</button><button onClick={onResetPendingRRs}>Reset</button></td>
					<td><button onClick={getResolvedRRs}>Get</button><button onClick={onResetResolvedRRs}>Reset</button></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<form onSubmit={doFilterPending}>
							<label>
								Employee Id:
								<input type="text" name="employee" />
							</label>
							<input type="submit" value="Filter" />
						</form>
					</td>
					<td>
						<form onSubmit={doFilterResolved}>
							<label>
								Employee Id:
								<input type="text" name="employee"/>
							</label>
							<input type="submit" value="Filter" />
						</form>
					</td>
				</tr>
				<tr>
					<td>{employees && <AllEmployees employees={employees} reset={resetEmployees} userData={location.state.userData}/>}</td>
					<td>{pendingRRs && <AllPendingRRs pending={pendingRRs} reset={resetPendingRRs} userData={location.state.userData} filter={filterPending}/>}</td>
					<td>{resolvedRRs && <AllResolvedRRs resolved={resolvedRRs} reset={resetResolvedRRs} userData={location.state.userData} filter={filterResolved}/>}</td>
				</tr>
			</tbody>
		</table>
    </Page>
  );
}