import React, {Component} from "react";
import axios from 'axios'
import HospitalFilters from "./hospitalFilters";
import Error from "./error";

class Operation extends Component {

    constructor(props){
        super(props)
        this.state = {
            specialist : "",
            patient : "",
            specialists : [],
            patients : [],
            date : "",
            error : false,
            errorMsg : "",
            select : false
        }
        this.Filter = React.createRef();
        this.handleSpecialistChange = this.handleSpecialistChange.bind(this);
        this.handlePatientChange = this.handlePatientChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.addOperation = this.addOperation.bind(this)
        this.getSpesialists = this.getSpesialists.bind(this);
    }

    handleSpecialistChange(event) {
        this.setState(prevState => ({ 
                ...prevState.patients,
                ...prevState.specialists,
                ...prevState.patient,
                specialist : event.target.value,
                ...prevState.date,
                ...prevState.error,
            ...prevState.errorMsg,
            ...prevState.select
        }))
    }

    handlePatientChange(event) {
        this.setState(prevState => ({
                patient : event.target.value
        }))
    }

    handleDateChange(event) {
        this.setState(prevState => ({  
                ...prevState.patients,
                ...prevState.specialists,
                ...prevState.patient,
                ...prevState.specialist,
                date : event.target.value,
                ...prevState.error,
            ...prevState.errorMsg,
            ...prevState.select
        }))
    }

    getSpesialists = async() =>{
        const filter = this.Filter.current
        let res = await axios.get("http://localhost:8080/specialists/organization",
        { params: {organization : filter.state.organizationValue} })
        if(res.data.length>0){
        this.setState(prevState => ({
            specialists : res.data,
            specialist : res.data.at(0)
       }))
        }
        else{
            this.setState(prevState => ({
                specialists : [],
                specialist : "0"
           }))
        }
    } 

    getPatients = async() =>{
        const filter = this.Filter.current
        let res = await axios.get("http://localhost:8080/treatment",
        { params: { organization : filter.state.organizationValue,
        department : filter.state.department, room : filter.state.room} })
        if(res.data.length>0){
        this.setState(prevState => ({
            patients : res.data,
            patient : res.data.at(0).id,
            patientName : res.data.at(0).patientName
       }))
        }
        else{
            this.setState(prevState => ({
                patients : [],
                patient : "0",
                patientName : ""
        }))
        }
    } 

    addOperation = async() => {
        const filter = this.Filter.current
        try{
            const result = await axios.post('http://localhost:8080/operation', 
            {
            treatment : this.state.patient,
            patientName : this.state.patientName,
            specialist : this.state.specialist.id,
            specialistName : this.state.specialist.name + " "  + this.state.specialist.surname,
            date : this.state.date})
            alert("Запись сохранена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    showSelect(){
        this.getSpesialists()
        this.getPatients()
        if(this.state.select){
            this.setState(prevState => ({
                ...prevState.patients,
                ...prevState.specialists,
                ...prevState.patient,
                ...prevState.specialist,
                ...prevState.date,
                ...prevState.error,
            ...prevState.errorMsg,
            select : false
            }));
        }
        else{
            this.setState(prevState => ({
                ...prevState.patients,
                ...prevState.specialists,
                ...prevState.patient,
                ...prevState.specialist,
                ...prevState.date,
                ...prevState.error,
            ...prevState.errorMsg,
            select : true
            }));
        }
    }

    render(){
        let organizationSelect;
        organizationSelect = <div><HospitalFilters ref={this.Filter}></HospitalFilters></div>
        let personsSelect;
        if(this.state.select){
                personsSelect = 
                <div>
            <div style={{display: "inline-block"}}>
                <p>Специалист:</p>
                <select value={this.state.specialist} onChange={this.handleSpecialistChange}>
                    { this.state.specialists.map(s=> <option value={s}>{s.name+" "+s.surname}</option>)}
                </select>
                <p></p>
            </div>
            <div style={{display : "inline-block", margin : 30}}> 
                <p>Пациент:</p>
                <select value={this.state.patient} onChange={this.handlePatientChange}>
                    { this.state.patients.map(s=> <option value={s}>{s.patientName}</option>)}
                </select>
                <p></p>
            </div>  
            <p>Дата:</p>
            <input
            name='end' 
            placeholder='ГГГГ-ММ-ДД'
            defaultValue={this.state.date}
            onChange={this.handleDateChange}
                />
            <button onClick={() => this.addOperation()}>Сохранить</button>
            </div>
        }
        let error
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        return (
        <div>
        {organizationSelect}
        <button onClick={() => this.showSelect()}>Далее</button>
        {personsSelect}
        {error}
        </div>
        )
    }


}

export default Operation;