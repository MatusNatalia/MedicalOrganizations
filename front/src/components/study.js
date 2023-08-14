import React, {Component} from "react";
import axios from 'axios';

class Study extends Component {

    constructor(props){
        super(props)
        this.state = {
            lab : "",
            patient : "",
            labs : [],
            patients : [],
            date : ""
        }
        this.handleLabChange = this.handleLabChange.bind(this);
        this.handlePatientChange = this.handlePatientChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.addStudy = this.addStudy.bind(this)
    }

    componentDidMount = async() => {
        let res = await axios.get("http://localhost:8080/laboratories")
        this.setState(prevState => ({
            labs : res.data,
            lab : res.data.at(0).id
       }));

       let re = await axios.get("http://localhost:8080/patient")
        this.setState(prevState => ({
            patients : re.data,
            patient : re.data.at(0).id
       }));
    }

    handleLabChange(event) {
        this.setState(prevState => ({ 
                lab : event.target.value
        }))
    }

    handlePatientChange(event) {
        this.setState(prevState => ({ 
                patient : event.target.value
        }))
    }

    handleDateChange(event) {
        this.setState(prevState => ({  
                date : event.target.value
        }))
    }

    addStudy = async() => {
        try{
            const result = await axios.post('http://localhost:8080/study', 
            {patient : this.state.patient, 
            laboratory: this.state.lab,
            date : this.state.date})
            alert("Запись сохранена")
        } catch (err) {
            alert(err.response.data)
        }
    }

    render(){
        let personsSelect;
                personsSelect = 
                <div>
            <div style={{display: "inline-block"}}>
                <p>Лаборатория:</p>
                <select value={this.state.lab} onChange={this.handleLabChange}>
                    { this.state.labs.map(s => <option value={s.id}>{s.name+", "+s.type}</option>)}
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
            <button onClick={() => this.addStudy()}>Записаться</button>
            </div>
        
        return (
        <div>
        {personsSelect}
        </div>
        )
    }


}

export default Study;