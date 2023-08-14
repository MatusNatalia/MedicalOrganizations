import React, {Component} from "react";
import axios from 'axios'

class Filters extends Component{

    constructor(props){
        super(props)
        this.state = {
            organizationTypeValue : "1",
            organizationValue : "0",
            typeValue : props.defaultType,
            hospitals: [],
            clinics: [],
            profiles : []
        }
        this.handleOrganizationTypeChange = this.handleOrganizationTypeChange.bind(this);
        this.handleOrganizationChange = this.handleOrganizationChange.bind(this);
        this.handleTypeChange = this.handleTypeChange.bind(this);
    }

    componentDidMount = async() => {

        let resHospitals = await axios.get("http://localhost:8080/hospitals");
        this.setState(prevState => ({  
            ...prevState.organizationTypeValue,
            ...prevState.organizationValue,
            ...prevState.typeValue,
            hospitals : resHospitals.data,
            ...prevState.clinics,
            ...prevState.profiles
        }))

        let resClinics = await axios.get("http://localhost:8080/clinics")
        this.setState(prevState => ({  
            ...prevState.organizationTypeValue,
            ...prevState.organizationValue,
            ...prevState.typeValue,
            ...prevState.hospitals,
            clinics : resClinics.data,
            ...prevState.profiles
        }))
        if(this.props.profiles === "true"){
        let resTypes = await axios.get(this.props.url)
        this.setState(prevState => ({  
            ...prevState.organizationTypeValue,
            ...prevState.organizationValue,
            ...prevState.typeValue,
            ...prevState.hospitals,
            ...prevState.clinics,
            profiles : resTypes.data
        }))
    }
    }

    handleOrganizationTypeChange(event) {
        this.setState(prevState => ({  
                organizationTypeValue: event.target.value,
                ...prevState.organizationValue,
                ...prevState.typeValue,
                ...prevState.hospitals,
                ...prevState.clinics,
                ...prevState.profiles
        }))
    }

    handleOrganizationChange(event) {
        this.setState(prevState => ({  
                ...prevState.organizationTypeValue,
                organizationValue : event.target.value,
                ...prevState.typeValue,
                ...prevState.hospitals,
                ...prevState.clinics,
                ...prevState.profiles
        }))
    }

    handleTypeChange(event) {
        this.setState(prevState => ({  
                ...prevState.organizationTypeValue,
                ...prevState.organizationValue,
                typeValue : event.target.value,
                ...prevState.hospitals,
                ...prevState.clinics,
                ...prevState.profiles
        }))
    }


    render(){
        let organizationSelect;
        let typeSelect;
        let profileSelect;
        if(this.props.onlyClinics !== "true"){
        typeSelect = <div><p>Тип организации:</p> 
        <select value={this.state.organizationTypeValue} onChange={this.handleOrganizationTypeChange}>
            <option value="1">Все</option>
            <option value="2">Больницы</option>
            <option value="3">Поликлиники</option>
        </select></div>
        switch(this.state.organizationTypeValue){
            case "1":
                organizationSelect =  <select value={this.state.organizationValue} onChange={this.handleOrganizationChange}>
                                        <option value="0">Все</option>
                                    { this.state.hospitals.map(hospital => <option value={hospital.name}>{hospital.name}</option>)}
                                    { this.state.clinics.map(clinic => <option value={clinic.name}>{clinic.name}</option>)}
                                        </select>
                break;
            case "2":
                organizationSelect =  <select value={this.state.organizationValue} onChange={this.handleOrganizationChange}>
                                    <option value="0">Все</option>
                                    { this.state.hospitals.map(hospital => <option value={hospital.name}>{hospital.name}</option>)}
                                        </select>
                break;
            case "3":
                organizationSelect = <select value={this.state.organizationValue} onChange={this.handleOrganizationChange}>
                    <option value="0">Все</option>
                    { this.state.clinics.map(clinic => <option value={clinic.name}>{clinic.name}</option>)}</select>
                break;
        }}
        else{
        organizationSelect = <select value={this.state.organizationValue} onChange={this.handleOrganizationChange}>
        <option value="0">Все</option>
        { this.state.clinics.map(clinic => <option value={clinic.name}>{clinic.name}</option>)}</select>
        }
        if(this.props.profiles === "true"){
            profileSelect = <div><p>Профиль:</p>
            <select value={this.state.typeValue} onChange={this.handleTypeChange}>
                { this.state.profiles.map(profile => <option value={profile.type}>{profile.type}</option>)}
            </select></div>
        }
        return (
        <div> 
           {typeSelect}
            <p>Название организации:</p>
            {organizationSelect}
            {profileSelect}
            <p></p>
        </div>  
        )
    }
}

export default Filters