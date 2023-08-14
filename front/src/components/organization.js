import React, {Component} from "react";
import axios from 'axios'
import HospitalTable from "./hospitalTable";
import ClinicTable from "./clinicTable";
import LaboratoryTable from "./laboratoryTable";
import HospitalForms from "./hospitalForms";
import ClinicForms from "./clinicForms";
import Error from "./error";

let formsHospital;

class Organization extends Component{

    constructor(){
        super()
        this.state = {
            hospitals : [],
            clinics : [],
            laboratories : [],
            updateHospital : false,
            updateClinic : false,
            isHospitalStatistic : false
        }

        this.ClinicForm = React.createRef();

        this.updateHospital = this.updateHospital.bind(this)
        this.handleMore = this.handleMore.bind(this)
        this.updateClinic = this.updateClinic.bind(this)
    }

    componentDidMount = async() => {

        let resHospitals = await axios.get("http://localhost:8080/hospitals");
        this.setState(prevState => ({  
            hospitals : resHospitals.data,
            ...prevState.clinics,
            ...prevState.laboratories,
            updateHospital : false,
            updateClinic : false,
            isHospitalStatistic : false
        }))

        let resClinics = await axios.get("http://localhost:8080/clinics")
        this.setState(prevState => ({ 
            ...prevState.hospitals,
            clinics : resClinics.data,
            ...prevState.laboratories,
            updateHospital : false,
            updateClinic : false,
            isHospitalStatistic : false
        }))

        let resLaboratories = await axios.get("http://localhost:8080/laboratories")
        this.setState(prevState => ({
            ...prevState.hospitals,
            ...prevState.clinics,
            laboratories : resLaboratories.data,
            updateHospital : false,
            updateClinic : false,
            isHospitalStatistic : false
        }))
    }

    handleMore = async(id) =>{
        try{
        let res = await axios.get("http://localhost:8080/organization",
        {params : {id : id, start : "null", end : "null"}})
        this.setState(prevState => ({
            ...prevState.hospitals,
            ...prevState.clinics,
            ...prevState.laboratories,
            updateHospital : false,
            updateClinic : false,
            isHospitalStatistic : true,
            hospitalStatistic : res.data
        }))}
        catch(err){
            this.props.errorHandler(err)
        }
    }

    updateHospital(hospital){
        this.setState(prevState => ({
            ...prevState.hospitals,
            ...prevState.clinics,
            ...prevState.laboratories,
            updateHospital : true,
            hospital : hospital,
            isHospitalStatistic : false
        }))
    }

    updateClinic(clinic){
        this.setState(prevState => ({
            ...prevState.hospitals,
            ...prevState.clinics,
            ...prevState.laboratories,
            updateClinic : true,
            clinic : clinic
        }))
    }

    updateClinicAsync = async() => {
        const form = this.ClinicForm.current
        try{
            const result = await axios.put('http://localhost:8080/clinic', 
            {
            id : this.state.clinic.id, 
            name: form.state.name, 
            number : form.state.number,
            address : form.state.address
            }, {validateStatus : function (status) {
                return console.log(status); // Resolve only if the status code is less than 500
              }})
            alert("Запись обновлена")
        } catch (err) {
            console.log(err)
            if(err.response.status === 401){
                alert("Действие недоступно")
            }
            else{
                alert(err.response.data)
            }
        }
    }

    render(){
        let hStatistic;
        let formsClinic;
        let error;
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        if(this.state.updateHospital){
            console.log(this.state.hospital.name)
            formsHospital = <div><HospitalForms hospital={this.state.hospital}></HospitalForms></div>
        }
        if(this.state.isHospitalStatistic){
            hStatistic = <div><h4>Статистика:</h4>
                                <p>Всего палат: {this.state.hospitalStatistic.totalRoomNumber}</p>
                                <p>Всего коек: {this.state.hospitalStatistic.totalBedNumber}</p>
                                <h4>Статистика по отделениям:</h4>
                                {this.state.hospitalStatistic.departmentStatistic.map(department => (
                                <div>
                                <ul>
                            <li>{department.name}:</li>
                            <ul>
                            <li>Свободных палат: {department.freeRoomNumber}</li>
                            <li>Свободных коек: {department.freeBedNumber}</li>
                            </ul>
                            </ul>
                            </div>
                        ))}
                        </div>
        }
        if(this.state.updateClinic){
            formsClinic = <div><ClinicForms ref={this.ClinicForm} clinic={this.state.clinic} laboratories={this.state.laboratories}></ClinicForms>
            <button onClick={() => this.updateClinicAsync()}>Обновить</button>
            </div>
        }
        return (
            <div>
                <div>
                <h4>Больницы:</h4>
                <HospitalTable errorHandler={this.props.errorHandler} data={this.state.hospitals} handleUpdate={this.updateHospital} handleMore={this.handleMore}></HospitalTable>
                </div>
                {formsHospital}
                {hStatistic}
                <h4>Поликлиники:</h4>
                <ClinicTable errorHandler={this.props.errorHandler} data={this.state.clinics} laboratories={this.state.laboratories} handleUpdate={this.updateClinic}></ClinicTable>
                {formsClinic}
                <h4>Лаборатории:</h4>
                <LaboratoryTable errorHandler={this.props.errorHandler} data={this.state.laboratories} handleUpdate={this.updateLaboratory}></LaboratoryTable>
            </div>
        )
    }

}

export default Organization
