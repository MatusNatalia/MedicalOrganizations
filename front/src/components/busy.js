import React, {Component} from "react";
import axios from 'axios'
import BusyTable from "./busyTable";

class Busy extends Component{

    constructor(){
        super()
        this.state = {
        }
    }

    componentDidMount = async() => {
        let resHospitals = await axios.get("http://localhost:8080/hospitals");
        this.setState(prevState => ({
            hospitals : resHospitals.data,
            hospital : resHospitals.data.at(0).id
        }))

        let resSpecialists = await axios.get("http://localhost:8080/hospital+specialists");
        this.setState(prevState => ({
            specialists : resSpecialists.data,
            specialist : resSpecialists.data.at(0).id
        }))

        let resTypes = await axios.get("http://localhost:8080/specialists+types")
        this.setState(prevState => ({  
            types : resTypes.data,
            type : resTypes.data.at(0).id
        }))
    }

    getInfo = async() =>{
        let res
        if(this.state.isHospital){
            res = await axios.get("http://localhost:8080/treatment/hospital",
            {params : {id : this.state.hospital}})
            this.setState(prevState => ({  
                data : res.data
            }))
        }
        else if(this.state.isType){
            res = await axios.get("http://localhost:8080/treatment/type",
            {params : {id : this.state.type}})
            this.setState(prevState => ({  
                data : res.data
            }))
        }
        else if(this.state.isSpecialist){
            res = await axios.get("http://localhost:8080/treatment/specialist",
            {params : {id : this.state.specialist}})
            this.setState(prevState => ({  
                data : res.data
            }))
        }
        this.setState({
            table : true
        })
    }


    render(){
        let window
        let select
        if(this.state.isHospital){
        select =  <select value={this.state.hospital} onChange={(e) => {this.setState({hospital : e.target.value})}}>
                                    { this.state.hospitals.map(hospital => <option value={hospital.id}>{hospital.name}</option>)}
                                        </select>
        }
        if(this.state.isSpecialist){
        select =  <select value={this.state.specialist} onChange={(e) => {this.setState({specialist : e.target.value})}}>
        { this.state.specialists.map(specialist => <option value={specialist.id}>{specialist.name+" "+specialist.surname}</option>)}
            </select>
        }
        if(this.state.isType){
            select =  <select value={this.state.type} onChange={(e) => {this.setState({type : e.target.value})}}>
            { this.state.types.map(type => <option value={type.id}>{type.type}</option>)}
                </select>
        }
        
        if(this.state.filters){
        window = <div><p></p>
            {select}
            <p></p>
                <p></p>
            <button onClick={() => this.getInfo()}>Поиск</button>
            <p></p>
            </div>
        }
        let table
        if(this.state.table){
            table = <div><BusyTable data={this.state.data}></BusyTable></div>
        }
        
        return (
        <div>
            <button onClick={() => {this.setState({filters : true, isHospital : true, isSpecialist : false, isType : false, table : false})}}>Загрузка больницы</button>
            <button onClick={() => {this.setState({filters : true, isHospital : false, isSpecialist : false, isType : true, table : false})}}>Загрузка профильных специалистов</button>
            <button onClick={() => {this.setState({filters : true, isHospital : false, isSpecialist : true, isType : false, table : false})}}>Загрузка конкретного специалиста</button>
            {window}
            {table}
        </div>  
        )
    }
}

export default Busy