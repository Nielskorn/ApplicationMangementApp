import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {Application} from "../types/Application.ts";

export default function Applicationdetails(){
    const {id}=useParams<{id:string}>();
    const navigate=useNavigate()
    //const [data,SetData]=useState<Application>();
    const [jobTitle,setJobTitle]=useState<string>("");
    const [jobDescription,setJobDescription]=useState<string>("")
    useEffect(()=>{
        const fetchApplication=async ()=>{
            const response=await axios.get<Application>(`/api/application/${id}`);
            setJobTitle(response.data.jobTitle);
            setJobDescription(response.data.jobDescription)
                console.log(jobTitle)
        };
        fetchApplication();
    },[id])
function navigateToEditPage(){
        navigate("/editApplication/"+id)
}
    return(
        <div className="ApplicationDetails">
            <h1>applicationDetails</h1>
            <h2>{jobTitle}</h2>
            <p>{jobDescription}</p>
            <button onClick={navigateToEditPage}></button>
        </div>
    )
}