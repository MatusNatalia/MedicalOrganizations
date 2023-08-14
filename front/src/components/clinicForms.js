import React, {Component} from "react";
import axios from 'axios'
import Error from "./error";

class ClinicForms extends Component{

    constructor(props){
        super(props)
        this.state = {
            name : props.clinic.name,
            id : props.clinic.id,
            laboratories : props.laboratories
        }
        this.handleCabinet = this.handleCabinet.bind(this)
    }

    handleCabinet(event){
        this.setState({
            cabinet : event.target.value
        })
    }

    addCabinet = async() => {
        try{
            const result = await axios.post('http://localhost:8080/clinic/add+cabinet', 
            { number: this.state.cabinet, 
                clinic : this.state.name
            })
            alert("Кабинет добавлен")
        } catch (err) {
            this.setState({
                errorMsg : err.response.data,
                error : true
            });
        }
    } 

    render(){
        let error;
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        return (
        <div>
            <p></p>
            <p></p>
            <p>Добавить кабинет:</p>
            <label htmlFor='cabinet'>Номер кабинета:</label>
                <input
                    name='cabinet' 
                    placeholder='Номер'
                    onChange={this.handleCabinet}
                />
            <button onClick={this.addCabinet}>Добавить</button>
            {error}
            <p></p>
        </div>  
        )
    }
}

export default ClinicForms