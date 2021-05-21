import {React, useState, useEffect} from 'react'
import { Link, useHistory} from 'react-router-dom'
import Page from '../page'
import axios from 'axios';

export default function AllPendingRRs(props) {
	const [respData, setRespData] = useState(null);

    const history = useHistory();

    useEffect( () => {
		if(respData === null){
			console.log("starting AllPendingRRs")
			return;
		}

	}, [respData])

    function PendingRRsList(){
        if(props.reset) return <div></div>;

        if(props.pending !== undefined){
            let listPendingRRs = [];
            for(var key in props.pending){
                let pendingRR = props.pending[key];
				if(pendingRR["status"] === "Pending"){
					let listItem;
					if((props.userData.role === "Employee") && (pendingRR["employee"] === props.userData._id)){
						listItem = 
						(<ul>
							<li key="_id:">ObjectId: {pendingRR["_id"]}</li>
							<li key="amount">Amount: {pendingRR["amount"]}</li>
							<li key="date">Date: {pendingRR["date"]}</li>
							<li key="employee">EmployeeId: {pendingRR["employee"]}</li>
							<li key="manager">ManagerId: {pendingRR["manager"]}</li>
						</ul>);
					}

					if(props.userData.role === "Manager"){
						if(props.filter === ""){
							listItem = 
							(<ul>
								<li key="_id:">ObjectId: {pendingRR["_id"]}</li>
								<li key="amount">Amount: {pendingRR["amount"]}</li>
								<li key="date">Date: {pendingRR["date"]}</li>
								<li key="employee">EmployeeId: {pendingRR["employee"]}</li>
								<li key="manager">ManagerId: {pendingRR["manager"]}</li>
							</ul>);
						}

						if(props.filter === pendingRR.employee){
							listItem = 
							(<ul>
								<li key="_id:">ObjectId: {pendingRR["_id"]}</li>
								<li key="amount">Amount: {pendingRR["amount"]}</li>
								<li key="date">Date: {pendingRR["date"]}</li>
								<li key="employee">EmployeeId: {pendingRR["employee"]}</li>
								<li key="manager">ManagerId: {pendingRR["manager"]}</li>
							</ul>);
						}
					}

					if(listItem !== undefined){
						listPendingRRs.push({"obj": pendingRR, "jsx": listItem});
					}
				}
			}
			return(
				<div>
					<ol>{listPendingRRs.map((prr) => <Link to={{ pathname:'/view-rr', rrProps:{request: prr["obj"], userData: props.userData}}}><li>{prr["jsx"]}</li></Link>)}</ol>
				</div>
				);
        }else{
            return <div></div>;
        }
    }

    return(
        <PendingRRsList/>
  );
}