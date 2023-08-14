import React, {Component} from "react";
import axios from "axios";
import DepartmentTable from "./departmentTable";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class BlockTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : []
        }
    }

    getDepartments = async(id) => {
        this.setState(prevState => ({  
            departments : [],
            showDepartments : false
         }))
        let res = await axios.get("http://localhost:8080/block+departments", 
        {params : {id : id}});
        this.setState(prevState => ({  
           departments : res.data,
           showDepartments : true
        }))
    }

    handleChange(event) {
        this.setState(prevState => ({  
                [event.target.name] : event.target.value
        }))
    }

    handleUpdate = async(block) => {
        try{
            const result = await axios.put('http://localhost:8080/blocks', 
            {
            id : block.id, 
            name: block.name, 
            address : block.address,
            hospital : block.hospital
            })
            alert("Запись обновлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    
    handleSave = async() => {
        try{
            const result = await axios.post('http://localhost:8080/blocks', 
            { 
            name: this.state.new_name, 
            address : this.state.new_address,
            hospital : this.props.hospital
            })
            alert("Блок добавлен")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    handleDelete = async(block) => {
        try{
            const result = await axios.delete('http://localhost:8080/blocks/'+block.id)
            alert("Блок удален")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }
    
    render() {
        let data = Array.from(this.props.data);
        let departments
        if(this.state.showDepartments){
            departments = <DepartmentTable errorHandler={this.props.errorHandler} block={this.state.block} data={this.state.departments}></DepartmentTable>
        }
        return (
            <div>
                <p></p>
                <h4>Блоки:</h4>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Адрес</th>
                <th>Обновить</th>
                <th>Удалить</th>
                <th>Посмотреть список отделений</th>
                </tr>
                </thead>
                <tbody>
                {data.map(block => (
                    <tr>
                        <td><input defaultValue={block.name} onChange={(event)=>{block.name=event.target.value}}></input></td>
                        <td><input defaultValue={block.address} onChange={(event)=>{block.address=event.target.value}}></input></td>
                        <td class="link" onClick={() => this.handleUpdate(block)}>Обновить</td>
                        <td class="link" onClick={() => this.handleDelete(block)}>Удалить</td>
                        <td class="link" onClick={() => {this.getDepartments(block.id);this.setState({block : block.id})}}>Отделения</td>
                    </tr>
                ))}
                </tbody>
                </table>
                {departments}
                <h4>Добавить блок:</h4>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Адрес</th>
                <th>Сохранить</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input onChange={(event)=>{this.setState({new_name : event.target.value})}}></input></td>
                        <td><input onChange={(event)=>{this.setState({new_address : event.target.value})}}></input></td>
                        <td class="link" onClick={() => this.handleSave()}>Сохранить</td>
                    </tr>
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default BlockTable