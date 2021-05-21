import {React, useState, useEffect} from 'react'
import { Link, useHistory, useLocation} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function ViewEmployee(props) {
    const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [respData, setRespData] = useState(null);
	const [submitted, setSubmitted] = useState(false);

	const history = useHistory();
    const location = useLocation();

    useEffect( () => {
		if(respData === null){
			console.log("starting up page")
			if (props.location.eProps === undefined){
				alert("Access Denied: redirecting to login");
				history.replace("/login");
			}else{
				console.log(props.location.eProps.userData);
			}
			return;
		}

		console.log(respData);


	}, [respData])

const goBack = (e) => {
	e.preventDefault();

	history.push({
		pathname : "/manager-home",
		state: {
			userData: props.location.eProps.userData
		}
	});
  }

return(
    <Page header = "View Employee">
		<table>
			<tbody>
				<tr>
					<td>
						ObjectId:
					</td>
					<td>
						{props.location.eProps.employee["_id"]}
					</td>
				</tr>
				<tr>
					<td>
						First Name:
					</td>
					<td>
						{props.location.eProps.employee["firstName"]}
					</td>
				</tr>
				<tr>
					<td>
						Last Name:
					</td>
					<td>
						{props.location.eProps.employee["lastName"]}
					</td>
				</tr>
				<tr>
					<td>
						username:
					</td>
					<td>
						{props.location.eProps.employee["username"]}
					</td>
				</tr>
				<tr>
					<td>
						password:
					</td>
					<td>
						{props.location.eProps.employee["password"]}
					</td>
				</tr>
			</tbody>
		</table>
		<button type="button" onClick={goBack}>Go back</button>
    </Page>
  );
}