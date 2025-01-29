import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {Application} from "../types/Application.ts";
import StatusIndicator from "../Component/StatusIndicator.tsx";


export default function Applicationdetails(){
    const {id}=useParams<{id:string}>();
    const navigate=useNavigate()
    //const [data,SetData]=useState<Application>();
    const [jobOfferID,setJobOfferID]=useState<string>("");

    const [resume,setResume]=useState<string>("");
    const [coverLetter, setCoverLetter]=useState<string>();
    const [status,setStatus]=useState<string>("");
    const [reminderTime,setReminderTime]=useState<string>()
    const [dateOfCreation,setDateOfCreation]=useState<string>();
    useEffect(()=>{
        const fetchApplication=async ()=>{
            const response=await axios.get<Application>(`/api/application/${id}`);
            setJobOfferID(response.data.jobOfferID);

            setResume(response.data.resume)
            setCoverLetter(response.data.coverLetter)
            setStatus(response.data.appliStatus)
            setReminderTime(response.data.reminderTime)
            setDateOfCreation(response.data.dateOfCreation)

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
            <h2>{jobOfferID}</h2>
            <p>{resume}</p>
            <p>{coverLetter}</p>
            <StatusIndicator status={status}/>
            <p>{reminderTime} </p>
            <p>Created:{dateOfCreation}</p>

            <button onClick={navigateToEditPage}>Edit</button>
            <button onClick={deleteEntry}>Delete</button>
        </div>
    )
}