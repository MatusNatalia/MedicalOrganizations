import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\tableStyle.css"

class LaboratoryStatisticTable extends React.Component {
 
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
                <table>
                <thead>
                <tr>
                <th>Лаборатория</th>
                <th>Среднее число анализов в день</th>
                </tr>
                </thead>
                <tbody>
                {data.map(lab => (
                    <tr>
                        <td>{lab.laboratory}</td>
                        <td>{lab.avgStudies}</td>
                    </tr>
                ))}
                </tbody>
                </table>
            </div>
        )
    }
   }

   export default LaboratoryStatisticTable