import {StyledNameDetails} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledNameDetails";
import {useEffect, useState} from "react";

function AddressDetails(props){

    const [street,setStreet] = useState(props.user.street);
    const [streetnumber, setStreetnumber] = useState(props.user.street_number);
    const [placeOfResidence, setPlaceOfResidence] = useState(props.user.placeOfResidence);
    const [postcode, setPostcode] = useState(props.user.postcode);

    useEffect(()=>{
        if(props.user != null){
            setStreet(props.user.street);
            setStreetnumber(props.user.street_number);
            setPlaceOfResidence(props.user.placeOfResidence);
            setPostcode(props.user.postcode);
        }
    }, [props]);

    return(
        <StyledNameDetails>
            <h2>Addressdetails:</h2>
            <label>{street} {streetnumber}</label>
            <label>{postcode} {placeOfResidence}</label>
        </StyledNameDetails>
    );
}

export default AddressDetails;