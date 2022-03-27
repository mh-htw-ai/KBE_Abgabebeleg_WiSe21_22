
import styled from "styled-components";

export const StyledDeleteButton = styled.button`
    
    margin-top: 15px;   
    margin-left: 5px;
    margin-right: 5px;  
    
    background-color: #e83838;
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