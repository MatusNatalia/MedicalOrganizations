import React, {Component} from "react";
import axios from "axios";
import RoomTable from "./roomTable";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class DepartmentTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : [],
            types : []
        }
    }

    componentDidMount = async() => {
        let resTypes = await axios.get("http://localhost:8080/department+types")
        this.setState(prevState => ({  
            types : resTypes.data
        }))
    }

    getRooms = async(id) => {
        this.setState(prevState => ({  
            rooms : [],
            showRooms : false
         }))
        let res = await axios.get("http://localhost:8080/rooms", 
        {params : {id : id}});
        this.setState(prevState => ({  
           rooms : res.data,
           showRooms : true
        }))
    }

    handleChange(event) {
        this.setState(prevState => ({  
                [event.target.name] : event.target.value
        }))
    }

    handleUpdate = async(department) => {
        try{
            const result = await axios.put('http://localhost:8080/departments', 
            {
            id : department.id, 
            name: department.name, 
            type : department.type,
            block : department.block
            })
            alert("Запись обновлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    handleSave = async() => {
        try{
            const result = await axios.post('http://localhost:8080/departments', 
            { 
            name: this.state.new_name, 
            type : this.state.new_type,
            block : this.props.block
            })
            alert("Отделение добавлено")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    handleDelete = async(department) => {
        try{
            const result = await axios.delete('http://localhost:8080/departments/'+department.id)
            alert("Отделение удалено")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }
    
    render() {
        let data = Array.from(this.props.data);
        let rooms
        if(this.state.showRooms){
            rooms = <RoomTable errorHandler={this.props.errorHandler} department={this.state.department} data={this.state.rooms}></RoomTable>
        }
        return (
            <div>
                <p></p>
                <h4>Отделения:</h4>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Тип</th>
                <th>Обновить</th>
                <th>Удалить</th>
                <th>Посмотреть список палат</th>
                </tr>
                </thead>
                <tbody>
                {data.map(department => (
                    <tr>
                        <td><input defaultValue={department.name} onChange={(event)=>{department.name=event.target.value}}></input></td>
                        <td>
                        <div>
                        <select defaultValue={department.type} onChange={(event)=>{department.type=event.target.value}}>
                                { this.state.types.map(type => <option value={type.type}>{type.type}</option>)}
                            </select></div>
                        </td>
                        <td class="link" onClick={() => this.handleUpdate(department)}>Обновить</td>
                        <td class="link" onClick={() => this.handleDelete(department)}>Удалить</td>
                        <td class="link" onClick={() => {this.getRooms(department.id);this.setState({department : department.id})}}>Палаты</td>
                    </tr>
                ))}
                </tbody>
                </table>
                {rooms}
                <h4>Добавить отделение:</h4>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Тип</th>
                <th>Сохранить</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input onChange={(event)=>{this.setState({new_name : event.target.value})}}></input></td>
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

   export default DepartmentTable