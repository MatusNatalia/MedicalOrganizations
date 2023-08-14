import React, {Component} from "react";
import axios from 'axios'
import Filters from "./filters";
import Forms from "./stuffForms";
import Error from "./error";
import StuffTable from "./stuffTable";

class Stuff extends Component{

    constructor(){
        super()
        this.state = {
            filters : false,
            forms : false,
            update : false,
            table : false,
            error : false,
            errorMsg : "",
            data : []
        }
        this.Filter = React.createRef();
        this.Form = React.createRef();
        this.getStuff = this.getStuff.bind(this)
        this.addStuff = this.addStuff.bind(this)
        this.showUpdate = this.showUpdate.bind(this)
    }

    showFilters(){
        if(this.state.filters){
            this.setState({
                filters : false,
                forms : false,
                update : false,
                table : false,
                errorMsg : "",
                error : false
            });
        }
        else{
            this.setState({
                filters : true,
                forms : false,
                update : false,
                table : false,
                errorMsg : "",
                error : false
            });
        }
    }

    showForms(){
        if(this.state.forms){
            this.setState({
                filters : false,
                forms : false,
                update : false,
                table : false,
                errorMsg : "",
                error : false
            });
        }
        else{
            this.setState({
                filters : false,
                forms : true,
                update : false,
                table : false,
                errorMsg : "",
                error : false
            });
        }
    }

    showUpdate(stuff){
         this.setState({
            filters : false,
            forms : false,
            update : true,
            table : false,
            errorMsg : "",
            error : false,
            stuff : stuff
        });
    }

    getStuff = async() => {
        const filter = this.Filter.current
        let res = await axios.get("http://localhost:8080/stuff/type",
         { params: { type: filter.state.typeValue,  organization : filter.state.organizationValue} })
         this.setState({
            filters : true,
            forms : false,
            table : true,
            error : false,
            errorMsg : "",
            data : res.data
        });
    }

    addStuff = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.post('http://localhost:8080/stuff', 
            { name: form.state.name, 
            surname: form.state.surname,
            organization : filter.state.organizationValue,
            type : filter.state.typeValue})
            alert("Запись сохранена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    updateStuff = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.put('http://localhost:8080/stuff', 
            {
            id : this.state.stuff.id, 
            name: form.state.name, 
            surname: form.state.surname,
            organization : this.state.stuff.organization,
            type : filter.state.typeValue})
            alert("Запись обновлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    deleteStuff = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.delete('http://localhost:8080/stuff', 
            { params: { id: this.state.stuff.id}})
            alert("Запись удалена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    render(){
        let window
        let error
        if (this.state.filters){
            let table
            if(this.state.table){
                table = <div>
                <StuffTable data={this.state.data} handleUpdate={this.showUpdate}></StuffTable></div>
            }
            window = <div><h4>Фильтры:</h4>
            <Filters ref={this.Filter} profiles="true" onlyClinics="false" url="http://localhost:8080/stuff+types" defaultType="медсестра"></Filters>
            <button onClick={() => this.getStuff()}>Поиск</button>
            <p></p>
            {table}
            </div>
        }
        else if(this.state.forms){
            window = <div><h4>Заполните поля:</h4>
            <Filters ref={this.Filter} profiles="true" onlyClinics="false" url="http://localhost:8080/stuff+types" defaultType="медсестра"></Filters>
            <Forms ref={this.Form}></Forms>
            <button onClick={() => this.addStuff()}>Сохранить</button>
            </div>
        }
        else if(this.state.update){
            window = <div><h4>Измените поля:</h4>
            <Filters ref={this.Filter} profiles="true" onlyClinics="false" specialist={this.state.stuff} url="http://localhost:8080/stuff+types" defaultType="медсестра"></Filters>
            <Forms ref={this.Form} stuff={this.state.stuff}></Forms>
            <button onClick={() => this.updateStuff()}>Обновить</button>
            <button onClick={() => this.deleteStuff()}>Удалить</button>
            </div>
        }
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        return (
        <div>
            <button onClick={() => this.showFilters()}>Найти персонал</button>
            <button onClick={() => this.showForms()}>Добавить персонал</button>
            {window}
            {error}
        </div>  
        )
    }
}

export default Stuff