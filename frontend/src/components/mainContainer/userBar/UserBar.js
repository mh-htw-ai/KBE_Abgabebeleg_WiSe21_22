
import {useContext, useEffect, useState} from "react";
import User from "./User";
import AddButton from "../AddButton";
import {StyledUserBar} from "../../styles/userBarStyles/StyledUserBar";
import Modal from "./addUserModal/Modal";
import {UserDataContext} from "../../UserDataContext";
import '../../styles/userBarStyles/list.css'

function UserBar(){

    const {users} = useContext(UserDataContext);
    const [showAddUserDialog, setShowAddUserDialog] = useState(false);
    let content = (
        <ul className="userList">
            {users.map((user) => (
                <li key={user.id}>
                    <User user = {user}/>
                </li>))}
        </ul>
    );

    useEffect(()=>{
        content = (
            <ul className="userList">
                {users.map((user) => (
                    <li key={user.id}>
                        <User user = {user}/>
                    </li>))}
            </ul>
        );
    },[users]);

    function show(){
        setShowAddUserDialog(true);
    }

    function hide(){
        setShowAddUserDialog(false);
    }

    return(
        <StyledUserBar>
            {content}
            <AddButton name="User" onClick={show}/>
            <Modal isOpen={showAddUserDialog} hide={hide}/>
        </StyledUserBar>
    );
}

export default UserBar;