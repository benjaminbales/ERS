#  Employee Reimbursement System Revature

## Project Description
ERS is a full stack web application that enables two types of user, an employee and a manager, to login and perform various actions.  Employees can submit new reimbursement requests, view their pending and resolved requests, and update their personal information.  Managers can view all pending and resolved  requests for all employees in the system and approve or deny them.  They can also filter by the employee ID to see the requests for individual empoyees, and they can register new employees.

## Technologies Used
- Java 1.8
- Javalin 3.13.6
- Mongo JDBC 3.12.8
- MongoDB 4.4.4
- JUnit 4.12
- SLF4J 1.7.12
- HTML/CSS/Javascript
- NodeJS 6.14.12
- React 17.0.2
- Axios 0.21.1

## Features

### Implemented
- An Employee can login
- An Employee can view the Employee Homepage
- An Employee can logout
- An Employee can submit a reimbursement request
- An Employee can view their pending reimbursement requests
- An Employee can view their resolved reimbursement requests
- An Employee can view their information
- An Employee can update their information
- A Manager can login
- A Manager can view the Manager Homepage
- A Manager can logout
- A Manager can approve/deny pending reimbursement requests
- A Manager can view all pending requests from all employees
- A Manager can view all resolved requests from all employees and see which manager resolved it
- A Manager can view all Employees
- A Manager can view reimbursement requests from a single Employee
- A Manager can register an Employee

### TODO
- When a manager registers a new employee, send an email notification to the employee with instructions on how to sign in.
- All users receive an email to reset their password from a password reset page in the client application.

## Getting Started
To install the client and server applications, click on the follow links to install the pre-requisite software.

1) [Intellij](https://www.jetbrains.com/help/idea/installation-guide.html)
2) [Visual Studio Code](https://code.visualstudio.com/download)
3) [JDK 1.8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
4) [NodeJS](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
5) [MongoDB](https://docs.mongodb.com/manual/installation/)

6) Open a terminal or powershell and clone this repo:

```git clone https://github.com/benjaminbales/ERS.git```

7) Navigate to your home directory and enter the following command to start MongoDB:

```mongod --dbpath mongo-data/```

8) Create the application database, and intialize the "managers" collection a single document by entering the following commands in a terminal or powershell:

```mongo```

```use ers```

```db.managers.insertOne({"firstName" : "Bob", "lastName" : "Dole", "password" : "bob", "username" : "bob"})```

10) Open the Client application in VS Code, open a terminal in VS Code, and enter the follow commands:

```cd app```

```npm install axios```

```npm install react-router-dom```

10) Open the Server application in Intellij and wait for gradle to finish building the application. Then, open main.java in the package com.ex and click the green play button to the left of the main method.  This should launch the server listening for HTTP requests on port 7777.
11) Back in VS Code, in the terminal, type:

```npm start```

12) This automatically opens a tab in your default web browser at http://localhost:3000.  If not, then copy/past the link in your browser, and you should see the following:



## Usage

1) Click on the login tab and enter the default manager username and password.  
2) This will re-direct you to the manager home page where can see an empty table with options to view a list of employees, and pending and resolved reimbursement requests.  
3) Create an employee using the "register employee" button.  
4) Then, log out and log back in as an employee.  
5) You'll see a similar empty table, but now you have a button to create a new reimbursment request.  Click it, submit a request with some arbitrary amount, and log out.  
6) Log back in as the manager, and verify that you can view both the employee and the pending request by clicking the "Get" buttons in their respective table columns.  
7) Then, click on the pending request and click the button to approve the request.  
8) Finally, log out and log back in as the employee and verify that the pending request now appears in the resolved column and not in the pending column. 


## Contributors
[Benjamin Bales](https://github.com/benjaminbales)
