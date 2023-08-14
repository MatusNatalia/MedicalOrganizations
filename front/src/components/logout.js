import React, {Component} from "react";
import axios from "axios";
axios.defaults.withCredentials=true

class Logout extends Component{

    constructor(props){
        super(props)
        this.state = {
            log : false
        }
    }

    logout = async() => {
        try{
        let res = await axios.post('http://localhost:8080/logout')
        alert("Вы вышли из системы")
        this.setState({log : true})
        this.props.LogIn(false)
        } catch(err){
            alert(err)
        }
        
    }

    render(){
        if(!this.state.log){
        return (
        <div>
            <p></p>
            <button onClick={() => this.logout()}>Выйти</button>
        </div>  
        )
    }
    else{
        return <div></div>
    }
}
}
export default Logout