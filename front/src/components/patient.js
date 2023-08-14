import React, {Component} from "react";
import axios from 'axios'
import Filters from "./filters";
import Error from "./error";
import PatientsTable from "./patientsTable";
import TreatmentTable from "./treatmentTable";
import Forms from "./patientForms";
import HospitalFilters from "./hospitalFilters";

class Patient extends Component{

    constructor(props){
        super(props)
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
        this.getPartients = this.getPatients.bind(this)
        this.addPatient = this.addPatient.bind(this)
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

    showUpdate(patient){
         this.setState({
            filters : false,
            forms : false,
            update : true,
            table : false,
            errorMsg : "",
            error : false,
            patient : patient
        });
    }
    getPatients = async() => {
        const filter = this.Filter.current
        let res = await axios.get("http://localhost:8080/patient/type",
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

    getPatientsInHospital = async() => {
        const filter = this.Filter.current
        let res = await axios.get("http://localhost:8080/treatment",
         { params: { organization : filter.state.organizationValue, department : filter.state.department, room : filter.state.room} })
         this.setState({
            filters : true,
            forms : false,
            table : true,
            error : false,
            errorMsg : "",
            data : res.data
        });
    }

    addPatient = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.post('http://localhost:8080/patient', 
            { name: form.state.name, 
            surname: form.state.surname,
            phone : form.state.phone,
            address : form.state.address,
            clinic : filter.state.organizationValue,
            })
            alert("Запись сохранена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    updatePatient = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.put('http://localhost:8080/patient', 
            {
            id : this.state.patient.id, 
            name: form.state.name, 
            surname: form.state.surname,
            phone : form.state.phone,
            address : form.state.address,
            clinic : this.state.patient.clinic,
            errorMsg : "",
            error : false
            })
            alert("Запись обновлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    deletePatient = async() => {
        const form = this.Form.current
        const filter = this.Filter.current
        try{
            const result = await axios.delete('http://localhost:8080/patient', 
            { params: { id: this.state.patient.id}})
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
                if(this.state.isInHospital){
                    table = <div>
                        <TreatmentTable errorHandler={this.props.errorHandler} data={this.state.data} handleUpdate={this.showUpdate}></TreatmentTable>
                    </div>
                }
                else{
                    table = <div>
                    <PatientsTable data={this.state.data} handleUpdate={this.showUpdate}></PatientsTable></div>
                }
            }
            if(this.state.isInHospital){
                window = <div><HospitalFilters ref={this.Filter}></HospitalFilters>
                <button onClick={() => this.getPatientsInHospital()}>Поиск</button>
                <p></p>
                {table}
                </div>
            }
            else{
            window = <div><h4>Фильтры:</h4>
            <Filters ref={this.Filter} profiles="true" onlyClinics="true" url="http://localhost:8080/specialists+types" defaultType="терапевт"></Filters>
            <button onClick={() => this.getPatients()}>Поиск</button>
            <p></p>
            {table}
            </div>}
        }
        else if(this.state.forms){
            window = <div><h4>Заполните поля:</h4>
            <Filters ref={this.Filter} profiles="false" onlyClinics="true" url="http://localhost:8080/specialists+types" defaultType="терапевт"></Filters>
            <Forms ref={this.Form}></Forms>
            <button onClick={() => this.addPatient()}>Сохранить</button>
            </div>
        }
        else if(this.state.update){
            window = <div>
            <Forms ref={this.Form} patient={this.state.patient}></Forms>
            <button onClick={() => this.updatePatient()}>Обновить</button>
            <button onClick={() => this.deletePatient()}>Удалить</button>
            </div>
        }
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        return (
        <div>
            <button onClick={() => {this.setState({isInHospital : false})
                this.showFilters()}}>Найти пациента</button>
            <button onClick={() => this.showForms()}>Добавить пациента</button>
            <button onClick={() => {this.setState({isInHospital : true})
                this.showFilters()}}>Пациенты на стационарном лечении</button>
            {window}
            {error}
        </div>  
        )
}


}

export default Patient