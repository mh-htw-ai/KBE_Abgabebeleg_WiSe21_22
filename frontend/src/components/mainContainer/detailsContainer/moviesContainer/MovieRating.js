import {useContext, useEffect, useState} from "react";
import {Rating} from "react-simple-star-rating";
import {UserDataContext} from "../../../UserDataContext";
import {StyledUpdateButton} from "../../../styles/StyledUpdateButton";


function MovieRating(props){

    const {activeUser, postNewRating, putUpdatedRating} = useContext(UserDataContext);
    const[movieId, setMovieId] = useState(props.movieId);
    const[ratingId, setRatingId] = useState(props.ratingId);
    const[initialRating, setInitialRating] = useState()
    const[rating, setRating] = useState(props.rating);

    useEffect(()=>{
        setRating(0);
        setInitialRating(0);
        setInitialRating(props.rating);
        setRatingId(props.ratingId);
    }, [props]);

    return(
        <div>
            <Rating readonly={true} ratingValue={rating} initialValue={initialRating}/>
            <StyledUpdateButton >Change Rating</StyledUpdateButton>
        </div>
    );
}

export default MovieRating;