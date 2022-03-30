
import {useContext, useEffect, useState} from "react";
import {UserDataContext} from "../../../../UserDataContext";
import ReactModal from "react-modal";
import {StyledDialogTitleRow} from "../../../../styles/userBarStyles/modalStyles/StyledDialogTitleRow";
import {StyledDialogInputContainer} from "../../../../styles/userBarStyles/modalStyles/StyledDialogInputContainer";
import {StyledDialogInputRow} from "../../../../styles/userBarStyles/modalStyles/StyledDialogInputRow";
import {StyledDialogInputPair} from "../../../../styles/userBarStyles/modalStyles/StyledDialogInputPair";
import {StyledDialogInput} from "../../../../styles/userBarStyles/modalStyles/StyledDialogInput";
import {StyledDialogButtonRow} from "../../../../styles/userBarStyles/modalStyles/StyledDialogButtonRow";
import {StyledCancelButton} from "../../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {StyledUpdateUserButton} from "../../../../styles/StyledUpdateUserButton";

function UpdateModal(props){

    const {activeUser, setActiveUser, getAllUsers, updateActiveUser} = useContext(UserDataContext);
    const [username, setUsername] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [email, setEmail] = useState("");
    const [street, setStreet] = useState("");
    const [streetnumber, setStreetnumber] = useState("");
    const [postcode, setPostcode] = useState("");
    const [placeOfResidence, setPlaceOfResidence] = useState("");

    useEffect(()=>{
        setUsername(activeUser.username);
        setFirstname(activeUser.firstname);
        setLastname(activeUser.lastname);
        setEmail(activeUser.email);
        setStreet(activeUser.street);
        setStreetnumber(activeUser.street_number);
        setPlaceOfResidence(activeUser.placeOfResidence);
        setPostcode(activeUser.postcode);
    },[activeUser]);

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

    let userObj = {
        id: activeUser.id,
        username: username,
        firstname: firstname,
        lastname: lastname,
        email: email,
        street: street,
        street_number: streetnumber,
        postcode: postcode,
        placeOfResidence: placeOfResidence
    };

    function update(){
        if(validateFields()) {
            updateActiveUser(userObj);
            props.hide();
            setActiveUser(null);
            getAllUsers();
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
                        <StyledDialogInput onChange={getUsernameInputValue} value={username} />
                    </StyledDialogInputPair>
                </StyledDialogInputRow>
                <StyledDialogInputRow>
                    <StyledDialogInputPair>
                        Firstname:
                        <StyledDialogInput onChange={getFirstnameInputValue} value={firstname}/>
                    </StyledDialogInputPair>
                    <StyledDialogInputPair>
                        Lastname:
                        <StyledDialogInput onChange={getLastnameInputValue} value={lastname}/>
                    </StyledDialogInputPair>
                </StyledDialogInputRow>
                <StyledDialogInputRow>
                    <StyledDialogInputPair>
                        Email:
                        <StyledDialogInput onChange={getEmailInputValue} value={email}/>
                    </StyledDialogInputPair>
                </StyledDialogInputRow>
                <StyledDialogInputRow>
                    <StyledDialogInputPair>
                        Street:
                        <StyledDialogInput onChange={getStreetInputValue} value={street}/>
                    </StyledDialogInputPair>
                    <StyledDialogInputPair>
                        Streetnumber:
                        <StyledDialogInput onChange={getStreetnumberInputValue} value={streetnumber}/>
                    </StyledDialogInputPair>
                </StyledDialogInputRow>
                <StyledDialogInputRow>
                    <StyledDialogInputPair>
                        Place of Residence:
                        <StyledDialogInput onChange={getPlaceOfResidenceInputValue} value={placeOfResidence}/>
                    </StyledDialogInputPair>
                    <StyledDialogInputPair>
                        Postcode:
                        <StyledDialogInput onChange={getPostcodeInputValue} value={postcode}/>
                    </StyledDialogInputPair>
                </StyledDialogInputRow>
            </StyledDialogInputContainer>
            <StyledDialogButtonRow>
                <StyledUpdateUserButton onClick={update}>Update</StyledUpdateUserButton>
                <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
            </StyledDialogButtonRow>
        </ReactModal>
    );
}

export default UpdateModal;