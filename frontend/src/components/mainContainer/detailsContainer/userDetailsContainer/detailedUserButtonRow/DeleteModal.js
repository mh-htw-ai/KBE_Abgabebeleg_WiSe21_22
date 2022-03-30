import {StyledDialogTitleRow} from "../../../../styles/userBarStyles/modalStyles/StyledDialogTitleRow";
import {StyledDialogButtonRow} from "../../../../styles/userBarStyles/modalStyles/StyledDialogButtonRow";
import {StyledCancelButton} from "../../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {StyledDeleteButton} from "../../../../styles/StyledDeleteButton";
import {useContext, useState} from "react";
import ReactModal from "react-modal";
import {UserDataContext} from "../../../../UserDataContext";

function DeleteModal(props){

    const {activeUser, removeActiveUser} = useContext(UserDataContext);
    const [userToBeDeleted] = useState(activeUser);

    function deleteUser(){
        removeActiveUser();
        props.hide();
    }

    return(
        <ReactModal className="Delete-User-Modal" overlayClassName="Overlay" isOpen={props.isOpen}>
            <StyledDialogTitleRow>
                Do You Want To Delete User:
            </StyledDialogTitleRow>
            {userToBeDeleted.username}
            <StyledDialogButtonRow>
                <StyledDeleteButton onClick={deleteUser}>Delete</StyledDeleteButton>
                <StyledCancelButton onClick={props.hide}>Cancel</StyledCancelButton>
            </StyledDialogButtonRow>
        </ReactModal>
    );
}

export default DeleteModal;