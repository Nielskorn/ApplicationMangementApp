import axios from "axios";
import {FormEvent, useState} from "react";

export default function NewApplicationSite(){
   const [jobTitle,setJobTitle]=useState<string>("");
   const [jobDescription,setJobDescription]=useState<string>("");
   const [resume,setResume]=useState<string>("");
   const [coverLetter,setCoverLetter]=useState<string>("");
   //const [appliStatus,setAppliStatus]=useState<string>();
    function OnSubmit(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.post("/api/application",{jobTitle: jobTitle, jobDescription: jobDescription, resume:resume,
            coverLetter: coverLetter
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
                <button type={"submit"}>create Application</button>
                <button type={"reset"}>reset Form</button>
            </form>
        </div>
    )
}