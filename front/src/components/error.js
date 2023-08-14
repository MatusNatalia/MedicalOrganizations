import React, {Component} from "react";
import "C:\\Users\\User\\Desktop\\medical\\src\\error.css"

class Error extends React.Component {
 
    constructor(props){
        super(props);
    }
    
    render() {
        let errors = Array.from(this.props.msg);
        return (
            <div>
                <ul>
             {errors.map(error => (
                    <li class="error">{error}</li>
                ))}
                </ul>
            </div>
        )
    }
   }

   export default Error