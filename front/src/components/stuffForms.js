import React, {Component} from "react";
import axios from 'axios'

class Forms extends Component{

    constructor(props){
        super(props)
        if(props.stuff != undefined){
            this.state = {
                name : props.stuff.name,
                surname : props.stuff.surname
            }
        }
        else{
            this.state = {
                name : "",
                surname : ""
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
            </form>
        </div>  
        )
    }
}

export default Forms