
import {StyledUser} from "../../styles/userBarStyles/StyledUser";
import {useContext, useState} from "react";
import {UserDataContext} from "../../UserDataContext";

function User(props){

    const {changeActiveUser} = useContext(UserDataContext);
    const [user, setUser] = useState(props.user);

    function handleClick(){
        changeActiveUser(user);
    }

    return(
        <StyledUser onClick={handleClick}>
            <h2>{user.username}</h2>
            <h4>{user.firstname} {user.lastname}</h4>
        </StyledUser>
    );
}

export default User;