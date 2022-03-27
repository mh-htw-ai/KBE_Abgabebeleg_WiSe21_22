
import styled from "styled-components";

export const StyledAddButton = styled.button`
   
    margin-top: 15px;   
    
    background-color: #5cb85c;
    color: white;
    
    border-radius: 50px;
    border: 0px;    
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    
    cursor: pointer;

    font-size: 16px;
    font-weight: 700;
    padding: 10px 15px;

    &:hover{
        opacity: 0.9;
    }
    
    &:active{
        transform: scale(0.98);
    }
`