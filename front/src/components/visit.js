import React, {Component} from "react";
import axios from 'axios'
import Filters from "./filters";
import Error from "./error";

class Visit extends Component {

    constructor(props){
        super(props)
        this.state = {
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
        this.addVisit = this.addVisit.bind(this)
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

    getSpesialists = async() =>{
        const filter = this.Filter.current
        let res = await axios.get("http://localhost:8080/specialists/type",
        { params: { type: filter.state.typeValue,  organization : filter.state.organizationValue, experience : 0} })
        if(res.data.length > 0){
        this.setState(prevState => ({
            ...prevState.patients,
            specialists : res.data,
            ...prevState.patient,
            specialist : res.data.at(0).id,
            ...prevState.date,
            ...prevState.error,
            ...prevState.errorMsg,
            ...prevState.select
       }));
    }
    } 

    getPatients = async() =>{
        const filter = this.Filter.current
        try{
        let res = await axios.get("http://localhost:8080/patient+organization",
        { params: { organization : filter.state.organizationValue} })
        if(res.data.length>0){
        this.setState(prevState => ({
            ...prevState.specialists,
            patients : res.data,
            patient : res.data.at(0).id,
            ...prevState.specialist,
            ...prevState.date,
            ...prevState.error,
            ...prevState.errorMsg,
            ...prevState.select
       }));}
    } catch(err){
        this.props.errorHandler(err)
    }
    } 

    addVisit = async() => {
        try{
            const result = await axios.post('http://localhost:8080/visit', 
            {patient : this.state.patient, 
            specialist: this.state.specialist,
            date : this.state.date})
            alert("Запись на "+this.state.date+" сохранена")
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
        organizationSelect = <div><Filters ref={this.Filter} profiles="true" onlyClinics="true" url="http://localhost:8080/specialists+types" defaultType="терапевт"></Filters></div>
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
                <p>Дата:</p>
                <input
                name='end' 
                placeholder='ГГГГ-ММ-ДД'
                defaultValue={this.state.date}
                onChange={this.handleDateChange}
                    />
            <p></p>
            <button onClick={() => this.addVisit()}>Записаться</button>
            </div>
        }
        let error
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        return (
        <div>
        {organizationSelect}
        <button onClick={() => this.showSelect()}>Выбрать специалиста</button>
        {personsSelect}
        {error}
        </div>
        )
    }


}

export default Visit;