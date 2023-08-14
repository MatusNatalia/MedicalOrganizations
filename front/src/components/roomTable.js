import React, {Component} from "react";
import axios from "axios";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class RoomTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : []
        }
    }

    handleChange(event) {
        this.setState(prevState => ({  
                [event.target.name] : event.target.value
        }))
    }

    handleUpdate = async(room) => {
        try{
            const result = await axios.put('http://localhost:8080/rooms', 
            {
            id : room.id, 
            number : room.number,
            size : room.size,
            busy : room.busy,
            department : room.department
            })
            alert("Запись обновлена")
        } catch (err) {
           alert(err.response.data)
        }
    }

    handleSave = async() => {
        try{
            const result = await axios.post('http://localhost:8080/rooms', 
            { 
            number: this.state.new_number, 
            size : this.state.new_size,
            busy : 0,
            department : this.props.department
            })
            alert("Палата добавлена")
        } catch (err) {
           alert(err.response.data)
        }
    }

    handleDelete = async(room) => {
        try{
            const result = await axios.delete('http://localhost:8080/rooms/'+room.id)
            alert("Запись удалена")
        } catch (err) {
           alert(err.response.data)
        }
    }
    
    render() {
        let data = Array.from(this.props.data);
        return (
            <div>
                <p></p>
                <h4>Палаты:</h4>
                <table>
                <thead>
                <tr>
                <th>Номер</th>
                <th>Кол-во коек</th>
                <th>Занято</th>
                <th>Свободно</th>
                <th>Обновить</th>
                <th>Удалить</th>
                </tr>
                </thead>
                <tbody>
                {data.map(room => (
                    <tr>
                        <td>{room.number}</td>
                        <td><input defaultValue={room.size} onChange={(event)=>{room.size=event.target.value}}></input></td>
                        <td>{room.busy}</td>
                        <td>{room.size-room.busy}</td>
                        <td class="link" onClick={() => this.handleUpdate(room)}>Обновить</td>
                        <td class="link" onClick={() => this.handleDelete(room)}>Удалить</td>
                    </tr>
                ))}
                </tbody>
                </table>
                <h4>Добавить палату:</h4>
                <table>
                <thead>
                <tr>
                <th>Номер</th>
                <th>Кол-во коек</th>
                <th>Сохранить</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input onChange={(event)=>{this.setState({new_number : event.target.value})}}></input></td>
                        <td><input onChange={(event)=>{this.setState({new_size : event.target.value})}}></input></td>
                        <td class="link" onClick={() => this.handleSave()}>Сохранить</td>
                    </tr>
                </tbody>
                </table>
            </div>
        )
        }
    }

export default RoomTable