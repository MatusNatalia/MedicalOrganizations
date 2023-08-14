import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class BusyTable extends React.Component {
 
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
                <p>На данный момент на стационарном лечении:</p>
                <p>Всего: {data.length}</p>
                <table>
                <thead>
                <tr>
                <th>Лечащий врач</th>
                <th>Пациент</th>
                <th>Дата поступления</th>
                </tr>
                </thead>
                <tbody>
                {data.map(treatment => (
                    <tr>
                        <td>{treatment.specialistName}</td>
                        <td>{treatment.patientName}</td>
                        <td>{treatment.enterDate}</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default BusyTable