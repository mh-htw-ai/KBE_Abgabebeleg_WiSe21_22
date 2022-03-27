import {StyledDeleteButton} from "../styles/StyledDeleteButton";


function DeleteButton(props){

    return(
        <StyledDeleteButton onClick={props.onClick}>
            Delete {props.name}
        </StyledDeleteButton>
    );
}

export default DeleteButton;