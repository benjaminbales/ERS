import {React, useState, useEffect} from 'react'
import { Link, useHistory, useLocation} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function CreateRR() {
    const [amount, setAmount] = useState(0);
	const [timestamp, setTimestamp] = useState(undefined);
    const [employee, setEmployee] = useState(undefined);
	const [manager, setManager] = useState(null);
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
				console.log("employee: ", location.state.employee);
            }
			return;
		}

		console.log(respData);

		if (respData.success === true){
			console.log("Create RR succeed!");
		}else{
			console.log("Create RR failed.");
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
            amount: amount,
			employee: location.state.userData._id
		};
		axios.post('http://localhost:7777/create-rr', data)
		.then( res => {
			setRespData(res.data);
			setSuccess(res.data.success);
			console.log("res.data", res.data);
		})
	}

    return(
    <Page header="Submit Reimbursement Request">
          <h2>Fill out the form data to submit a new reimbursment request:</h2><br/>
          <form onSubmit={handleSubmit}>
              <label>
                  Amount:
                  <input type="text" name="amount" onChange = {e => setAmount(e.target.value)}/>
              </label><br/>
              <input type="submit" value="Submit" />
          </form>
		  {success && <p>Submit Reimbursement Request Succeeded!</p>}
      </Page>
    );
}