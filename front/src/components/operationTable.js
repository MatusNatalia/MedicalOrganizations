import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class OperationTable extends React.Component {
 
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
                <p>Всего: {data.length}</p>
                <table>
                <thead>
                <tr>
                <th>Пациент</th>
                <th>Специалист</th>
                <th>Дата</th>
                </tr>
                </thead>
                <tbody>
                {data.map(op => (
                    <tr>
                        <td>{op.patientName}</td>
                        <td>{op.specialistName}</td>
                        <td>{op.date}</td>
                    </tr>
                ))}
                </tbody>
                </table>
                </div>
        )
    }
}

export default OperationTable;