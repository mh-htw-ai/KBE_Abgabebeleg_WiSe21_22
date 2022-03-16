
import {useState} from "react";

function AddButton(props){

    const [name, setName] = useState(props.name);

    function doStuff(){

    }

    return(
        <button onClick={doStuff}>
            Add {name}
        </button>
    );
}

export default AddButton;