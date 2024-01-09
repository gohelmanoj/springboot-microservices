import React, { Component } from 'react';
import EmployeeService from '../service/EmployeeService';

class EmployeeComponent extends Component {
    
    constructor(props) {
        super(props);
        
        this.state = {
            employee : {},
            department: {},
            organization: {}
        }
    }
    
    componentDidMount() {
        EmployeeService.getEmployee().then((response) => {

            this.setState({employee : response.data.employee})
            this.setState({department: response.data.department})
            this.setState({organization: response.data.organization})

            console.log(this.state.employee)
            console.log(this.state.department)
            console.log(this.state.organization)
        })
    }

    render() {
        return (
            <div> <br /><br />
                <div className='col-md-6 card offset-md-3'>
                    <h3 className='text-center card-header'>Employee Detail</h3>
                    <div className='card-body'>
                        <div className='row'>
                            <p><strong>First Name: </strong>{this.state.employee.firstName}</p>
                        </div>
                        <div className='row'>
                            <p><strong>Last Name: </strong>{this.state.employee.lastName}</p>
                        </div>
                        <div className='row'>
                            <p><strong>Email: </strong>{this.state.employee.email}</p>
                        </div>
                    </div>

                    <h3 className='text-center card-header'>Department Detail</h3>
                    <div className='card-body'>
                        <div className='row'>
                            <p><strong>Name: </strong>{this.state.department.name}</p>
                        </div>
                        <div className='row'>
                            <p><strong>Description: </strong>{this.state.department.description}</p>
                        </div>
                        <div className='row'>
                            <p><strong>Code: </strong>{this.state.department.code}</p>
                        </div>
                    </div>

                    <h3 className='text-center card-header'>Organization Detail</h3>
                    <div className='card-body'>
                        <div className='row'>
                            <p><strong>Name: </strong>{this.state.organization.name}</p>
                        </div>
                        <div className='row'>
                            <p><strong>Description: </strong>{this.state.organization.description}</p>
                        </div>
                        <div className='row'>
                            <p><strong>Code: </strong>{this.state.organization.code}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default EmployeeComponent;