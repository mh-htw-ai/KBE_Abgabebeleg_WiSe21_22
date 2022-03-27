
import ReactModal from "react-modal";
import {DialogButtonRow} from "../../../styles/userBarStyles/modalStyles/DialogButtonRow";
import {StyledAddButton} from "../../../styles/StyledAddButton";
import {StyledCancelButton} from "../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {StyledSubmitButton} from "../../../styles/userBarStyles/modalStyles/StyledSubmitButton";
import {DialogTitleRow} from "../../../styles/userBarStyles/modalStyles/DialogTitleRow";
import {useState} from "react";
import {DialogInput} from "../../../styles/userBarStyles/modalStyles/DialogInput";

function RatingModal(props){

    const[rating, setRating] = useState(props.currentRating);

    function getRatingInputValue(event){
        setRating(event.target.value);
    }

    function submitNewRating(){
        props.sumbitFunction(rating);
        props.hide();
    }

    return(
        <ReactModal  className="Rating-Modal" overlayClassName="Overlay" isOpen={props.isOpen}>
            <DialogTitleRow>Enter new Rating Value:</DialogTitleRow>
            <DialogInput type="text" onChange={getRatingInputValue}/>
            <DialogButtonRow>
                <StyledSubmitButton onClick={submitNewRating}>Submit</StyledSubmitButton>
                <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
            </DialogButtonRow>
        </ReactModal>
    );
}

export default RatingModal;