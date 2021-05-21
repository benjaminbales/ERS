import axios from 'axios';

export const createEmployee = (data, setRespData) => {
	axios.post('http://localhost:7777/create-employee', data)
	.then( res => {
		setRespData(res.data);
	})
}

export const findEmployee = (data, setRespData) => {
	axios.post('http://localhost:7777/view-employee', data)
	.then( res => {
		setRespData(res.data);
	})
}
