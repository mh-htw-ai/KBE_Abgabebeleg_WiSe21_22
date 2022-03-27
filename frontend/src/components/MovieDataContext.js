
import React, {useEffect, useState} from "react";
import axios from "axios";

export const MovieDataContext = React.createContext();

const MovieDataContextProvider = ({children}) => {

    const getAllMoviesURL = "/main/api/v1.0/movies";
    const [movies, setMovies] = useState([]);
    const [activeMovie, setActiveMovie] = useState(null);

    const value = {
        movies,
        activeMovie,
        setActiveMovie
    }

    useEffect(
        getAllMovies ,[]);

    function getAllMovies(){
        axios
            .get(getAllMoviesURL)
            .then(response => setMovies(response.data))
            .catch(error => console.log(error))
    }

    return(
        <MovieDataContext.Provider value={value}>
            {children}
        </MovieDataContext.Provider>
    );
}

export default MovieDataContextProvider;