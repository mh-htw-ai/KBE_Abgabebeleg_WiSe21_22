
import ReactModal from "react-modal";
import {StyledDialogButtonRow} from "../../../styles/userBarStyles/modalStyles/StyledDialogButtonRow";
import {StyledCancelButton} from "../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {StyledSubmitButton} from "../../../styles/userBarStyles/modalStyles/StyledSubmitButton";
import {StyledDialogTitleRow} from "../../../styles/userBarStyles/modalStyles/StyledDialogTitleRow";
import {useState} from "react";
import {StyledDialogRatingInput} from "../../../styles/detailsContainerStyles/moviesContainerStyles/StyledDialogRatingInput";

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
            <StyledDialogTitleRow>Enter new Rating Value:</StyledDialogTitleRow>
            <StyledDialogRatingInput type="text" onChange={getRatingInputValue}/>
            <StyledDialogButtonRow>
                <StyledSubmitButton onClick={submitNewRating}>Submit</StyledSubmitButton>
                <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
            </StyledDialogButtonRow>
        </ReactModal>
    );
}

export default RatingModal;