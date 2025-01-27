import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import {Application} from "../types/Application.ts";

export default function EditApplicationSite(){
    const {id}=useParams<{id:string}>();
    const [jobTitle,setJobTitle]=useState<string>("");
    const [jobDescription,setJobDescription]=useState<string>("");
    const [resume,setResume]=useState<string>("");
    const [coverLetter,setCoverLetter]=useState<string>("");
    const [appliStatus,setAppliStatus]=useState<string>("");
    useEffect(()=>{
        const fetchApplication=async ()=>{
            const response=await axios.get<Application>(`/api/application/${id}`);
            setJobTitle(response.data.jobTitle);
            setJobDescription(response.data.jobDescription)
            setResume(response.data.resume)
            setCoverLetter(response.data.coverLetter)
            setAppliStatus(response.data.appliStatus)
            console.log(jobTitle)
        };
        fetchApplication();
    },[id])
    function OnSubmit(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.put("/api/application",{id,jobTitle: jobTitle, jobDescription: jobDescription, resume:resume,
            coverLetter: coverLetter,appliStatus:appliStatus
        }).then().catch(error=>{console.log(error)})
        OnReset()
    }
    function OnReset(){
        setJobTitle("")
        setJobDescription("")
        setCoverLetter("")
        setResume("")
    }
    return(


        <div>
            <form className="addForm" onSubmit={OnSubmit} onReset={OnReset}>
                <label>Jobtitle:
                    <input type="text"
                           value={jobTitle}
                           placeholder="Job Title"
                           className="formField"
                           onChange={event => {setJobTitle(event.target.value)}}
                    />
                </label>
                <label>JobDescription
                    <input type="text"
                           value={jobDescription}
                           placeholder="Job Description"
                           className="formField"
                           onChange={event => {setJobDescription(event.target.value)}}
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
                <button type={"submit"}>edit Application</button>
                <button type={"reset"}>reset Form</button>
            </form>
        </div>
    )
}
