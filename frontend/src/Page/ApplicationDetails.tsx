import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import StatusIndicator from "../Component/StatusIndicator.tsx";
import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";




export default function Applicationdetails(){
    const {id}=useParams<{id:string}>();
    const navigate=useNavigate()

    const [companyName,setCompanyName]=useState<string>("");
    const [jobTitle,setJobTitle]=useState<string>("");
    const [resume,setResume]=useState<string>("");
    const [coverLetter, setCoverLetter]=useState<string>();
    const [status,setStatus]=useState<string>("");
    const [reminderTime,setReminderTime]=useState<string>()
    const [dateOfCreation,setDateOfCreation]=useState<string>();

    function fetchJobApplication(){
        axios.get<JobApplicationTracker>(`/api/JobApplication/`+id).then((response)=>{
            setResume(response.data.application.resume)
            setCoverLetter(response.data.application.coverLetter)
            setStatus(response.data.application.appliStatus)
            setReminderTime(response.data.application.reminderTime)
            setDateOfCreation(response.data.application.dateOfCreation)
            setCompanyName(response.data.jobOffer.companyName)
            setJobTitle (response.data.jobOffer.jobTitle)


        })
    }





    useEffect(()=>{

        fetchJobApplication();



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
            <h2>Company: {companyName}</h2>
            <p>JobTitle: {jobTitle}</p>
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