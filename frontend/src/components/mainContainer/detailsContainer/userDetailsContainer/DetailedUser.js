
import { useEffect, useState} from "react";
import DetailedUserButtonRow from "./detailedUserButtonRow/DetailedUserButtonRow";
import {StyledDetailedUser} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledDetailedUser";
import NameDetails from "./NameDetails";
import AddressDetails from "./AddressDetails";

function DetailedUser(props){

    const [setId] = useState("");
    const [setUsername] = useState("");
    const [setFirstname] = useState("");
    const [setLastname] = useState("");
    const [setEmail] = useState("");
    const [setStreet] = useState("");
    const [setStreetnumber] = useState("");
    const [setPlaceOfResidence] = useState("");
    const [setPostcode] = useState("");

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