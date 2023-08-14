import React, {Component} from "react";
import Specialists from "./specialists";
import Stuff from "./stuff";
import Visit from "./visit";
import Patient from "./patient";
import Organization from "./organization";
import Treatment from "./treatment";
import Operation from "./operation";
import Study from "./study";
import Archive from "./archive";
import Busy from "./busy";
import Login from "./login";
import Logout from "./logout";
import User from "./user";
import Work from "./work";
import axios from "axios";
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from 'react-router-dom';


class StartMenu extends Component{

    constructor(){
        super()
        this.state = {
            loggedIn : false,
            isUser : false,
            isAdmin : false
        }
        axios.post('http://localhost:8080/logout')
        this.LogIn = this.LogIn.bind(this)
        this.setUser = this.setUser.bind(this)
        this.setAdmin = this.setAdmin.bind(this)
    }

    handleError(err){
        console.log(err)
        if(err.response.status===401 || err.response.status===403){
            alert("Действие недоступно")
        }
        else{
            alert(err.response.data)
        }
    }

    LogIn(state){
        if(state){
        this.setState({
            loggedIn : state
        })
    }
    else{
        this.setState({
            loggedIn : state,
            isUser : false,
            isAdmin : false
        })
    }
    }

    setUser(state){
        this.setState({
            isUser : state
        })
    }

    setAdmin(state){
        this.setState({
            isAdmin : state
        })
    }

    render(){
        let login
        let logout
        let forUser
        let forAdmin
        if(this.state.loggedIn){
            logout = <Link to="/logout"><button>Выйти</button></Link>
            if(this.state.isUser || this.state.isAdmin){
                forUser = <div>
                    <Link to="/patients"><button>Пациенты</button></Link>
                <Link to="/busy"><button>Загрузка больниц</button></Link>
                <Link to="/work"><button>Выработка поликлиник</button></Link>
                <Link to="/visits"><button>Запись на прием</button></Link>
                <Link to="/treatments"><button>Запись на стационарное лечение</button></Link>
                <Link to="/operations"><button>Запись на операцию</button></Link>
                <Link to="/studies"><button>Запись на анализы</button></Link>
                <Link to="/archive"><button>Архив</button></Link>
                </div>
            }
            if(this.state.isAdmin){
                forAdmin = <Link to="/users"><button>Пользователи</button></Link>
            }
        }
        else{
            login = <Link to="/login"><button>Войти</button></Link>
        }
        return (
        <Router>
            <div>
                <Link to="/"><button>На главную</button></Link>
                <Link to="/organizations"><button>Организации</button></Link>
                <Link to="/specialists"><button>Специалисты</button></Link>
                <Link to="/stuff"><button>Персонал</button></Link>
                {forUser}
                {forAdmin}
                {login}
                {logout}
            <Routes>
                <Route path="/organizations" element={<Organization errorHandler={this.handleError}/> } />
                <Route path="/specialists" element={<Specialists errorHandler={this.handleError}/> } />
                <Route path="/stuff" element={<Stuff errorHandler={this.handleError}/> } />
                <Route path="/patients" element={<Patient errorHandler={this.handleError}/> } />
                <Route path="/busy" element={<Busy errorHandler={this.handleError}/> } />
                <Route path="/work" element={<Work errorHandler={this.handleError}/> } />
                <Route path="/visits" element={<Visit errorHandler={this.handleError}/> } />
                <Route path="/treatments" element={<Treatment errorHandler={this.handleError}/> } />
                <Route path="/operations" element={<Operation errorHandler={this.handleError}/> } />
                <Route path="/studies" element={<Study errorHandler={this.handleError}/> } />
                <Route path="/archive" element={<Archive errorHandler={this.handleError}/> } />
                <Route path="/users" element={<User errorHandler={this.handleError}/> } />
                <Route path="/login" element={<Login LogIn={this.LogIn} setUser={this.setUser} setAdmin={this.setAdmin}/> } />
                <Route path="/logout" element={<Logout LogIn={this.LogIn} setUser={this.setUser} setAdmin={this.setAdmin}/>} />
            </Routes>
            </div>
        </Router>
        )
    }
}

export default StartMenu