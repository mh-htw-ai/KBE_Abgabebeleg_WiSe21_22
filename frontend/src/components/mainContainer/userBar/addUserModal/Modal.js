

import ReactModal from "react-modal";
import {DialogInputContainer} from "../../../styles/userBarStyles/modalStyles/DialogInputContainer";
import {DialogInputPair} from "../../../styles/userBarStyles/modalStyles/DialogInputPair";
import {DialogInputRow} from "../../../styles/userBarStyles/modalStyles/DialogInputRow";
import '../../../styles/userBarStyles/modalStyles/ReactModal.css'
import {DialogInput} from "../../../styles/userBarStyles/modalStyles/DialogInput";
import {DialogButtonRow} from "../../../styles/userBarStyles/modalStyles/DialogButtonRow";
import {DialogTitleRow} from "../../../styles/userBarStyles/modalStyles/DialogTitleRow";
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
                <DialogTitleRow>
                    Enter New User Data:
                </DialogTitleRow>
                <DialogInputContainer>
                    <DialogInputRow>
                        <DialogInputPair>
                            Username:
                            <DialogInput onChange={getUsernameInputValue}/>
                        </DialogInputPair>
                    </DialogInputRow>
                    <DialogInputRow>
                        <DialogInputPair>
                            Firstname:
                            <DialogInput onChange={getFirstnameInputValue}/>
                        </DialogInputPair>
                        <DialogInputPair>
                            Lastname:
                            <DialogInput onChange={getLastnameInputValue}/>
                        </DialogInputPair>
                    </DialogInputRow>
                    <DialogInputRow>
                        <DialogInputPair>
                            Email:
                            <DialogInput onChange={getEmailInputValue}/>
                        </DialogInputPair>
                    </DialogInputRow>
                    <DialogInputRow>
                        <DialogInputPair>
                            Street:
                            <DialogInput onChange={getStreetInputValue}/>
                        </DialogInputPair>
                        <DialogInputPair>
                            Streetnumber:
                            <DialogInput onChange={getStreetnumberInputValue}/>
                        </DialogInputPair>
                    </DialogInputRow>
                    <DialogInputRow>
                        <DialogInputPair>
                            Place of Residence:
                            <DialogInput onChange={getPlaceOfResidenceInputValue}/>
                        </DialogInputPair>
                        <DialogInputPair>
                            Postcode:
                            <DialogInput onChange={getPostcodeInputValue}/>
                        </DialogInputPair>
                    </DialogInputRow>
                </DialogInputContainer>
                <DialogButtonRow>
                    <StyledSubmitButton onClick={submit}>Submit</StyledSubmitButton>
                    <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
                </DialogButtonRow>
        </ReactModal>
    );
}

export default Modal;