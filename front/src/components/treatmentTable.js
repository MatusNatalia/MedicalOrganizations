import React, {Component} from "react";
import axios from "axios";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class TreatmentTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : []
        }
    }

    handleUpdate = async(treatment) => {
        try{
            const result = await axios.put('http://localhost:8080/treatment', 
            {
            id : treatment.id, 
            patient : treatment.patient,
            specialist : treatment.specialist,
            room : treatment.room,
            temp : treatment.temp,
            state: treatment.state,
            enterDate : treatment.enterDate,
            checkOutDate : treatment.checkOutDate
            })
            alert("Запись обновлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }
    
    
    render() {
        let data = Array.from(this.props.data);
        return (
            <div>
                <table>
                <thead>
                <tr>
                <th>Пациент</th>
                <th>Лечащий врач</th>
                <th>Палата</th>
                <th>Состояние</th>
                <th>Температура</th>
                <th>Дата поступления</th>
                <th>Дата выписки</th>
                <th>Обновить</th>
                </tr>
                </thead>
                <tbody>
                {data.map(treatment => (
                    <tr>
                        <td>{treatment.patientName}</td>
                        <td>{treatment.specialistName}</td>
                        <td>{treatment.roomNumber}</td>
                        <td><input defaultValue={treatment.state} onChange={(event)=>{treatment.state=event.target.value}}></input></td>
                        <td><input defaultValue={treatment.temp} onChange={(event)=>{treatment.temp=event.target.value}}></input></td>
                        <td>{treatment.enterDate}</td>
                        <td><input defaultValue={treatment.checkOutDate} onChange={(event)=>{treatment.checkOutDate=event.target.value}}></input></td>
                        <td class="link" onClick={() => this.handleUpdate(treatment)}>Обновить</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default TreatmentTable