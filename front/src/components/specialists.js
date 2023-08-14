import React, {Component} from "react";
import axios from 'axios'
import Filters from "./filters";
import Forms from "./specialistForms";
import Error from "./error";
import SpecialistsTable from "./specialistsTable";

class Specialists extends Component{

    constructor(){
        super()
        this.state = {
            filters : false,
            forms : false,
            update : false,
            table : false,
            error : false,
            errorMsg : "",
            exp : 0,
            op : 0,
            degree : "кандидат",
            title : "доцент",
            data : []
        }
        this.Filter = React.createRef();
        this.Form = React.createRef();
        this.getSpecialists = this.getSpecialists.bind(this)
        this.addSpecialist = this.addSpecialist.bind(this)
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

    showUpdate(specialist){
         this.setState({
            filters : false,
            forms : false,
            update : true,
            table : false,
            errorMsg : "",
            error : false,
            specialist : specialist
        });
    }

    getSpecialists = async() => {
        const filter = this.Filter.current
        if(this.state.isOperating && this.state.op > 0){
            let res = await axios.get("http://localhost:8080/operating+specialists/type",
         { params: { type: filter.state.typeValue,  organization : filter.state.organizationValue, operations : this.state.op} })
         this.setState({
            filters : true,
            forms : false,
            table : true,
            error : false,
            errorMsg : "",
            data : res.data
        })
        }
        else if(this.state.isDegree){
            let res = await axios.get("http://localhost:8080/specialists/degree",
         { params: { type: filter.state.typeValue,  organization : filter.state.organizationValue, degree : this.state.degree, title : this.state.title} })
         this.setState({
            filters : true,
            forms : false,
            table : true,
            error : false,
            errorMsg : "",
            data : res.data
        })
        }
        else{
        let res = await axios.get("http://localhost:8080/specialists/type",
         { params: { type: filter.state.typeValue,  organization : filter.state.organizationValue, experience : this.state.exp} })
         this.setState({
            filters : true,
            forms : false,
            table : true,
            error : false,
            errorMsg : "",
            data : res.data
        })
    }
    }

    addSpecialist = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.post('http://localhost:8080/specialists', 
            { name: form.state.name, 
            surname: form.state.surname,
            organization : filter.state.organizationValue,
            cabinet : form.state.cabinet,
            type : filter.state.typeValue,
            title : form.state.title,
            degree : form.state.degree,
            experience : form.state.experience})
            alert("Запись сохранена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    updateSpecialist = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.put('http://localhost:8080/specialists', 
            {
            id : this.state.specialist.id, 
            name: form.state.name, 
            surname: form.state.surname,
            organization : this.state.specialist.organization,
            cabinet : form.state.cabinet,
            type : filter.state.typeValue,
            title : form.state.title,
            degree : form.state.degree,
            experience : form.state.experience})
            alert("Запись обновлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    deleteSpecialist = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.delete('http://localhost:8080/specialists', 
            { params: { id: this.state.specialist.id}})
            alert("Запись удалена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    render(){
        let window
        let error
        let experience
        let operations
        let degree
        if(this.state.isExp){
            experience = <div>
            <label htmlFor='exp'>Стаж:</label>
            <input
                name='exp' 
                placeholder='Стаж'
                defaultValue="0"
                onChange={(event) => {this.setState({exp : event.target.value})}}
            /></div>
        }
        if(this.state.isOperating){
            experience = <div>
            <label htmlFor='op'>Проведенных операций:</label>
            <input
                name='op' 
                placeholder='Число операций'
                defaultValue="0"
                onChange={(event) => {this.setState({op : event.target.value})}}
            /></div>
        }
        if(this.state.isDegree){
            degree = <div>
                <input type="radio" defaultChecked="true" name="degree" value="Кандидат" onChange={() => {this.setState({degree:"кандидат"})}}/><label>кандидат мед. наук</label>
                <input type="radio" name="degree" value="Доктор" onChange={() => {this.setState({degree:"доктор"})}}/><label>доктор мед. наук</label>
                <p></p>
                <input type="radio" name="title" defaultChecked="true" value="Доцент" onChange={() => {this.setState({title:"доцент"})}}/><label>доцент</label>
                <input type="radio" name="title" value="Профессор" onChange={() => {this.setState({title:"профессор"})}}/><label>профессор</label>
            </div>
        }
        if (this.state.filters){
            let table
            if(this.state.table){
                table = <div>
                <SpecialistsTable data={this.state.data} handleUpdate={this.showUpdate}></SpecialistsTable></div>
            }
            window = <div><h4>Фильтры:</h4>
            <Filters ref={this.Filter} profiles="true" onlyClinics="false" url="http://localhost:8080/specialists+types" defaultType="терапевт"></Filters>
            {experience}
            {operations}
            {degree}
            <p></p>
            <button onClick={() => this.getSpecialists()}>Поиск</button>
            <p></p>
            {table}
            </div>
        }
        else if(this.state.forms){
            window = <div><h4>Заполните поля:</h4>
            <Filters ref={this.Filter} profiles="true" onlyClinics="false" url="http://localhost:8080/specialists+types" defaultType="терапевт"></Filters>
            <Forms ref={this.Form}></Forms>
            <button onClick={() => this.addSpecialist()}>Сохранить</button>
            </div>
        }
        else if(this.state.update){
            window = <div><h4>Измените поля:</h4>
            <Filters ref={this.Filter} profiles="true" onlyClinics="false" specialist={this.state.specialist} url="http://localhost:8080/specialists+types" defaultType="терапевт"></Filters>
            <Forms ref={this.Form} specialist={this.state.specialist}></Forms>
            <button onClick={() => this.updateSpecialist()}>Обновить</button>
            <button onClick={() => this.deleteSpecialist()}>Удалить</button>
            </div>
        }
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        return (
        <div>
            <button onClick={() => {this.setState({filters : true, isExp : false, isOperating : false, isDegree : false})}}>Найти специалиста</button>
            <button onClick={() => this.setState({filters : true, isExp : true, isOperating : false, isDegree : false})}>Найти специалиста со стажем</button>
            <button onClick={() => this.setState({filters : true, isExp : false, isOperating : false, isDegree : true})}>Найти специалиста со степенью</button>
            <button onClick={() => this.setState({filters : true, isExp : false, isOperating : true, isDegree : false})}>Найти оперирующего специалиста</button>
            <button onClick={() => this.showForms()}>Добавить специалиста</button>
            {window}
            {error}
        </div>  
        )
    }
}

export default Specialists