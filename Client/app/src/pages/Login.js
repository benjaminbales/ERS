import {React, useState, useEffect} from 'react'
import { Link, useHistory} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function Login() {
    const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [respData, setRespData] = useState(null);

    const history = useHistory();

    useEffect( () => {
		if(respData === null) return;

		console.log(respData);
        //check response data and push target url
        //to history to transition to next page.
		if (respData.loginStatus === true){

			console.log("Login succeeded.");

            if(respData.role === "Employee"){
                history.push({
                    pathname : "/employee-home",
                    state : {
                        userData: respData
                    }
                });
            }

            if(respData.role === "Manager"){
                history.push({
                    pathname : "/manager-home",
                    state : {
                        userData: respData,
                    }
                });
            }
		}
		else{
			console.log("Login failed.");
		}
	}, [respData])


    const handleSubmit= (e) =>{
		e.preventDefault();
		const data = {
            username: username,
            password: password,
		};
        axios.post('http://localhost:7777/login', data)
        .then( res => {
            setRespData(res.data);
        })
	}

    return(
    <Page header="Login Page">
          <form onSubmit={handleSubmit}>
              <label>
                  username:
                  <input type="text" name="username" onChange = {e => setUsername(e.target.value)}/>
              </label><br/>
              <label>
                  password:
                  <input type="password" name="password" onChange = {e => setPassword(e.target.value)}/>
              </label><br/>
              <input type="submit" value="Submit" />
          </form>
    </Page>
  );
}