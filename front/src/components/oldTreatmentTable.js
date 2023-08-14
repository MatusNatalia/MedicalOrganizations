import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class OldTreatmentTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : []
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
                <th>Дата поступления</th>
                <th>Дата выписки</th>
                </tr>
                </thead>
                <tbody>
                {data.map(treatment => (
                    <tr>
                        <td>{treatment.patientName}</td>
                        <td>{treatment.specialistName}</td>
                        <td>{treatment.roomNumber}</td>
                        <td>{treatment.enterDate}</td>
                        <td>{treatment.checkOutDate}</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default OldTreatmentTable