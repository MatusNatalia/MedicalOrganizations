import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class SpecialistsTable extends React.Component {
 
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
                <th>Организация</th>
                <th>Кабинет</th>
                <th>Звание</th>
                <th>Степень</th>
                <th>Стаж</th>
                <th>Редактировать</th>
                </tr>
                </thead>
                <tbody>
                {data.map(specialist => (
                    <tr>
                        <td>{specialist.name}</td>
                        <td>{specialist.surname}</td>
                        <td>{specialist.organization}</td>
                        <td>{specialist.cabinet}</td>
                        <td>{specialist.title}</td>
                        <td>{specialist.degree}</td>
                        <td>{specialist.experience}</td>
                        <td class="link" onClick={() => handleUpdate(specialist)}>Редактировать</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default SpecialistsTable