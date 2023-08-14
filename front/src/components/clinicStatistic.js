import React, {Component} from "react";
import axios from "axios";
import Error from "./error";
import LaboratoryCheckbox from "./laboratoryCheckbox";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class ClinicStatistic extends React.Component {
 
    constructor(props){
        super(props);
    
    this.state = {
        start : "",
        end : "",
        error : false,
        errorMsg : "",
        statisticTable : false,
        statistic : []
    }
    this.handleStartDateChange = this.handleStartDateChange.bind(this);
    this.handleEndDateChange = this.handleEndDateChange.bind(this);
    this.getStatistic = this.getStatistic.bind(this);
}


    handleStartDateChange(event) {
        this.setState(prevState => ({
                start : event.target.value,
                ...prevState.end,
                statisticTable : false
        }))
    }

    handleEndDateChange(event) {
        this.setState(prevState => ({ 
                end : event.target.value,
                ...prevState.start,
                statisticTable : false
        }))
    }

    getStatistic = async() => {
        try{
        let res = await axios.get("http://localhost:8080/organization",
        { params: { id: this.props.clinic,
            start : this.state.start,
                end : this.state.end} })
        this.setState(prevState => ({
                ...prevState.end,
                ...prevState.start,
                error : false,
                errorMsg : "",
                statistic : res.data,
                statisticTable : true
        }));
    } catch (err) {
        if(err.response === undefined){
            this.setState(prevState => ({
                    ...prevState.end,
                    ...prevState.start,
                errorMsg : "",
                statisticTable : false,
                statistic : [],
                error : true
            }));
        }
        else{
            this.props.errorHandler(err)
            this.setState(prevState => ({
                    ...prevState.end,
                    ...prevState.start,
                errorMsg : err.response.data,
                statisticTable : false,
                statistic : [],
                error : true
            }));
    }
    }
    }

    render() {
        let error
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        let statistic;
        if(this.state.statisticTable){
            statistic = <div>
                <p>Общее число кабинетов: {this.state.statistic.cabinetNumber}</p>
                <p>Статистика по кабинетам:</p>
                <table>
                <thead>
                <tr>
                <th>Номер кабинета</th>
                <th>Число посещений за выбранный период</th>
                </tr>
                </thead>
                <tbody>
                {this.state.statistic.cabinetStatistic.map(cab => (
                    <tr>
                        <td>{cab.number}</td>
                        <td>{cab.visits}</td>
                    </tr>
                ))}
                </tbody>
                </table>
                </div>
        }
        return (
            <div>
                <p>Лаборатории:</p>
            <LaboratoryCheckbox orgLabs={this.props.orgLabs} errorHandler={this.props.errorHandler} laboratories={this.props.laboratories} org={this.props.clinic}></LaboratoryCheckbox>
            <p>Статистика посещений:</p>
                <p>Выберите период:</p>
                <label htmlFor='startDate'>С</label>
                <input
                    name='start' 
                    placeholder='ГГГГ-ММ-ДД'
                    defaultValue={this.state.start}
                    onChange={this.handleStartDateChange}
                />
                <label htmlFor='endDate'>по</label>
                <input
                    name='end' 
                    placeholder='ГГГГ-ММ-ДД'
                    defaultValue={this.state.end}
                    onChange={this.handleEndDateChange}
                />
                <p></p>
                <button onClick={this.getStatistic}>Получить статистику</button>
                {error}
                <p></p>
                {statistic}
            </div>
        )
    }
   }

   export default ClinicStatistic