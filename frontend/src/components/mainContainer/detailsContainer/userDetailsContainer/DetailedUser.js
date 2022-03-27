
import {useContext, useEffect, useState} from "react";
import {UserDataContext} from "../../../UserDataContext";
import DetailedUserButtonRow from "./DetailedUserButtonRow";
import {StyledDetailedUser} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledDetailedUser";
import {StyledUserDetailsContainer} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledUserDetailsContainer";
import {StyledEmptyLabelContainer} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledEmptyLabelContainer";
import NameDetails from "./NameDetails";
import AddressDetails from "./AddressDetails";

function DetailedUser(props){

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
        if(props.user != null){
            setId(props.user.id);
            setUsername(props.user.username);
            setFirstname(props.user.firstname);
            setLastname(props.user.lastname);
            setEmail(props.user.email);
            setStreet(props.user.street);
            setStreetnumber(props.user.street_number);
            setPlaceOfResidence(props.user.placeOfResidence);
            setPostcode(props.user.postcode);
        }
    }, [props]);

    return(
        <StyledDetailedUser>
            <NameDetails user={props.user}/>
            <AddressDetails user={props.user}/>
            <DetailedUserButtonRow/>
        </StyledDetailedUser>
    );
}

export default DetailedUser;