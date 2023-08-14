import React, {Component} from "react";
import axios from "axios";

var checkedLab;
var newLabs = [];

class LaboratoryCheckbox extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            laboratories : props.laboratories,
            id : props.org,
            orgLaboratories : []
        }
        checkedLab = new Map()  

    }

    componentDidMount = async() => {
        try{
        let labs = await axios.get("http://localhost:8080/organization+labs", 
        { params: { id: this.state.id} });
        this.setState(prevState => ({  
            orgLaboratories : labs.data
        }))
        this.state.orgLaboratories.map(lab => {
            return checkedLab.set(lab.id, true)
        })
    } catch(err){
        this.props.errorHandler(err)
    }
    }

    updateLabs = async() => {
        newLabs = []
        this.state.laboratories.map(lab => {
            if(checkedLab.get(lab.id)===true){
                return newLabs.push(lab)
            }
        })
        try{
        await axios.post('http://localhost:8080/organization+labs', 
         newLabs, {params: { id: this.state.id}})
        } catch(err){
            this.props.errorHandler(err)
        }
    }
    
    render() {
        let labs = Array.from(this.props.orgLabs);
        this.state.laboratories.map(lab => {
            return checkedLab.set(lab.id, false)
        })
        if(labs.length > 0){
            labs.map(lab => {
                return checkedLab.set(lab.id, true)
            })
        }
        console.log(checkedLab)
        return (
            <div>
                {this.state.laboratories.map(lab => (
                    <div>
                    <input type="checkbox" defaultChecked={checkedLab.get(lab.id)} onClick=
                    {(e) => {checkedLab.set(lab.id, e.target.checked)}}/>
                    <label>{lab.name}</label>
                    </div>
                ))}
                <button onClick={this.updateLabs}>Обновить список лабораторий</button>
            </div>
        )
    }
   }

   export default LaboratoryCheckbox