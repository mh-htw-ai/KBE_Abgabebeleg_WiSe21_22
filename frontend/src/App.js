
import UserBar from "./components/mainContainer/userBar/UserBar";
import {StyledMainContainer} from "./components/styles/StyledMainContainer";
import UserDataContextProvider from "./components/UserDataContext";
import DetailsContainer from "./components/mainContainer/detailsContainer/userDetailsContainer/DetailsContainer";
import MovieDataContextProvider from "./components/MovieDataContext";

function App() {
  return (
      <UserDataContextProvider>
          <StyledMainContainer>
              <UserBar/>
              <MovieDataContextProvider>
                  <DetailsContainer/>
              </MovieDataContextProvider>
          </StyledMainContainer>
      </UserDataContextProvider>
  );
}

export default App;
