import React, {Component} from "react";
import axios from "axios";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class CabinetTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : []
        }
    }

    handleDelete = async(cabinet) => {
        try{
            const result = await axios.delete('http://localhost:8080/cabinets/'+cabinet.number)
            alert("Кабинет удален")
        } catch (err) {
           alert(err.response.data)
        }
    }
    
    render() {
        let data = Array.from(this.props.data);
        return (
            <div>
                <table>
                <thead>
                <tr>
                <th>Номер кабинета</th>
                <th>Специалисты</th>
                <th>Удалить</th>
                </tr>
                </thead>
                <tbody>
                {data.map(hospital => (
                    <tr>
                        <td>{hospital.number}</td>
                        <td>{hospital.specialists.map(address => (
                            <p>{address}</p>
                        ))}
                        </td>
                        <td class="link" onClick={() => this.handleDelete(hospital)}>Удалить</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default CabinetTable