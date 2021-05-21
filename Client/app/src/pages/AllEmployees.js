import {React, useState, useEffect} from 'react'
import { Link, useHistory} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function AllEmployees(props) {
	const [respData, setRespData] = useState(null);

    const history = useHistory();

    useEffect( () => {
		if(respData === null){
			console.log("starting up page")
			return;
		}

	}, [respData])

    function EmployeeList(){
        if(props.reset) return <div></div>;

        if(props.employees !== undefined){
            let listEmployees = [];
            for(var key in props.employees){
                let employee = props.employees[key];
                const listItem = 
                (<ul>
                    <li key="_id:">ObjectId: {employee["_id"]}</li>
                    <li key="firstName">First Name: {employee["firstName"]}</li>
                    <li key="lastName">Last Name: {employee["lastName"]}</li>
                    <li key="username">username: {employee["username"]}</li>
                    <li key="password">password: {employee["password"]}</li>
                </ul>);

                listEmployees.push({"obj": employee, "jsx": listItem});
            }
            return(
            <div>
                <ol>{listEmployees.map((emp) => <Link to={{ pathname:'/view-employee', eProps:{employee: emp["obj"], userData: props.userData}}}><li>{emp["jsx"]}</li></Link>)}</ol>
            </div>
            );
        }else{
            return <div></div>;
        }
    }

    return(
        <EmployeeList/>
  );
}