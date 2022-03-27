
import {MovieDataContext} from "../../../MovieDataContext";
import {useContext, useEffect, useState} from "react";
import Movie from "./Movie";
import {StyledMoviesContainer} from "../../../styles/detailsContainerStyles/moviesContainerStyles/StyledMoviesContainer";
import '../../../styles/userBarStyles/list.css';
import {UserDataContext} from "../../../UserDataContext";

function MoviesContainer(){

    const {movies} = useContext(MovieDataContext);
    const {userRatings, userRentings} = useContext(UserDataContext);
    const [ratings,setRatings] = useState(userRatings);
    const [rentings, setRentings] = useState(userRentings);

    useEffect(() => {
        setRatings(userRatings);
    },[userRatings]);

    useEffect( () => {
        setRentings(userRentings);
    }, [userRentings]);

    return(
        <StyledMoviesContainer>
            <ul className="movieList">
                {movies.map((movie,key) => (
                    <li key={key}>
                        <Movie movie = {movie} userRatingObjs = {ratings} userRentingObjs = {rentings}/>
                    </li>
                ))}
            </ul>
        </StyledMoviesContainer>
    );
}

export default MoviesContainer;