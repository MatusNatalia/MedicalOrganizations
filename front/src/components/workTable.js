import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class WorkTable extends React.Component {
 
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
                <p>Среднее число принятых пациентов в день (учитываются только рабочие дни):</p>
                <table>
                <thead>
                <tr>
                <th>Специалист</th>
                <th>Выработка</th>
                </tr>
                </thead>
                <tbody>
                {data.map(d => (
                    <tr>
                        <td>{d.specialistName}</td>
                        <td>{d.avgPatients}</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default WorkTable