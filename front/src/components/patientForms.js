import React, {Component} from "react";

class Forms extends Component{

    constructor(props){
        super(props)
        if(props.patient != undefined){
            this.state = {
                name : props.patient.name,
                surname : props.patient.surname,
                phone : props.patient.phone,
                address : props.patient.address
            }
        }
        else{
            this.state = {
                name : "",
                surname : "",
                phone : "",
                address : ""
            }
        }
        this.handlePersonChange = this.handlePersonChange.bind(this);
    }

    handlePersonChange(event) {
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
                <label htmlFor='name'>Имя:</label>
                <input
                    name='name' 
                    placeholder='Имя'
                    defaultValue={this.state.name}
                    onChange={this.handlePersonChange}
                />
                </div>
                <p></p>
                <div>
                <label htmlFor='surname'>Фамилия:</label>
                <input
                    name='surname' 
                    placeholder='Фамилия'
                    defaultValue={this.state.surname}
                    onChange={this.handlePersonChange}
                />
                </div>
                <p></p>
                <div>
                <label htmlFor='phone'>Номер телефона:</label>
                <input
                    name='phone' 
                    placeholder='Номер'
                    defaultValue={this.state.phone}
                    onChange={this.handlePersonChange}
                />
                </div>
                <p></p>
                <div>
                <label htmlFor='address'>Адрес:</label>
                <input
                    name='address' 
                    placeholder='Адрес'
                    defaultValue={this.state.address}
                    onChange={this.handlePersonChange}
                />
                </div>
                <p></p>
            </form>
        </div>  
        )
    }
}

export default Forms