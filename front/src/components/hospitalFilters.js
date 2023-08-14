import React, {Component} from "react";
import axios from 'axios'

class HospitalFilters extends Component{

    constructor(props){
        super(props)
        this.state = {
            hospitals: [],
            departments : [],
            rooms : [],
            room : "0",
            department : "0"
        }
        this.handleOrganizationChange = this.handleOrganizationChange.bind(this);
        this.handlDepartmentChange = this.handleDepartmentChange.bind(this);
        this.handleRoomChange = this.handleRoomChange.bind(this);
    }

    componentDidMount = async() => {
        let resHospitals = await axios.get("http://localhost:8080/hospitals")
        let org = Array.from(resHospitals.data)
        this.setState(prevState => ({ 
            organizationValue : org.at(0).id,
            hospitals : resHospitals.data,
            ...prevState.departments,
            ...prevState.department
        }))
        if(resHospitals.data.length > 0){
        let resDepartments = await axios.get("http://localhost:8080/departments",
        {params : {id : org.at(0).id}});
        let d = Array.from(resDepartments.data)
        if(d.length > 0){
        this.setState(prevState => ({ 
            ...prevState.organizationValue,
            departments : resDepartments.data,
            ...prevState.hospitals,
            department : d.at(0).id
        }))
    
        let resRooms = await axios.get("http://localhost:8080/rooms",
        {params : {id : d.at(0).id}});
        this.setState(prevState => ({ 
            rooms : resRooms.data,
            room : resRooms.data.at(0).id
        }))
        }
        else{
            this.setState(prevState => ({ 
                rooms : [],
                room : "0"
            }))
        }}
    }


    handleOrganizationChange = async(event) => {
        this.setState(prevState => ({  
                organizationValue : event.target.value,
                departments : [],
                rooms : [],
                department : "0",
                room : "0"
        }))
        let resDepartments = await axios.get("http://localhost:8080/departments",
        {params : {id : event.target.value}});
        let d = Array.from(resDepartments.data)
        if(d.length > 0){
        this.setState(prevState => ({ 
            departments : resDepartments.data,
            department : resDepartments.data.at(0).id
        }))
        let resRooms = await axios.get("http://localhost:8080/rooms",
        {params : {id : d.at(0).id}});
        let r = Array.from(resRooms.data)
        if(r.length > 0){
        this.setState(prevState => ({ 
            rooms : resRooms.data,
            room : resRooms.data.at(0).id
        }))
        }
    }
        
    }

    handleDepartmentChange = async(event) => {
        this.setState(prevState => ({  
                department : event.target.value,
                ...prevState.hospitals,
                ...prevState.departments
        }))
        if(event.target.value !== "0"){
        let resRooms = await axios.get("http://localhost:8080/rooms",
        {params : {id : event.target.value}});
        let r = Array.from(resRooms.data)
        if(r.length > 0){
        this.setState(prevState => ({ 
            rooms : resRooms.data,
            room : resRooms.data.at(0).id
        }))
        }
        else{
            this.setState(prevState => ({ 
                rooms : [],
                room : "0"
            }))
        }

    }
    else{
        this.setState(prevState => ({ 
            rooms : [],
            room : "0"
        }))
    }
    }

    handleRoomChange(event){
        this.setState(prevState => ({  
                room : event.target.value
        }))
    }


    render(){
        let organizationSelect
        let departmentSelect
        let roomSelect
        organizationSelect =  <select value={this.state.organizationValue} onChange={this.handleOrganizationChange}>
                            { this.state.hospitals.map(hospital => <option value={hospital.id}>{hospital.name}</option>)}
                            </select>
        departmentSelect =  <select value={this.state.department} onChange={this.handleDepartmentChange}>
            <option value="0" onChange={this.handleDepartmentChange}>Все</option>
        { this.state.departments.map(d => <option value={d.id}>{d.name}</option>)}
        </select>
        roomSelect =  <select value={this.state.room} onChange={this.handleRoomChange}>
            <option value="0" onChange={this.handleRoomChange}>Все</option>
        { this.state.rooms.map(d => <option value={d.id}>{d.number}</option>)}
        </select>
        return (
        <div> 
            <h4>Фильтры:</h4>
            {organizationSelect}
            <p></p>
            {departmentSelect}
            <p></p>
            {roomSelect}
        </div>  
        )
    }
}

export default HospitalFilters