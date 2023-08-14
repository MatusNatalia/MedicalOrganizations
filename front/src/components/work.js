import React, {Component} from "react";
import axios from 'axios'
import WorkTable from "./workTable";

class Work extends Component{

    constructor(){
        super()
        this.state = {
            end : "",
            start : ""
        }
    }

    componentDidMount = async() => {
        let resClinics = await axios.get("http://localhost:8080/clinics");
        this.setState(prevState => ({
            clinics : resClinics.data,
            clinic : resClinics.data.at(0).id
        }))

        let resSpecialists = await axios.get("http://localhost:8080/clinic+specialists");
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
        try{
        if(this.state.isClinic){
            res = await axios.get("http://localhost:8080/specialists/clinic+statistic",
            {params : {id : this.state.clinic,
                    start : this.state.start,
                    end : this.state.end}})
            this.setState(prevState => ({  
                data : res.data
            }))
        }
        else if(this.state.isType){
            res = await axios.get("http://localhost:8080/specialists/type+statistic",
            {params : {id : this.state.type,
                start : this.state.start,
                end : this.state.end}})
            this.setState(prevState => ({  
                data : res.data
            }))
        }
        else if(this.state.isSpecialist){
            res = await axios.get("http://localhost:8080/specialists/specialist+statistic",
            {params : {id : this.state.specialist,
                start : this.state.start,
                end : this.state.end}})
            this.setState(prevState => ({  
                data : res.data
            }))
        }
        this.setState({
            table : true
        })
    } catch(err){
        this.props.errorHandler(err)
    }
    }


    render(){
        let window
        let select
        if(this.state.isClinic){
        select =  <select value={this.state.clinic} onChange={(e) => {this.setState({clinic : e.target.value})}}>
                                    { this.state.clinics.map((clinic) => <option value={clinic.id}>{clinic.name}</option>)}
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
                <p></p>
            <button onClick={() => this.getInfo()}>Поиск</button>
            <p></p>
            </div>
        }
        let table
        if(this.state.table){
            table = <div><WorkTable data={this.state.data}></WorkTable></div>
        }
        
        return (
        <div>
            <button onClick={() => {this.setState({filters : true, isClinic : true, isSpecialist : false, isType : false, table : false})}}>По поликлиникам</button>
            <button onClick={() => {this.setState({filters : true, isClinic : false, isSpecialist : false, isType : true, table : false})}}>По профилям</button>
            <button onClick={() => {this.setState({filters : true, isClinic : false, isSpecialist : true, isType : false, table : false})}}>По специалистам</button>
            {window}
            {table}
        </div>  
        )
    }
}

export default Work