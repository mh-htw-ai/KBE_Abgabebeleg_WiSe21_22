import {useContext, useEffect, useState} from "react";
import {Rating} from "react-simple-star-rating";
import {UserDataContext} from "../../../UserDataContext";
import {StyledUpdateButton} from "../../../styles/StyledUpdateButton";
import {StyledMovieRating} from "../../../styles/detailsContainerStyles/moviesContainerStyles/StyledMovieRating";
import RatingModal from "./RatingModal";
import axios from "axios";


function MovieRating(props){

    const postNewRatingURL = "/main/api/v1.0/ratings/create";
    const putNewRatingURL = "/main/api/v1.0/ratings/update/";
    const {activeUser, postNewRating, putUpdatedRating} = useContext(UserDataContext);
    const[movieId, setMovieId] = useState(props.movieId);
    const[ratingId, setRatingId] = useState(props.ratingId);
    const[initialRating, setInitialRating] = useState()
    const[rating, setRating] = useState(props.rating);
    const[showRatingDialog, setShowDialog] = useState(false);

    useEffect(()=>{
        setRating(0);
        setInitialRating(0);
        setInitialRating(props.rating);
        setRatingId(props.ratingId);
    }, [props]);

    function changeRatingValue(newRatingValue){
        if(newRatingValue!==rating && newRatingValue<=5 && newRatingValue>=0) {
            if (newRatingValue == 0) {
                let newRatingObj = {
                    movieId: movieId,
                    ownerId: activeUser.id,
                    rating: newRatingValue
                }
                let newRatingJson = JSON.stringify(newRatingObj);
                axios
                    .post(postNewRatingURL, newRatingJson, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                    .then((response) => {
                        if(response.status < 300){
                            setInitialRating(newRatingValue);
                        }
                    })
                    .catch(error => console.log(error));
            } else {
                let finalURL = putNewRatingURL+ratingId;
                axios
                    .put(finalURL, newRatingValue, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                    .then((response) => {
                        if(response.status < 300){
                            setInitialRating(newRatingValue);
                        }
                    })
                    .catch(error => console.log(error));
            }
        }
    }

    function hide(){
        setShowDialog(false);
    }

    function show(){
        setShowDialog(true);
    }

    return(
        <StyledMovieRating>
            <Rating readonly={true} ratingValue={rating} initialValue={initialRating}/>
            <StyledUpdateButton onClick={show}>Change Rating</StyledUpdateButton>
            <RatingModal isOpen={showRatingDialog} hide={hide} sumbitFunction={changeRatingValue}/>
        </StyledMovieRating>
    );
}

export default MovieRating;