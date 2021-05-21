import {React, useState, useEffect} from 'react'
import { Link, useHistory, useLocation} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function UpdateEmployee() {
	const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [id, setId] = useState("");
	const [respData, setRespData] = useState(null);
	const [success, setSuccess] = useState(false);

    const history = useHistory();
    const location = useLocation();

    useEffect( () => {
		if(respData === null){
			console.log("starting up page");
            if (location.state === undefined){
				alert("Access Denied: redirecting to login");
				history.replace("/login");
			}else{
				setFirstName(location.state.userData.firstName);
				setLastName(location.state.userData.lastName);
				setUsername(location.state.userData.username);
				setPassword(location.state.userData.password);
				setId(location.state.userData._id);
            }
			return;
		}

		console.log(respData);

		if (respData.success === true){
			console.log("Update Employee succeed!");
		}else{
			console.log("Update Employee failed.");
		}
		
		history.push({
			pathname: "/employee-home",
			state: {
				userData: location.state.userData
			}
		});

	}, [respData])


    const handleSubmit= (e) =>{
		e.preventDefault();
		const data = {
            firstName: firstName,
			lastName: lastName,
			username: username,
			password: password,
			_id: id
		};
		axios.post('http://localhost:7777/update-employee', data)
		.then( res => {
			setRespData(res.data);
			setSuccess(res.data.success);
			console.log("res.data", res.data);
		})
	}

    return(
    <Page header="Update Employee Information">
          <h2>Fill out the form data to update your personal information:</h2><br/>
          <form onSubmit={handleSubmit}>
              <label>
                  First Name: {firstName}
                  <input type="text" name="firstName" onChange = {e => setFirstName(e.target.value)}/>
              </label><br/>
			  <label>
                  Last Name: {lastName}
                  <input type="text" name="lastName" onChange = {e => setLastName(e.target.value)}/>
              </label><br/>
			  <label>
                   username: {username}
                  <input type="text" name="username" onChange = {e => setUsername(e.target.value)}/>
              </label><br/>
			  <label>
                  password: {password}
                  <input type="text" name="password" onChange = {e => setPassword(e.target.value)}/>
              </label><br/>
              <input type="submit" value="Submit" />
          </form>
		  {success && <p>Update Succeeded!</p>}
      </Page>
    );
}