import {useContext, useEffect, useState} from "react";
import {StyledAddButton} from "../../../styles/StyledAddButton";
import {StyledCancelButton} from "../../../styles/userBarStyles/modalStyles/StyledCancelButton";
import {UserDataContext} from "../../../UserDataContext";
import axios from "axios";


function MovieRenting(props){

    const postNewRentingURL = "/main/api/v1.0/rentings/create";
    const deleteRentingURL = "/main/api/v1.0/rentings/delete/";
    const {activeUser} = useContext(UserDataContext);
    const [movieId, setMovieId] = useState(props.movieId);
    const [rentingDate, setRentingDate] = useState(props.rentingDate);
    const [rentingId, setRentingId] = useState(props.id);

    useEffect(()=>{
        setRentingDate(null);
        if(props.rentingDate != null){
            setRentingDate(new Date(props.rentingDate));
            setRentingId(props.id);
        }
    },[props])

    function createRenting(){
        let rentingObj = {
            movieId: movieId,
            renterId: activeUser.id
        };
        const rentingJSON = JSON.stringify(rentingObj);
        axios
            .post(postNewRentingURL,rentingJSON,{
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then((response) => {
                if(response.status < 300){
                    setRentingId(response.data);
                    setRentingDate(new Date());
                }
            })
            .catch(error => console.log(error));
    }

    function deleteRenting(){
        const finalURL = deleteRentingURL+rentingId;
        axios
            .delete(finalURL)
            .then((response) => {
                if(response.status < 300){
                    setRentingDate(null);
                }
            })
            .catch(error => console.log(error));
    }

    let content = (
        <div>
            Currently not Rented!
            <StyledAddButton onClick={createRenting}>
                Rent Now
            </StyledAddButton>
        </div>
    );

    if(rentingDate !=  null){
        content = (
            <div>
                <>Rented since:</>
                <>{String(rentingDate)}</>
                <StyledCancelButton onClick={deleteRenting}>
                    Stop Renting
                </StyledCancelButton>
            </div>
        );
    }

    return(
        content
    );
}

export default MovieRenting;