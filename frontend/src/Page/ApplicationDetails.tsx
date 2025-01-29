import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {Application} from "../types/Application.ts";
import StatusIndicator from "../Component/StatusIndicator.tsx";

export default function Applicationdetails(){
    const {id}=useParams<{id:string}>();
    const navigate=useNavigate()
    //const [data,SetData]=useState<Application>();
    const [jobTitle,setJobTitle]=useState<string>("");
    const [jobDescription,setJobDescription]=useState<string>("");
    const [resume,setResume]=useState<string>("");
    const [coverLetter,setCoverletter]=useState<string>();
    const [status,setStatus]=useState<string>("");
    useEffect(()=>{
        const fetchApplication=async ()=>{
            const response=await axios.get<Application>(`/api/application/${id}`);
            setJobTitle(response.data.jobTitle);
            setJobDescription(response.data.jobDescription)
            setResume(response.data.resume)
            setCoverletter(response.data.coverLetter)
            setStatus(response.data.appliStatus)
                console.log(jobTitle)
        };
        fetchApplication();
    },[id])
function navigateToEditPage(){
        navigate("/editApplication/"+id)
}
function deleteEntry() {
   try{
    axios.delete(`/api/application/${id}`)
    alert("application Gelöscht")
    navigate("/");
}catch(error){
       console.error("fehler Beim löschen:", error);
   }
    }


    return(
        <div className="ApplicationDetails">
            <h1>applicationDetails</h1>
            <h2>{jobTitle}</h2>
            <p>{jobDescription}</p>
            <p>{resume}</p>
            <p>{coverLetter}</p>
            <StatusIndicator status={status}/>
            <button onClick={navigateToEditPage}>Edit</button>
            <button onClick={deleteEntry}>Delete</button>
        </div>
    )
}