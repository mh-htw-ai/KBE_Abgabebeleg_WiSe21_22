
import React, {useEffect, useState} from "react";
import axios from "axios";

export const UserDataContext = React.createContext();

const UserDataContextProvider = ({children}) => {

    const getAllUsersURL = "/main/api/v1.0/users";
    const postNewUserURL = "/main/api/v1.0/users/create";
    const putUpdatedUserURL = "/main/api/v1.0/users/update";
    const deleteUserURL = "/main/api/v1.0/users/delete/";

    const getRatingsOfUserURL = "/main/api/v1.0/ratings/of_user/";

    const getRentingsOfUserURL = "/main/api/v1.0/rentings/of_user/";

    const [users, setUsers] = useState([]);
    const [activeUser, setActiveUser] = useState(null);
    const [userRatings, setUserRatings] = useState([]);
    const [userRentings, setUserRentings] = useState([]);

    const value = {
        users,
        activeUser,
        setActiveUser,
        changeActiveUser,
        getAllUsers,
        addNewUser,
        updateActiveUser,
        removeActiveUser,
        userRatings,
        userRentings
    }

    useEffect(
        getAllUsers ,[]);

    function getAllUsers(){
        setUsers([]);
        axios
            .get(getAllUsersURL)
            .then(response => setUsers(response.data))
            .catch(error => console.log(error));
    }

    function addNewUser(newUserObj){
        const newUserJson = JSON.stringify(newUserObj);
        axios
            .post(postNewUserURL, newUserJson, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .catch(error => console.log(error.toJSON));
        getAllUsers();
    }

    function updateActiveUser(updatedUserObj){
        const updatedUserJson = JSON.stringify(updatedUserObj);
        axios
            .put(putUpdatedUserURL, updatedUserJson, {
                headers:{
                    'Content-Type': 'application/json'
                }
            })
            .catch(error => console.log(error.toJSON));
    }

    function removeActiveUser(){
        let finalURL = deleteUserURL + activeUser.id;
        axios
            .delete(finalURL)
            .catch(error=> console.log(error.toJSON));
        let userArray = users;
        userArray = userArray.filter((user) => user.id !== activeUser.id)
        setUsers(userArray);
        changeActiveUser(null);
    }

    function changeActiveUser(newActiveUser){
        if(activeUser !== newActiveUser){
            setActiveUser(newActiveUser);
            if(newActiveUser !== null){
                getAllRatingsOfActiveUser(newActiveUser);
                getAllRentingsOfActiveUser(newActiveUser);
            }
            setUserRentings([]);
            setUserRatings([]);
        }
    }

    function getAllRatingsOfActiveUser(currentActiveUser){
        if(currentActiveUser !== null){
            let finalURL = getRatingsOfUserURL+currentActiveUser.id;
            axios
                .get(finalURL)
                .then(response => setUserRatings(response.data))
                .catch(error => console.log(error))
        }else{
            setUserRatings([]);
        }
    }

    function getAllRentingsOfActiveUser(currentActiveUser){
        if(currentActiveUser != null) {
            let finalURL = getRentingsOfUserURL + currentActiveUser.id;
            axios
                .get(finalURL)
                .then(response => setUserRentings(response.data))
                .catch(error => console.log(error))
        }else{
            setUserRentings([]);
        }
    }


    return(
        <UserDataContext.Provider value={value}>
            {children}
        </UserDataContext.Provider>
    );
}

export default UserDataContextProvider;