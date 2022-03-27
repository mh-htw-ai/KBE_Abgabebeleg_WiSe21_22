import {DialogTitleRow} from "../../../styles/userBarStyles/modalStyles/DialogTitleRow";
import {DialogButtonRow} from "../../../styles/userBarStyles/modalStyles/DialogButtonRow";
import {StyledCancelButton} from "../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {StyledDeleteButton} from "../../../styles/StyledDeleteButton";
import {useContext, useState} from "react";
import ReactModal from "react-modal";
import {UserDataContext} from "../../../UserDataContext";

function DeleteModal(props){

    const {activeUser, removeActiveUser} = useContext(UserDataContext);
    const [userToBeDeleted, setUserToBeDeleted] = useState(activeUser);

    function deleteUser(){
        removeActiveUser();
        props.hide();
    }

    return(
        <ReactModal className="Delete-User-Modal" overlayClassName="Overlay" isOpen={props.isOpen}>
            <DialogTitleRow>
                Do You Want To Delete User:
            </DialogTitleRow>
            {userToBeDeleted.username}
            <DialogButtonRow>
                <StyledDeleteButton onClick={deleteUser}>Delete</StyledDeleteButton>
                <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
            </DialogButtonRow>
        </ReactModal>
    );
}

export default DeleteModal;