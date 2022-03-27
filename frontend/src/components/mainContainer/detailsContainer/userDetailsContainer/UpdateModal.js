
import {useContext, useEffect, useState} from "react";
import {UserDataContext} from "../../../UserDataContext";
import ReactModal from "react-modal";
import {DialogTitleRow} from "../../../styles/userBarStyles/modalStyles/DialogTitleRow";
import {DialogInputContainer} from "../../../styles/userBarStyles/modalStyles/DialogInputContainer";
import {DialogInputRow} from "../../../styles/userBarStyles/modalStyles/DialogInputRow";
import {DialogInputPair} from "../../../styles/userBarStyles/modalStyles/DialogInputPair";
import {DialogInput} from "../../../styles/userBarStyles/modalStyles/DialogInput";
import {DialogButtonRow} from "../../../styles/userBarStyles/modalStyles/DialogButtonRow";
import {StyledCancelButton} from "../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {StyledUpdateButton} from "../../../styles/StyledUpdateButton";

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
            <DialogTitleRow>
                Enter New User Data:
            </DialogTitleRow>
            <DialogInputContainer>
                <DialogInputRow>
                    <DialogInputPair>
                        Username:
                        <DialogInput onChange={getUsernameInputValue} value={username} />
                    </DialogInputPair>
                </DialogInputRow>
                <DialogInputRow>
                    <DialogInputPair>
                        Firstname:
                        <DialogInput onChange={getFirstnameInputValue} value={firstname}/>
                    </DialogInputPair>
                    <DialogInputPair>
                        Lastname:
                        <DialogInput onChange={getLastnameInputValue} value={lastname}/>
                    </DialogInputPair>
                </DialogInputRow>
                <DialogInputRow>
                    <DialogInputPair>
                        Email:
                        <DialogInput onChange={getEmailInputValue} value={email}/>
                    </DialogInputPair>
                </DialogInputRow>
                <DialogInputRow>
                    <DialogInputPair>
                        Street:
                        <DialogInput onChange={getStreetInputValue} value={street}/>
                    </DialogInputPair>
                    <DialogInputPair>
                        Streetnumber:
                        <DialogInput onChange={getStreetnumberInputValue} value={streetnumber}/>
                    </DialogInputPair>
                </DialogInputRow>
                <DialogInputRow>
                    <DialogInputPair>
                        Place of Residence:
                        <DialogInput onChange={getPlaceOfResidenceInputValue} value={placeOfResidence}/>
                    </DialogInputPair>
                    <DialogInputPair>
                        Postcode:
                        <DialogInput onChange={getPostcodeInputValue} value={postcode}/>
                    </DialogInputPair>
                </DialogInputRow>
            </DialogInputContainer>
            <DialogButtonRow>
                <StyledUpdateButton onClick={update}>Update</StyledUpdateButton>
                <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
            </DialogButtonRow>
        </ReactModal>
    );
}

export default UpdateModal;