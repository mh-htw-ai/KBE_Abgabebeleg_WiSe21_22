import {useEffect, useState} from "react";
import {StyledNameDetails} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledNameDetails";


function NameDetails(props){

    const [username, setUsername] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [email, setEmail] = useState("");

    useEffect(()=>{
        if(props.user != null){
            setUsername(props.user.username);
            setFirstname(props.user.firstname);
            setLastname(props.user.lastname);
            setEmail(props.user.email);
        }
    }, [props]);

    return(
        <StyledNameDetails>
            <h2>Userdetails:</h2>
            <label>Username: {username}</label>
            <label>Firstname: {firstname}</label>
            <label>Lastname: {lastname}</label>
            <label>Email: {email}</label>
        </StyledNameDetails>
    );

}

export default NameDetails