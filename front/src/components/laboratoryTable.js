import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"
import axios from "axios";
import Laboratory from "./laboratory";

class LaboratoryTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : [],
            types : [],
            more : false
        }
    }

    componentDidMount = async() => {
        let resTypes = await axios.get("http://localhost:8080/laboratory+types")
        this.setState(prevState => ({  
            types : resTypes.data
        }))
    }

    handleUpdate = async(lab) => {
        try{
            const result = await axios.put('http://localhost:8080/laboratory', 
            {
            id : lab.id, 
            name : lab.name, 
            number : lab.number,
            type : lab.type
            })
            alert("Запись обновлена")
        } catch (err) {
           this.props.errorHandler(err)
        }
    }

    handleSave = async() => {
        try{
            const result = await axios.post('http://localhost:8080/laboratory', 
            { 
            name: this.state.new_name, 
            number : this.state.new_number,
            type : this.state.new_type
            })
            alert("Лаборатория добавлена")
        } catch (err) {
           this.props.errorHandler(err)
        }
    }

    handleDelete = async(lab) => {
        try{
            const result = await axios.delete('http://localhost:8080/laboratory/'+lab.id)
            alert("Лаборатория удалена")
        } catch (err) {
           this.props.errorHandler(err)
        }
    }
    
    render() {
        let data = Array.from(this.props.data);
        let statistics
        if(this.state.more){
            statistics = <Laboratory errorHandler={this.props.errorHandler}></Laboratory>
        }
        return (
            <div>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Номер телефона</th>
                <th>Тип</th>
                <th>Обновить</th>
                <th>Удалить</th>
                <th>Подробнее</th>
                </tr>
                </thead>
                <tbody>
                {data.map(lab => (
                    <tr>
                        <td><input defaultValue={lab.name} onChange={(event)=>{lab.name=event.target.value}}></input></td>
                        <td><input defaultValue={lab.number} onChange={(event)=>{lab.number=event.target.value}}></input></td>
                        <td>
                        <div>
                        <select defaultValue={lab.type} onChange={(event)=>{lab.type=event.target.value}}>
                                { this.state.types.map(type => <option value={type.type}>{type.type}</option>)}
                            </select></div>
                        </td>
                        <td class="link" onClick={() => this.handleUpdate(lab)}>Обновить</td>
                        <td class="link" onClick={() => this.handleDelete(lab)}>Удалить</td>
                        <td class="link" onClick={() => this.setState({more : true})}>Подробнее</td>
                    </tr>
                ))}
                </tbody>
                </table>
                {statistics}
                <h4>Добавить лабораторию:</h4>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Номер телефона</th>
                <th>Тип</th>
                <th>Сохранить</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input onChange={(event)=>{this.setState({new_name : event.target.value})}}></input></td>
                        <td><input onChange={(event)=>{this.setState({new_number : event.target.value})}}></input></td>
                        <td><div>
                        <select defaultValue={this.state.types.at(0)} onChange={(event)=>{this.setState({new_type : event.target.value})}}>
                                { this.state.types.map(type => <option value={type.type}>{type.type}</option>)}
                            </select></div></td>
                        <td class="link" onClick={() => this.handleSave()}>Сохранить</td>
                    </tr>
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default LaboratoryTable