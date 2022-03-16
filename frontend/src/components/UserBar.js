
import {useEffect, useState} from "react";
import axios from "axios";
import User from "./User";
import AddButton from "./AddButton";

function UserBar(){
    const getURL = "/main/api/v1.0/users"
    const [users, setUsers] = useState([]);

    useEffect( () => {
        axios
            .get(getURL)
            .then(response => setUsers(response.data))
            .catch(error => console.log(error))
    },[]);

    return(
        <>
            <ul>
                {users.map((user,key) => (
                        <li key = {key}>
                            <User data = {user}/>
                        </li>
                ))}
            </ul>
            <AddButton name="User"/>
        </>
    );
}

export default UserBar;