import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class StuffTable extends React.Component {
 
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
                <th>Редактировать</th>
                </tr>
                </thead>
                <tbody>
                {data.map(stuff => (
                    <tr>
                        <td>{stuff.name}</td>
                        <td>{stuff.surname}</td>
                        <td>{stuff.organization}</td>
                        <td class="link" onClick={() => handleUpdate(stuff)}>Редактировать</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default StuffTable