
import MoviesContainer from "../moviesContainer/MoviesContainer";
import {StyledDetailsContainer} from "../../../styles/detailsContainerStyles/StyledDetailsContainer";
import DetailedUser from "./DetailedUser";

function DetailsContainer(){

    return(
        <StyledDetailsContainer>
            <DetailedUser/>
            <MoviesContainer/>
        </StyledDetailsContainer>
    );
}

export default DetailsContainer;