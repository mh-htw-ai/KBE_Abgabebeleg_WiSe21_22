
import {StyledAddButton} from "../styles/StyledAddButton";

function AddButton(props){

    return(
        <StyledAddButton onClick={props.onClick}>
            Add {props.name}
        </StyledAddButton>
    );
}

export default AddButton;