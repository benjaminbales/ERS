import {React, useState, useEffect} from 'react'
import { Link, useHistory, useLocation} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function ViewRR(props) {
	const [respData, setRespData] = useState(null);
	const [role, setRole] = useState("");

    const history = useHistory();
    const location = useLocation();

    useEffect( () => {
		if(respData === null){
			console.log("starting up page")
			if (props.location.rrProps === undefined){
				alert("Access Denied: redirecting to login");
				history.replace("/login");
			}else{
				console.log(location.state);
				setRole(props.location.rrProps.userData.role);
			}
			return;
		}

		console.log("respData is ", respData);

		if(role === "Manager"){
			history.push({
				pathname : "/manager-home",
				state : {
					userData: props.location.rrProps.userData
				}
			});
		}

		if (respData !== null){
			console.log("ReviewRR succeeded!");
		}
		else{
			console.log("ReviewRR failed.");
		}
	}, [respData])
	

    const handleStatusChange= (e) =>{
		e.preventDefault();
		
		console.log(e.target.textContent);

		if(e.target.textContent == "Approve"){
			props.location.rrProps.request["status"] = "Approved";
		}
		
		if(e.target.textContent == "Deny"){
			props.location.rrProps.request["status"] = "Denied";
		}

        axios.post('http://localhost:7777/update-rr', props.location.rrProps.request)
        .then( res => {
            setRespData(res.data);
        })
	}

	function RenderButtons(status){
		console.log(status["status"]);
		let jsx;
		if(status["status"] === "Pending"){
			jsx = (
				<tr>
					<td>
						<button onClick={handleStatusChange}>Approve</button>
					</td>
					<td>
						<button onClick={handleStatusChange}>Deny</button>
					</td>
				</tr>
			);
		}else{
			jsx = (
				<tr>
					<td>
						<button onClick={handleStatusChange} disabled>Approve</button>
					</td>
					<td>
						<button onClick={handleStatusChange} disabled>Deny</button>
					</td>
				</tr>
			);
		}
		return jsx;
	}

	const goBack = (e) =>{
		e.preventDefault();

		if(role === "Employee"){
			history.push({
				pathname : "/employee-home",
				state : {
					userData: props.location.rrProps.userData
				}
			});
		}
		
		if(role === "Manager"){
			history.push({
				pathname : "/manager-home",
				state : {
					userData: props.location.rrProps.userData
				}
			});
		}
	}


    return(
    <Page header = "View Request">
		<table>
			<tbody>
				<tr>
					<td>
						ObjectId:
					</td>
					<td>
						{props.location.rrProps.request["_id"]}
					</td>
				</tr>
				<tr>
					<td>
						Amount:
					</td>
					<td>
						${props.location.rrProps.request["amount"]}
					</td>
				</tr>
				<tr>
					<td>
					Timestamp:
					</td>
					<td>
						{props.location.rrProps.request["timestamp"]}
					</td>
				</tr>
				<tr>
					<td>
					Employee:
					</td>
					<td>
						{props.location.rrProps.request["employee"]}
					</td>
				</tr>
				<tr>
					<td>
					Manager:
					</td>
					<td>
						{props.location.rrProps.request["manager"]}
					</td>
				</tr>
				<tr>
					<td>
					Status:
					</td>
					<td>
						{props.location.rrProps.request["status"]}
					</td>
				</tr>
				{(role === "Manager") && <RenderButtons status={props.location.rrProps.request["status"]}/>}
				<button onClick={goBack}>Go Back</button>
			</tbody>
		</table>
    </Page>
  );
}