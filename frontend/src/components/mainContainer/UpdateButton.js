import {StyledUpdateButton} from "../styles/StyledUpdateButton";


function UpdateButton(props){

    return(
        <StyledUpdateButton onClick={props.onClick}>
            Update {props.name}
        </StyledUpdateButton>
    );
}

export default UpdateButton;