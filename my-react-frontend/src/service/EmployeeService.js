import axios from 'axios'

const EMPLOYEE_BASE_URL = "http://localhost:9191/api/employees";
const EMPLOYEE_EMAIL = "my@gmail.com";

class EmployeeService {

    getEmployee() {
        return axios.get(EMPLOYEE_BASE_URL + "/" + EMPLOYEE_EMAIL);
    }
}

export default new EmployeeService