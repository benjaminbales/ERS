import {React, useState, useEffect} from 'react'
import { Link, useHistory, useLocation} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function RegisterEmployee() {
    const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
    const [userName, setUserName] = useState("");
	const [password, setPassword] = useState("");
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
				console.log("loggedIn: ", location.state.loggedIn);
            }
			return;
		}

		console.log(respData);

		if (respData.success === true){
			console.log("Register employee succeed!");
		}else{
			console.log("Register employee failed.");
		}
		
		history.push({
            pathname: "/manager-home",
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
			username: userName,
			password: password
		};
		axios.post('http://localhost:7777/register-employee', data)
		.then( res => {
			setRespData(res.data);
			setSuccess(res.data.success);
			console.log("res.data", res.data);
		})
	}

    return(
    <Page header="RegisterEmployee">
          <h2>Fill out the form data to regiser a new employee:</h2><br/>
          <form onSubmit={handleSubmit}>
              <label>
                  First Name:
                  <input type="text" name="firstName" onChange = {e => setFirstName(e.target.value)}/>
              </label><br/>
              <label>
                  Last Name:
                  <input type="text" name="lastName" onChange = {e => setLastName(e.target.value)}/>
              </label><br/>
              <label>
                  username:
                  <input type="text" name="username" onChange = {e => setUserName(e.target.value)}/>
              </label><br/>
              <label>
                  password:
                  <input type="password" name="password" onChange = {e => setPassword(e.target.value)}/>
              </label><br/>
              <input type="submit" value="Submit" />
          </form>
		  {success && <p>Employee Registration Succeeded!</p>}
      </Page>
    );
}