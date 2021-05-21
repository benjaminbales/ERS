import React    from 'react'
import { Link } from 'react-router-dom'
import Page     from '../page'

export default function Home(){
    return(
    <Page header="">
        <h2> Welcome to the Revature Expense Reimbursement System!</h2>
        <p>Click the Login tab to log in as either a manager
            or an employee.
        </p>
    </Page>
    );
}