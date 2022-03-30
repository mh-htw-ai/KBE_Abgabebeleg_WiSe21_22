
import MoviesContainer from "./moviesContainer/MoviesContainer";
import {StyledDetailsContainer} from "../../styles/detailsContainerStyles/StyledDetailsContainer";
import DetailedUser from "./userDetailsContainer/DetailedUser";
import {StyledEmptyLabelContainer} from "../../styles/detailsContainerStyles/userDetailsRowStyles/StyledEmptyLabelContainer";
import {useContext} from "react";
import {UserDataContext} from "../../UserDataContext";

function DetailsContainer(){

    const{activeUser} = useContext(UserDataContext);

    let content = (
        <StyledDetailsContainer>
            <StyledEmptyLabelContainer>Select a User!</StyledEmptyLabelContainer>
        </StyledDetailsContainer>
    );

    if(activeUser !== null){
        content = (
            <StyledDetailsContainer>
                <DetailedUser user={activeUser}/>
                <MoviesContainer/>
            </StyledDetailsContainer>
        );
    }

    return(
        content
    );
}

export default DetailsContainer;