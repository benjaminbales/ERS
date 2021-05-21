import {React, useState, useEffect} from 'react'
import { Link, useHistory} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function AllResolvedRRs(props) {
	const [respData, setRespData] = useState(null);

    const history = useHistory();

    useEffect( () => {
		if(respData === null){
			console.log("starting up page")
			return;
		}

	}, [respData])

    function ResolvedRRsList(){
        if(props.reset) return <div></div>;

        if(props.resolved !== undefined){
            let listResolvedRRs = [];
            for(var key in props.resolved){
                let resolvedRR = props.resolved[key];
				if((resolvedRR["status"] === "Approved") || (resolvedRR["status"] === "Denied")){
                    let listItem;
					if((props.userData.role === "Employee") && (resolvedRR["employee"] === props.userData._id)){
						listItem = 
						(<ul>
							<li key="_id:">ObjectId: {resolvedRR["_id"]}</li>
							<li key="amount">Amount: {resolvedRR["amount"]}</li>
							<li key="date">Date: {resolvedRR["date"]}</li>
							<li key="employee">EmployeeId: {resolvedRR["employee"]}</li>
							<li key="manager">ManagerId: {resolvedRR["manager"]}</li>
						</ul>);
					}

					if(props.userData.role === "Manager"){
						if(props.filter === ""){
							listItem = 
							(<ul>
								<li key="_id:">ObjectId: {resolvedRR["_id"]}</li>
								<li key="amount">Amount: {resolvedRR["amount"]}</li>
								<li key="date">Date: {resolvedRR["date"]}</li>
								<li key="employee">EmployeeId: {resolvedRR["employee"]}</li>
								<li key="manager">ManagerId: {resolvedRR["manager"]}</li>
							</ul>);
						}

						if(props.filter === resolvedRR.employee){
							listItem = 
							(<ul>
								<li key="_id:">ObjectId: {resolvedRR["_id"]}</li>
								<li key="amount">Amount: {resolvedRR["amount"]}</li>
								<li key="date">Date: {resolvedRR["date"]}</li>
								<li key="employee">EmployeeId: {resolvedRR["employee"]}</li>
								<li key="manager">ManagerId: {resolvedRR["manager"]}</li>
							</ul>);
						}
					}

					if(listItem !== undefined){
						listResolvedRRs.push({"obj": resolvedRR, "jsx": listItem});
					}
				}
            }
            return(
            <div>
                <ol>{listResolvedRRs.map((rrr) => <Link to={{ pathname:'/view-rr', rrProps:{request: rrr["obj"], userData: props.userData}}}><li>{rrr["jsx"]}</li></Link>)}</ol>
            </div>
            );
        }else{
            return <div></div>;
        }
    }

    return(
        <ResolvedRRsList/>
  );
}