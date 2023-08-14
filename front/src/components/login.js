import React, {Component} from "react";
import axios from "axios";
axios.defaults.withCredentials=true

class Login extends Component{

    constructor(props){
        super(props)
        this.state = {
            username : "",
            password : "",
            log : false
        }
        this.handleChange = this.handleChange.bind(this);
        this.getRoles = this.getRoles.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name] : event.target.value
          })
    }

    getRoles = async() => {
        let res = await axios.get('http://localhost:8080/roles',
        {params : {
            username : this.state.username
        }})

        res.data.map(role => {
            if(role === "USER"){
                this.props.setUser(true)
            }
            if(role === "ADMIN"){
                this.props.setAdmin(true)
            }
        })
    }

    login = async() => {
        try{
        let res = await axios.post('http://localhost:8080/login?username='+this.state.username+"&password="+this.state.password)
        alert("Вы вошли как "+this.state.username)
        this.setState({log : true})
        this.props.LogIn(true)
        this.getRoles()
        } catch(err){
            if(err.response.status===401){
                alert("Неверный логин или пароль")
            }
        }
        
    }

    render(){
        if (!this.state.log){
        return (
        <div>
            <p></p>
            <form>
                <div>
                <label htmlFor='username'>Логин:</label>
                <input
                    name='username' 
                    placeholder='Логин'
                    defaultValue={this.state.username}
                    onChange={this.handleChange}
                />
                </div>
                <div>
                <label htmlFor='password'>Пароль:</label>
                <input
                    name='password' 
                    placeholder='Пароль'
                    defaultValue={this.state.password}
                    onChange={this.handleChange}
                />
                </div>
                <p></p>
            </form>
            <button onClick={() => this.login()}>Войти</button>
        </div>  
        )
    } 
    else {
        return (<div></div>)
    }
}
}

export default Login