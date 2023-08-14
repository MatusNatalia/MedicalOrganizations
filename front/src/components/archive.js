import React, {Component} from "react";
import OperationTable from "./operationTable";
import OldTreatmentTable from "./oldTreatmentTable";
import axios from 'axios'

class Archive extends Component{

    constructor(){
        super()
        this.state = {
            filters : false,
            hospitals: [],
            specialists : [],
            specialist : "0",
            end : "",
            start : ""
        }
    }

    componentDidMount = async() => {
        let resHospitals = await axios.get("http://localhost:8080/hospitals");
        this.setState(prevState => ({
            hospitals : resHospitals.data,
            hospital : resHospitals.data.at(0).id
        }))
        this.getSpecialists(resHospitals.data.at(0).id)
    }

    getSpecialists = async(hospitalId) =>{
        let resSpecialists = await axios.get("http://localhost:8080/specialists/organization",
        {params : {organization : hospitalId}});
        this.setState(prevState => ({
            specialists : resSpecialists.data
        }))
    }

    getInfo = async() =>{
        if(this.state.isOp){
            try{
            let resOps = await axios.get("http://localhost:8080/operation",
            {params : {hospital : this.state.hospital, specialist : this.state.specialist,
            start : this.state.start, end : this.state.end}});
            this.setState({
                opTable : true,
                data : resOps.data
            })}
            catch(err){
                alert(err.response.data)
            }
        }
        else if(this.state.isPatients) {
            try{
            let resPatients = await axios.get("http://localhost:8080/old+treatment",
            {params : {organization : this.state.hospital, specialist : this.state.specialist,
                start : this.state.start, end : this.state.end}});
            this.setState({
                patientTable : true,
                data : resPatients.data
            })}
            catch(err){
                alert(err.response.data)
            }
        }
    }


    render(){
        let window
        let hospitalSelect
        let specialistSelect
        hospitalSelect =  <select value={this.state.hospital} onChange={(e) => {this.setState({hospital : e.target.value});this.getSpecialists(e.target.value)}}>
                                    { this.state.hospitals.map(hospital => <option value={hospital.id}>{hospital.name}</option>)}
                                        </select>
        specialistSelect =  <select value={this.state.specialist} onChange={(e) => {this.setState({specialist : e.target.value})}}>
            <option value="0">Все</option>
        { this.state.specialists.map(specialist => <option value={specialist.id}>{specialist.name+" "+specialist.surname}</option>)}
            </select>
        if(this.state.filters){
        window = <div><h4>Фильтры:</h4>
            {hospitalSelect}
            <p></p>
            {specialistSelect}
            <p></p>
            <p>Выберите период:</p>
                <label htmlFor='startDate'>С</label>
                <input
                    name='start' 
                    placeholder='ГГГГ-ММ-ДД'
                    defaultValue={this.state.start}
                    onChange={e => this.setState({start : e.target.value})}
                />
                <label htmlFor='endDate'>по</label>
                <input
                    name='end' 
                    placeholder='ГГГГ-ММ-ДД'
                    defaultValue={this.state.end}
                    onChange={e => this.setState({end : e.target.value})}
                />
                <p></p>
            <button onClick={() => this.getInfo()}>Поиск</button>
            <p></p>
            </div>
        }
        let table
        if(this.state.opTable){
            table = <OperationTable data={this.state.data}></OperationTable>
        }
        if(this.state.patientTable){
            table = <OldTreatmentTable data={this.state.data}></OldTreatmentTable>
        }
        
        return (
        <div>
            <button onClick={() => {this.setState({filters : true, isOp : true, isPatients : false, patientTable : false})}}>Операции</button>
            <button onClick={() => this.setState({filters : true, isOp : false, isPatients : true, opTable : false})}>Пациенты</button>
            {window}
            {table}
        </div>  
        )
    }
}

export default Archive