
import {useContext, useEffect, useState} from "react";
import {UserDataContext} from "../../../UserDataContext";
import DetailedUserButtonRow from "./DetailedUserButtonRow";
import {StyledDetailedUser} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledDetailedUser";
import {StyledUserDetailsContainer} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledUserDetailsContainer";
import {StyledEmptyLabelContainer} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledEmptyLabelContainer";
import NameDetails from "./NameDetails";
import AddressDetails from "./AddressDetails";

function DetailedUser(){

    const {activeUser} = useContext(UserDataContext);
    const [id, setId] = useState("");
    const [username, setUsername] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [email, setEmail] = useState("");
    const [street, setStreet] = useState("");
    const [streetnumber, setStreetnumber] = useState("");
    const [placeOfResidence, setPlaceOfResidence] = useState("");
    const [postcode, setPostcode] = useState("");

    useEffect(()=>{
        if(activeUser != null){
            setId(activeUser.id);
            setUsername(activeUser.username);
            setFirstname(activeUser.firstname);
            setLastname(activeUser.lastname);
            setEmail(activeUser.email);
            setStreet(activeUser.street);
            setStreetnumber(activeUser.street_number);
            setPlaceOfResidence(activeUser.placeOfResidence);
            setPostcode(activeUser.postcode);
        }
    }, [activeUser]);

    let content = (
        <StyledEmptyLabelContainer>Select a User!</StyledEmptyLabelContainer>
    );

    if(activeUser !=null){
        content = (
            <StyledDetailedUser>
                <NameDetails user={activeUser}/>
                <AddressDetails user={activeUser}/>
                <DetailedUserButtonRow/>
            </StyledDetailedUser>
        );
    }

    return(
        content
    );
}

export default DetailedUser;