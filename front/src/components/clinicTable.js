import React, {Component} from "react";
import axios from "axios";
import ClinicStatistic from "./clinicStatistic";
import CabinetTable from "./cabinetTable";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"
import ClinicForms from "./clinicForms";

class ClinicTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : [],
            more : false,
            orgLaboratories : []
        }
    }

    showMore = async(clinic) => {
        try{
            let labs = await axios.get("http://localhost:8080/organization+labs", 
            { params: { id: clinic.id} });
            this.setState(prevState => ({  
                orgLaboratories : labs.data
            }))
        } catch(err){
            this.props.errorHandler(err)
        }
        this.setState(prevState => ({
            clinic : clinic,
            more : true, 
            showCabinets : false
        }))
    }

    handleUpdate = async(clinic) => {
        try{
            const result = await axios.put('http://localhost:8080/clinic', 
            {
            id : clinic.id, 
            name: clinic.name, 
            number : clinic.number,
            address :clinic.address
            })
            alert("Запись обновлена")
        } catch (err) {
           this.props.errorHandler(err)
        }
    }

    handleSave = async() => {
        try{
            const result = await axios.post('http://localhost:8080/clinic', 
            { 
            name: this.state.new_name, 
            number : this.state.new_number,
            address : this.state.new_address
            })
            alert("Поликлиника добавлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    handleDelete = async(clinic) => {
        try{
            const result = await axios.delete('http://localhost:8080/organization/'+clinic.id)
            alert("Поликлиника удалена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    getCabinets = async(clinic) => {
        let res = await axios.get("http://localhost:8080/cabinets", 
        {params : {id : clinic.id}});
        this.setState(prevState => ({  
           cabinets : res.data,
           showCabinets : true,
           more : false,
           clinic : clinic
        }))
    }
    
    render() {
        let data = Array.from(this.props.data);
        let stat
        if(this.state.more){
            stat = <ClinicStatistic orgLabs={this.state.orgLaboratories} errorHandler={this.props.errorHandler} clinic={this.state.clinic.id} laboratories={this.props.laboratories}></ClinicStatistic>
        }
        let cabinets
        if(this.state.showCabinets){
            cabinets = <div><CabinetTable data={this.state.cabinets}></CabinetTable>
            <ClinicForms clinic={this.state.clinic} laboratories={this.props.laboratories}></ClinicForms>
            </div>
        }
        return (
            <div>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Номер телефона</th>
                <th>Адрес</th>
                <th>Обновить</th>
                <th>Удалить</th>
                <th>Показать список кабинетов</th>
                <th>Подробнее</th>
                </tr>
                </thead>
                <tbody>
                {data.map(clinic => (
                    <tr>
                        <td><input defaultValue={clinic.name} onChange={(event)=>{clinic.name=event.target.value}}></input></td>
                        <td><input defaultValue={clinic.number} onChange={(event)=>{clinic.number=event.target.value}}></input></td>
                        <td><input defaultValue={clinic.address} onChange={(event)=>{clinic.address=event.target.value}}></input></td>
                        <td class="link" onClick={() => this.handleUpdate(clinic)}>Обновить</td>
                        <td class="link" onClick={() => this.handleDelete(clinic)}>Удалить</td>
                        <td class="link" onClick={() => {this.getCabinets(clinic)}}>Кабинеты</td>
                        <td class="link" onClick={() => {this.showMore(clinic)}}>Подробнее</td>
                    </tr>
                ))}
                </tbody>
                </table>
                {cabinets}
                {stat}
                <h4>Добавить поликлинику:</h4>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Номер телефона</th>
                <th>Адрес</th>
                <th>Сохранить</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input onChange={(event)=>{this.setState({new_name : event.target.value})}}></input></td>
                        <td><input onChange={(event)=>{this.setState({new_number : event.target.value})}}></input></td>
                        <td><input onChange={(event)=>{this.setState({new_address : event.target.value})}}></input></td>
                        <td class="link" onClick={() => this.handleSave()}>Сохранить</td>
                    </tr>
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default ClinicTable