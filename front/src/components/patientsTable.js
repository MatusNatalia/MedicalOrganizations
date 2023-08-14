import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class PatientsTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : []
        }
    }
    
    render() {
        let data = Array.from(this.props.data);
        var handleUpdate = this.props.handleUpdate;
        return (
            <div>
                <p>Всего: {data.length}</p>
                <table>
                <thead>
                <tr>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Номер телефона</th>
                <th>Адрес</th>
                <th>Поликлиника</th>
                <th>Редактировать</th>
                </tr>
                </thead>
                <tbody>
                {data.map(specialist => (
                    <tr>
                        <td>{specialist.name}</td>
                        <td>{specialist.surname}</td>
                        <td>{specialist.phone}</td>
                        <td>{specialist.address}</td>
                        <td>{specialist.clinic}</td>
                        <td class="link" onClick={() => handleUpdate(specialist)}>Редактировать</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default PatientsTable