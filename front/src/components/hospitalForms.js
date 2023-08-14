import React, {Component} from "react";

class HospitalForms extends Component{

    constructor(props){
        super(props)
        this.state = {
            name : props.hospital.name,
            number : props.hospital.number
        }
        this.handleHospitalChange = this.handleHospitalChange.bind(this);
    }

    handleHospitalChange(event) {
        this.setState({
            [event.target.name] : event.target.value
          })
    }

    handleBlock(event){
        this.setState({
            [event.target.name] : event.target.value
        })
    }



    render(){
        return (
        <div>
            <p></p>
            <form>
                <div>
                <label htmlFor='name'>Название:</label>
                <input
                    name='name' 
                    placeholder='Название'
                    defaultValue={this.state.name}
                    onChange={this.handleHospitalChange}
                />
                </div>
                <p></p>
                <div>
                <label htmlFor='number'>Номер:</label>
                <input
                    name='number' 
                    placeholder='Номер'
                    defaultValue={this.state.number}
                    onChange={this.handleHospitalChange}
                />
                </div>
                <p></p>
            </form>
        </div>  
        )
    }
}

export default HospitalForms