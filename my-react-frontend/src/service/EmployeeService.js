import axios from 'axios'

const EMPLOYEE_BASE_URL = "http://localhost:9191/api/employees";
const EMPLOYEE_EMAIL = "hello@gmail.com";

class EmployeeService {

    getEmployee() {
        return axios.get(EMPLOYEE_BASE_URL + "/" + EMPLOYEE_EMAIL);
    }
}

const exportedObject =  new EmployeeService();
export default exportedObject