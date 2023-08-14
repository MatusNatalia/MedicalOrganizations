import React, {Component} from "react";
import BlockTable from "./blockTable";
import axios from "axios";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class HospitalTable extends React.Component {
 
    constructor(props){
        super(props);
        this.state = {
            data : []
        }
    }

    getBlocks = async(id) => {
        this.setState(prevState => ({  
            blocks : [],
            showBlocks : false
         }))
        let res = await axios.get("http://localhost:8080/blocks", 
        {params : {id : id}});
        this.setState(prevState => ({  
           blocks : res.data,
           showBlocks : true
        }))
    }

    handleUpdate = async(clinic) => {
        try{
            const result = await axios.put('http://localhost:8080/hospital', 
            {
            id : clinic.id, 
            name: clinic.name, 
            number : clinic.number
            })
            alert("Запись обновлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    handleSave = async() => {
        try{
            const result = await axios.post('http://localhost:8080/hospital', 
            { 
            name: this.state.new_name, 
            number : this.state.new_number
            })
            alert("Больница добавлена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }

    handleDelete = async(clinic) => {
        try{
            const result = await axios.delete('http://localhost:8080/organization/'+clinic.id)
            alert("Больница удалена")
        } catch (err) {
            this.props.errorHandler(err)
        }
    }
    
    render() {
        let data = Array.from(this.props.data);
        var handleMore = this.props.handleMore;
        let blocks
        if(this.state.showBlocks){
            blocks = <BlockTable errorHandler={this.props.errorHandler} hospital={this.state.hospital} data={this.state.blocks}></BlockTable>
        }
        return (
            <div>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Номер телефона</th>
                <th>Адреса</th>
                <th>Обновить</th>
                <th>Удалить</th>
                <th>Подробнее</th>
                <th>Посмотреть список блоков</th>
                </tr>
                </thead>
                <tbody>
                {data.map(hospital => (
                    <tr>
                        <td><input defaultValue={hospital.name} onChange={(event)=>{hospital.name=event.target.value}}></input></td>
                        <td><input defaultValue={hospital.number} onChange={(event)=>{hospital.number=event.target.value}}></input></td>
                        <td>{hospital.addresses.map(address => (
                            <p>{address}</p>
                        ))}
                        </td>
                        <td class="link" onClick={() => this.handleUpdate(hospital)}>Обновить</td>
                        <td class="link" onClick={() => this.handleDelete(hospital)}>Удалить</td>
                        <td class="link" onClick={() => {handleMore(hospital.id) 
                        this.setState({showBlocks:false})}}>Подробнее</td>
                        <td class="link" onClick={() => {this.getBlocks(hospital.id);this.setState({hospital : hospital.name})}}>Блоки</td>
                    </tr>
                ))}
                </tbody>
                </table>
                {blocks}
                <h4>Добавить больницу:</h4>
                <table>
                <thead>
                <tr>
                <th>Название</th>
                <th>Номер телефона</th>
                <th>Сохранить</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input onChange={(event)=>{this.setState({new_name : event.target.value})}}></input></td>
                        <td><input onChange={(event)=>{this.setState({new_number : event.target.value})}}></input></td>
                        <td class="link" onClick={() => this.handleSave()}>Сохранить</td>
                    </tr>
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default HospitalTable