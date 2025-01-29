import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import {Application} from "../types/Application.ts";

export default function EditApplicationSite(){
    const {id}=useParams<{id:string}>();
    const [jobOfferID,setJobOfferID]=useState<string>("");

    const [resume,setResume]=useState<string>("");
    const [coverLetter,setCoverLetter]=useState<string>("");
    const [appliStatus,setAppliStatus]=useState<string>("");
    const [reminderTime,setReminderTime]=useState<string>("")

    useEffect(()=>{
        const fetchApplication=async ()=>{
            const response=await axios.get<Application>(`/api/application/${id}`);
            setJobOfferID(response.data.jobOfferID);
            setResume(response.data.resume)
            setCoverLetter(response.data.coverLetter)
            setAppliStatus(response.data.appliStatus)
            setReminderTime(response.data.dateOfCreation)

        };
        fetchApplication();
    },[id])
    function OnSubmit(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.put("/api/application",{id, jobOfferID:jobOfferID,resume:resume,
            coverLetter: coverLetter,appliStatus:appliStatus, reminderTime: reminderTime
        }).then().catch(error=>{console.log(error)})
        OnReset()
    }
    function OnReset(){
        setJobOfferID("")
        setCoverLetter("")
        setResume("")
        setAppliStatus("OPEN")
        setReminderTime("")
    }
    return(


        <div>
            <form className="addForm" onSubmit={OnSubmit} onReset={OnReset}>
                <label>Jobtitle:
                    <input type="text"
                           value={jobOfferID}
                           placeholder="JobOfferID"
                           className="formField"
                           onChange={event => {setJobOfferID(event.target.value)}}
                    />
                </label>

                <label>resume:
                    <input type="text"
                           value={resume}
                           placeholder="resume"
                           className="formField"
                           onChange={event => {setResume(event.target.value)}}
                    />
                </label>
                <label> coverLetter
                    <input type="text"
                           value={coverLetter}
                           placeholder="coverLetter"
                           className="formField"
                           onChange={event => {setCoverLetter(event.target.value)}}
                    />
                </label>
                <label> Status:
                    <select onChange={event => setAppliStatus(event.target.value)}>
                        <option value="OPEN">Open</option>
                        <option value="IN_PROGRESS">in Progress</option>
                        <option value="CLOSED">Close</option>
                        <option value="SUCCESS">success</option>
                    </select>
                </label>
                <label>ReminderTime:
                    <input type={"datetime-local"} value={reminderTime} onChange={event=> setReminderTime(event.target.value)}/>
                </label>
                <button type={"submit"}>edit Application</button>
                <button type={"reset"}>reset Form</button>
            </form>
        </div>
    )
}
