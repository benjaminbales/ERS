import {React, useState, useEffect} from 'react'
import { useHistory, useLocation} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';
import AllPendingRRs from './AllPendingRRs'
import AllResolvedRRs from './AllResolvedRRs'

export default function EmployeeHome() {
	const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
	const [respData, setRespData] = useState(null);
	const [pendingRRs, setPendingRRs] = useState(undefined);
	const [resolvedRRs, setResolvedRRs] =  useState(undefined);
	const [resetPendingRRs, setResetPendingRRs] = useState(false);
	const [resetResolvedRRs, setResetResolvedRRs] = useState(false);
	const location = useLocation();
	const history = useHistory();
	const state = useState();

    useEffect( () => {
		if(respData === null){
			console.log("starting EmployeeHome");
			if (location.state === undefined){
				alert("Access Denied: redirecting to login");
				history.replace("/login");
			}else{
				console.log(location.state);
				setFirstName(location.state.userData.firstName);
				setLastName(location.state.userData.lastName);
			}
			return;
		}

		console.log("respData is ", respData);

	}, [respData, location, history]);

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
		history.replace("/login");
	}

	const handleCreateRR = (e) =>{
		e.preventDefault();
		history.push({
			pathname : "/create-rr",
			state : {
				userData: location.state.userData
			}
		});
	}

	const handleUpdateInfo = (e) =>{
		e.preventDefault();
		history.push({
			pathname : "/update-employee",
			state : {
				userData: location.state.userData
			}
		});
	}

    return(
    <Page header="Employee Home">
		<h1>Welcome {firstName} {lastName}!</h1>
		<button onClick={handleCreateRR}>Create Reimbursement Request</button>
		<button onClick={handleUpdateInfo}>Update Account Info</button>
		<button onClick={handleLogout}>Logout</button>
		<table>
			<tbody>
				<tr>
					<td>Pending Reimbursment Requests</td>
					<td>Resolved Reimbursment Requests</td>
				</tr>
				<tr>
					<td><button onClick={getPendingRRs}>Get</button><button onClick={onResetPendingRRs}>Reset</button></td>
					<td><button onClick={getResolvedRRs}>Get</button><button onClick={onResetResolvedRRs}>Reset</button></td>
				</tr>
				<tr>
					<td>{pendingRRs && <AllPendingRRs pending={pendingRRs} reset={resetPendingRRs} userData={location.state.userData}/>}</td>
					<td>{resolvedRRs && <AllResolvedRRs resolved={resolvedRRs} reset={resetResolvedRRs} userData={location.state.userData}/>}</td>
				</tr>
			</tbody>
		</table>
    </Page>
  );
}