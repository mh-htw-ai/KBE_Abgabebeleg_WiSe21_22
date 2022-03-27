
import UpdateButton from "../../UpdateButton";
import DeleteButton from "../../DeleteButton";
import {StyledDetailedUserButtonRow} from "../../../styles/detailsContainerStyles/userDetailsRowStyles/StyledDetailedUserButtonRow";
import {useState} from "react";
import DeleteModal from "./DeleteModal";
import UpdateModal from "./UpdateModal";

function DetailedUserButtonRow(){

    const [showUpdateUserDialog, setShowUpdateUserDialog] = useState(false);
    const [showDeleteUserDialog, setShowDeleteUserDialog] = useState(false);

    function hideUpdateDialog(){
        setShowUpdateUserDialog(false);
    }

    function showUpdateDialog(){
        setShowUpdateUserDialog(true);
    }

    function hideDeleteDialog(){
        setShowDeleteUserDialog(false);
    }

    function showDeleteDialog(){
        setShowDeleteUserDialog(true);
    }

    return(
        <StyledDetailedUserButtonRow>
            <UpdateButton name="User" onClick={showUpdateDialog}/>
            <UpdateModal isOpen={showUpdateUserDialog} hide={hideUpdateDialog}/>
            <DeleteButton name="User" onClick={showDeleteDialog}/>
            <DeleteModal isOpen={showDeleteUserDialog} hide={hideDeleteDialog}/>
        </StyledDetailedUserButtonRow>
    );
}

export default DetailedUserButtonRow;