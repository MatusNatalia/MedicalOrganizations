import React, {Component} from "react";
import axios from "axios";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class User extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            users : [],
            roles : [],
            user : true,
            admin : false
        }
    }

    componentDidMount = async() => {
        let res = await axios.get("http://localhost:8080/users");
        this.setState(prevState => ({  
            users : res.data
        }))
    }

    handleChange(event) {
        this.setState(prevState => ({  
                [event.target.name] : event.target.value
        }))
    }

    handleSave = async() => {
        let r = []
        if(this.state.user){
            r.push("USER")
        }
        if(this.state.admin){
            r.push("ADMIN")
        }
        try{
            const result = await axios.post('http://localhost:8080/users', 
            { 
            login : this.state.new_username,
            password : this.state.new_password,
            roles : r
            })
            alert("Пользователь добавлен")
        } catch (err) {
           alert(err.response.data)
        }
    }

    handleDelete = async(user) => {
        try{
            const result = await axios.delete('http://localhost:8080/users/'+user.id)
            alert("Пользователь удален")
        } catch (err) {
           alert(err.response.data)
        }
    }
    
    render() {
        let data = Array.from(this.state.users);
        return (
            <div>
                <p></p>
                <h4>Пользователи:</h4>
                <table>
                <thead>
                <tr>
                <th>Логин</th>
                <th>Пароль</th>
                <th>Роли</th>
                <th>Удалить</th>
                </tr>
                </thead>
                <tbody>
                {data.map(user => (
                    <tr>
                        <td>{user.login}</td>
                        <td>{user.password}</td>
                        <td>{user.roles.map((role) => (
                            <p>{role}</p>
                        ))}
                        </td>
                        <td class="link" onClick={() => this.handleDelete(user)}>Удалить</td>
                    </tr>
                ))}
                </tbody>
                </table>
                <h4>Добавить пользователя:</h4>
                <table>
                <thead>
                <tr>
                <th>Логин</th>
                <th>Пароль</th>
                <th>Роли</th>
                <th>Сохранить</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input onChange={(event)=>{this.setState({new_username : event.target.value})}}></input></td>
                        <td><input onChange={(event)=>{this.setState({new_password : event.target.value})}}></input></td>
                        <td>
                        <input type="checkbox" defaultChecked="true" name="user" value="USER" onChange={
                            (e) => {this.setState({user : e.target.checked})}}/><label>USER</label>
                        <input type="checkbox" name="admin" value="ADMIN" onClick={(e) => {this.setState({admin : e.target.checked})}}/><label>ADMIN</label>
                        </td>
                        <td class="link" onClick={() => this.handleSave()}>Сохранить</td>
                    </tr>
                </tbody>
                </table>
            </div>
        )
        }
    }

export default User