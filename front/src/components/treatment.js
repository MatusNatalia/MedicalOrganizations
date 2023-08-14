import React, {Component} from "react";
import axios from 'axios'
import HospitalFilters from "./hospitalFilters";
import Error from "./error";

class Treatment extends Component {

    constructor(props){
        super(props)
        this.state = {
            specialist : "",
            patient : "",
            specialists : [],
            patients : [],
            date : "",
            state : "",
            error : false,
            errorMsg : "",
            select : false
        }
        this.Filter = React.createRef();
        this.handleSpecialistChange = this.handleSpecialistChange.bind(this);
        this.handlePatientChange = this.handlePatientChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleStateChange = this.handleStateChange.bind(this);
        this.handleTempChange = this.handleTempChange.bind(this);
        this.addTreatment = this.addTreatment.bind(this)
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
                ...prevState.patients,
                ...prevState.specialists,
                ...prevState.specialist,
                patient : event.target.value,
                ...prevState.date,
                ...prevState.error,
            ...prevState.errorMsg,
            ...prevState.select
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

    handleStateChange(event) {
        this.setState(prevState => ({  
                state : event.target.value
        }))
    }

    handleTempChange(event) {
        this.setState(prevState => ({  
                temp : event.target.value
        }))
    }

    getSpesialists = async() =>{
        const filter = this.Filter.current
        let res = await axios.get("http://localhost:8080/specialists/organization",
        { params: {organization : filter.state.organizationValue} })
        if(res.data.length>0){
        this.setState(prevState => ({
            specialists : res.data,
            specialist : res.data.at(0).id
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
        let res = await axios.get("http://localhost:8080/patient")
        if(res.data.length>0){
        this.setState(prevState => ({
            patients : res.data,
            patient : res.data.at(0).id
       }))}
       else{
        this.setState(prevState => ({
            patients : [],
            patient : "0"
       }))}
       
    } 

    addTreatment = async() => {
        const filter = this.Filter.current
        try{
            const result = await axios.post('http://localhost:8080/treatment', 
            {
            room : filter.state.room,
            patient : this.state.patient, 
            specialist: this.state.specialist,
            enterDate : this.state.date,
            state : this.state.state,
            temp : this.state.temp})
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
                    { this.state.specialists.map(s=> <option value={s.id}>{s.name+" "+s.surname}</option>)}
                </select>
                <p></p>
            </div>
            <div style={{display : "inline-block", margin : 30}}> 
                <p>Пациент:</p>
                <select value={this.state.patient} onChange={this.handlePatientChange}>
                    { this.state.patients.map(s=> <option value={s.id}>{s.name+" "+s.surname}</option>)}
                </select>
                <p></p>
            </div>  
            <p>Дата поступления:</p>
            <input
            name='end' 
            placeholder='ГГГГ-ММ-ДД'
            defaultValue={this.state.date}
            onChange={this.handleDateChange}
                />
            <p>Состояние:</p>
            <input
            name='state' 
            placeholder='состояние'
            defaultValue={this.state.state}
            onChange={this.handleStateChange}
                />
            <p>Температура:</p>
            <input
            name='temp' 
            placeholder='36.6'
            defaultValue={this.state.temp}
            onChange={this.handleTempChange}
                />
            <button onClick={() => this.addTreatment()}>Записаться</button>
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

export default Treatment;