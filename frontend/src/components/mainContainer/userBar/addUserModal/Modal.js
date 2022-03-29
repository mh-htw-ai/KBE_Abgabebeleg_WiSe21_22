

import ReactModal from "react-modal";
import {StyledDialogInputContainer} from "../../../styles/userBarStyles/modalStyles/StyledDialogInputContainer";
import {StyledDialogInputPair} from "../../../styles/userBarStyles/modalStyles/StyledDialogInputPair";
import {StyledDialogInputRow} from "../../../styles/userBarStyles/modalStyles/StyledDialogInputRow";
import '../../../styles/userBarStyles/modalStyles/ReactModal.css'
import {StyledDialogInput} from "../../../styles/userBarStyles/modalStyles/StyledDialogInput";
import {StyledDialogButtonRow} from "../../../styles/userBarStyles/modalStyles/StyledDialogButtonRow";
import {StyledDialogTitleRow} from "../../../styles/userBarStyles/modalStyles/StyledDialogTitleRow";
import {useContext, useState} from "react";
import {StyledSubmitButton} from "../../../styles/userBarStyles/modalStyles/StyledSubmitButton";
import {StyledCancelButton} from "../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {UserDataContext} from "../../../UserDataContext";

function Modal(props){

    const [username, setUsername] = useState();
    const [firstname, setFirstname] = useState();
    const [lastname, setLastname] = useState();
    const [email, setEmail] = useState();
    const [street, setStreet] = useState();
    const [streetnumber, setStreetnumber] = useState();
    const [postcode, setPostcode] = useState();
    const [placeOfResidence, setPlaceOfResidence] = useState();
    const {addNewUser} = useContext(UserDataContext);

    function getUsernameInputValue(event){
        setUsername(event.target.value);
    }

    function getFirstnameInputValue(event){
        setFirstname(event.target.value);
    }

    function getLastnameInputValue(event){
        setLastname(event.target.value);
    }

    function getEmailInputValue(event){
        setEmail(event.target.value);
    }

    function getStreetInputValue(event){
        setStreet(event.target.value);
    }

    function getStreetnumberInputValue(event){
        setStreetnumber(event.target.value);
    }

    function getPostcodeInputValue(event){
        setPostcode(event.target.value);
    }

    function getPlaceOfResidenceInputValue(event){
        setPlaceOfResidence(event.target.value);
    }

    let userJson = {
        username: username,
        firstname: firstname,
        lastname: lastname,
        email: email,
        street: street,
        street_number: streetnumber,
        postcode: postcode,
        placeOfResidence: placeOfResidence
    };

    function submit(){
        if(validateFields()) {
            addNewUser(userJson);
            props.hide();
        }
    }

    function validateFields(){
        return true;
    }
    return(
        <ReactModal className="Add-User-Modal" overlayClassName="Overlay" isOpen={props.isOpen}>
                <StyledDialogTitleRow>
                    Enter New User Data:
                </StyledDialogTitleRow>
                <StyledDialogInputContainer>
                    <StyledDialogInputRow>
                        <StyledDialogInputPair>
                            Username:
                            <StyledDialogInput onChange={getUsernameInputValue}/>
                        </StyledDialogInputPair>
                    </StyledDialogInputRow>
                    <StyledDialogInputRow>
                        <StyledDialogInputPair>
                            Firstname:
                            <StyledDialogInput onChange={getFirstnameInputValue}/>
                        </StyledDialogInputPair>
                        <StyledDialogInputPair>
                            Lastname:
                            <StyledDialogInput onChange={getLastnameInputValue}/>
                        </StyledDialogInputPair>
                    </StyledDialogInputRow>
                    <StyledDialogInputRow>
                        <StyledDialogInputPair>
                            Email:
                            <StyledDialogInput onChange={getEmailInputValue}/>
                        </StyledDialogInputPair>
                    </StyledDialogInputRow>
                    <StyledDialogInputRow>
                        <StyledDialogInputPair>
                            Street:
                            <StyledDialogInput onChange={getStreetInputValue}/>
                        </StyledDialogInputPair>
                        <StyledDialogInputPair>
                            Streetnumber:
                            <StyledDialogInput onChange={getStreetnumberInputValue}/>
                        </StyledDialogInputPair>
                    </StyledDialogInputRow>
                    <StyledDialogInputRow>
                        <StyledDialogInputPair>
                            Place of Residence:
                            <StyledDialogInput onChange={getPlaceOfResidenceInputValue}/>
                        </StyledDialogInputPair>
                        <StyledDialogInputPair>
                            Postcode:
                            <StyledDialogInput onChange={getPostcodeInputValue}/>
                        </StyledDialogInputPair>
                    </StyledDialogInputRow>
                </StyledDialogInputContainer>
                <StyledDialogButtonRow>
                    <StyledSubmitButton onClick={submit}>Submit</StyledSubmitButton>
                    <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
                </StyledDialogButtonRow>
        </ReactModal>
    );
}

export default Modal;