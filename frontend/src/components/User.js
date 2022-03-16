

function User(props){

    return(
        <div>
            <h2>{props.data.username}</h2>
            <h4>{props.data.firstname} {props.data.lastname}</h4>
        </div>
    );
}

export default User;