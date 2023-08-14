import React, {Component} from "react";
import axios from 'axios'
import Error from "./error";
import Filters from "./filters";
import LaboratoryStatisticTable from "./laboratoryStatisticTable";

class Laboratory extends Component{
    constructor(){
        super()
        this.state = {
            start : "",
            end : "",
            error : false,
            errorMsg : "",
            statisticTable : false,
            statisticData : []
        }
        this.Filter = React.createRef();
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
        const filter = this.Filter.current
        try{
        let res = await axios.get("http://localhost:8080/laboratory",
         { params: { organization: filter.state.organizationValue,
              start : this.state.start,
                end : this.state.end} })
         this.setState(prevState => ({
                ...prevState.end,
                ...prevState.start,
                error : false,
                errorMsg : "",
                statisticData : res.data,
                statisticTable : true
        }));
    } catch (err) {
        if(err.response === undefined){
            this.setState(prevState => ({
                    ...prevState.end,
                    ...prevState.start,
                errorMsg : "",
                statisticTable : false,
                statisticData : [],
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
                statisticData : [],
                error : true
            }));
    }
    }
    }


    render(){
        let organizationSelect = <div><Filters ref={this.Filter} profiles="false" onlyClinics="false"></Filters></div>
        let error
        if(this.state.error){
            error = <Error msg = {this.state.errorMsg}></Error>
        }
        let statistic;
        if(this.state.statisticTable){
            statistic = <LaboratoryStatisticTable data={this.state.statisticData}></LaboratoryStatisticTable>
        }
        return(
            <div>
                <p>Выберите организацию, к которой прикреплена лаборатория:</p>
                {organizationSelect}
            
            <p></p>
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
                <p></p>
                <div style={{display: "inline-block"}}>
                </div>
            </div>
        )
    }

}

export default Laboratory