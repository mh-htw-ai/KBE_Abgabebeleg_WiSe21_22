
import {useEffect, useState} from "react";
import {StyledMovie} from "../../../styles/detailsContainerStyles/moviesContainerStyles/StyledMovie";
import MovieRating from "./MovieRating";
import MovieRenting from "./MovieRenting";

function Movie(props){

    const [movie, setMovie] = useState(props.movie);
    const [ratingId, setRatingId] = useState(null);
    const [userRatingObjs, setUserRatingObjs] = useState(props.userRatingObjs);
    const [userRentingObjs, setUserRentingsObjs] = useState(props.userRentingObjs);
    const [ratingOfThis, setRatingOfThis] = useState(0);
    const [rentingDateOfThis, setRentingDateOfThis] = useState(null);
    const [rentingId, setRentingId] = useState(null);

    useEffect(()=>{
        setUserRatingObjs(props.userRatingObjs);
        setUserRentingsObjs(props.userRentingObjs);
    },[props])

    useEffect(() => {
        getRatingValue();
    }, [userRatingObjs]);

    useEffect(() => {
        getRentingDateValue();
    }, [userRentingObjs]);

    function getRatingValue(){
        if(userRatingObjs.length > 0) {
            let ratingOfThisObj = userRatingObjs.filter(ratingObj => ratingObj.movieId === movie.id);
            if(ratingOfThisObj.length > 0) {
                setRatingOfThis(ratingOfThisObj[0].rating);
                setRatingId(ratingOfThisObj[0].id);
            }else{
                setRatingOfThis(0);
            }
        }else{
            setRatingOfThis(0);
        }
    }

    function getRentingDateValue(){
        if(userRentingObjs.length > 0) {
            let rentingOfThisObj = userRentingObjs.filter(rentingObj => rentingObj.movieId === movie.id);
            if(rentingOfThisObj.length > 0) {
                setRentingDateOfThis(rentingOfThisObj[0].startOfRenting);
                console.log(rentingOfThisObj[0].id);
                setRentingId(rentingOfThisObj[0].id);
            }else{
                setRentingDateOfThis(null);
            }
        }else{
            setRentingDateOfThis(null);
        }
    }

    return(
        <StyledMovie>
            <h1>{movie.titel}</h1>
            <h4>{movie.kurzbeschreibung}</h4>
            <h4>{movie.leihPreis}</h4>
            <MovieRating movieId={movie.id} ratingId={ratingId} rating={ratingOfThis}/>
            <MovieRenting movieId={movie.id} id={rentingId} rentingDate={rentingDateOfThis}/>
        </StyledMovie>
    );
}

export default Movie;