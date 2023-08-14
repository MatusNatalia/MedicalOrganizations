import React, {Component} from "react";
import axios from 'axios'

class Forms extends Component{

    constructor(props){
        super(props)
        if(props.specialist != undefined){
            this.state = {
                name : props.specialist.name,
                surname : props.specialist.surname,
                cabinet : props.specialist.cabinet,
                title : props.specialist.title,
                degree : props.specialist.degree,
                experience : props.specialist.experience
            }
        }
        else{
            this.state = {
                name : "",
                surname : "",
                cabinet : "",
                title : "",
                degree : "",
                experience : ""
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
                <label htmlFor='cabinet'>Кабинет:</label>
                <input
                    name='cabinet' 
                    placeholder='№'
                    defaultValue={this.state.cabinet}
                    onChange={this.handlePersonChange}
                />
                </div>
                <p></p>
                <div>
                <label htmlFor='title'>Звание:</label>
                <input
                    name='title' 
                    placeholder='Звание'
                    defaultValue={this.state.title}
                    onChange={this.handlePersonChange}
                />
                </div>
                <p></p>
                <div>
                <label htmlFor='degree'>Степень:</label>
                <input
                    name='degree' 
                    placeholder='Степень'
                    defaultValue={this.state.degree}
                    onChange={this.handlePersonChange}
                />
                </div>
                <p></p>
                <div>
                <label htmlFor='experience'>Стаж:</label>
                <input
                    name='experience' 
                    placeholder='Стаж'
                    defaultValue={this.state.experience}
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